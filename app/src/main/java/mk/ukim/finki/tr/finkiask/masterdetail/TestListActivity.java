package mk.ukim.finki.tr.finkiask.masterdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.DBHelper;
import mk.ukim.finki.tr.finkiask.database.models.TestInstance;
import mk.ukim.finki.tr.finkiask.masterdetail.questionfragment.BaseQuestionFragment;
import mk.ukim.finki.tr.finkiask.masterdetail.questionfragment.QuestionFragmentFactory;
import mk.ukim.finki.tr.finkiask.timer.Countdown;
import mk.ukim.finki.tr.finkiask.timer.CountdownInterface;


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

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.timer) TextView toolbarTimer;
    @Bind(R.id.submit) ImageButton submitButton;

    @Nullable @Bind(R.id.btn_next_question) FloatingActionButton btnNextQuestion;

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
            Toast.makeText(getApplicationContext(), "TestInstance already found in DB", Toast.LENGTH_LONG).show();
            TestInstance t = DBHelper.getTestInstanceById(testInstanceId);
            if (t != null) {
                countdown.start(t.getDuration());
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
                    DBHelper.deleteEverything();
                    Toast.makeText(getApplicationContext(), "TestInstance removed from local DB", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No TestInstanceFound", Toast.LENGTH_LONG).show();
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

        // TODO: If exposing deep links into your app, handle intents here.
    }

    public void changeTimer(long milliseconds){
        int sec = (int) (milliseconds / 1000);
        int min = sec / 60;
        sec = sec - (min * 60);
        toolbarTimer.setText(String.format("%d:%02d", min, sec));
    }
    @Override
    public void onBackPressed(){

        final Drawable originalDrawable = ContextCompat.getDrawable(this, R.drawable.ic_action_school);
        final Drawable wrappedDrawable = DrawableCompat.wrap(originalDrawable);
        DrawableCompat.setTint(wrappedDrawable, this.getResources().getColor(R.color.primary));
        new AlertDialog.Builder(this)
                .setTitle("Cancel test")
                .setMessage("Are you sure you want to cancel this test?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countdown.stop();
                        TestListActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(wrappedDrawable)
                .show();
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

    // TODO
    // Different behavior from with TestDetailActivity
    public void nextQuestion() {
        int currentPosition = fragment.getActivatedPosition();
        int total = fragment.getListView().getAdapter().getCount();
        int nextPosition = (currentPosition + 1) % total;
        fragment.getListView().setSelection(nextPosition);
        fragment.getListView().performItemClick(fragment.getListView().getAdapter().getView(nextPosition, null, null), nextPosition, nextPosition);
        fragment.getListView().invalidate();
    }
}
