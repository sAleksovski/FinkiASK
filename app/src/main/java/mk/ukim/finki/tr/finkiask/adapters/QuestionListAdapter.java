package mk.ukim.finki.tr.finkiask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.models.Question;
import mk.ukim.finki.tr.finkiask.helper.Strings;

/**
 * Created by stefan on 8/3/15.
 */
public class QuestionListAdapter
        extends ArrayAdapter<Question> {

    public QuestionListAdapter(Context context, List<Question> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Question question = getItem(position);

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
        viewHolder.questionNumber.setText(question.getId()+"");
        viewHolder.questionShortText.setText(Strings.subString(question.getText(), 20));
        // TODO check from db
        viewHolder.questionAnsweredCb.setChecked(false);
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