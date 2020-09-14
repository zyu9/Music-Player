import java.io.*;
import javax.swing.*; 
import javax.sound.sampled.*; 

/**
 * Write a description of class Audio here.
 *
 * @author (zyu9)
 * @version (9/13/2020)
 */
public class Audio
{
    //store current position
    Long current;
    Clip clip;
    
    //current status of clip
    String status; 
    
    //audio and input
    AudioInputStream audioInputStream;
    AudioFormat format; 
    DataLine.Info dataline; 
    File file;
    JFileChooser jfilechooser; 
    
    public void openFile() {
        try{
        jfilechooser = new JFileChooser();
        jfilechooser.showOpenDialog(null);
        file = jfilechooser.getSelectedFile();
        
        audioInputStream = AudioSystem.getAudioInputStream(file);
        format = audioInputStream.getFormat(); 
        dataline = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(dataline);
        
        clip = AudioSystem.getClip();
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
       }catch(Exception ex){
           ex.getMessage();
       }
    }
    
    public void play() // Method to play the audio 
    { 
        clip.start();  
        status = "play"; 
    } 
    
    public void pause() // Method to pause the audio 
    { 
        if (status.equals("paused"))  
        { 
            System.out.println("audio is already paused"); 
            return; 
        } 
        this.current = this.clip.getMicrosecondPosition(); 
        clip.stop(); 
        status = "paused"; 
    } 
      
    // Method to resume the audio 
    public void resume()                       
    { 
        try{
          if (status.equals("play"))  
          { 
             System.out.println("Audio is already being played"); 
             return; 
          } 
          clip.close(); 
          resetAudioStream(); 
          clip.setMicrosecondPosition(current); 
          this.play(); 
       }catch(Exception ex){
          ex.getMessage(); 
       }
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
      
    // Method to stop the audio 
    public void stop() 
    { 
        try{
        current = 0L; 
        clip.stop(); 
        clip.close(); 
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
      
    // Method to reset audio stream 
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
}
