package mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.models.Answer;

public class MultipleChoiceQuestionFragment extends BaseQuestionFragment {

    @Bind(R.id.answers_checkbox_group) LinearLayout answersCheckboxGroup;

    int checked = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_multiple_choice, container, false);

            ButterKnife.bind(this, rootView);

            questionText.setText(mItem.getText());

            for (final Answer a : mItem.getAnswers()) {
                CheckBox cb = new CheckBox(getActivity());
                cb.setText(a.getText());
                cb.setChecked(a.getIsChecked());
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAnswerChanged(a);
                    }
                });
                answersCheckboxGroup.addView(cb);

                if (a.getIsChecked()) {
                    checked++;
                }
            }

            isChanged = false;

            return rootView;
        }

        return null;
    }

    private void onAnswerChanged(Answer a) {
        a.setIsChecked(!a.getIsChecked());
        a.save();

        isChanged = true;

        if (a.getIsChecked()) {
            checked++;
        } else {
            checked--;
        }

        mItem.setIsAnswered(checked > 0);
        mItem.save();
    }
}
