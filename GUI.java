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
    ImageIcon icon1, icon2, icon3, icon4;
    JPanel mainPanel;
    JFrame theFrame;
    JMenuItem menuItem; 
    
    public void buildGUI(){
        theFrame = new JFrame("Music Player");
        theFrame.setDefaultCloseOperation(theFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        FlowLayout flow = new FlowLayout();
        
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Open");
        bar.add(menu);
        menuItem = new JMenuItem("Open");
        menu.add(menuItem);
        menuItem.addActionListener(new MyMenuItemListener());

        mainPanel = new JPanel();
        theFrame.setLayout(layout);
        mainPanel.setLayout(flow);
        theFrame.setJMenuBar(bar);

        icon1 = new ImageIcon("./play.png");
        icon2 = new ImageIcon("./pause.png");
        icon3 = new ImageIcon("./stop.png");
        icon4 = new ImageIcon("./wallpaper.png");
        
        theFrame.getContentPane().setBackground(Color.BLACK);
        
        JButton play = new JButton(icon1);
        play.addActionListener(new MyPlayListener());
        play.setVisible(true);
        mainPanel.add(play);
        
        JButton pause = new JButton(icon2);
        pause.addActionListener(new MyPlayListener());
        pause.setVisible(true);
        mainPanel.add(pause);
        
        JButton stop = new JButton(icon3);
        stop.addActionListener(new MyStopListener());
        stop.setVisible(true);
        mainPanel.add(stop);

        theFrame.add(new JLabel(icon4), BorderLayout.CENTER);
        theFrame.add(mainPanel, BorderLayout.SOUTH);

        theFrame.setSize(500, 500);
        theFrame.setLocationRelativeTo(null);
        theFrame.setVisible(true);
        theFrame.setFocusable(true);
    }
    
    public class MyMenuItemListener implements ActionListener{
       public void actionPerformed(ActionEvent e) 
       {
         Audio audio = new Audio();

         if (e.getSource() instanceof JMenuItem) {
           if (((JMenuItem) (e.getSource())) == menuItem) {
                audio.openFile();
           }
         }
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
            audio.pause(); 
        }
    }
    
    public class MyStopListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Audio audio = new Audio();
            audio.stop(); 
        }
    }
    
}
