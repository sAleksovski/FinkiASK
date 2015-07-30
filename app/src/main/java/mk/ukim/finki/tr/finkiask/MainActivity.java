package mk.ukim.finki.tr.finkiask;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.ListAdapter.MyAdapter;
import mk.ukim.finki.tr.finkiask.TempData.Answer;
import mk.ukim.finki.tr.finkiask.TempData.Question;
import mk.ukim.finki.tr.finkiask.TempData.Test;
import mk.ukim.finki.tr.finkiask.helper.AuthHelper;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTestRecyclerView;
    private RecyclerView.Adapter mTestAdapter;
    private RecyclerView.LayoutManager mTestLayoutManager;

    private RecyclerView mSurveyRecyclerView;
    private RecyclerView.Adapter mSurveyAdapter;
    private RecyclerView.LayoutManager mSurveyLayoutManager;

    private RecyclerView mPreviousRecyclerView;
    private RecyclerView.Adapter mPreviousAdapter;
    private RecyclerView.LayoutManager mPreviousLayoutManager;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initialiseTest();
        initialiseSurvey();
        initialisePrevious();
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_action_school);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void initialiseTest(){
        mTestRecyclerView = (RecyclerView) findViewById(R.id.test_recycler_view);
        mTestRecyclerView.setHasFixedSize(true);
        mTestLayoutManager = new LinearLayoutManager(this);
        mTestRecyclerView.setLayoutManager(mTestLayoutManager);
        ArrayList<Test> testDataset = new ArrayList<Test>();
        for (int i= 0; i < 10; i++){
            Test t = new Test("Test Name "+i,"type","subject");
            ArrayList<Answer> tmp = new ArrayList<Answer>();
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
        mTestAdapter = new MyAdapter(MainActivity.this, testDataset);
        mTestRecyclerView.setAdapter(mTestAdapter);
    }
    public void initialiseSurvey(){
        mSurveyRecyclerView = (RecyclerView) findViewById(R.id.survey_recycler_view);
        mSurveyRecyclerView.setHasFixedSize(true);
        mSurveyLayoutManager = new LinearLayoutManager(this);
        mSurveyRecyclerView.setLayoutManager(mSurveyLayoutManager);
        ArrayList<Test> surveyDataset = new ArrayList<Test>();
        for (int i= 0; i < 10; i++){
            Test t = new Test("Survey Name "+i,"type","subject");
            ArrayList<Answer> tmp = new ArrayList<Answer>();
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
        mSurveyAdapter = new MyAdapter(MainActivity.this, surveyDataset);
        mSurveyRecyclerView.setAdapter(mSurveyAdapter);
    }
    public void initialisePrevious(){
        mPreviousRecyclerView = (RecyclerView) findViewById(R.id.previous_recycler_view);
        mPreviousRecyclerView.setHasFixedSize(true);
        mPreviousLayoutManager = new LinearLayoutManager(this);
        mPreviousRecyclerView.setLayoutManager(mPreviousLayoutManager);
        ArrayList<Test> previousDataset = new ArrayList<Test>();
        mPreviousAdapter = new MyAdapter(MainActivity.this, previousDataset);
        mPreviousRecyclerView.setAdapter(mPreviousAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            AuthHelper.logout(this);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
