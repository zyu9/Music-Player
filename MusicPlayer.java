import java.io.*;
import java.util.*;
import javax.sound.sampled.*; 

/**
 * Write a description of class MusicPlayer here.
 *
 * @author (zyu9)
 * @version (9/12/2020)
 */
public class MusicPlayer
{
    //store current position
    Long current;
    Clip clip;
    
    //current status of clip
    String status; 
    
    AudioInputStream audioInputStream;
    String filePath;
    
    //constructor
    public MusicPlayer() throws UnsupportedAudioFileException,IOException,LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    }
    
    public static void main(String[] args) throws UnsupportedAudioFileException,IOException,LineUnavailableException{
        new MusicPlayer().startUp(); 
    }
    
    public void startUp(){
        try
        { 
            filePath = "Your path for the file"; 
            MusicPlayer musicPlayer =  new MusicPlayer();
              
            musicPlayer.play(); 
            Scanner scanner = new Scanner(System.in); 
              
            while (true) 
            { 
                System.out.println("1. pause"); 
                System.out.println("2. resume"); 
                System.out.println("3. restart"); 
                System.out.println("4. stop"); 
                System.out.println("5. Jump to specific time"); 
                int input = scanner.nextInt(); 
                musicPlayer.gotoChoice(input); 
                if (input == 4) 
                break; 
            } 
            scanner.close(); 
        }  
          
        catch (Exception ex)  
        { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
          
        } 
    }
    
    public void gotoChoice(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException  
    { 
        switch (c)  
        { 
            case 1: 
                pause(); 
                break; 
            case 2: 
                resume(); 
                break; 
            case 3: 
                restart(); 
                break; 
            case 4: 
                stop(); 
                break; 
            case 5: 
                System.out.println("Enter time (" + 0 +  
                ", " + clip.getMicrosecondLength() + ")"); 
                Scanner sc = new Scanner(System.in); 
                long c1 = sc.nextLong(); 
                jump(c1); 
                break; 
      
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
    public void resume() throws UnsupportedAudioFileException, IOException, LineUnavailableException                      
    { 
        if (status.equals("play"))  
        { 
            System.out.println("Audio is already being played"); 
            return; 
        } 
        clip.close(); 
        resetAudioStream(); 
        clip.setMicrosecondPosition(current); 
        this.play(); 
    } 
      
    // Method to restart the audio 
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException                                        
    { 
        clip.stop(); 
        clip.close(); 
        resetAudioStream(); 
        current = 0L; 
        clip.setMicrosecondPosition(0); 
        this.play(); 
    } 
      
    // Method to stop the audio 
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException  
    { 
        current = 0L; 
        clip.stop(); 
        clip.close(); 
    } 
      
    // Method to jump over a specific part 
    public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException                                                  
    { 
        if (c > 0 && c < clip.getMicrosecondLength())  
        { 
            clip.stop(); 
            clip.close(); 
            resetAudioStream(); 
            current = c; 
            clip.setMicrosecondPosition(c); 
            this.play(); 
        } 
    } 
      
    // Method to reset audio stream 
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException                                 
    { 
        audioInputStream = AudioSystem.getAudioInputStream( 
        new File(filePath).getAbsoluteFile()); 
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
    
 } 
