package mk.ukim.finki.tr.finkiask.util.timer;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Countdown {
    private long milliseconds = 0;
    public boolean isStarted = false;
    public List<CountdownInterface> countdownInterfaces;
    private static Countdown countdown;
    private CountDownTimer countDownTimer;

    protected Countdown() {
        countdownInterfaces = new ArrayList<>();
    }

    public static Countdown getInstance() {
        if (countdown == null) {
            countdown = new Countdown();
        }
        return countdown;
    }

    public void addTestCountdownInterface(CountdownInterface countdownInterface) {
        countdownInterfaces.add(countdownInterface);
    }

    public void start(long duration, TimeUnit sourceUnit) {
        if (!isStarted) {
            isStarted = true;
            TimeUnit timeUnitMilliSeconds = TimeUnit.MILLISECONDS;
            countDownTimer = new CountDownTimer(timeUnitMilliSeconds.convert(duration, sourceUnit), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    milliseconds = millisUntilFinished;
                    for (int i = 0; i < countdownInterfaces.size(); i++)
                        countdownInterfaces.get(i).changeTimer(milliseconds);
                }

                @Override
                public void onFinish() {
                    isStarted = false;
                }
            };
            countDownTimer.start();
        }

    }

    public long getMilliseconds() {
        return milliseconds;
    }
    public void stop(){
        countDownTimer.cancel();
        isStarted = false;
    }
}
