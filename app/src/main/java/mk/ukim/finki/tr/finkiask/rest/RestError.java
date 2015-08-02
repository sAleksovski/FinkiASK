package mk.ukim.finki.tr.finkiask.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stefan on 8/2/15.
 */
public class RestError {
    @SerializedName("code")
    public int code;
    @SerializedName("error")
    public String errorDetails;
}
