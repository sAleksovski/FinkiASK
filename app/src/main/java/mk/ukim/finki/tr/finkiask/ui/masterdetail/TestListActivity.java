package mk.ukim.finki.tr.finkiask.ui.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.ui.MainActivity;
import mk.ukim.finki.tr.finkiask.ui.dialog.BaseDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.CancelTestDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment.BaseQuestionFragment;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment.QuestionFragmentFactory;
import mk.ukim.finki.tr.finkiask.util.timer.CountdownHelper;


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
public class TestListActivity extends BaseTestActivity
        implements TestListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    @Nullable
    @Bind(R.id.btn_next_question)
    FloatingActionButton btnNextQuestion;

    private TestListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_test_list);
//        setContentView(R.layout.activity_test_list);
        ButterKnife.bind(this);

        final ActionBar ab = super.getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_action_school);
        ab.setDisplayHomeAsUpEnabled(true);

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
        ;

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
    public void onBackPressed() {
        CancelTestDialogFragment.newInstance(new BaseDialogFragment.OnPositiveCallback() {
            @Override
            public void onPositiveClick(String data) {
                countdown.stop();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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
