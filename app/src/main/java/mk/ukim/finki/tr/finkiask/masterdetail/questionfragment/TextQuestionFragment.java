package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

public class TextQuestionFragment extends BaseQuestionFragment {

    @Bind(R.id.text_answer) EditText textAnswer;
    Answer a;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_text, container, false);

            ButterKnife.bind(this, rootView);

            questionText.setText(mItem.getText());

            a = mItem.getAnswers().get(0);

            textAnswer.setText(a.getText());

            return rootView;
        }

        return null;
    }

    @Override
    public void onPause() {
        super.onPause();

        a.setText(textAnswer.getText().toString());
        a.save();

        mItem.setIsAnswered(a.getText().length() > 0);
        mItem.save();
    }
}
