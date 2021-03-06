package mk.ukim.finki.tr.finkiask.data.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import mk.ukim.finki.tr.finkiask.data.AppDatabase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ModelContainer
@Table(databaseName = AppDatabase.NAME)
public class TestInstance extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = false)
    protected long id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private int duration;

    // TODO
    // fali u api
    @Column
    private long userID;

    @Column
    private Date start;

    @Column
    private Date end;

    @Column
    private Date openedTime;

    protected List<Question> questions;

    public TestInstance() { }

    public TestInstance(long id, String name, String type, int duration, long userID, Date start, Date end) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.userID = userID;
        this.start = start;
        this.end = end;
        this.openedTime = new Date();
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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "questions")
    public List<Question> getQuestions() {
        if(questions == null) {
            questions = new Select()
                    .from(Question.class)
                    .where(Condition.column(Question$Table.TESTINSTANCEMODELCONTAINER_TEST_ID).eq(getId()))
                    .queryList();
        }
        return questions;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(Date openedTime) {
        this.openedTime = openedTime;
    }
}
