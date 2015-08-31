package mk.ukim.finki.tr.finkiask.util.timer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class CountdownHelper {
    public static final String PREFS_NAME = "FINKI_ASK_PREFS";
    public static final String START_TIME = "START_TIME";
    public static final String DURATION = "DURATION";

    public static void setStartTime(Context context, Date startTime) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.putLong(START_TIME, startTime.getTime());
        editor.apply();
    }

    public static Date getStartTime(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new Date(settings.getLong(START_TIME, new Date().getTime()));
    }

    public static void setDuration(Context context, int duration) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.putInt(DURATION, duration);
        editor.apply();
    }

    public static Integer getDuration(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(DURATION, 0);
    }
}
