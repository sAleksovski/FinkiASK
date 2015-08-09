package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

public class SingleChoiceQuestionFragment extends BaseQuestionFragment {

    private Answer previousAnswer = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_single_choice, container, false);

            TextView tv = (TextView) rootView.findViewById(R.id.question_text);
            tv.setText(mItem.getText());

            RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.answers_radio_group);
            for (final Answer a : mItem.getAnswers()) {
                RadioButton rb = new RadioButton(getActivity());
                rb.setText(a.getText());
                radioGroup.addView(rb);
                if (a.getIsAnswered()) {
                    rb.setChecked(true);
                    previousAnswer = a;
                }
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAnswerChanged(a);
                    }
                });
            }

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

    private void onAnswerChanged(Answer answer) {
        if (previousAnswer != null) {
            previousAnswer.setIsAnswered(false);
            previousAnswer.save();
        }
        answer.setIsAnswered(true);
        answer.save();
        previousAnswer = answer;

        mItem.setIsAnswered(true);
        mItem.save();
    }
}
