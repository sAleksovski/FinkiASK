package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

@Table(databaseName = AppDatabase.NAME)
public class Question extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    protected long id;

    @Column
    private String text;

    @Column
    private String type;

    @Column
    private long questionID;

    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "test_id",
                                    columnType = Long.class,
                                    foreignColumnName = "id")})
    protected TestInstance testInstance;

    protected List<Answer> answers;

    public Question() {}

    public Question(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTestInstance(TestInstance testInstance) {
        this.testInstance = testInstance;
    }

    public TestInstance getTestInstance() {
        return testInstance;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "answers")
    public List<Answer> getAnswers() {
        if(answers == null) {
            answers = new Select()
                    .from(Answer.class)
                    .where(Condition.column(Answer$Table.QUESTION_QUESTION_ID).eq(getId()))
                    .queryList();
        }
        return answers;
    }

    @Override
    public String toString() {
        return "Question " + getId();
    }
}
