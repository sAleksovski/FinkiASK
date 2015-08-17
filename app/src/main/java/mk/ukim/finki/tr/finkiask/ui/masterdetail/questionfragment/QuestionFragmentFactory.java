package mk.ukim.finki.tr.finkiask.ui.masterdetail.questionfragment;

import mk.ukim.finki.tr.finkiask.data.models.Question;

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
