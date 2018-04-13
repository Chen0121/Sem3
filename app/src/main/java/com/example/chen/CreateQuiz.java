package com.example.chen;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chen.final_project.R;

import java.util.ArrayList;

public class CreateQuiz extends AppCompatActivity {
    private ListView list_multiple;
    private Button btn_multiple;
    private Cursor cursor;
    private String query;
    private QuizDatabaseHelper dbHelper = new QuizDatabaseHelper(this);
    private SQLiteDatabase db=null;
    private String table_multiple = dbHelper.table_multiple;
    private String KEY_ID = QuizDatabaseHelper.KEY_ID;
    private String KEY_QUESTION = QuizDatabaseHelper.KEY_Question;
    private boolean isTablet;
    private Question Question;
    ArrayList<Question> questionArray = new ArrayList<>();
    QuestionAdapter questionAdapter=new QuestionAdapter(this);
    int tFA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        db = dbHelper.getWritableDatabase();
        isTablet = (findViewById(R.id.framelayout) != null);

        list_multiple = findViewById(R.id.list_1);
//        list_tf = findViewById(R.id.list_2);
//        list_numeric = findViewById(R.id.list_3);

        btn_multiple = findViewById(R.id.button_multiple);
//        btn_tf = findViewById(R.id.button_tf);
//        btn_numeric = findViewById(R.id.button_numeric);

        list_multiple.setAdapter(questionAdapter);
//        list_tf.setAdapter(questionAdapter);
//        list_numeric.setAdapter(questionAdapter);


        query = "SELECT * FROM " + QuizDatabaseHelper.table_multiple + ";";
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int ctr = 0;
        while (!cursor.isAfterLast()) {
            String question = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_Question));
            String answerA = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_A));
            String answerB = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_B));
            String answerC = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_C));
            String answerD = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_D));
            String correct = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_Correct));
            Question = new multipleQuestion(answerA, answerB, answerC, answerD, question, correct);
            questionArray.add(Question);
            cursor.moveToNext();
        }

        btn_multiple.setOnClickListener((View view) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateQuiz.this);
                final View view = this.getLayoutInflater().inflate(R.layout.layout_multiple, null);
                builder.setView(view);
                builder.setPositiveButton("new multiplechoice", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText a = view.findViewById(R.id.txt_A);
                        EditText b = view.findViewById(R.id.txt_B);
                        EditText c = view.findViewById(R.id.txt_C);
                        EditText d = view.findViewById(R.id.txt_D);
                        EditText question = view.findViewById(R.id.question_fragment);
                        EditText cor = view.findViewById(R.id.correct);
                        String ansA = a.getText().toString();
                        String ansB = b.getText().toString();
                        String ansC = c.getText().toString();
                        String ansD = d.getText().toString();
                        String que = question.getText().toString();
                        String correct = cor.getText().toString();
                        multipleQuestion mQuestion = new multipleQuestion(ansA, ansB, ansC, ansD, que, correct);
                        questionArray.add(mQuestion);

                        ContentValues cv = new ContentValues();
                        cv.put(QuizDatabaseHelper.KEY_Question, que);
                        cv.put(QuizDatabaseHelper.KEY_A, ansA);
                        cv.put(QuizDatabaseHelper.KEY_B, ansB);
                        cv.put(QuizDatabaseHelper.KEY_C, ansC);
                        cv.put(QuizDatabaseHelper.KEY_D, ansD);
                        cv.put(QuizDatabaseHelper.KEY_Correct, correct);

                        db.insert(QuizDatabaseHelper.table_multiple, "", cv);
                        query = "SELECT * FROM " + QuizDatabaseHelper.table_multiple + ";";
                        cursor = db.rawQuery(query, null);
                        cursor.moveToFirst();
                        questionAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(R.string.CANCEL_multiple, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
        });

        list_multiple.setOnItemClickListener((adapterView, view, position, id) -> {
                String q = questionAdapter.getItem(position).getQue();
                String a1 = questionAdapter.getItem(position)).getAns1();
                String a2 = questionAdapter.getItem(position)).getAns2();
                String a3 = questionAdapter.getItem(position)).getAns3();
                String a4 = questionAdapter.getItem(position)).getAns4();
                String c = questionAdapter.getItem(position)).getCorrect();
                Long id_inList = questionAdapter.getId(position);
                long ID = id;
                multipleFragment Fragment = new multipleFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Answer1", a1);
                bundle.putString("Answer2", a2);
                bundle.putString("Answer3", a3);
                bundle.putString("Answer4", a4);
                bundle.putString("Question", q);
                bundle.putInt("Correct", c);
                bundle.putLong("IDInChat", id_inList);
                bundle.putLong("ID", ID);

            if (isTablet) {
                Fragment.setArguments(bundle);
                Fragment.setIsTablet(true);
                getFragmentManager().beginTransaction().replace(R.id.framelayout, Fragment).commit();
            } else{
                Fragment.setIsTablet(false);
                Intent multiDetails = new Intent(CreateQuiz.this, multipleDetails.class);
                multiDetails.putExtra("QuestionItem", bundle);
                startActivityForResult(multiDetails, 1, bundle);
            }
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle b = data.getExtras();
            if (b.getInt("action") == 1) {
                long id = b.getLong("DeleteID");
                long id_inList = b.getLong("IDInChat");
                db.delete(QuizDatabaseHelper.table_multiple, QuizDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});
                questionArray.remove((int) id_inList);
                query="SELECT * FROM " + QuizDatabaseHelper.table_multiple + ";";
                cursor = db.rawQuery(query,null);
                cursor.moveToFirst();
                questionAdapter.notifyDataSetChanged();
                CharSequence text = "delete question";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            } else if (b.getInt("action") == 2) {
                long id = b.getLong("UpdateID");
                long id_inList = b.getLong("IDInChat");
                db.delete(QuizDatabaseHelper.table_multiple, QuizDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});
                questionArray.remove((int) id_inList);
                query="SELECT * FROM " + QuizDatabaseHelper.table_multiple + ";";
                cursor = db.rawQuery(query, null);
                cursor.moveToFirst();
                questionAdapter.notifyDataSetChanged();
                String question = b.getString("Question");
                String ans1 = b.getString("Ans1");
                String ans2 = b.getString("Ans2");
                String ans3 = b.getString("Ans3");
                String ans4 = b.getString("Ans4");
                String correct = b.getString("Correct");
                update( ans1, ans2, ans3, ans4,question, correct);
            }
        }
    }

    public void update(String ans1,String ans2,String ans3,String ans4,String question, String correct) {
    }



    private class QuestionAdapter extends ArrayAdapter<Question> {
        private QuestionAdapter(Context ctx) {
            super(ctx, 0);
        }

        }
    }








