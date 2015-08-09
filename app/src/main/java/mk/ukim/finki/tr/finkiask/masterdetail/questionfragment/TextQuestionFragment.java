package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

public class TextQuestionFragment extends BaseQuestionFragment {

    EditText et;
    Answer a;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_text, container, false);

            TextView tv = (TextView) rootView.findViewById(R.id.question_text);
            tv.setText(mItem.getText());

            et = (EditText) rootView.findViewById(R.id.test_edit);
            a = mItem.getAnswers().get(0);

            et.setText(a.getText());

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
    public void onPause() {
        super.onPause();

        a.setText(et.getText().toString());
        a.save();

        mItem.setIsAnswered(a.getText().length() > 0);
        mItem.save();
    }
}
