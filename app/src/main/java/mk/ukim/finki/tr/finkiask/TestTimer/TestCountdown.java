package mk.ukim.finki.tr.finkiask.TestTimer;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beti on 8/3/2015.
 */
public class TestCountdown {
    public long milliseconds = 0;
    public boolean isStarted = false;
    public List<TestCountdownInterface> testCountdownInterfaces;
    private static TestCountdown testCountdown;

    protected TestCountdown(){
        testCountdownInterfaces = new ArrayList<>();
    }
    public static TestCountdown getInstance(){
        if(testCountdown == null) testCountdown = new TestCountdown();
        return testCountdown;
    }
    public void addTestCountdownInterface(TestCountdownInterface testCountdownInterface){
        testCountdownInterfaces.add(testCountdownInterface);
    }
    public void start(int duration){
        if(!isStarted) {
            isStarted = true;
            new CountDownTimer(duration * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //toolbarTimer.setText(millisUntilFinished/1000 +" ");
                    milliseconds=millisUntilFinished;
                    for(int i=0;i<testCountdownInterfaces.size();i++)
                        testCountdownInterfaces.get(i).changeTimer(milliseconds);
                }

                @Override
                public void onFinish() {
                    //toolbarTimer.setText("done");
                    isStarted = false;
                }
            }.start();
        }

    }
    public long getMilliseconds(){return milliseconds;}
}
