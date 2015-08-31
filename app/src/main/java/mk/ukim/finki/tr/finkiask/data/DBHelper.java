package mk.ukim.finki.tr.finkiask.data;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import mk.ukim.finki.tr.finkiask.data.models.Answer;
import mk.ukim.finki.tr.finkiask.data.models.Question;
import mk.ukim.finki.tr.finkiask.data.models.Question$Table;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance$Table;

import java.util.List;

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

    public static Question getQuestionById(long id) {
        return new Select().from(Question.class).where(Condition.column(Question$Table.ID).is(id)).querySingle();
    }

    public static void saveTestInstanceToDb(TestInstance t) {
        t.save();
        for(Question q : DBHelper.getAllQuestion()) {
            q.setTestInstance(t);
            q.save();
        }
    }

    public static TestInstance getTestInstanceById(long id) {
        return new Select()
                .from(TestInstance.class)
                .where(Condition.column(TestInstance$Table.ID).is(id))
                .querySingle();
    }

    public static void deleteEverything() {
        new Select().from(TestInstance.class).querySingle().delete();
        for (Question q : new Select().from(Question.class).queryList()) {
            q.delete();
        }
        for (Answer a : DBHelper.getAllAnswers()) {
            a.delete();
        }
    }

    public static Question getNextQuestion(long id) {
//        Question q = new Select()
//                .from(Question.class)
//                .where(Condition.column(Question$Table.ID).greaterThan(id))
//                .and(Question$Table.ISANSWERED, false)
//                .querySingle();
//
//        if (q != null) return q;
//
//        q = new Select()
//                .from(Question.class)
//                .where(Condition.column(Question$Table.ID).lessThan(id))
//                .and(Question$Table.ISANSWERED, false)
//                .querySingle();
//
//        if (q != null) return q;

        Question q = new Select()
                .from(Question.class)
                .where(Condition.column(Question$Table.ID).greaterThan(id))
                .querySingle();

        if (q != null) return q;

        q = new Select()
                .from(Question.class)
                .where(Condition.column(Question$Table.ID).lessThan(id))
                .querySingle();

        if (q != null) return q;

        q = getQuestionById(id);

        return q;
    }
}
