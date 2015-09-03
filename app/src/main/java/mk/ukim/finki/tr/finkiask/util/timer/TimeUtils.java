package mk.ukim.finki.tr.finkiask.util.timer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import mk.ukim.finki.tr.finkiask.data.models.TestInstance;

public class TimeUtils {
    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    /**
     * Calculates remaining time for given test based on current time
     * @param duration duration of test instance
     * @param openedTime time when test was first opened
     * @return remaining time
     */
    public static long remainingTime(TestInstance test, TimeUnit timeUnit) {
        return  test.getDuration() - TimeUtils.getDateDiff(test.getOpenedTime(),
                new Date(), timeUnit);
    }
}
