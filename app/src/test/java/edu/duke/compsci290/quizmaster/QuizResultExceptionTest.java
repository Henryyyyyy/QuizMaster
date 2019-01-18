package edu.duke.compsci290.quizmaster;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabrielcrowell on 3/9/18.
 */
public class QuizResultExceptionTest {

    private String quizString = "This is a Quiz Result Exception";

    @Test
    public void createException() throws Exception {
        QuizResultException quizResultException = new QuizResultException(quizString);
        assertEquals(quizResultException.toString(), quizString);

    }

}

