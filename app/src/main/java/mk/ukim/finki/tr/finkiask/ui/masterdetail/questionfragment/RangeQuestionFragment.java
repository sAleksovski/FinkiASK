package mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.models.Answer;

public class RangeQuestionFragment extends BaseQuestionFragment {

    @Bind(R.id.seekBar) DiscreteSeekBar seekBar;
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
            if (a.getIsChecked()) {
                value = Integer.parseInt(rangeParts[2]);
            }

            seekBar.setMin(min);
            seekBar.setMax(max);
            seekBar.setProgress(value);

            seekBarText.setText(String.valueOf(value));

            seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                int mProgress;

                @Override
                public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
                    mProgress = i;
                    seekBarText.setText(String.valueOf(mProgress));
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
                    a.setText(String.format("%d:%d:%d", min, max, mProgress));
                    a.setIsChecked(true);
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
