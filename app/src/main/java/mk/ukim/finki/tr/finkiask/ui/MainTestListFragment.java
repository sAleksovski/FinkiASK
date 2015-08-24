package mk.ukim.finki.tr.finkiask.ui;

import android.content.Context;
import android.content.Intent;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.adapter.TestRecyclerViewAdapter;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.api.RestError;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestInterface;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.data.pojo.TestInstanceWrapperPOJO;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import mk.ukim.finki.tr.finkiask.ui.dialog.BaseDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.StartTestDialog;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.TestListActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainTestListFragment extends Fragment {

    @Bind(R.id.tests_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.no_tests_message)
    TextView mNoTestsMessage;
    TestRecyclerViewAdapter adapter;

    List<TestPOJO> testDataSet;

    public static MainTestListFragment newInstance(String type) {
        MainTestListFragment f = new MainTestListFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        f.setArguments(args);

        return f;
    }

    public String getType() {
        return getArguments().getString("type", TestPOJO.SURVEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(
                R.layout.fragment_main_test_list, container, false);

        ButterKnife.bind(this, rl);

        testDataSet = new ArrayList<>();
        getTests();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        adapter = new TestRecyclerViewAdapter(testDataSet, new TestRecyclerViewAdapter.TestRecyclerViewAdapterClickCallback() {
            @Override
            public void onItemClick(int position) {
                testSelected(testDataSet.get(position));
            }
        });
        mRecyclerView.setAdapter(adapter);

        if (testDataSet.size() == 0) {
            mNoTestsMessage.setVisibility(View.VISIBLE);
        }

        return rl;
    }

    private void getTests() {
        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        testsRestAdapter.listAllActive(getType(), new Callback<List<TestPOJO>>() {
            @Override
            public void success(List<TestPOJO> tests, Response response) {
                processTests(tests);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("REST", "Not loaded correctly");
            }
        });
    }

    private void processTests(List<TestPOJO> tests) {

        testDataSet.addAll(tests);

        if (testDataSet.size() == 0) {
            mNoTestsMessage.setVisibility(View.VISIBLE);
            return;
        }
        mNoTestsMessage.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void testSelected(final TestPOJO test) {
        // TODO
        // if tests is locked, InsertPasswordDialog

        StartTestDialog.newInstance(test.getDuration(),
                new BaseDialogFragment.OnPositiveCallback() {
                    @Override
                    public void onPositiveClick() {
                        // TODO
                        // startTest(getActivity(), test.getId());
                        startTest(getActivity(), 1);
                    }
                }).show(getFragmentManager(), "fragment_start_test");
    }

    private void startTest(final Context context, long id) {
        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        //TODO get password from user with form
        //TODO get session from response cookie (can be stored in shared preferences)
        testsRestAdapter.getTest(id, "password123", new Callback<TestInstanceWrapperPOJO>() {

            @Override
            public void success(TestInstanceWrapperPOJO testInstanceWrapperPOJO, Response response) {
                TestInstance testInstance = testInstanceWrapperPOJO.getData();
                DBHelper.saveTestInstanceToDb(testInstance);

                Intent intent = new Intent(context, TestListActivity.class);
                intent.putExtra("testInstanceId", testInstance.getId());

                context.startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("REST", "Not loaded correctly");
                if (error.getResponse() != null) {
                    RestError body = (RestError) error.getBodyAs(RestError.class);
                    Log.e("RESTError", body.errorDetails);
                }
            }
        });
    }

}
