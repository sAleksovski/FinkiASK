package mk.ukim.finki.tr.finkiask.rest;

import com.google.gson.annotations.SerializedName;

public class RestError {
    @SerializedName("code")
    public int code;
    @SerializedName("error")
    public String errorDetails;
}
