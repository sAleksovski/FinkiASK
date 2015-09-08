package mk.ukim.finki.tr.finkiask.ui.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.api.ServerResponseWrapper;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestInterface;
import mk.ukim.finki.tr.finkiask.data.models.Answer;
import mk.ukim.finki.tr.finkiask.data.models.Question;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.ui.MainActivity;
import mk.ukim.finki.tr.finkiask.ui.ResultActivity;
import mk.ukim.finki.tr.finkiask.ui.dialog.BaseDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.CancelTestDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.FinishTestDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.TimerExpiredDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment.BaseQuestionFragment;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment.QuestionFragmentFactory;
import mk.ukim.finki.tr.finkiask.util.AuthHelper;
import mk.ukim.finki.tr.finkiask.util.timer.Countdown;
import mk.ukim.finki.tr.finkiask.util.timer.CountdownHelper;
import mk.ukim.finki.tr.finkiask.util.timer.CountdownInterface;
import mk.ukim.finki.tr.finkiask.util.timer.TimeUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * An activity representing a list of Tests. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TestDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link TestListFragment} and the item details
 * (if present) is a {@link BaseQuestionFragment}.
 * <p/>
 * This activity also implements the required
 * {@link TestListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class TestListActivity extends AppCompatActivity
        implements TestListFragment.Callbacks, CountdownInterface {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.timer)
    TextView toolbarTimer;
    @Bind(R.id.submit)
    ImageButton submitButton;

    @Nullable
    @Bind(R.id.btn_next_question)
    FloatingActionButton btnNextQuestion;

    public static String ARG_RESULT = "result";
    public static String ARG_TYPE = "type";

    public Countdown countdown;

    private TestListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_action_school);
        ab.setDisplayHomeAsUpEnabled(true);

        countdown = Countdown.getInstance();
        countdown.addTestCountdownInterface(this);

        long testInstanceId = getIntent().getLongExtra("testInstanceId", -1);

        if (testInstanceId != -1) {
            //TODO inspect,
            TestInstance t = DBHelper.getTestInstanceById(testInstanceId);
            if (t != null) {
                CountdownHelper.setStartTime(getApplicationContext(), t.getOpenedTime());
                CountdownHelper.setDuration(getApplicationContext(), t.getDuration());
            }
        }

        if (findViewById(R.id.test_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp-land). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            fragment = (TestListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.test_list);
            fragment.setActivateOnItemClick(true);

            fragment.getListView().setSelection(0);
            fragment.getListView().performItemClick(fragment.getListView().getAdapter().getView(0, null, null), 0, 0);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBHelper.isTestInstanceFound()) {

                    List<Question> unsynced = DBHelper.getUnsyncedQuestions();
                    for (final Question q : unsynced) {
                        List<Answer> answers = q.getAnswers();
                        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
                        testsRestAdapter.postAnswer(AuthHelper.getSessionCookie(view.getContext()), q.getTestInstance().getId(), answers, new Callback<ServerResponseWrapper<TestInstance>>() {

                            @Override
                            public void success(ServerResponseWrapper<TestInstance> serverResponseWrapper, Response response) {
                                Log.d("SAVE", serverResponseWrapper.getResponseStatus());
//                                q.setIsSynced(true);
//                                q.save();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }

                    FinishTestDialogFragment.newInstance(new BaseDialogFragment.OnPositiveCallback() {
                        @Override
                        public void onPositiveClick(String data) {

                            final String type = DBHelper.getSingleTestInstance().getType();
                            final long testId = DBHelper.getSingleTestInstance().getId();
                            DBHelper.deleteEverything();

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
                                    testsRestAdapter.getResult(AuthHelper.getSessionCookie(getApplicationContext()), testId,
                                            new ArrayList<Answer>(), new Callback<ServerResponseWrapper<Integer>>() {
                                                @Override
                                                public void success(ServerResponseWrapper<Integer> serverResponseWrapper, Response response) {
                                                    Log.d("getResult", serverResponseWrapper.getData() + "");
                                                    Log.d("getResultType", type);
                                                    Intent resultIntent = new Intent(getApplication(), ResultActivity.class);
                                                    resultIntent.putExtra(TestListActivity.ARG_RESULT, serverResponseWrapper.getData());
                                                    resultIntent.putExtra(TestListActivity.ARG_TYPE, type);
                                                    startActivity(resultIntent);
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    Log.d("getResult", error.toString());
                                                }
                                            });
                                }

                            }, 1000);

                        }
                    }).show(getSupportFragmentManager(), "finish_test_dialog");

                }
            }
        });

        if (btnNextQuestion != null) {
            btnNextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextQuestion();
                }
            });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        countdown.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        long duration = TimeUnit.MILLISECONDS.convert(CountdownHelper.getDuration(getApplicationContext()), TimeUnit.MINUTES);
        long elapsedTime = TimeUtils.getDateDiff(CountdownHelper.getStartTime(getApplicationContext()),
                new Date(), TimeUnit.MILLISECONDS);

        if ((duration - elapsedTime) <= 0) {
            toolbarTimer.setText(String.format("%d:%02d", 0, 0));
            TimerExpiredDialogFragment.newInstance(new BaseDialogFragment.OnPositiveCallback() {
                @Override
                public void onPositiveClick(String data) {
                    DBHelper.deleteEverything();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }).show(getSupportFragmentManager(), "time_expired_dialog");
            return;
        }

        countdown.start(duration - elapsedTime, TimeUnit.MILLISECONDS);


        // if timer is finished
    }


    public void changeTimer(long milliseconds) {
        int sec = (int) (milliseconds / 1000);
        int min = sec / 60;
        sec = sec - (min * 60);
        toolbarTimer.setText(String.format("%d:%02d", min, sec));
    }

    @Override
    public void onBackPressed() {
        CancelTestDialogFragment.newInstance(new BaseDialogFragment.OnPositiveCallback() {
            @Override
            public void onPositiveClick(String data) {
                countdown.stop();
                TestListActivity.super.onBackPressed();
            }
        }).show(getSupportFragmentManager(), "cancel_test_dialog");
    }

    /**
     * Callback method from {@link TestListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(long id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(BaseQuestionFragment.ARG_QUESTION_ID, id);
            BaseQuestionFragment fragment = QuestionFragmentFactory.
                    getQuestionFragment(DBHelper.getQuestionById(id).getType());
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.test_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, TestDetailActivity.class);
            detailIntent.putExtra(BaseQuestionFragment.ARG_QUESTION_ID, id);
            startActivity(detailIntent);
        }
    }

    public void nextQuestion() {
        int currentPosition = fragment.getActivatedPosition();
        int total = fragment.getListView().getAdapter().getCount();
        int nextPosition = (currentPosition + 1) % total;
        fragment.getListView().setSelection(nextPosition);
        fragment.getListView().performItemClick(fragment.getListView().getAdapter().getView(nextPosition, null, null), nextPosition, nextPosition);
        fragment.getListView().invalidate();
    }
}
