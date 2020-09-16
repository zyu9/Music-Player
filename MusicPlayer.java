/**
 * Write a description of class MusicPlayer here.
 *
 * @author (Zhiyan Yu)
 * @version (9/12/2020-9/14/2020)
 */
public class MusicPlayer
{
    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayer(); 
        musicPlayer.startup(); 
    }
    
    public void startup(){
        GUI gui = new GUI();
        gui.buildGUI(); 
    }
 } 
