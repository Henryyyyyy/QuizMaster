package edu.duke.compsci290.quizmaster;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ola on 2/14/17.
 */


public class JSONParser {

    /**
     * @param jString in string.xml, inside the linear_quizzes string array
     * @return a LinearQuiz object
    */
    public static LinearQuiz parseLinearQuiz(String jString){
        try {
            JSONObject quizString = new JSONObject(jString);
            Log.d("quizmaster", "created json object");
            String qtitle = quizString.getString("title");
            Log.d("quizmaster", "json title = "+qtitle);

            JSONArray array = quizString.getJSONArray("questions"); // array of all questions
            OQuestion[] questions = new OQuestion[array.length()];

            for(int k=0; k < array.length(); k++){
                JSONObject current = array.getJSONObject(k); //the current question
                String query = current.getString("query"); //query of current question
                JSONArray answers = current.getJSONArray("answers");
                JSONArray values = current.getJSONArray("values");

                Answer[] allAnswers = new Answer[answers.length()];
                for(int j=0; j < allAnswers.length; j++){
                    Answer temp = new Answer(answers.getString(j),values.getInt(j));
                    allAnswers[j] = temp;

                }
                OQuestion q = new OQuestion(query,allAnswers);
                questions[k] = q;
            }
            LinearQuiz qz = new LinearQuiz(qtitle, questions, "incomplete");
            return qz;
        } catch (JSONException e) {
            Log.d("json parseLinearQuiz","error in parsing");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jString in string.xml, inside the personality_quiz string array
     * @return a PersonalityQuiz object
     */
    public static PersonalityQuiz parsePersonalityQuiz(String jString){
        try {
            JSONObject quizString = new JSONObject(jString);
            Log.d("quizmaster", "created json object");
            String qtitle = quizString.getString("title");
            Log.d("quizmaster", "json title = "+qtitle);

            JSONArray array = quizString.getJSONArray("questions"); // array of all questions
            OQuestion[] questions = new OQuestion[array.length()];

            for(int k=0; k < array.length(); k++){
                JSONObject current = array.getJSONObject(k); //the current question
                String query = current.getString("query"); //query of current question
                JSONArray answers = current.getJSONArray("answers");
                JSONArray values = current.getJSONArray("values");

                Answer[] allAnswers = new Answer[answers.length()];
                for(int j=0; j < allAnswers.length; j++){
                    Answer temp = new Answer(answers.getString(j),values.getInt(j));
                    allAnswers[j] = temp;

                }

                OQuestion q = new OQuestion(query,allAnswers);
                questions[k] = q;
            }

            //get the result array and the weight array
            JSONArray qresult = quizString.getJSONArray("result");
            JSONArray qweight = quizString.getJSONArray("weight");

            String[] qres = new String[qresult.length()];
            int[] qwei = new int[qweight.length()];

            for (int i = 0; i < qres.length; i++){
                qres[i] = qresult.getString(i);
            }
            for (int i = 0; i < qwei.length; i++) {
                qwei[i] = qweight.getInt(i);
            }

            //the status of the quiz is incomplete when this method is called from QuizGenerator
            PersonalityQuiz qz = new PersonalityQuiz(qtitle, questions, qres, qwei, "incomplete");
            return qz;

        } catch (JSONException e) {
            Log.d("json parsePersonalityQz","error parsing");
            e.printStackTrace();
        }
        return null;
    }
}
