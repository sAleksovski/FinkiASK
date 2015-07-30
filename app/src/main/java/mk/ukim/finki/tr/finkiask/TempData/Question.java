package mk.ukim.finki.tr.finkiask.TempData;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
    private String id;
    private String text;
    private String type;
    private ArrayList<Answer> answers;

    public Question(String id, String text, String type, ArrayList<Answer> answers){
        this.id = id;
        this.text = text;
        this.type = type;
        this.answers = answers;
    }
    public String getID(){return id;}
    public String getText(){return text;}
    public String getType(){return type;}
    public ArrayList<Answer> getAnswers(){return answers;}

    @Override
    public String toString() {
        return "Question "+id;
    }
}


