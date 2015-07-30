package mk.ukim.finki.tr.finkiask.database.models;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import mk.ukim.finki.tr.finkiask.database.AppDatabase;

@Table(databaseName = AppDatabase.NAME)
public class Question extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    private long id;

    @Column
    private String text;

    @Column
    private String type;

    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "test_id",
                                    columnType = Long.class,
                                    foreignColumnName = "id")})
    protected Test test;

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

    public void associateTest(Test test) {
        this.test = test;
    }

    public Test getTest() {
        return test;
    }

}
