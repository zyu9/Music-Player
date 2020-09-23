import java.io.*;
import javax.swing.*; 
import javax.sound.sampled.*; 
/**
 * Write a description of class Audio here.
 *
 * @author (zyu9)
 * @version (9/13/2020;9/21/2020)
 */
public class Audio
{
    private static final int SECONDS_IN_HOUR = 60 * 60;
    private static final int SECONDS_IN_MINUTE = 60;
    //store current position
    Long current;
    Clip clip;
    
    //current status of clip
    boolean playCompleted;
    boolean isStopped;
    boolean isPaused; 
    
    //audio and input
    AudioInputStream audioInputStream;
    AudioFormat format; 
    DataLine.Info dataline; 
    File file;
    JFileChooser jfilechooser; 
    public String audioFilePath;
    public String lastOpenPath; 
    
    public void openFile() {
       try{
        jfilechooser = new JFileChooser();
        if (lastOpenPath != null && !lastOpenPath.equals("")) {
		jfilechooser = new JFileChooser(lastOpenPath);
	} else {
		jfilechooser = new JFileChooser();
	}
        jfilechooser.showOpenDialog(null);
        file = jfilechooser.getSelectedFile();
        audioFilePath = jfilechooser.getSelectedFile().getAbsolutePath();
	lastOpenPath = jfilechooser.getSelectedFile().getParent();
        
        audioInputStream = AudioSystem.getAudioInputStream(file);
        
        format = audioInputStream.getFormat(); 
        
        dataline = new DataLine.Info(Clip.class, format);
        
        clip = (Clip) AudioSystem.getLine(dataline);
        
        clip = AudioSystem.getClip();
        
        clip.open(audioInputStream);  
       }catch(Exception ex){
         ex.getMessage();
       }
    }
    
    public String getAudioFilePath(){
        return audioFilePath; 
    }
    
    public long getClipSecondLength() {
	return clip.getMicrosecondLength() / 1_000_000;
    }
    
    public String getClipLengthString() {
	String length = "";
	long hour = 0;
	long minute = 0;
	long seconds = clip.getMicrosecondLength() / 1_000_000;
		
	System.out.println(seconds);
		
	if (seconds >= SECONDS_IN_HOUR) {
	   hour = seconds / SECONDS_IN_HOUR;
	   length = String.format("%02d:", hour);
	} else {
	   length += "00:";
	}
		
	minute = seconds - hour * SECONDS_IN_HOUR;
	if (minute >= SECONDS_IN_MINUTE) {
	   minute = minute / SECONDS_IN_MINUTE;
	   length += String.format("%02d:", minute);		
	} else {
	   minute = 0;
	   length += "00:";
	}
		
	long second = seconds - hour * SECONDS_IN_HOUR - minute * SECONDS_IN_MINUTE;
		
	length += String.format("%02d", second);
		
	return length;
    }
    
    public void play() 
    { 
        clip.start(); 
        
        playCompleted = false;
        isStopped = false; 
        
        while(!playCompleted){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                ex.printStackTrace();
                
                if(isStopped){
                    clip.stop();
                    break; 
                }
                
                if(isPaused){
                    clip.stop(); 
                }else{
                    clip.start(); 
                }
            }
        }
        
        clip.close(); 
    } 
    
    public void pause() 
    { 
        isPaused = true; 
    } 
      
    public void resume()                       
    { 
       isPaused = false; 
    } 
       
    public void stop() 
    { 
       isStopped = true; 
    } 
    
    // Method to restart the audio 
    public void restart()                                         
    { 
       try{
         clip.stop(); 
         clip.close(); 
         resetAudioStream(); 
         current = 0L; 
         clip.setMicrosecondPosition(0); 
         this.play(); 
       }catch(Exception ex){
           ex.getMessage(); 
       }
    } 
    
    // Method to jump over a specific part 
    public void jump(long c)                                          
    { 
        try{
          if (c > 0 && c < clip.getMicrosecondLength())  
          { 
            clip.stop(); 
            clip.close(); 
            resetAudioStream(); 
            current = c; 
            clip.setMicrosecondPosition(c); 
            this.play(); 
          } 
      }catch(Exception ex){
         ex.getMessage(); 
      }
    } 
      
    public void resetAudioStream()                                 
    { 
       try{
        audioInputStream = AudioSystem.getAudioInputStream( 
        file.getAbsoluteFile()); 
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
      }catch(Exception ex){
         ex.getMessage(); 
      }
    } 
    
    //notice play completed
    public void update(LineEvent event) {
	LineEvent.Type type = event.getType();
	if (type == LineEvent.Type.STOP) {
	    System.out.println("STOP");
	    if (isStopped || !isPaused) {
		playCompleted = true;
	    }
	}
    }
    
    //Method to exit
    public void exit(){
        System.exit(0);
    }
    
    public Clip getClip(){
        return clip; 
    }
}
