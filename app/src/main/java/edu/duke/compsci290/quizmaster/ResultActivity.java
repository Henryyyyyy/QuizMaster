package edu.duke.compsci290.quizmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Henry_Yu on 3/7/18.
 */

public class ResultActivity extends AppCompatActivity{

    //quiz elements
    private String mQResult;
    private String mQName;
    private boolean mMenuInitialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        Intent intent = this.getIntent();

        mQName = intent.getStringExtra("QUIZ_NAME");
        mQResult = intent.getStringExtra("QUIZ_RESULT");

        /*get quiz name and quiz result*/
        ( (TextView)findViewById(R.id.quiz_name)).setText("Quiz Name: \n"+mQName);
        ( (TextView)findViewById(R.id.quiz_result)).setText("Your Quiz Result: \n"+mQResult);

    }

    public void RetakeClick(View button){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("QUIZ_RESULT", mQResult);
        intent.putExtra("QUIZ_NAME", mQName);
        intent.putExtra("CLASS_FROM", ResultActivity.class.toString());
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!mMenuInitialized) {
            String[] qtitles = JSONQuizGenerator.getQuizTitles();
            for (String title : qtitles) {
                MenuItem item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, title);
                System.out.println("adding " + title + " in result activity");

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
        Log.d("quizmaster", "Result Activity: MA.onOptionsItemSelected");

        //adding toString() in the end cuz menuString could be a SpannableString
        String menuString = (String) item.getTitle().toString();

        IQuiz q = JSONQuizGenerator.getQuiz(menuString);

        //if the quiz selected is already completed then simply go to that page
        if (q.getStatus().equals("complete")){
            //start that result activity
            String display = q.displayResult();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("QUIZ_RESULT", display);
            intent.putExtra("QUIZ_NAME", menuString);
            startActivity(intent);
        }
        //now the quiz selected is incomplete. Take that quiz from start (goes to the onCreate method in MainActivity)
        else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("QUIZ_NAME", menuString);
            intent.putExtra("CLASS_FROM", ResultActivity.class.toString());
            startActivity(intent);
        }

        //TODO: think about adding these back
        // save which quiz we started taking
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(sQUIZTITLE, mQuiz.getTitle());
//        editor.commit();
        return super.onOptionsItemSelected(item);
    }

}
