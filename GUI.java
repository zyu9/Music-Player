import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/**
 * Write a description of class GUI here.
 *
 * @author (zyu9)
 * @version (9/13/2020)
 */
public class GUI
{
    private static final String VERSION = "Version 0.2"; 
    
    private ImageIcon icon1, icon2, icon3, icon4, icon5;
    private JPanel mainPanel;
    private JFrame theFrame;
    private JMenuItem menuItem1,menuItem2; 
    private JList fileList;
    private JSlider slider;
    private JLabel infoLabel;
    private JButton play, pause, stop, restart; 
    //private List<Track> trackList;
    
    public void buildGUI(){
        theFrame = new JFrame("Music Player");
        theFrame.setDefaultCloseOperation(theFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        FlowLayout flow = new FlowLayout();
        
        menuBar(); 

        mainPanel = new JPanel();
        theFrame.setLayout(layout);
        mainPanel.setLayout(flow);

        icon1 = new ImageIcon("./play.png");
        icon2 = new ImageIcon("./pause.png");
        icon3 = new ImageIcon("./stop.png");
        icon4 = new ImageIcon("./restart.jpg");
        icon5 = new ImageIcon("./wallpaper.jpg");
        
        theFrame.getContentPane().setBackground(Color.BLACK);
        
        play = new JButton(icon1);
        play.addActionListener(new MyPlayListener());
        play.setVisible(true);
        mainPanel.add(play);
        
        pause = new JButton(icon2);
        pause.addActionListener(new MyPlayListener());
        pause.setVisible(true);
        mainPanel.add(pause);
        
        stop = new JButton(icon3);
        stop.addActionListener(new MyStopListener());
        stop.setVisible(true);
        mainPanel.add(stop);
        
        restart = new JButton(icon4);
        restart.addActionListener(new MyRestartListener());
        restart.setVisible(true);
        mainPanel.add(stop);

        theFrame.add(new JLabel(icon5), BorderLayout.CENTER);
        theFrame.add(mainPanel, BorderLayout.SOUTH);

        theFrame.setSize(500, 500);
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
    
    /**
     * Display information about the selected song(name + length)
     * @param message - displayed message
     */
    private void showInfo(String message){
        infoLabel.setText(message);
    }
    
    public class MyMenuItem1Listener implements ActionListener{
       public void actionPerformed(ActionEvent e) 
       {
         Audio audio = new Audio();

         if (e.getSource() instanceof JMenuItem) {
           if (((JMenuItem) (e.getSource())) == menuItem1) {
                audio.openFile();
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
            Audio audio = new Audio(); 
            audio.play(); 
        }
    }
    
    public class MyPauseListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Audio audio = new Audio();
            if(pause.getIcon() == icon2)
            {
              pause.setIcon(icon1);
              audio.pause(); 
           }else if(pause.getIcon() == icon1){
               pause.setIcon(icon2);
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
    
    public class MyPlayListListener implements ActionListener{
        /**
         * ActionListener method for display play list in a combo box
         * @param e - details of the event
         */
        public void actionPerformed(ActionEvent e){
            Audio audio = new Audio();
            JComboBox box = (JComboBox)e.getSource();
            String ordering = (String) box.getSelectedItem();
            if(ordering != null) {
                //audio.setListOrdering(ordering);
            }
        }
    }
}
