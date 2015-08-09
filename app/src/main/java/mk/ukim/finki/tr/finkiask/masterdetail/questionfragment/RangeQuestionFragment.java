package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Answer;

public class RangeQuestionFragment extends BaseQuestionFragment {

    @Bind(R.id.seekBar) SeekBar seekbar;
    @Bind(R.id.seekBarText) TextView seekBarText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mItem != null) {
            final View rootView = inflater.inflate(R.layout.fragment_question_range, container, false);

            ButterKnife.bind(this, rootView);

            questionText.setText(mItem.getText());

            final Answer a = mItem.getAnswers().get(0);
            String rangeString = a.getText();
            String[] rangeParts = rangeString.split(":");
            final int min = Integer.parseInt(rangeParts[0]);
            final int max = Integer.parseInt(rangeParts[1]);

            int value = min;
            if (a.getIsAnswered()) {
                value = Integer.parseInt(rangeParts[2]);
            }

            seekbar.setMax(max - min);
            seekbar.setProgress(value - min);

            seekBarText.setText(String.valueOf(value));

            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

                    mItem.setIsAnswered(true);
                    mItem.save();
                }
            });

            return rootView;
        }

        return null;
    }
}
