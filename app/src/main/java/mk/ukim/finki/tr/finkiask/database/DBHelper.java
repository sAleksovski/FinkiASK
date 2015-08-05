package mk.ukim.finki.tr.finkiask.database;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import mk.ukim.finki.tr.finkiask.database.models.Answer;
import mk.ukim.finki.tr.finkiask.database.models.Question;
import mk.ukim.finki.tr.finkiask.database.models.TestInstance;

public class DBHelper {
    public static boolean isTestInstanceFound() {
        return new Select().from(TestInstance.class).queryList().size() > 0;
    }

    public static List<Question> getAllQuestion() {
        return new Select().from(Question.class).queryList();
    }

    public static List<Answer> getAllAnswers() {
        return new Select().from(Answer.class).queryList();
    }
}
