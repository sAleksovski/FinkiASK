package mk.ukim.finki.tr.finkiask;


public class Answer {
    private String text;
    private boolean isCorrect;

    public Answer(String text, boolean isCorrect){
        this.text = text;
        this.isCorrect = isCorrect;
    }
    public String getText(){return text;}
    public boolean getCorrect(){return isCorrect;}
}
