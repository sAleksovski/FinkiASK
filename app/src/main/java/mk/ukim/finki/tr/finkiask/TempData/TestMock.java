package mk.ukim.finki.tr.finkiask.TempData;

import java.util.ArrayList;


public class TestMock {
    private ArrayList<QuestionMock> questionMocks;
    private String name;
    private String subject;
    private String type;

    public TestMock(String name, String type, String subject){
        this.name = name;
        this.type = type;
        this.subject = subject;
    }
    public void setQuestionMocks(ArrayList<QuestionMock> questionMocks){
        this.questionMocks = questionMocks;
    }
    public String getName(){return name;}
    public String getType(){return type;}
    public String getSubject(){return subject;}
    public ArrayList<QuestionMock> getQuestionMocks(){return questionMocks;}

    @Override
    public String toString() {
        return name;
    }
}
