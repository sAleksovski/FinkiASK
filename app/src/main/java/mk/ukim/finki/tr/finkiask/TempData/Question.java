package mk.ukim.finki.tr.finkiask.TempData;

import java.util.ArrayList;

public class Question {
    private String text;
    private String type;
    private ArrayList<Answer> answers;

    public Question(String text, String type, ArrayList<Answer> answers){
        this.text = text;
        this.type = type;
        this.answers = answers;
    }
    public String getText(){return text;}
    public String getType(){return type;}
    public ArrayList<Answer> getAnswers(){return answers;}
}

