package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

/**
 * Created by stefan on 8/5/15.
 */
public class RangeQuestionFragment extends BaseQuestionFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_range, container, false);

            TextView tv = (TextView) rootView.findViewById(R.id.question_text);
            tv.setText(mItem.getText());

            final Answer a = mItem.getAnswers().get(0);
            String rangeString = a.getText();
            String[] rangeParts = rangeString.split(":");
            final int min = Integer.parseInt(rangeParts[0]);
            final int max = Integer.parseInt(rangeParts[1]);

            int value = min;
            if (a.getIsAnswered()) {
                value = Integer.parseInt(rangeParts[2]);
            }

            SeekBar sb = (SeekBar) rootView.findViewById(R.id.seekBar);
            sb.setMax(max - min);
            sb.setProgress(value - min);

            final TextView seekBarText = (TextView) rootView.findViewById(R.id.seekBarText);
            seekBarText.setText(String.valueOf(value));

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int mProgress;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mProgress = min + progress;
                    seekBarText.setText(String.valueOf(mProgress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    a.setText(String.format("%d:%d:%d", min, max, mProgress));
                    a.setIsAnswered(true);
                    a.save();
                }
            });

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
}
