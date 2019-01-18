package edu.duke.compsci290.quizmaster;


/**
 * Created by Henry_Yu on 3/3/18.
 */

public interface IQuiz {

    String getTitle();
    String getStatus();
    int getScore();

    void setStatus(String source);

    void setmNumOfCorrect(int i);

    /**
     * get the Question object from the quiz at index
     * @param index of the question
     * @return a Question object at that index
     */
    OQuestion getQuestion(int index);

    /*
     * length of the question list
     */
    int size();


    String updateScore(int dex, int weight);

    /*
     * happens when quiz ends. return a string to display to activity
     */
    String displayResult();

    /*
     * set the status of the quiz
     */
    void setComplete();
    void setIncomplete();
    void setOngoing();

    /*
     * reset GV for restarting a quiz
     */
    void clear();


}
