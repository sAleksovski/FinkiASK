package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

@ModelContainer
@Table(databaseName = AppDatabase.NAME)
public class Test extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    protected long id;

    @Column
    private String name;

    @Column
    private String testingType;

    @Column
    private int duration;

    @Column
    private long testInstanceID;

    @Column
    private long userID;

    @Column
    private Date startTime;

    protected List<Question> questions;

    public Test() {}

    public Test(String name, String testingType, int duration, long testInstanceID, long userID, Date startTime) {
        this.name = name;
        this.testingType = testingType;
        this.duration = duration;
        this.testInstanceID = testInstanceID;
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

    public String getTestingType() {
        return testingType;
    }

    public void setTestingType(String testingType) {
        this.testingType = testingType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getTestInstanceID() {
        return testInstanceID;
    }

    public void setTestInstanceID(long testInstanceID) {
        this.testInstanceID = testInstanceID;
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

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "questions")
    public List<Question> getMyQuestions() {
        if(questions == null) {
            questions = new Select()
                    .from(Question.class)
                    .where(Condition.column(Question$Table.TEST_TEST_ID).eq(getId()))
                    .queryList();
        }
        return questions;
    }
}
