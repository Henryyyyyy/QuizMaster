package edu.duke.compsci290.quizmaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Henry_Yu on 3/6/18.
 */

public class PersonalityQuiz implements IQuiz{

    private String[] mResult;
    private int[] mScore; //matches the jstring that's used to process result

    private int mWeight = 0; //keeps track of the score the user has so far
    private ArrayList<OQuestion> myQuestions;
    private final String mTitle;
    private String mStatus;

    public PersonalityQuiz(String title, OQuestion[] questions, String[] res, int[] score) {
        mTitle = title;
        myQuestions = new ArrayList<>(Arrays.asList(questions));
        mResult = res;
        mScore = score;
    }
    public PersonalityQuiz(String title, OQuestion[] questions, String[] res, int[] score, String status) {
        mTitle = title;
        myQuestions = new ArrayList<>(Arrays.asList(questions));
        mResult = res;
        mScore = score;
        mStatus = status;
    }

    @Override
    public String getTitle(){ return mTitle; }
    @Override
    public int getScore(){ return mWeight; }
    @Override
    public void setmNumOfCorrect(int i) {mWeight = i;}


    @Override
    public int size(){
        return myQuestions.size();
    }
    @Override
    public void setStatus(String source) {
        mStatus = source;
    }
    @Override
    public void clear(){
        mWeight = 0;
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
        mWeight += weight;
        String s;
        if (dex < this.size()) {
            s = String.format(" %d in total with %d to go", this.size(), remaining);
        }
        else { //now index == the size of the questions list, which is an overflow
            throw new IndexOutOfBoundsException("index overflow when passing index to updateScore");
        }
        return s;
    }

    @Override
    public String displayResult(){
        mStatus = "complete";

        //s holds the result of the personality quiz. Get from the json file
        String s = "possible overflow/underflow of the total score in personality quiz";
        for (int i = 0; i < mResult.length; i++){
            if (mWeight >= mScore[i] && mWeight < mScore[i+1]){
                s = mResult[i];
                break;
            }
        }
        return s;
    }

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
}
