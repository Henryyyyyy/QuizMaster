package edu.duke.compsci290.quizmaster;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by ola on 2/6/17.
 */


public class LinearQuiz implements IQuiz{

    private int mNumOfCorrect = 0;
    private ArrayList<OQuestion> myQuestions;
    private final String mTitle;

    private String mStatus; //complete OR incomplete OR ongoing

    public LinearQuiz(String title, OQuestion[] questions) {
        mTitle = title;
        myQuestions = new ArrayList<>(Arrays.asList(questions));
        mStatus = "incomplete";
    }
    public LinearQuiz(String title, OQuestion[] questions, String status){
        mTitle = title;
        myQuestions = new ArrayList<>(Arrays.asList(questions));
        mStatus = status;
    }

    @Override
    public void setStatus(String source){
        mStatus = source;
    }
    @Override
    public int getScore(){ return mNumOfCorrect; }
    @Override
    public void setmNumOfCorrect(int i) {mNumOfCorrect = i;}
    @Override
    public void setComplete(){
        mStatus = "complete";
    }
    @Override
    public void setIncomplete(){
        mStatus = "incomplete";
    }
    @Override
    public void setOngoing(){
        mStatus = "ongoing";
    }
    @Override
    public String getStatus(){
        return mStatus;
    }
    @Override
    public String getTitle(){ return mTitle; }
    @Override
    public int size(){
        return myQuestions.size();
    }

    @Override
    public void clear(){
        mNumOfCorrect = 0;
        mStatus = "incomplete";
    }

    @Override
    public OQuestion getQuestion(int index){
        if (0 <= index && index < myQuestions.size()){
            return myQuestions.get(index);
        }
        throw new IndexOutOfBoundsException("bad index getting the question at index "+index);
    }

    @Override
    public String updateScore(int dex, int weight){
        int remaining = this.size()-dex-1;
        mNumOfCorrect += weight;
        if (mNumOfCorrect > this.size()) mNumOfCorrect = this.size();
        String s;
        if (dex < this.size()) {
            s = String.format("Scored %d/%d with %d to go", mNumOfCorrect, this.size(), remaining);
        }
        else { //now index == the size of the questions list, which is an overflow
            throw new IndexOutOfBoundsException("index overflow when passing index to updateScore");
        }
        return s;
    }

    /*
     * happens when quiz ends. return a string to display to activity
     */
    @Override
    public String displayResult(){
        mStatus = "complete";
        String s = String.format(" you scored %d out of %d", mNumOfCorrect, this.size());
        return s;
    }

}
