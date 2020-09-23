import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel; 
import javax.swing.JSlider; 
import javax.sound.sampled.*; 
/**
 * This class counts the time for the audio. 
 *
 * @author (Zhiyan Yu)
 * @version (9/19/2020-9/21/2020)
 */
public class Timer 
{
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    boolean isRunning = false;
    boolean isPause = false;
    boolean isReset = false;
    long startTime;
    long pauseTime;
    
    JLabel labelRecordTime;
    JSlider slider;
    Clip audio; 
    
    public void setClip(Clip audio){
        this.audio = audio; 
    }
    
    public void playingTimer(JLabel labelRecordTime, JSlider slider){
        this.labelRecordTime = labelRecordTime;
        this.slider = slider; 
    }
    
    public void run(){
        isRunning = true;
        startTime = System.currentTimeMillis();
        while(isRunning){
            try{
                Thread.sleep(100);
                if(!isPause){
                    if(audio != null && audio.isRunning()){
                        labelRecordTime.setText(toTimeString());
                        int currentSecond = (int) audio.getMicrosecondPosition() / 1_000_000; 
			slider.setValue(currentSecond);
                    }else{
                        pauseTime += 100; 
                    }
                }
            }catch(InterruptedException ex){
                ex.printStackTrace();
                if(isReset){
                    slider.setValue(0);
                    labelRecordTime.setText("00:00:00");
                    isRunning = false;
                    break; 
                }
            }
        }
    }
    
    /**
     * Set different status. 
     */
    void reset(){
        isReset = true;
        isRunning = false;
    }
    
    void pauseTimer(){
        isPause = true; 
    }
    
    void resumeTimer(){
        isPause = false; 
    }
    
    private String toTimeString(){
        long now = System.currentTimeMillis();
	Date current = new Date(now - startTime - pauseTime);
	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	String timeCounter = dateFormat.format(current);
	return timeCounter;
    }
}
