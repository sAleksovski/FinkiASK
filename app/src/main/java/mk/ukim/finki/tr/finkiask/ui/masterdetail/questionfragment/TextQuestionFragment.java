package mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.models.Answer;

public class TextQuestionFragment extends BaseQuestionFragment {

    @Bind(R.id.text_answer) EditText textAnswer;
    Answer a;

    String oldText = "";

    Timer timer = new Timer();
    public static int DELAY = 50;

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

            if (a.getText() == null) {
                oldText = "";
            } else {
                oldText = a.getText();
            }

            textAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(timer != null) {
                        timer.cancel();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            a.setText(textAnswer.getText().toString());
                            if (DBHelper.isTestInstanceFound()) {
                                a.save();
                            }

                            mItem.setIsAnswered(a.getText().length() > 0);
                            mItem.setIsSynced(false);
                            if (DBHelper.isTestInstanceFound()) {
                                mItem.save();
                            }

                            if ( ! textAnswer.getText().toString().equals(oldText)) {
                                isChanged = true;
                            }
                        }

                    }, DELAY);
                }
            });

            return rootView;
        }

        return null;
    }

}
