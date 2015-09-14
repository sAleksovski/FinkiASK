package mk.ukim.finki.tr.finkiask.ui.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
import mk.ukim.finki.tr.finkiask.ui.dialog.FinishTestDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.TimerExpiredDialogFragment;
import mk.ukim.finki.tr.finkiask.util.AuthHelper;
import mk.ukim.finki.tr.finkiask.util.timer.Countdown;
import mk.ukim.finki.tr.finkiask.util.timer.CountdownHelper;
import mk.ukim.finki.tr.finkiask.util.timer.CountdownInterface;
import mk.ukim.finki.tr.finkiask.util.timer.TimeUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BaseTestActivity extends AppCompatActivity
        implements CountdownInterface {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.timer) TextView toolbarTimer;
    @Bind(R.id.submit) ImageButton submitButton;

    Countdown countdown;

    public static String ARG_RESULT = "result";
    public static String ARG_TYPE = "type";

    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layout) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        countdown = Countdown.getInstance();
        countdown.addTestCountdownInterface(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTest(true);
            }
        });
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
    }

    public void changeTimer(long milliseconds) {
        int sec = (int) (milliseconds / 1000);
        int min = sec / 60;
        sec = sec - (min * 60);
        toolbarTimer.setText(String.format("%d:%02d", min, sec));

        //test run out of time
        if (sec == 1) {
            submitTest(false);
        }
    }

    public abstract void nextQuestion();

    private void submitTest(boolean showFinishDialog) {
        if (DBHelper.isTestInstanceFound()) {
            sendUnsyncedQuestions();

            if (showFinishDialog) {
                FinishTestDialogFragment.newInstance(new BaseDialogFragment.OnPositiveCallback() {
                    @Override
                    public void onPositiveClick(String data) {
                        finishTest();
                    }
                }).show(getSupportFragmentManager(), "finish_test_dialog");
            }
            else {
                finishTest();
            }
        }
    }

    private void finishTest() {
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
                                resultIntent.putExtra(BaseTestActivity.ARG_RESULT, serverResponseWrapper.getData());
                                resultIntent.putExtra(BaseTestActivity.ARG_TYPE, type);
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

    private void sendUnsyncedQuestions() {
        List<Question> unsynced = DBHelper.getUnsyncedQuestions();
        for (final Question q : unsynced) {
            List<Answer> answers = q.getAnswers();
            TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
            testsRestAdapter.postAnswer(AuthHelper.getSessionCookie(getApplicationContext()), q.getTestInstance().getId(), answers, new Callback<ServerResponseWrapper<TestInstance>>() {

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
    }


}
