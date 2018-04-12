package com.example.chen;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    //继承抽象类创建子类实现 OnCreate和onUpgrade
    private static final int db_version = 1;
    private static final String db_name = "Quiz.db";
    public static final String table_multiple = "table_multipleChoice";
    public static final String table_numeric = "table_numericQuestion";
    public static final String table_tf = "table_tfQuestion";
    public static final String KEY_ID = "id";
    public static final String KEY_Question = "question";
    public static final String KEY_Correct = "correctAnswer";
    public static final String KEY_A = "AnswerA";
    public static final String KEY_B = "AnswerB";
    public static final String KEY_C = "AnswerC";
    public static final String KEY_D = "AnswerD";


    QuizDatabaseHelper(Context ctx) {
        super(ctx, db_name, null, db_version);
    }

    //如果数据库不存在就创建数据库和表
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
          insertRecords(db);

    }

    private void insertRecords(SQLiteDatabase db){
         ContentValues values = new ContentValues();

         values.put("id",001);
         values.put("question","question");
         values.put("correct","correct");
         values.put("A","a");
         values.put("B","b");
         values.put("C","c");
         values.put("D","d");
         db.insert(table_multiple,null,values);

    }

    //如果数据库存在，完成升级
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


    public Cursor query(String[] columns, String selection, String[] selectionArgs) {
        // 获取一个只读数据库
        SQLiteDatabase db = getReadableDatabase();
        // 执行查询方法，返回游标
        return db.query(table_multiple, columns, selection, selectionArgs, null, null, null);
    }

    public int delete(String whereClause, String[] whereArgs) {
        // 获取一个可写数据库
        SQLiteDatabase db = getWritableDatabase();
        // 执行删除方法，返回删除记录数
        return db.delete(table_multiple, whereClause, whereArgs);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return 0;
    }

    public int insert(ContentValues values) {
        return 0;
    }
}

