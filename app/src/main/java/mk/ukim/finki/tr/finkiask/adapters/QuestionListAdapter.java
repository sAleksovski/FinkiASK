package mk.ukim.finki.tr.finkiask.adapters;

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

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.DBHelper;
import mk.ukim.finki.tr.finkiask.database.models.Question;

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
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_question_list, parent, false);
            viewHolder.questionNumber = (TextView) convertView.findViewById(R.id.question_number);
            viewHolder.questionShortText = (TextView) convertView.findViewById(R.id.question_short_text);
            viewHolder.questionAnsweredCb = (CheckBox) convertView.findViewById(R.id.question_answered);
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

        TextView questionNumber;
        TextView questionShortText;
        CheckBox questionAnsweredCb;

        public ViewHolder() {}

    }
}
