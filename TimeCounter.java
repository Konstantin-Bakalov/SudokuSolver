import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeCounter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Frame.seconds++;
        if(Frame.seconds >= 60) {
            Frame.minutes++;
            Frame.seconds -=60;
        }
        String sec = "", min = "";
        if(Frame.seconds < 10) sec = "0" + Frame.seconds;
        else sec = Frame.seconds + "";
        if(Frame.minutes < 10) min = "0" + Frame.minutes;
        else min = Frame.minutes + "";
        Frame.time.setText("Time: " + min + ":" + sec + "       ");
    }
}