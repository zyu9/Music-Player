import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.*; 
import javax.swing.*; 
import javax.sound.sampled.*; 
/**
 * Write a description of class GUI here.
 *
 * @author (zyu9)
 * @version (9/13/2020; 9/21/2020; 9/23/2020)
 */
public class GUI
{
    private static final String VERSION = "Version 0.5"; 
    private Audio audio = new Audio();
    private Timer timer = new Timer(); 
    private Thread playbackThread; 
   
    private boolean isPlaying = false;
    private boolean isPause = false; 
    
    private JLabel labelFileName = new JLabel("Playing File:");
    private JLabel labelTimeCounter = new JLabel("00:00:00");
    private JLabel labelDuration = new JLabel("00:00:00");
	
    private JButton buttonPlay = new JButton("Play");
    private JButton buttonStop = new JButton("Stop");
    private JButton buttonPause = new JButton("Pause");
    private JButton buttonRestart = new JButton("Restart");
    
    private JSlider sliderTime = new JSlider();
    
    private ImageIcon iconPlay = new ImageIcon("./play.png"); 
    private ImageIcon iconStop = new ImageIcon("./stop.png"); 
    private ImageIcon iconPause = new ImageIcon("./pause.png"); 
    private ImageIcon iconRestart = new ImageIcon("./restart.png"); 
    private ImageIcon iconWallpaper = new ImageIcon("./wallpaper.jpg");
    
    private JPanel mainPanel;
    private JFrame theFrame;
    private JMenuItem menuItem1,menuItem2; 
    private JSlider slider;
    
    public void buildGUI(){        
        theFrame = new JFrame("Music Player");
        BorderLayout layout = new BorderLayout();
        GridBagLayout grid = new GridBagLayout();
	GridBagConstraints constraints = new GridBagConstraints();
	constraints.insets = new Insets(5, 5, 5, 5);
	constraints.anchor = GridBagConstraints.WEST;
        
        menuBar(); 

        mainPanel = new JPanel();
        theFrame.setLayout(layout);
        mainPanel.setLayout(grid);//flow
        
        buttonPlay.setFont(new Font("Sans", Font.BOLD, 14));
        buttonPlay.setIcon(iconPlay);
	buttonPlay.addActionListener(new MyPlayListener());
        buttonPlay.setEnabled(false);
        buttonPlay.setVisible(true);
        
        buttonStop.setFont(new Font("Sans", Font.BOLD, 14));
	buttonStop.setIcon(iconStop);
        buttonStop.addActionListener(new MyStopListener());
        buttonStop.setVisible(true);
     
	buttonPause.setFont(new Font("Sans", Font.BOLD, 14));
	buttonPause.setIcon(iconPause);
        buttonPause.addActionListener(new MyPauseListener());
        buttonPause.setEnabled(false);
        buttonPause.setVisible(true);
	
        buttonRestart.setFont(new Font("Sans", Font.BOLD, 14));
	buttonRestart.setIcon(iconRestart);
        buttonRestart.addActionListener(new MyRestartListener());
        buttonRestart.setVisible(true); 
        
	labelTimeCounter.setFont(new Font("Sans", Font.BOLD, 12));
	labelDuration.setFont(new Font("Sans", Font.BOLD, 12));
		
	sliderTime.setPreferredSize(new Dimension(400, 20));
	sliderTime.setEnabled(false);
	sliderTime.setValue(0);
	
	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.gridwidth = 3;
	mainPanel.add(labelFileName, constraints);
		
	constraints.anchor = GridBagConstraints.CENTER;
	constraints.gridy = 1;
	constraints.gridwidth = 1;
	mainPanel.add(labelTimeCounter, constraints);
		
	constraints.gridx = 1;
	mainPanel.add(sliderTime, constraints);
		
	constraints.gridx = 2;
	mainPanel.add(labelDuration, constraints);
	
        theFrame.add(new JLabel(iconWallpaper), BorderLayout.CENTER);
        theFrame.add(mainPanel, BorderLayout.SOUTH);

        theFrame.setSize(500, 500);
        
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
	panelButtons.add(buttonPlay);
	panelButtons.add(buttonStop);
	panelButtons.add(buttonPause);
	panelButtons.add(buttonRestart);
		
	constraints.gridwidth = 3;
	constraints.gridx = 0;
	constraints.gridy = 2;
	mainPanel.add(panelButtons, constraints);
	
	theFrame.pack();
        theFrame.setResizable(true);
        theFrame.setDefaultCloseOperation(theFrame.EXIT_ON_CLOSE);
        theFrame.setLocationRelativeTo(null);
        theFrame.setVisible(true);
        theFrame.setFocusable(true);
    }
    
