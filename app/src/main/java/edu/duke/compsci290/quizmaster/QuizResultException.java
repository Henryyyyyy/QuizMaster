package edu.duke.compsci290.quizmaster;

/**
 * Created by Henry_Yu on 3/3/18.
 */

public class QuizResultException extends Exception {
    private final String mMessage;

    public QuizResultException(String message){
        mMessage = message;
    }
    @Override
    public String toString(){
        return mMessage;
    }
}

