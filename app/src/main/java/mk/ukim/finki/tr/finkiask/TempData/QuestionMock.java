package mk.ukim.finki.tr.finkiask.TempData;

import java.util.ArrayList;

public class QuestionMock {
    private String text;
    private String type;
    private ArrayList<AnswerMock> answerMocks;

    public QuestionMock(String text, String type, ArrayList<AnswerMock> answerMocks){
        this.text = text;
        this.type = type;
        this.answerMocks = answerMocks;
    }
    public String getText(){return text;}
    public String getType(){return type;}
    public ArrayList<AnswerMock> getAnswerMocks(){return answerMocks;}
}

