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
    private SQLiteDatabase db;
    String TABLE_NAME=QuizDatabaseHelper.TABLE_NAME;
    String KEY_ID = QuizDatabaseHelper.KEY_ID;
    String KEY_QUESTION = QuizDatabaseHelper.KEY_QUESTION;
    private QuizDatabaseHelper dbHelper;
    private boolean isFrame=true;
    ArrayList<Question> question = new ArrayList<>();
    int tfA = 1;
    QueAdapter queAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        isFrame=(findViewById(R.id.framelayout)!=null);
        dbHelper = new QuizDatabaseHelper(this);

        list_multiple = findViewById(R.id.list_1);
        list_tf = findViewById(R.id.list_2);
        list_numeric = findViewById(R.id.list_3);

        btn_multiple=findViewById(R.id.button_multiple);
        btn_tf=findViewById(R.id.button_tf);
        btn_numeric=findViewById(R.id.button_numeric;

    }

    private class MultipleAdapter extends ArrayAdapter<String> {
        public MultipleAdapter(Context ctx) {
            super(ctx, 0);
        }
    }
}
