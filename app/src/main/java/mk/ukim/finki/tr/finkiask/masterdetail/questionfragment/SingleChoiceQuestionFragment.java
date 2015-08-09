package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

public class SingleChoiceQuestionFragment extends BaseQuestionFragment {

    private Answer previousAnswer = null;

    @Bind(R.id.answers_radio_group)
    RadioGroup answersRadioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_single_choice, container, false);

            ButterKnife.bind(this, rootView);

            questionText.setText(mItem.getText());

            for (final Answer a : mItem.getAnswers()) {
                RadioButton rb = new RadioButton(getActivity());
                rb.setText(a.getText());
                answersRadioGroup.addView(rb);
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
