package mk.ukim.finki.tr.finkiask.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.adapter.TestRecyclerViewAdapter;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.api.ResponseStatus;
import mk.ukim.finki.tr.finkiask.data.api.RestError;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestInterface;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.data.api.ServerResponseWrapper;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import mk.ukim.finki.tr.finkiask.ui.dialog.BaseDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.InsertPasswordDialog;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.TestListActivity;
import mk.ukim.finki.tr.finkiask.util.AuthHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        InsertPasswordDialog.newInstance(test.getDuration(), new BaseDialogFragment.OnPositiveCallback() {
            @Override
            public void onPositiveClick(String data) {
                startTest(getActivity(), test.getId(), data);
            }
        }).show(getFragmentManager(), "fragment_start_test");
    }

    private void startTest(final Context context, long id, String password) {
        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        testsRestAdapter.getTest(id, password, new Callback<ServerResponseWrapper>() {

            @Override
            public void success(ServerResponseWrapper serverResponseWrapper, Response response) {
                for (Header header : response.getHeaders()) {
                    if (header.getName() != null && header.getName().equals("Set-Cookie")) {
                        if (header.getValue().startsWith("JSESSIONID")) {
                            String sessionId = header.getValue().split(";")[0];
                            AuthHelper.setSessionCookie(context, sessionId);
                        }
                    }
                }

                if (serverResponseWrapper.getResponseStatus().equals(ResponseStatus.SUCCESS)) {
                    TestInstance testInstance = serverResponseWrapper.getData();
                    testInstance.setOpenedTime(new Date());
                    DBHelper.saveTestInstanceToDb(testInstance);

                    Intent intent = new Intent(context, TestListActivity.class);
                    intent.putExtra("testInstanceId", testInstance.getId());

                    context.startActivity(intent);
                } else if (serverResponseWrapper.getResponseStatus().equals(ResponseStatus.ERROR)) {
                    Snackbar.make(mRecyclerView, serverResponseWrapper.getDescription(), Snackbar.LENGTH_LONG).show();
                }

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
