package com.example.chen.androidlabs;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Messages.db";
    static final String TABLE_NAME = "table_msg";
    static final String KEY_ID = "id";
    static final String KEY_MESSAGE = "message";

    ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT);";
        try {
            Log.i("ChatDatabaseHelper", query);
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("ChatDatabaseHelper", e.getMessage());
        }
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
    }
}