package mk.ukim.finki.tr.finkiask.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthHelper {

    public static final String PREFS_NAME = "FINKI_ASK_PREFS";
    public static final String SESSION_USER = "SESSION_USER";
    public static final String SESSION_VALID_UNTIL = "SESSION_VALID_UNTIL";
    private static final String SESSION_HASH = "SESSION_HASH";
    private static final String SESSION_COOKIE = "SESSION_COOKIE";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();

        String user = settings.getString(SESSION_USER, null);
        long sessionValidUntil = settings.getLong(SESSION_VALID_UNTIL, -1);

        return user != null && currentTime < sessionValidUntil;
    }

    public static void setCredentials(Context context, String username, String hash) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.putString(SESSION_USER, username);
        editor.putString(SESSION_HASH, hash);
        long currentTime = System.currentTimeMillis();
        long sevenDays = 7 * 24 * 60 * 60000;
        editor.putLong(SESSION_VALID_UNTIL, currentTime + sevenDays);

        editor.apply();
    }

    public static void logout(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.remove(SESSION_USER);
        editor.remove(SESSION_HASH);
        editor.remove(SESSION_VALID_UNTIL);

        editor.apply();
    }

    public static void setSessionCookie(Context context, String cookie) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.putString(SESSION_COOKIE, cookie);

        editor.apply();
    }

    public static String getSessionCookie(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return settings.getString(SESSION_COOKIE, null);
    }

}
