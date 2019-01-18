package edu.duke.compsci290.quizmaster;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


/**
 * Created by gabrielcrowell on 3/8/18.
 */
public class LinearQuizTest {
    private LinearQuiz mCompleteLinearQuiz;
    private LinearQuiz mIncompleteLinearQuiz;
    private LinearQuiz mLinearQuiz;
    private ArrayList<OQuestion> mQuestions;
    private String mTitle;
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
    private int mNumOfCorrect;
    private String mStatu = "Status";
    private Answer[] mAnswers = {
            new Answer(mText1, mWeight1),
            new Answer(mText2, mWeight2),
    };
    private OQuestion[] mQuestion = {
            new OQuestion(mQuery1, mAnswers),
            new OQuestion(mQuery2, mAnswers),
    };

    @Before
    public void pretest() {

        mLinearQuiz = new LinearQuiz(mQuizTitle, mQuestion, mStatus);
        mCompleteLinearQuiz = new LinearQuiz(mQuizTitle, mQuestion, mComplete);
        mIncompleteLinearQuiz = new LinearQuiz(mQuizTitle, mQuestion, mIncomplete);


/*
        mTitle = mQuizTitle;
        mQuestions = new ArrayList<>();
        mNumOfCorrect = 0;
        mLinearQuiz = new LinearQuiz(mTitle, mQuestions.toArray(new OQuestion[mQuestions.size()]), mStatus);
        mIncompleteLinearQuiz = new LinearQuiz(mTitle, mQuestions.toArray(new OQuestion[mQuestions.size()]), mIncomplete);
*/

    }

    @Test
    public void checkifComplete() throws Exception {
        //assertSame(mCompleteLinearQuiz, mLinearQuiz.setComplete());
        assertNotEquals(mStatus, mComplete);
        assertNull(mStatus);
    }

    @Test
    public void setIncomplete() throws Exception {
        assertNotEquals(mStatus, mIncomplete);
        assertNull(mStatus);
    }
    @Test
    public void setOngoing() throws Exception {
        assertNull(mStatus);
    }

    @Test
    public void gettheStatus() throws Exception {
        //assertEquals(mStatus.getStatus(), mStatu);
        assertNull(mStatus);
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals(mLinearQuiz.getTitle(), mQuizTitle);
    }
//
//    @Test
//    public void size() throws Exception {
//        int mQuestionsize = mQuestions.size();
//        assertNotNull(mQuestions);
//        assertEquals(mQuestionsize, 0);
//    }

    @Test
    public void clear() throws Exception {

    }

    @Test
    public void getQuestion() throws Exception {

        assertNotEquals(mLinearQuiz.getQuestion(1), mQuizTitle);
        assertNotEquals(mLinearQuiz.getQuestion(0), mQuizTitle);


    }

    @Test
    public void updateScore() throws Exception {
        int cScore = mNumOfCorrect;
        mLinearQuiz.updateScore(0, 1);
        assertNotEquals(mLinearQuiz.displayResult(), "");
    }

    @Test
    public void displayResult() throws Exception {
        assertNotEquals(mLinearQuiz.displayResult(), "");
    }
}