package mk.ukim.finki.tr.finkiask.TempData;


import java.io.Serializable;

public class Answer implements Serializable{
    private String text;
    private boolean isCorrect;

    public Answer(String text, boolean isCorrect){
        this.text = text;
        this.isCorrect = isCorrect;
    }
    public String getText(){return text;}
    public boolean isCorrect(){return isCorrect;}
}