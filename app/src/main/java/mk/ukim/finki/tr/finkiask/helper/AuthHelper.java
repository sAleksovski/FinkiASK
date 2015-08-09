package mk.ukim.finki.tr.finkiask.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AuthHelper {

    public static final String PREFS_NAME = "FINKI_ASK_PREFS";
    public static final String SESSION_USER = "SESSION_USER";
    public static final String SESSION_VALID_UNTIL = "SESSION_VALID_UNTIL";
    private static final String SESSION_HASH = "SESSION_HASH";

    public static boolean isLoggedIn(Activity activity) {

        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();

        String user = settings.getString(SESSION_USER, null);
        long sessionValidUntil = settings.getLong(SESSION_VALID_UNTIL, -1);

        return user != null && currentTime < sessionValidUntil;

    }

    public static void setCredentials(Activity activity, String username, String hash) {

        SharedPreferences.Editor editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.putString(SESSION_USER, username);
        editor.putString(SESSION_HASH, hash);
        long currentTime = System.currentTimeMillis();
        long sevenDays = 7 * 24 * 60 * 60000;
        editor.putLong(SESSION_VALID_UNTIL, currentTime + sevenDays);

        editor.apply();

    }

    public static void logout(Activity activity) {

        SharedPreferences.Editor editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.remove(SESSION_USER);
        editor.remove(SESSION_HASH);
        editor.remove(SESSION_VALID_UNTIL);

        editor.apply();

    }

}
