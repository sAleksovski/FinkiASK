package mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.api.ServerResponseWrapper;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.data.api.TestsRestInterface;
import mk.ukim.finki.tr.finkiask.data.models.Answer;
import mk.ukim.finki.tr.finkiask.data.models.Question;
import mk.ukim.finki.tr.finkiask.util.AuthHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BaseQuestionFragment extends Fragment {

    public static final String ARG_QUESTION_ID = "item_id";

    protected Question mItem;

    @Bind(R.id.question_text) TextView questionText;

    public BaseQuestionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_QUESTION_ID)) {
            mItem = DBHelper.getQuestionById(getArguments().getLong(ARG_QUESTION_ID));
        }
    }

    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onPause() {
        super.onPause();

        List<Answer> answers = mItem.getAnswers();

        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        testsRestAdapter.postAnswer(AuthHelper.getSessionCookie(getActivity()), mItem.getTestInstance().getId(), answers, new Callback<ServerResponseWrapper>() {

            @Override
            public void success(ServerResponseWrapper serverResponseWrapper, Response response) {
                Log.d("SAVE", serverResponseWrapper.getResponseStatus());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("FAILURE", error.getMessage());
            }
        });
    }
}
