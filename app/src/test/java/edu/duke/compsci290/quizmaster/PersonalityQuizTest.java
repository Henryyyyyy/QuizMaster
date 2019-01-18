package edu.duke.compsci290.quizmaster;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by gabrielcrowell on 3/8/18.
 */
public class PersonalityQuizTest {
    private PersonalityQuiz mCompletePersonalityQuiz;
    private PersonalityQuiz mInCompletePersonalityQuiz;
    private PersonalityQuiz mPersonalityQuiz;
    private String mText1 = "text 1";
    private int mWeight1 = 1;
    private String mText2 = "text 2";
    private int mWeight2 = 0;
    private String mQuery1 = "q";
    private String mQuery2 = "q2";
    private String mStatus; //complete OR incomplete OR ongoing
    private String mQuizTitle = "Quick Quiz";
    private String mComplete = "complete";
    private String mIncomplete = "incomplete";
    private String mStatu = "Status";
    private Answer[] mAnswers = {
            new Answer(mText1, mWeight1),
            new Answer(mText2, mWeight2),
    };
    private OQuestion[] mQuestion = {
            new OQuestion(mQuery1, mAnswers),
            new OQuestion(mQuery2, mAnswers),
    };
    private String mResult[];
    private int mScore[];


    @Before
    public void pretest() {

        int scoreArray[] = {0};
        mScore = scoreArray;
        String resultArray[] = {"empty"};
        mResult = resultArray;
        mStatus = "Status";

        mPersonalityQuiz = new PersonalityQuiz(mQuizTitle, mQuestion, mResult, mScore, mStatus);
        mCompletePersonalityQuiz = new PersonalityQuiz(mQuizTitle, mQuestion, mResult, mScore, mComplete);
        mInCompletePersonalityQuiz = new PersonalityQuiz(mQuizTitle, mQuestion, mResult, mScore, mIncomplete);

    }

    @Test
    public void getTitle() throws Exception {
        assertEquals(mPersonalityQuiz.getTitle(), mQuizTitle);
        assertNotNull(mPersonalityQuiz);
        assertNotNull(mQuizTitle);
    }

    @Test
    public void size() throws Exception {
//        assertEquals(mPersonalityQuiz.size(), mQuestion);
//        assertNull(mQuestion);
    }

    @Test
    public void clear() throws Exception {
//        assertNull(mPersonalityQuiz);
        assertNotNull(mScore);
        assertNotNull(mStatus);
        assertNotNull(mResult);
    }

    @Test
    public void getQuestion() throws Exception {
//        assertNull(mQuestion);
//        assertEquals(mPersonalityQuiz.getQuestion(1), mQuestion);
    }

    @Test
    public void updateScore() throws Exception {
//        assertNull(mPersonalityQuiz);
        assertNotNull(mPersonalityQuiz);
        mPersonalityQuiz.updateScore(mWeight1, mWeight2);
//        assertSame(mPersonalityQuiz, mCompletePersonalityQuiz);
//        assertEquals(Integer.parseInt(mPersonalityQuiz.displayResult()), mScore);
    }

    @Test
    public void displayResult() throws Exception {
        assertNotNull(mPersonalityQuiz);
        assertNotNull(mPersonalityQuiz);
        assertNotNull(mScore);
        assertNotNull(mStatus);
        assertNotNull(mResult);
//        assertNotEquals(mPersonalityQuiz.displayResult(), String.valueOf(mScore));
    }

    @Test
    public void setComplete() throws Exception {
        assertNotEquals(mStatus, mIncomplete);
//        assertSame(mPersonalityQuiz, mComplete);
//        assertNull(mScore);
    }

    @Test
    public void setIncomplete() throws Exception {
        assertNotEquals(mStatus, mIncomplete);
        //assertNotNull(mStatus);
//        assertNull(mStatus);
//        assertNull(mScore);

    }

    @Test
    public void setOngoing() throws Exception {
        assertNotNull(mStatus);
//        assertNull(mStatus);
    }

    @Test
    public void getStatus() throws Exception {
//        assertNull(mStatus);
    }

}