    /**
     * Create the frame's menu bar. 
     */
    private void menuBar(){
        JMenuBar mbar = new JMenuBar();
        JMenu menu; 
        theFrame.setJMenuBar(mbar);
        
        //create the File menu
        menu = new JMenu("File");
        mbar.add(menu);
        
        menuItem1 = new JMenuItem("Open");
        menuItem1.addActionListener(new MyMenuItem1Listener());
        menu.add(menuItem1);
        
        menuItem2 = new JMenuItem("Exit");
        menuItem2.addActionListener(new MyMenuItem2Listener());
        menu.add(menuItem2);
        
        //create the Help menu
        menu = new JMenu("Help");
        mbar.add(menu);
        
        JMenuItem menuItem3 = new JMenuItem("About");
        //menuItem3.addActionListener(new MyMenuItem3Listener());
        menu.add(menuItem3);
    }
    
    private void playBack(){
        timer.playingTimer(labelTimeCounter, sliderTime);
        timer.run();
        isPlaying = true; 
        playbackThread = new Thread(new Runnable() {

	public void run() {
	try {
	   buttonPlay.setText("Stop");
	   buttonPlay.setIcon(iconStop);
	   buttonPlay.setEnabled(true);
					
	   buttonPause.setText("Pause");
	   buttonPause.setEnabled(true);
					
	   timer.setClip(audio.getClip());
	   labelFileName.setText("Playing File: " + audio.getAudioFilePath());
	   sliderTime.setMaximum((int) audio.getClipSecondLength());
					
	   labelDuration.setText(audio.getClipLengthString());
	   audio.play();
				
	   resetControls();
	} catch (Exception ex) {
	   resetControls();
	   ex.printStackTrace();
      }
     }
     });
	playbackThread.start();
    }
    
    private void stopPlaying(){
        isPause = false;
	buttonPause.setText("Pause");
	buttonPause.setEnabled(false);
	timer.reset();
	//timer.interrupt();
	audio.stop();
	playbackThread.interrupt();
    }
    
    private void pausePlaying(){
        buttonPause.setText("Resume");
	isPause = true;
	audio.pause();
	timer.pauseTimer();
	playbackThread.interrupt();
    }
    
    private void resumePlaying(){
        buttonPause.setText("Pause");
	isPause = false;
	audio.resume();
	timer.resumeTimer();
	playbackThread.interrupt();
    }
    
    private void resetControls() {
	timer.reset();
	//timer.interrupt();

	buttonPlay.setText("Play");
	buttonPlay.setIcon(iconPlay);
		
	buttonPause.setEnabled(false);
		
	isPlaying = false;		
    }
    
    public class MyMenuItem1Listener implements ActionListener{
       public void actionPerformed(ActionEvent e) 
       {
         Audio audio = new Audio();

         if (e.getSource() instanceof JMenuItem) {
           if (((JMenuItem) (e.getSource())) == menuItem1) {
                audio.openFile();
                if (isPlaying || isPause) {
		    stopPlaying();
		}
                playBack();
           }
         }
       }
    }
    
    public class MyMenuItem2Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Audio audio = new Audio();
            audio.exit(); 
        }
    }
    
    public class MyPlayListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
	if (source instanceof JButton) {
	   JButton button = (JButton) source;
            if(button == buttonPlay){
              if (!isPlaying) {
		  playBack();
	      }else{
		  stopPlaying();
	      }
           }
        }
      }
    }
    
    public class MyPauseListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Object source = e.getSource();
            if(source instanceof JButton){
                JButton button = (JButton) source; 
            if(button == buttonPause)
            {
                if(isPause){
                    pausePlaying();
                }else{
                    resumePlaying(); 
                }
            }
        }
        
     }
    }
    
    public class MyStopListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Audio audio = new Audio();
            audio.stop(); 
        }
    }
    
    public class MyRestartListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Audio audio = new Audio();
            audio.restart(); 
        }
    }
    
}
