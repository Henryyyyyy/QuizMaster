package edu.duke.compsci290.quizmaster;

/**
 * Created by Henry_Yu on 3/6/18.
 */


public class Answer {

    /**
     * The Answer object has a string (TEXT) of the answer and a corresponding weight (WEIGHT)
     */
    private String TEXT;
    private int WEIGHT;

    public Answer(String s, int i){
        TEXT = s;
        WEIGHT = i;
    }

    public String getText(){return TEXT;}
    public int getWeight(){return WEIGHT;}
    public void setText(String s){TEXT = s;}

}
