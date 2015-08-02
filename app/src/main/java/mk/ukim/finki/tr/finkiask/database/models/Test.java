package mk.ukim.finki.tr.finkiask.database.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Test implements Serializable {
    private long id;

    private String name;

    private String type;

    private int duration;

    private Date endTime;

    private List<Question> questions;

    public Test() {}

    public Test(long id, String name, String type, int duration, Date endTime, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.endTime = endTime;
        this.questions = questions;
    }

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
