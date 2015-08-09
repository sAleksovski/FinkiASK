package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

@Table(databaseName = AppDatabase.NAME)
public class Answer extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = false)
    protected long id;

    @Column
    private String text;

    @Column
    private boolean isAnswered;

    @Column
    private long questionID;

    private Question question;

    public Answer() {}

    public Answer(long id, String text, long questionID) {
        this.id = id;
        this.text = text;
        this.questionID = questionID;
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

    public boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public Question getQuestion() {
        if (question == null)
            question = new Select().from(Question.class).where(Condition.column(Question$Table.ID).eq(questionID)).querySingle();
        return question;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }
}
