package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

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
    private int isCorrect;

    @Column
    private boolean isAnswered;

    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "question_id",
            columnType = Long.class,
            foreignColumnName = "id")}, saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    protected ForeignKeyContainer<Question> questionModelContainer;

    public Answer() {}

    public Answer(long id, String text) {
        this.id = id;
        this.text = text;
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

    public Question getQuestion() {
        return questionModelContainer.toModel();
    }

    public void setQuestion(Question question) {
        questionModelContainer = new ForeignKeyContainer<>(Question.class);
        questionModelContainer.setModel(question);
        questionModelContainer.put(Question$Table.ID, question.id);
    }
}
