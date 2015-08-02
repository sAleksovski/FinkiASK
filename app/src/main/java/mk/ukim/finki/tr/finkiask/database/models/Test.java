package mk.ukim.finki.tr.finkiask.database.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mk.ukim.finki.tr.finkiask.TempData.Question;

public class Test implements Serializable {
    private String name;

    private String type;

    private int duration;

    private Date endTime;

    private List<mk.ukim.finki.tr.finkiask.TempData.Question> questions;

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

    public List<mk.ukim.finki.tr.finkiask.TempData.Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
