package mk.ukim.finki.tr.finkiask.masterdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.TempData.Question;
import mk.ukim.finki.tr.finkiask.masterdetailcontent.TestContent;


/**
 * A fragment representing a single Test detail screen.
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
            mItem = TestContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        if (mItem != null) {
            switch (mItem.getType())
            {
                case "1": rootView = inflater.inflate(R.layout.fragment_test_detail_radio, container, false);
                    break;
                case "2": rootView = inflater.inflate(R.layout.fragment_test_detail_checkbox, container, false);
                    break;
                case "3": rootView = inflater.inflate(R.layout.fragment_test_detail_text, container, false);
                    break;
                case "4": rootView = inflater.inflate(R.layout.fragment_test_detail_range, container, false);
                    break;
                default: rootView = inflater.inflate(R.layout.fragment_test_detail_radio, container, false);
            }
            ((TextView) rootView.findViewById(R.id.test_text)).setText(mItem.getText());
            if(mItem.getAnswers().size()>1) {
                ((TextView) rootView.findViewById(R.id.test_option1)).setText(mItem.getAnswers().get(0).getText());
                ((TextView) rootView.findViewById(R.id.test_option2)).setText(mItem.getAnswers().get(1).getText());
                ((TextView) rootView.findViewById(R.id.test_option3)).setText(mItem.getAnswers().get(2).getText());
            }
        }

        return rootView;
    }
}
