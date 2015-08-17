package mk.ukim.finki.tr.finkiask.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.models.Question;

public class QuestionListAdapter
        extends ArrayAdapter<Question> {

    public QuestionListAdapter(Context context, List<Question> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Question question = getItem(position);
        // TODO
        // hack
        question = DBHelper.getQuestionById(question.getId());

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_question_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.questionNumber.setText("Question #" + question.getId());
        viewHolder.questionShortText.setText(question.getText());
        viewHolder.questionAnsweredCb.setChecked(question.getIsAnswered());

        final Drawable originalDrawable = ContextCompat.getDrawable(getContext(), R.drawable.question_answered_checkbox);
        final Drawable wrappedDrawable = DrawableCompat.wrap(originalDrawable);
        DrawableCompat.setTint(wrappedDrawable, getContext().getResources().getColor(R.color.question_answered));
        viewHolder.questionAnsweredCb.setButtonDrawable(wrappedDrawable);

        // Return the completed view to render on screen
        return convertView;
    }

    public static class ViewHolder {

        @Bind(R.id.question_number) TextView questionNumber;
        @Bind(R.id.question_short_text) TextView questionShortText;
        @Bind(R.id.question_answered) CheckBox questionAnsweredCb;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
