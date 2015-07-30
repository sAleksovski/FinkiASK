package mk.ukim.finki.tr.finkiask.TempData;


public class AnswerMock {
    private String text;
    private boolean isCorrect;

    public AnswerMock(String text, boolean isCorrect){
        this.text = text;
        this.isCorrect = isCorrect;
    }
    public String getText(){return text;}
    public boolean getCorrect(){return isCorrect;}
}
