package mk.ukim.finki.tr.finkiask.ui.masterdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.adapter.QuestionListAdapter;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.models.Question;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment.BaseQuestionFragment;


/**
 * A list fragment representing a list of Tests. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link BaseQuestionFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TestListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sTestCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(long id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sTestCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new QuestionListAdapter(getActivity(), DBHelper.getAllQuestion()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getListView().invalidateViews();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sTestCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        long itemId = ((Question) getListAdapter().getItem(position)).getId();
        mCallbacks.onItemSelected(itemId);
        setActivatedPosition(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public int getActivatedPosition() {
        return mActivatedPosition;
    }
}