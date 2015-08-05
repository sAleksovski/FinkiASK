package mk.ukim.finki.tr.finkiask.masterdetail.questionfragment;

import mk.ukim.finki.tr.finkiask.database.models.Question;

/**
 * Created by stefan on 8/5/15.
 */
public class QuestionFragmentFactory {

    public static BaseQuestionFragment getQuestionFragment(String questionType) {

        if (questionType.equals(Question.SINGLE_CHOICE)) {
            return new SingleChoiceQuestionFragment();
        } else if (questionType.equals(Question.MULTIPLE_CHOICE)) {
            return new MultipleChoiceQuestionFragment();
        } else if (questionType.equals(Question.RANGE)) {
            return new RangeQuestionFragment();
        } else if (questionType.equals(Question.TEXT)) {
            return new TextQuestionFragment();
        }

        throw new IllegalArgumentException();
    }

}
