package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

public class MultipleChoiceQuestionFragment extends BaseQuestionFragment {

    int checked = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_multiple_choice, container, false);

            TextView tv = (TextView) rootView.findViewById(R.id.question_text);
            tv.setText(mItem.getText());

            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.answers_checkbox_group);
            for (final Answer a : mItem.getAnswers()) {
                CheckBox cb = new CheckBox(getActivity());
                cb.setText(a.getText());
                cb.setChecked(a.getIsAnswered());
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAnswerChanged(a);
                    }
                });
                ll.addView(cb);

                if (a.getIsAnswered()) {
                    checked++;
                }
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

    private void onAnswerChanged(Answer a) {
        a.setIsAnswered(!a.getIsAnswered());
        a.save();

        if (a.getIsAnswered()) {
            checked++;
        } else {
            checked--;
        }

        mItem.setIsAnswered(checked > 0);
        mItem.save();
    }
}
