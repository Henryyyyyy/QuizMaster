package edu.duke.compsci290.quizmaster;

/**
 * Created by ola on 2/6/17.
 */

public class OQuestion {

    private String mQuery;
    private Answer[] mAnswers;

    public OQuestion(String q, Answer[] answers) {
        mQuery = q;
        mAnswers = answers;
        mAnswers = new Answer[answers.length];
        System.arraycopy(answers,0, mAnswers,0,answers.length);
    }

    public String getQuery() {
        return mQuery;
    }

    public Answer[] getAllAnswers() {
        return mAnswers;
    }

    public String[] getAnswerStrings() {
        String[] res = new String[mAnswers.length];
        for (int i = 0; i < res.length; i++){
            res[i] = mAnswers[i].getText();
        }
        return res;
    }

    /*
    get the weight of a corresponding answer using its string
    returns -1 if not found
     */
    public int getWeight(String text){
        for (Answer ans : mAnswers){
            if (ans.getText().equals(text)) {return ans.getWeight();}
        }
        return -1;
    }

    public int sizeOfAnswers() {
        return mAnswers.length;
    }

}
