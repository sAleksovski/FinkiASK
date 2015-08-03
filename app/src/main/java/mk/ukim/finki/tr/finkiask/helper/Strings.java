package mk.ukim.finki.tr.finkiask.helper;

/**
 * Created by stefan on 8/3/15.
 */
public class Strings {

    public static String subString(String text, int length) {
        if (text.length() < length) {
            return text;
        } else {
            return text.substring(0, length);
        }
    }

}
