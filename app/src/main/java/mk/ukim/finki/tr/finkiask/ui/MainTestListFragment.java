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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.adapter.TestRecyclerViewAdapter;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.api.ResponseStatus;
import mk.ukim.finki.tr.finkiask.data.api.RestError;
import mk.ukim.finki.tr.finkiask.data.api.ServerResponseWrapper;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestInterface;
import mk.ukim.finki.tr.finkiask.data.models.Answer;
import mk.ukim.finki.tr.finkiask.data.models.Question;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import mk.ukim.finki.tr.finkiask.ui.dialog.AnotherTestDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.BaseDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.InsertPasswordDialog;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.TestListActivity;
import mk.ukim.finki.tr.finkiask.util.AuthHelper;
import mk.ukim.finki.tr.finkiask.util.timer.TimeUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
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
        testDataSet.removeAll(testDataSet);
        testDataSet.addAll(tests);

        if (testDataSet.size() == 0) {
            mNoTestsMessage.setVisibility(View.VISIBLE);
            return;
        }
        mNoTestsMessage.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void testSelected(final TestPOJO test) {
        final boolean[] startNewTest = {true};

        if (DBHelper.isTestInstanceFound()) {
            TestInstance testInstance = DBHelper.getSingleTestInstance();
            if (TimeUtils.remainingTime(testInstance, TimeUnit.MINUTES) <= 0) {
                DBHelper.deleteEverything();
            }
        }

        if (DBHelper.isTestInstanceFound() && DBHelper.getSingleTestInstance().getId() != test.getId()) {
            startNewTest[0] = false;

            AnotherTestDialogFragment.newInstance(new BaseDialogFragment.OnPositiveCallback() {
                @Override
                public void onPositiveClick(String data) {
                    submitPreviousTest();
                    InsertPasswordDialog.newInstance(test.getDuration(), new BaseDialogFragment.OnPositiveCallback() {
                        @Override
                        public void onPositiveClick(String data) {
                            startTest(getActivity(), test.getId(), data);
                        }
                    }).show(getFragmentManager(), "fragment_start_test");
                }
            }).show(getFragmentManager(), "fragment_another_test");
        }

        if (DBHelper.isTestInstanceFound() && DBHelper.getSingleTestInstance().getId() == test.getId()) {
            Intent intent = new Intent(getActivity().getApplicationContext(), TestListActivity.class);
            intent.putExtra("testInstanceId", DBHelper.getSingleTestInstance().getId());

            startActivity(intent);
            return;
        }

        if (!startNewTest[0]) {
            return;
        }

        InsertPasswordDialog.newInstance(test.getDuration(), new BaseDialogFragment.OnPositiveCallback() {
            @Override
            public void onPositiveClick(String data) {
                startTest(getActivity(), test.getId(), data);
            }
        }).show(getFragmentManager(), "fragment_start_test");
    }

    private void submitPreviousTest() {
        List<Question> unsynced = DBHelper.getUnsyncedQuestions();
        for (final Question q : unsynced) {
            List<Answer> answers = q.getAnswers();
            TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
            testsRestAdapter.postAnswer(AuthHelper.getSessionCookie(getActivity().getApplicationContext()),
                    q.getTestInstance().getId(), answers, new Callback<ServerResponseWrapper<TestInstance>>() {

                        @Override
                        public void success(ServerResponseWrapper<TestInstance> serverResponseWrapper, Response response) {
                            Log.d("SAVE", serverResponseWrapper.getResponseStatus());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                        }
                    });
        }
        DBHelper.deleteEverything();
    }

    private void startTest(final Context context, long id, String password) {
        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        testsRestAdapter.getTest(id, password, new Callback<ServerResponseWrapper<TestInstance>>() {

            @Override
            public void success(ServerResponseWrapper<TestInstance> serverResponseWrapper, Response response) {
                for (Header header : response.getHeaders()) {
                    if (header.getName() != null && header.getName().equals("Set-Cookie")) {
                        if (header.getValue().startsWith("JSESSIONID")) {
                            String sessionId = header.getValue().split(";")[0];
                            AuthHelper.setSessionCookie(context, sessionId);
                        }
                    }
                }

                if (serverResponseWrapper.getResponseStatus().equals(ResponseStatus.SUCCESS)) {
                    DBHelper.deleteEverything();
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

    @Override
    public void onResume() {
        if (testDataSet.size() > 0) {
            adapter.notifyDataSetChanged();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
            adapter = new TestRecyclerViewAdapter(testDataSet, new TestRecyclerViewAdapter.TestRecyclerViewAdapterClickCallback() {
                @Override
                public void onItemClick(int position) {
                    testSelected(testDataSet.get(position));
                }
            });
            mRecyclerView.setAdapter(adapter);
        }

        super.onResume();
    }
}
