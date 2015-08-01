package mk.ukim.finki.tr.finkiask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.tr.finkiask.TempData.Answer;
import mk.ukim.finki.tr.finkiask.TempData.Question;
import mk.ukim.finki.tr.finkiask.TempData.Test;
import mk.ukim.finki.tr.finkiask.adapters.TestRecyclerViewAdapter;

/**
 * Created by stefan on 7/31/15.
 */
public class MainTestListFragment extends Fragment {

    public static MainTestListFragment newInstance(String type) {
        MainTestListFragment f = new MainTestListFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        f.setArguments(args);

        return f;
    }

    public String getType() {
        return getArguments().getString("type", "test");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(
                R.layout.fragment_main_test_list, container, false);

        RecyclerView rv = (RecyclerView) rl.findViewById(R.id.recyclerview);
        TextView tv = (TextView) rl.findViewById(R.id.noTestsMessage);

        List<Test> tests = new ArrayList<>();

        if (getType().equals("test")) {
            tests = getTests();
        } else if (getType().equals("survey")) {
            tests = getSurveys();
        } else if (getType().equals("anonsurvey")) {
            tests = getAnonSurveys();
        }

        if (tests.size() > 0) {
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
            rv.setAdapter(new TestRecyclerViewAdapter(tests));
        } else {
            tv.setVisibility(View.VISIBLE);
        }

        return rl;
    }

    private List<Test> getTests() {
        ArrayList<Test> testDataset = new ArrayList<>();
        for (int i= 0; i < 10; i++){
            Test t = new Test("Test Name "+i,"type "+i,"Duration: "+i+"m");
            ArrayList<Answer> tmp = new ArrayList<>();
            tmp.add(new Answer("Answer 1",true));
            tmp.add(new Answer("Answer 2", false));
            tmp.add(new Answer("Answer 3", false));
            ArrayList<Answer> textAnswer = new ArrayList<>();
            textAnswer.add(new Answer("Answer ", true));
            ArrayList<Question> questions = new ArrayList<>();
            questions.add(new Question("1", "Question Text 1", "1", tmp));
            questions.add(new Question("2", "Question Text 2", "2", tmp));
            questions.add(new Question("3", "Question Text 3", "3", textAnswer));
            questions.add(new Question("4", "Question Text 4","4", textAnswer));
            t.setQuestions(questions);
            testDataset.add(t);
        }
        return testDataset;
    }

    public List<Test> getSurveys(){
        ArrayList<Test> surveyDataset = new ArrayList<>();
        for (int i= 0; i < 10; i++){
            Test t = new Test("Survey Name "+i,"type","subject");
            ArrayList<Answer> tmp = new ArrayList<>();
            tmp.add(new Answer("Answer 1",true));
            tmp.add(new Answer("Answer 2", false));
            tmp.add(new Answer("Answer 3", false));
            ArrayList<Answer> textAnswer = new ArrayList<>();
            textAnswer.add(new Answer("Answer ", true));
            ArrayList<Question> questions = new ArrayList<>();
            questions.add(new Question("1", "Question Text 1", "1", tmp));
            questions.add(new Question("2", "Question Text 2", "2", tmp));
            questions.add(new Question("3", "Question Text 3", "3", textAnswer));
            questions.add(new Question("4", "Question Text 4","4", textAnswer));
            t.setQuestions(questions);
            surveyDataset.add(t);
        }
        return surveyDataset;
    }

    public List<Test> getAnonSurveys(){
//        ArrayList<Test> surveyDataset = new ArrayList<>();
        return new ArrayList<>();
//        for (int i= 0; i < 10; i++){
//            Test t = new Test("Anon Survey Name "+i,"type","subject");
//            ArrayList<Answer> tmp = new ArrayList<>();
//            tmp.add(new Answer("Answer 1",true));
//            tmp.add(new Answer("Answer 2", false));
//            tmp.add(new Answer("Answer 3", false));
//            ArrayList<Answer> textAnswer = new ArrayList<>();
//            textAnswer.add(new Answer("Answer ", true));
//            ArrayList<Question> questions = new ArrayList<>();
//            questions.add(new Question("1", "Question Text 1", "1", tmp));
//            questions.add(new Question("2", "Question Text 2", "2", tmp));
//            questions.add(new Question("3", "Question Text 3", "3", textAnswer));
//            questions.add(new Question("4", "Question Text 4","4", textAnswer));
//            t.setQuestions(questions);
//            surveyDataset.add(t);
//        }
//        return surveyDataset;
    }

}
