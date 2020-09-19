import java.util.*; 
import java.util.concurrent.TimeUnit; 
import javax.swing.*; 
import javax.sound.sampled.*; 
/**
 * This class counts the time for the audio. 
 *
 * @author (Zhiyan Yu)
 * @version (9/19/2020)
 */
public class Timer 
{
    Timer timer = new Timer( );
    public void onTick(long millisUntilFinished) {
        long millis = millisUntilFinished;
        //Convert milliseconds into hour,minute and seconds
        String hms = String.format("%02d:%02d:%02d", 
        TimeUnit.MILLISECONDS.toHours(millis), 
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        timer.setText(hms);//set text
    }
}
