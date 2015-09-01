package mk.ukim.finki.tr.finkiask.data.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import mk.ukim.finki.tr.finkiask.data.AppDatabase;

@Table(databaseName = AppDatabase.NAME)
public class Answer extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = false)
    protected long id;

    @Column
    private String text;

    @Column
    private boolean isChecked;

    @Column
    private long questionId;

    private Question question;

    public Answer() {}

    public Answer(long id, String text, long questionId) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
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

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Question getQuestion() {
        if (question == null)
            question = new Select().from(Question.class).where(Condition.column(Question$Table.ID).eq(questionId)).querySingle();
        return question;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
