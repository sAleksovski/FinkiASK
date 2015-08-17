package mk.ukim.finki.tr.finkiask.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.io.Serializable;
import java.util.List;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

@Table(databaseName = AppDatabase.NAME)
@ModelContainer
public class Question extends BaseModel implements Serializable {

    // Question types
    public static String SINGLE_CHOICE = "SINGLE";
    public static String MULTIPLE_CHOICE = "MULTIPLE";
    public static String TEXT = "TEXT";
    public static String RANGE = "RANGE";

    @Column
    @PrimaryKey(autoincrement = false)
    protected long id;

    @Column
    private String text;

    @Column
    private String type;

    @Column
    private boolean isAnswered;

    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "test_id",
            columnType = Long.class,
            foreignColumnName = "id")}, saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE)
    protected ForeignKeyContainer<TestInstance> testInstanceModelContainer;

    protected List<Answer> answers;

    public Question() {}

    public Question(long id, String text, String type) {
        this.id = id;
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

    public boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public void setTestInstance(TestInstance testInstance) {
        testInstanceModelContainer = new ForeignKeyContainer<>(TestInstance.class);
        testInstanceModelContainer.setModel(testInstance);
        testInstanceModelContainer.put(TestInstance$Table.ID, testInstance.id);
    }

    public TestInstance getTestInstance() {
        return testInstanceModelContainer.toModel();
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "answers")
    public List<Answer> getAnswers() {
        if(answers == null) {
            answers = new Select()
                    .from(Answer.class)
                    .where(Condition.column(Answer$Table.QUESTIONID).eq(getId()))
                    .queryList();
        }
        return answers;
    }

    @Override
    public String toString() {
        return "Question " + getId();
    }

}