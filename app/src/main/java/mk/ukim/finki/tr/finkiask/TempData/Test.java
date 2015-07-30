package mk.ukim.finki.tr.finkiask.TempData;

import java.util.ArrayList;


public class Test {
    private ArrayList<Question> questions;
    private String name;
    private String subject;
    private String type;

    public Test(String name, String type, String subject){
        this.name = name;
        this.type = type;
        this.subject = subject;
    }
    public void setQuestions(ArrayList<Question> questions){
        this.questions = questions;
    }
    public String getName(){return name;}
    public String getType(){return type;}
    public String getSubject(){return subject;}
    public ArrayList<Question> getQuestions(){return questions;}

    @Override
    public String toString() {
        return name;
    }
}
