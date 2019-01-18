package edu.duke.compsci290.quizmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //UI elements
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private TextView myQuestionView;
    private TextView mScoreView;
    private String mScoreBase;
    private boolean mMenuInitialized = false;

    //Quiz elements
    private IQuiz mQuiz;
    private int mQuestionIndex;
    private int mWeight;

    //used for share/save/restore states
    private static String sQUIZTITLE = "quiztitle";
    private static String INDEX = "INDEX";
    private static String SCORE = "SCORE";


    /********************************** Activity Methods **********************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        myQuestionView = (TextView) this.findViewById(R.id.question_text);
        mButton1 = (Button) this.findViewById(R.id.button1);
        mButton2 = (Button) this.findViewById(R.id.button2);
        mButton3 = (Button) this.findViewById(R.id.button3);
        mButton4 = (Button) this.findViewById(R.id.button4);
        mButton5 = (Button) this.findViewById(R.id.button5);
        mScoreView = (TextView) this.findViewById(R.id.score_view);
        mScoreBase = mScoreView.getText().toString();

        //might be passing back from Result
        Intent intent = this.getIntent();
        Bundle temp = intent.getExtras();

        //if it's the first time creating Main, not passed back from Result
        if (temp == null || temp.getString("CLASS_FROM") == null){
            Context c = getApplicationContext();
            JSONQuizGenerator.createQuizzes(c); //this creates all quizzes

            // restore our quiz
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            String prevQuiz = sharedPref.getString(sQUIZTITLE, "");

            if (!prevQuiz.equals("")) {
                IQuiz q = JSONQuizGenerator.getQuiz(prevQuiz);
                mQuestionIndex = sharedPref.getInt(INDEX, 0);
                mWeight = sharedPref.getInt(SCORE, 0);

                //restore all status of quizzes
                String[] allStatus = new String[JSONQuizGenerator.getSize()];
                for (int i = 0; i < allStatus.length; i++){
                    allStatus[i] = sharedPref.getString("STATUS"+i, "incomplete");
                }
                setStatusForAll(allStatus);

                //restore all scores of completed quizzes
                int[] allScores = new int[JSONQuizGenerator.getSize()];
                for (int i = 0; i < allScores.length; i++){
                    allScores[i] = sharedPref.getInt("SCORE"+i, 0);
                }
                setScoreForAll(allScores);

                //if the restored quiz is finished just show the result activity
                if (q.getStatus().equals("complete")){
                    //storeAll();
                    Intent intnt = new Intent(this, ResultActivity.class);
                    intnt.putExtra("QUIZ_RESULT", q.displayResult());
                    intnt.putExtra("QUIZ_NAME", q.getTitle());
                    startActivity(intnt);
                }
                else{//if it's not finished, restore the quiz
                    resumeGame(prevQuiz, mQuestionIndex, mWeight);
                }

            }
        }
        else if (temp.getString("CLASS_FROM").equals(ResultActivity.class.toString())){
            String quiz_title = intent.getStringExtra("QUIZ_NAME");
            newGame(quiz_title);
        }
        else{
            throw new RuntimeException("potential problem passing back to MainActivity");
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        mQuestionIndex = savedInstanceState.getInt(INDEX);
        mWeight = savedInstanceState.getInt(SCORE);
        String tempTitle = savedInstanceState.getString(sQUIZTITLE);
        mQuiz = JSONQuizGenerator.getQuiz(tempTitle);
        askQuestion();
        mQuestionIndex--;  // hack because of state in update
        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        if(mQuiz != null){
            state.putString(sQUIZTITLE, mQuiz.getTitle());
            state.putInt(INDEX, mQuestionIndex);
            state.putInt(SCORE, mWeight);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mQuiz != null){
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            //store score
            editor.putInt(SCORE, mWeight);
            //store index
            editor.putInt(INDEX, mQuestionIndex);
            //store quiz
            editor.putString(sQUIZTITLE, mQuiz.getTitle());
            //store status
            String[] allStatus = JSONQuizGenerator.getAllQuizzesStatus();
            for (int i = 0; i < allStatus.length; i++){
                editor.putString("STATUS"+i, allStatus[i]);
            }
            //store scores
            int[] allScores = JSONQuizGenerator.getAllQuizzesScore();
            for (int i = 0; i < allScores.length; i++){
                editor.putInt("SCORE"+i, allScores[i]);
            }
            editor.commit();
        }
    }

    public void storeAll(){

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //store score
        editor.putInt(SCORE, mWeight);
        //store index
        editor.putInt(INDEX, mQuestionIndex);
        //store quiz
        editor.putString(sQUIZTITLE, mQuiz.getTitle());
        //store status
        String[] allStatus = JSONQuizGenerator.getAllQuizzesStatus();
        for (int i = 0; i < allStatus.length; i++){
            editor.putString("STATUS"+i, allStatus[i]);
        }
        //store scores
        int[] allScores = JSONQuizGenerator.getAllQuizzesScore();
        for (int i = 0; i < allScores.length; i++){
            editor.putInt("SCORE"+i, allScores[i]);
        }
        editor.commit();
    }


    @Override
    protected void onStart(){
        super.onStart();
    }


    /********************************** END Activity Methods **********************************************************************/

    /********************************** Clicking Methods **********************************************************************/

    public void Click(View button){

        Button b = (Button) button;
        String buttonText = b.getText().toString();

        //dealing with an empty button
        if (buttonText.equals("")) {
            return;}

        if (mQuestionIndex < mQuiz.size()){
            OQuestion ques = mQuiz.getQuestion(mQuestionIndex);
            int weight = ques.getWeight(buttonText);
            if (weight == -1) throw new IndexOutOfBoundsException("error getting the weight of a text from Button");
            mWeight += weight;

            String display = mQuiz.updateScore(mQuestionIndex,weight);
            mScoreView.setText(display);

            updateQuestion();
        }
        else {
            Toast.makeText(MainActivity.this,
                    "Quiz has ended. Choose quiz from menu and click RETAKE or choose another quiz.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //TODO: took out the updateScore logic in the original code. Since method is called else where (other than onClick) might want to update
    private void updateQuestion() {
        mQuestionIndex += 1;

        if (mQuestionIndex < mQuiz.size()){
            askQuestion();
        }

        else {
            //end of Quiz. start new activity. Pass quiz title and the string returned by displayResult
            //storeAll();
            String display = mQuiz.displayResult();

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("QUIZ_RESULT", display);
            intent.putExtra("QUIZ_NAME", mQuiz.getTitle());
            startActivity(intent);
        }
    }

    /********************************** ENDClicking Methods **********************************************************************/

    /********************************** Menu Methods **********************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    //creating the menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!mMenuInitialized) {
            String[] qtitles = JSONQuizGenerator.getQuizTitles();
            for (String title : qtitles) {
                MenuItem item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, title);
                Log.d("QuizMaster","adding title " + title);

                //if the quiz is completed, set the color of the menu item to red
                if (JSONQuizGenerator.getQuiz(title).getStatus().equals("complete")) {
                    SpannableString s = new SpannableString(item.getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
            }
            mMenuInitialized = true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.d("quizmaster", "MA.onOptionsItemSelected");

        //adding toString() in the end cuz menuString could be a SpannableString
        String menuString = (String) item.getTitle().toString();

        IQuiz q = JSONQuizGenerator.getQuiz(menuString);
        if (q.getStatus().equals("complete")){

            //start that result activity
            //storeAll();
            String display = q.displayResult();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("QUIZ_RESULT", display);
            intent.putExtra("QUIZ_NAME", menuString);
            startActivity(intent);
        }
        else{
            newGame(menuString);

        }
        //TODO: there was a sharepres saving here. maybe need to get it back

        return super.onOptionsItemSelected(item);
    }
    /********************************** END Menu Methods **********************************************************************/

    /********************************** Quiz Methods **********************************************************************/


    private void newGame(String title) {
        IQuiz q = JSONQuizGenerator.getQuiz(title);
        Log.d("quizmaster", "MA.newGame " + title);
        if (q != null) {
            Log.d("quizmaster", "MA.newGame returned " + q.toString());
            mQuiz = q;

            mQuiz.clear(); //clear up data in the quiz for retaking

            mQuestionIndex = 0;
            mWeight = 0;
            askQuestion();
        } else {
            Log.d("quizmaster", "MA.newGame returned null");
            newGame();
        }
    }

    private void resumeGame(String title, int dex, int score) {
        IQuiz q = JSONQuizGenerator.getQuiz(title);
        Log.d("quizmaster", "MA.newGame " + title);
        if (q != null) {
            Log.d("quizmaster", "MA.newGame returned " + q.toString());
            mQuiz = q;
            mQuiz.setmNumOfCorrect(mWeight);
            mQuestionIndex = dex;
            mWeight = score;
            mQuiz.updateScore(mQuestionIndex,0);
            askQuestion();
        } else {
            Log.d("quizmaster", "MA.newGame returned null");
            newGame();
        }
    }

    //gives the default quiz
    private void newGame() {
        mQuiz = JSONQuizGenerator.getQuiz(); //QuizGenerator.getQuiz();

        mQuiz.clear(); //clear up data in the quiz for retaking

        mQuestionIndex = 0;
        mWeight = 0;
        askQuestion();
    }

    private void newGame(IQuiz q){
        mQuiz = q;
        mQuestionIndex = 0;
        mWeight = 0;
        askQuestion();
    }

    /**
     * initializes all status of quizzes. Used for restoring states
     * @param allStatus is array of statuses of quizzes. Order is the same order as getTitle from generator
     */
    private void setStatusForAll(String[] allStatus){
        String[] titles = JSONQuizGenerator.getQuizTitles();
        for (int i = 0; i < titles.length; i++){
            JSONQuizGenerator.getQuiz(titles[i]).setStatus(allStatus[i]);
        }
    }

    /**
     * initializes all scores of quizzes. Used for restoring states
     * @param allScore is array of scores of quizzes. Order is the same order as getTitle from generator
     */
    private void setScoreForAll(int[] allScore){
        String[] titles = JSONQuizGenerator.getQuizTitles();
        for (int i = 0; i < titles.length; i++){
            JSONQuizGenerator.getQuiz(titles[i]).setmNumOfCorrect(allScore[i]);
        }
    }

    private void askQuestion() {
        OQuestion q = mQuiz.getQuestion(mQuestionIndex);
        myQuestionView.setText(q.getQuery());
        String[] answers = q.getAnswerStrings();

        //reset all buttons to empty
        mButton1.setText("");
        mButton2.setText("");
        mButton3.setText("");
        mButton4.setText("");
        mButton5.setText("");
        //set the actual text of the button
        if (answers.length > 0) mButton1.setText(answers[0]);
        if (answers.length > 1) mButton2.setText(answers[1]);
        if (answers.length > 2) mButton3.setText(answers[2]);
        if (answers.length > 3) mButton4.setText(answers[3]);
        if (answers.length > 4) mButton5.setText(answers[4]);
    }

    /********************************** END Clicking Methods **********************************************************************/

}
