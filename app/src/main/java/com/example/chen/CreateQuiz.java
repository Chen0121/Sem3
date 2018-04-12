package com.example.chen;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.chen.final_project.R;


public class CreateQuiz extends Activity {
    private ListView list_multiple;
    private ListView list_tf;
    private ListView list_numeric;
    private Button btn_multiple;
    private Button btn_tf;
    private Button btn_numeric;
    private Cursor cursor;
    private SQLiteDatabase db;
    private QuizDatabaseHelper dbHelper;
    QuizAdapter mAdapter;
    QuizAdapter tfAdapter;
    QuizAdapter nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        dbHelper = new QuizDatabaseHelper(this);

        list_multiple = findViewById(R.id.list_1);
        list_tf = findViewById(R.id.list_2);
        list_numeric = findViewById(R.id.list_3);

    }

    private class MultipleAdapter extends ArrayAdapter<String> {
        public MultipleAdapter(Context ctx) {
            super(ctx, 0);
        }
    }
}
