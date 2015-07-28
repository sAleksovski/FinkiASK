package mk.ukim.finki.tr.finkiask.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stefan on 7/28/15.
 */
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

}
