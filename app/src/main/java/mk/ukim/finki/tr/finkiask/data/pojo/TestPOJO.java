package mk.ukim.finki.tr.finkiask.data.pojo;

import java.io.Serializable;
import java.util.Date;

public class TestPOJO implements Serializable {

    public static String TEST = "TEST";
    public static String ANONYMOUS_TEST = "ANONYMOUSTEST";
    public static String SURVEY = "SURVEY";

    private long id;
    private String name;
    private String type;
    private boolean isPublic;
    private Date dateCreated;
    private Date start;
    private Date end;
    private int duration;
    private boolean isActive;

    public TestPOJO() {
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

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
