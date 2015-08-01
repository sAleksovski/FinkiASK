package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

@Table(databaseName = AppDatabase.NAME)
public class Answer extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    protected long id;

    @Column
    private String text;

    @Column
    private int isCorrect;

    @Column
    private boolean isAnswered;

    @Column
    private long questionID;

    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "question_id",
            columnType = Long.class,
            foreignColumnName = "id")})
    protected Question question;

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

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }
}
