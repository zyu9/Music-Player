import javax.swing.*; 
/**
 * This contains the main method for lanuch the whole program. 
 *
 * @author (Zhiyan Yu)
 * @version (9/12/2020-9/19/2020)
 */
public class MusicPlayer
{
    public static void main(String[] args) {
        try {
	   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception ex) {
	   ex.printStackTrace();
	}
        MusicPlayer musicPlayer = new MusicPlayer(); 
        musicPlayer.startup(); 
    }
    
    public void startup(){
        GUI gui = new GUI();
        gui.buildGUI(); 
    }
 } 
