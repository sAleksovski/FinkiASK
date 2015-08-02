package mk.ukim.finki.tr.finkiask.database.pojo;

import java.io.Serializable;
import java.util.Date;

public class TestPOJO implements Serializable {

    public static String TEST = "test";
    public static String SURVEY = "survey";
    public static String ANONYMOUS_SURVEY = "anonymousSurvey";

    private long id;
    private String name;
    private String type;
    private int duration;
    private Date endTime;

    public TestPOJO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
