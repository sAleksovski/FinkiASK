package mk.ukim.finki.tr.finkiask.database.models;

import java.util.Date;

public class Test {
    private String name;

    private String type;

    private int duration;

    private Date endTime;

    public Test() {}

    public Test(String name, String testingType, int duration) {
        this.name = name;
        this.type = testingType;
        this.duration = duration;
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
