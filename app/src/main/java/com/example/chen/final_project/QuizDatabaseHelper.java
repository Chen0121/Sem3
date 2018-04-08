package com.example.chen.final_project;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final int db_version = 1;
    private static final String db_name = "Quiz.db";
    static final String table_multiple = "table_multipleChoice";
    static final String table_numeric = "table_numericQuestion";
    static final String table_tf = "table_tfQuestion";
    static final String KEY_ID = "id";
    static final String KEY_Question = "question";
    static final String KEY_Correct = "correctAnswer";
    static final String KEY_A = "AnswerA";
    static final String KEY_B = "AnswerB";
    static final String KEY_C = "AnswerC";
    static final String KEY_D = "AnswerD";


    private QuizDatabaseHelper(Context ctx) {
        super(ctx, db_name, null, db_version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i("QuizDatabaseHelper", "onCreate");
            db.execSQL( "CREATE TABLE " + table_multiple + " ( "+ KEY_ID +" INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +KEY_Question+ "text, "+KEY_Correct +"text, "+ KEY_A
                    + "text, "+ KEY_B + "text, " + KEY_C + "text, " + KEY_D + "text )"
            );
            db.execSQL( "CREATE TABLE " + table_numeric + " ( "+ KEY_ID +" INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +KEY_Question+ "text, "+KEY_Correct +"text, "+ KEY_A
                    + "text )"
            );
            db.execSQL( "CREATE TABLE " + table_tf + " ( "+ KEY_ID +" INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +KEY_Question+ "text, "+KEY_Correct +"text )"
            );
        } catch (SQLException e) {
            Log.e("QuizDatabaseHelper", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + table_multiple + ";");
        db.execSQL("DROP TABLE IF EXISTS " + table_numeric + ";");
        db.execSQL("DROP TABLE IF EXISTS " + table_tf + ";");
        onCreate(db);
        Log.i("QuizDatabaseHelper", "onUpgrade, oldVer=" + oldVer + " newVer= " + newVer);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + table_multiple + ";");
        db.execSQL("DROP TABLE IF EXISTS " + table_numeric + ";");
        db.execSQL("DROP TABLE IF EXISTS " + table_tf + ";");
        onCreate(db);
        Log.i("QuizDatabaseHelper", "onDowngrade, oldVer=" + oldVer + " newVer= " + newVer);
    }

}

