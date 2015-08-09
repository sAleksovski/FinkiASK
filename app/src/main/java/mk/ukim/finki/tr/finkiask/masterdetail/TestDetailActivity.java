package mk.ukim.finki.tr.finkiask.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.DBHelper;
import mk.ukim.finki.tr.finkiask.database.models.Question;
import mk.ukim.finki.tr.finkiask.masterdetail.questionfragment.BaseQuestionFragment;
import mk.ukim.finki.tr.finkiask.masterdetail.questionfragment.QuestionFragmentFactory;
import mk.ukim.finki.tr.finkiask.timer.Countdown;
import mk.ukim.finki.tr.finkiask.timer.CountdownInterface;


/**
 * An activity representing a single TestPOJO detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TestListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link BaseQuestionFragment}.
 */
public class TestDetailActivity extends AppCompatActivity
        implements CountdownInterface, BaseQuestionFragment.NextQuestionCallback {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.timer) TextView toolbarTimer;
    @Bind(R.id.submit) ImageButton submitButton;

    Countdown countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detail);
        ButterKnife.bind(this);

        // Show the Up button in the action bar.
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        countdown = Countdown.getInstance();
        countdown.addTestCountdownInterface(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBHelper.isTestInstanceFound()) {
                    DBHelper.deleteEverything();
                    Toast.makeText(getApplicationContext(), "TestInstance removed from local DB", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "No TestInstanceFound", Toast.LENGTH_LONG).show();
            }
        });

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            long id = getIntent().getLongExtra(BaseQuestionFragment.ARG_QUESTION_ID, -1);

            Bundle arguments = new Bundle();
            arguments.putLong(BaseQuestionFragment.ARG_QUESTION_ID, id);

            BaseQuestionFragment fragment = QuestionFragmentFactory.
                    getQuestionFragment(DBHelper.getQuestionById(id).getType());

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.test_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, TestListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changeTimer(long milliseconds) {
        int sec = (int) (milliseconds / 1000);
        int min = sec / 60;
        sec = sec - (min * 60);
        toolbarTimer.setText(String.format("%d:%02d", min, sec));
    }

    @Override
    public void onNextQuestion(long thisQuestionId) {
        Question q = DBHelper.getNextQuestion(thisQuestionId);
        long nextId = q.getId();
        Bundle arguments = new Bundle();
        arguments.putLong(BaseQuestionFragment.ARG_QUESTION_ID, nextId);
        BaseQuestionFragment fragment = QuestionFragmentFactory.
                getQuestionFragment(q.getType());
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.test_detail_container, fragment)
                .commit();
    }
}