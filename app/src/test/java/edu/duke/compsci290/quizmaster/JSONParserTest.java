package edu.duke.compsci290.quizmaster;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by gabrielcrowell on 3/8/18.
 */

public class JSONParserTest {
    private JSONObject question;

    @Before
    public void setUp() throws Exception {
        question = new JSONObject();
        question.put("question", "question test");
        JSONArray answers = new JSONArray();
        answers.put("answer test");
        JSONArray attributes = new JSONArray();
        attributes.put("attribute test");
        question.put("answers", answers);
        question.put("attributes", attributes);
    }

    @Test
    public void parseLinearQuiz() throws Exception {
        JSONObject qJSON = new JSONObject();
        qJSON.put("quizTitle", "Test Linear Quiz");
        JSONArray questions = new JSONArray();
        questions.put(question);
        qJSON.put("questions", questions);
        String jString = qJSON.toString();
        LinearQuiz q = JSONParser.parseLinearQuiz(jString);
        assertTrue(q != null);
    }

    @Test
    public void parsePersonalityQuiz() throws Exception {
        JSONObject qJSON = new JSONObject();
        qJSON.put("quizTitle", "Test Personality Quiz");
        JSONArray questions = new JSONArray();
        questions.put(question);
        qJSON.put("questions", questions);
        String jString = qJSON.toString();
        PersonalityQuiz q = JSONParser.parsePersonalityQuiz(jString);
        assertTrue(q != null);
    }

}
