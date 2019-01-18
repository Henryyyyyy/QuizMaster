package edu.duke.compsci290.quizmaster;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ola on 2/13/17.
 */

public class JSONQuizGenerator {
    private static Map<String,LinearQuiz> mLinearQuizzes;
    private static Map<String,PersonalityQuiz> mPersonalityQuizzes;

    private static final String sDEFAULT = "Grammy Quiz";

    static {
        mLinearQuizzes = new HashMap<>();
        mPersonalityQuizzes = new HashMap<>();
    }

    private static void createLinearQuiz(Context context, int index) {
        try {
            Resources res = context.getResources();
            String JSONString = res.getStringArray(R.array.linear_quizzes)[index];
            LinearQuiz q = (new JSONParser().parseLinearQuiz(JSONString));
            mLinearQuizzes.put(q.getTitle(), q);
            Log.d("quizmaster", "XMLQG.createLinearQuiz added " + q.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPersonalityQuiz(Context context, int index) {
        try {
            Resources res = context.getResources();
            String JSONString = res.getStringArray(R.array.personality_quizzes)[index];
            PersonalityQuiz q = (new JSONParser().parsePersonalityQuiz(JSONString));
            mPersonalityQuizzes.put(q.getTitle(), q);
            Log.d("quizmaster", "XMLQG.createPersonalityQuiz added " + q.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read in quizzes from the string.xml and create quizzes for each file found
     * @param context
     */
    public static void createQuizzes(Context context){
        try {
            //create linear quizzes
            String[] linear_quizzes = context.getResources().getStringArray(R.array.linear_quizzes);
            for (int i = 0; i < linear_quizzes.length; i++) {
                Log.d("quizmaster", "createLinearQuizzes" + linear_quizzes[i]);
                createLinearQuiz(context, i); //create quiz in index i
            }

            //create personality quizzes
            String[] personality_quizzes = context.getResources().getStringArray(R.array.personality_quizzes);
            for (int i = 0; i < personality_quizzes.length; i++) {
                Log.d("quizmaster", "createPersonalityQuizzes" + personality_quizzes[i]);
                createPersonalityQuiz(context, i); //create quiz in index i
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * returns a quiz that matches input title
     * @param s title of the quiz
     * @return a quiz matching the title (if found), null if no match
     */
    public static IQuiz getQuiz(String s) {
        Log.d("quizmaster", "XMLQG.getQuiz requested " + s);
        if (mLinearQuizzes.get(s) != null) {
            LinearQuiz linearq = mLinearQuizzes.get(s);
            Log.d("quizmaster", "XMLQG.getQuiz found linear quiz" + linearq);
            return linearq;
        }
        else if (mPersonalityQuizzes.get(s) != null) {
            PersonalityQuiz personq = mPersonalityQuizzes.get(s);
            Log.d("quizmaster", "XMLQG.getQuiz found person quiz" + personq);
            return personq;
        }
        else{
            Log.d("quizmaster", "XMLQG.getQuiz found null");
            return null;
        }
    }

    /**
     * @return the default quiz
     */
    public static IQuiz getQuiz(){
        return getQuiz(sDEFAULT);
    }

    /**
     * returns quiz titles
     * @return array of quiz titles
     */
    public static String[] getQuizTitles(){
        Set<String> LinearTitles = mLinearQuizzes.keySet();
        Set<String> PersonalityTitles = mPersonalityQuizzes.keySet();
        String[] res = new String[LinearTitles.size()+PersonalityTitles.size()];
        int i = 0;
        for (String s: LinearTitles){
            res[i] = s;
            i++;
        }
        for (String s: PersonalityTitles){
            res[i] = s;
            i++;
        }

        return res;
    }

    /**
     *
     * @return array of all status of quizzes
     */
    public static String[] getAllQuizzesStatus(){
        String[] titles = getQuizTitles();
        String[] res = new String[titles.length];
        for (int i = 0; i < titles.length; i++){
            res[i] = getQuiz(titles[i]).getStatus();
        }
        return res;
    }

    /**
     *
     * @return array of all scores of quizzes
     */
    public static int[] getAllQuizzesScore(){
        String[] titles = getQuizTitles();
        int[] res = new int[titles.length];
        for (int i = 0; i < titles.length; i++){
            res[i] = getQuiz(titles[i]).getScore();
        }
        return res;
    }


    /**
     *
     * @return total number of quizzes
     */
    public static int getSize(){return mLinearQuizzes.size()+mPersonalityQuizzes.size();}

}
