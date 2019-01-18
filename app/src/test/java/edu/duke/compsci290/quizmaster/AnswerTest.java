package edu.duke.compsci290.quizmaster;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabrielcrowell on 3/8/18.
 */
public class AnswerTest {
    private Answer testAnswer;
    private String TEXTdummy = "dummytext";
    private int WEIGHTdummy = 0;
    private String s = TEXTdummy;

    @Before
    public void setAnswer() {
        testAnswer = new Answer(TEXTdummy, WEIGHTdummy);
    }


    @Test
    public void getText() throws Exception {
        assertEquals(testAnswer.getText(), TEXTdummy);
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(testAnswer.getWeight(), WEIGHTdummy);
    }

    @Test
    public void setText() throws Exception {

        assertTrue(s != null);

    }

}