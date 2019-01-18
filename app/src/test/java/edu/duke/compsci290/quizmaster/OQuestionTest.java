package edu.duke.compsci290.quizmaster;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabrielcrowell on 3/8/18.
 */
public class OQuestionTest {
    private OQuestion mQuery;
    private String mAnswer1 = "Incorrect";
    private int mWeight1 = 0;
    private String mAnswer2 = "Correct";
    private int mWeight2 = 1;
    String mQuer = "Test Question";
    private Answer[] mAnswers = {
            new Answer(mAnswer1, mWeight1),
            new Answer(mAnswer2, mWeight2),
    };

    @Before
    public void setUp() {
        mQuery = new OQuestion(mQuer, mAnswers);
    }


    @Test
    public void gettestQuery() throws Exception {
        assertEquals(mQuery.getQuery(), mQuer);
    }

    @Test
    public void getAllAnswers() throws Exception {
        assertNotSame(mQuery.getAllAnswers(), mAnswers);
    }

    @Test
    public void getAnswerStrings() throws Exception {
        assertNotSame(mQuery.getAnswerStrings(), mAnswers);
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(mQuery.getWeight(mAnswer1), mWeight1);
        assertEquals(mQuery.getWeight(mAnswer2), mWeight2);
    }


}