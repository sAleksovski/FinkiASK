package mk.ukim.finki.tr.finkiask.masterdetail;

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

            if(mItem.getAnswers().size() > 1) {
                ((TextView) rootView.findViewById(R.id.test_option1)).setText(mItem.getAnswers().get(0).getText());
                ((TextView) rootView.findViewById(R.id.test_option2)).setText(mItem.getAnswers().get(1).getText());
                ((TextView) rootView.findViewById(R.id.test_option3)).setText(mItem.getAnswers().get(2).getText());
            }

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.btn_next_question);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Go to next question", Snackbar.LENGTH_LONG).show();
                }
            });

            return rootView;
        }

        return null;
    }
}
