package com.example.chen.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.chen.QuizDatabaseHelper;
import com.example.chen.abstractQuestion;
import com.example.chen.final_project.R;
import com.example.chen.multipleQuestion;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class CreateQuiz extends AppCompatActivity {
    private ListView list_multiple;
    private ListView list_tf;
    private ListView list_numeric;
    private Button btn_multiple;
    private Button btn_tf;
    private Button btn_numeric;
    private Cursor cursor;
    private SQLiteDatabase db=null;
    private String query;
    private QuizDatabaseHelper dbHelper;
    private String table_multiple = dbHelper.table_multiple;
    private String table_tf = dbHelper.table_tf;
    private String table_numeric = dbHelper.table_numeric;
    private String KEY_ID = QuizDatabaseHelper.KEY_ID;
    private String KEY_QUESTION = QuizDatabaseHelper.KEY_Question;
    private boolean isTablet;
    private abstractQuestion que;


    int tfA = 1;
    QueAdapter queAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        isTablet=(findViewById(R.id.framelayout)!=null);

        list_multiple = findViewById(R.id.list_1);
        list_tf = findViewById(R.id.list_2);
        list_numeric = findViewById(R.id.list_3);

        btn_multiple=findViewById(R.id.button_multiple);
        btn_tf=findViewById(R.id.button_tf);
        btn_numeric=findViewById(R.id.button_numeric);

        queAdapter = new QueAdapter(this);
        list_1.setAdapter(queAdapter);
        QuizDatabaseHelper quizHelper=new QuizDatabaseHelper(this);
        db=quizHelper.getWritableDatabase();

        query="SELECT * FROM " + table_multiple + ";";
        cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            if(cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_TYPE))==1) {
                String answerA = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_A));
                String answerB = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_B));
                String answerC = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_C));
                String answerD = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_D));
                String question = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_Question));
                String correct = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_Correct));
                que = new multipleQuestion(answerA, answerB, answerC, answerD, question, correct, 1);

                ArrayList<abstractQuestion> qustionArray = new ArrayList<>();
                qustionArray.add(que);
                cursor.moveToNext();
            }
        }



    }

    private class MultipleAdapter extends ArrayAdapter<String> {
        public MultipleAdapter(Context ctx) {
            super(ctx, 0);
        }
    }
}
