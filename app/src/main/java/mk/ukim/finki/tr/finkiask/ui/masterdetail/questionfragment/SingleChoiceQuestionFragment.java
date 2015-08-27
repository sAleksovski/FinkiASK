package mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment;

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
import mk.ukim.finki.tr.finkiask.data.models.Answer;

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
                rb.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.MATCH_PARENT, 1f));
                answersRadioGroup.addView(rb);
                if (a.getIsChecked()) {
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
            previousAnswer.setIsChecked(false);
            previousAnswer.save();
        }
        answer.setIsChecked(true);
        answer.save();
        previousAnswer = answer;

        mItem.setIsAnswered(true);
        mItem.save();
    }
}
