package mk.ukim.finki.tr.finkiask.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.timer.Countdown;
import mk.ukim.finki.tr.finkiask.timer.CountdownInterface;
import mk.ukim.finki.tr.finkiask.database.models.TestInstance;
import mk.ukim.finki.tr.finkiask.masterdetailcontent.TestContent;


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
 * (if present) is a {@link TestDetailFragment}.
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

    public Countdown countdown;

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

        toolbarTimer = (TextView) toolbar.findViewById(R.id.timer);
        countdown = Countdown.getInstance();
        countdown.addTestCountdownInterface(this);

        Bundle b = getIntent().getBundleExtra("testInstance");
        if(b != null) {
            TestInstance t = (TestInstance) b.getSerializable("testInstance");
            if (t != null) {
                TestContent.addAll(t.getQuestions());
                countdown.start(t.getDuration());
            }
        }
        if (findViewById(R.id.test_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((TestListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.test_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    public void changeTimer(long milliseconds){
        int sec = (int) (milliseconds / 1000);
        int min = sec / 60;
        sec = sec - (min * 60);
        toolbarTimer.setText(String.format("%d:%02d", min, sec));
    }
    /**
     * Callback method from {@link TestListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(TestDetailFragment.ARG_ITEM_ID, id);
            TestDetailFragment fragment = new TestDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.test_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, TestDetailActivity.class);
            detailIntent.putExtra(TestDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
