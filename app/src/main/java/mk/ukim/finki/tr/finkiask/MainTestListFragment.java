package mk.ukim.finki.tr.finkiask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.tr.finkiask.adapters.TestRecyclerViewAdapter;
import mk.ukim.finki.tr.finkiask.database.models.Test;
import mk.ukim.finki.tr.finkiask.rest.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.rest.TestsRestInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by stefan on 7/31/15.
 */
public class MainTestListFragment extends Fragment {

    RecyclerView mRecyclerView;
    TextView mNoTestsMessage;
    TestRecyclerViewAdapter adapter;

    List<Test> testDataSet;

    public static MainTestListFragment newInstance(String type) {
        MainTestListFragment f = new MainTestListFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        f.setArguments(args);

        return f;
    }

    public String getType() {
        return getArguments().getString("type", "test");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(
                R.layout.fragment_main_test_list, container, false);

        mRecyclerView = (RecyclerView) rl.findViewById(R.id.recyclerview);
        mNoTestsMessage = (TextView) rl.findViewById(R.id.noTestsMessage);

        testDataSet = new ArrayList<>();

        if (getType().equals("test")) {
            testDataSet = getTests();
        } else if (getType().equals("survey")) {
            testDataSet = getTests();
        } else if (getType().equals("anonSurvey")) {
            testDataSet = new ArrayList<>();
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        adapter = new TestRecyclerViewAdapter(testDataSet);
        mRecyclerView.setAdapter(adapter);

        if (testDataSet.size() == 0) {
            mNoTestsMessage.setVisibility(View.VISIBLE);
        }

        return rl;
    }

    private List<Test> getTests() {
        testDataSet = new ArrayList<>();

        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        testsRestAdapter.listAllActive(new Callback<List<Test>>() {
            @Override
            public void success(List<Test> tests, Response response) {
                processTests(tests);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("REST", "Not loaded correctly");
            }
        });

        return testDataSet;
    }

    private void processTests(List<Test> tests) {
        if (tests.size() == 0) {
            mNoTestsMessage.setVisibility(View.VISIBLE);
            return;
        }
        mNoTestsMessage.setVisibility(View.GONE);
        testDataSet.addAll(tests);
        adapter.notifyDataSetChanged();
    }

}
