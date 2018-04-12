package com.example.chen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.chen.final_project.R;

import java.util.ArrayList;

public class CreateQuiz extends AppCompatActivity {
    private ListView list_multiple;
    private ListView list_tf;
    private ListView list_numeric;
    private Button btn_multiple;
    private Button btn_tf;
    private Button btn_numeric;
    private Cursor cursor;
    private String query;
    private QuizDatabaseHelper dbHelper = new QuizDatabaseHelper(this);
    private SQLiteDatabase db = dbHelper.getWritableDatabase();
    private String table_multiple = dbHelper.table_multiple;
    private String table_tf = dbHelper.table_tf;
    private String table_numeric = dbHelper.table_numeric;
    private String KEY_ID = QuizDatabaseHelper.KEY_ID;
    private String KEY_QUESTION = QuizDatabaseHelper.KEY_Question;
    private boolean isTablet;
    private Question Question;
    QuestionAdapter quetionAdapter = new QuestionAdapter(this);
    int tFA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        isTablet = (findViewById(R.id.framelayout) != null);

        list_multiple = findViewById(R.id.list_1);
        list_tf = findViewById(R.id.list_2);
        list_numeric = findViewById(R.id.list_3);

        btn_multiple = findViewById(R.id.button_multiple);
        btn_tf = findViewById(R.id.button_tf);
        btn_numeric = findViewById(R.id.button_numeric);

        list_1.setAdapter(questionAdapter);
        list_2.setAdapter(questionAdapter);
        list_3.setAdapter(questionAdapter);

        query = "SELECT * FROM " + table_multiple + ";";
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int ctr = 0;
        while (!cursor.isAfterLast()) {
            String question = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_Question));
            String answerA = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_A));
            String answerB = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_B));
            String answerC = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_C));
            String answerD = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_D));
            String correct = cursor.getString(cursor.getColumnIndex(dbHelper.KEY_Correct));
            ArrayList<Question> questionArray = new ArrayList<>();
            Question = new multipleQuestion(answerA, answerB, answerC, answerD, question, correct);
            questionArray.add(Question);
            cursor.moveToNext();
        }
    }

        btn_multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CreateQuiz.this);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundle=new Bundle();
                        bundle.putString("type","Multiple choice");
                        multipleFragment mFragment=new multipleFragment();
                        if(isTablet){
                            mFragment.setArguments(bundle);
                            mFragment.setIsTablet(true);
                            getFragmentManager().beginTransaction().replace(R.id.framelayout, mFragment).commit();
                        }else{
                            mFragment.setIsTablet(false);
                            Intent toDetail=new Intent(CreateQuiz.this,multipleDetails.class);
                            toDetail.putExtra("mutipleDetails", bundle);
                            startActivityForResult(toDetail, 1, bundle);
                        }
                    }
                });
                builder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private class MultipleAdapter extends ArrayAdapter<String> {
        public MultipleAdapter(Context ctx) {
            super(ctx, 0);
        }
    }
}
