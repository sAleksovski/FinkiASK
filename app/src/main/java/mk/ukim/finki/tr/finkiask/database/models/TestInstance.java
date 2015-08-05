package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

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

    @Column
    private long userID;

    @Column
    private Date startTime;

    protected List<Question> questions;

    public TestInstance() { }

    public TestInstance(long id, String name, String type, int duration, long userID, Date startTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.userID = userID;
        this.startTime = startTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
}
