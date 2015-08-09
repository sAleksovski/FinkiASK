package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.ukim.finki.tr.finkiask.database.DBHelper;
import mk.ukim.finki.tr.finkiask.database.models.Question;

public abstract class BaseQuestionFragment extends Fragment {

    public static final String ARG_QUESTION_ID = "item_id";

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

    private static NextQuestionCallback sTestCallbacks = new NextQuestionCallback() {
        @Override
        public void onNextQuestion(long thisQuestionId) {
        }
    };

    protected NextQuestionCallback mCallbacks = sTestCallbacks;

    protected Question mItem;

    public BaseQuestionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_QUESTION_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // TODO read from db
            mItem = DBHelper.getQuestionById(getArguments().getLong(ARG_QUESTION_ID));
        }
    }

    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

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
