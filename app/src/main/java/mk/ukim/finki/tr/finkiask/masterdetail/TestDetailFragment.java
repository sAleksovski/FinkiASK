package mk.ukim.finki.tr.finkiask.masterdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Question;
import mk.ukim.finki.tr.finkiask.masterdetailcontent.TestContent;


/**
 * A fragment representing a single TestPOJO detail screen.
 * This fragment is either contained in a {@link TestListActivity}
 * in two-pane mode (on tablets) or a {@link TestDetailActivity}
 * on handsets.
 */
public class TestDetailFragment extends Fragment {

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of request
     * for next question.
     */
    public interface NextQuestionCallback {
        /**
         * Callback for when the fab for next question has been clicked.
         */
        void onNextQuestion(long thisQuestionId);
    }

    /**
     * A dummy implementation of the {@link NextQuestionCallback} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static NextQuestionCallback sTestCallbacks = new NextQuestionCallback() {
        @Override
        public void onNextQuestion(long thisQuestionId) {
        }
    };

    private NextQuestionCallback mCallbacks = sTestCallbacks;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Question mItem;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // TODO read from db
            mItem = TestContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(mItem.getTemplateFile(), container, false);

            TextView tv = (TextView) rootView.findViewById(R.id.question_text);
            tv.setText(mItem.getText());

//            if(mItem.getAnswers().size() > 1) {
//                ((TextView) rootView.findViewById(R.id.test_option1)).setText(mItem.getAnswers().get(0).getText());
//                ((TextView) rootView.findViewById(R.id.test_option2)).setText(mItem.getAnswers().get(1).getText());
//                ((TextView) rootView.findViewById(R.id.test_option3)).setText(mItem.getAnswers().get(2).getText());
//            }

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.btn_next_question);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Go to next question", Snackbar.LENGTH_LONG).show();
                    mCallbacks.onNextQuestion(mItem.getId());
                }
            });

            return rootView;
        }

        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof NextQuestionCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (NextQuestionCallback) activity;
    }

}
