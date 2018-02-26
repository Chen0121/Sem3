package com.example.chen.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "Messages.db";
    public static final String TABLE_NAME="message";
    public static final int VERSION_NUM =1;
    public static final String KEY_ID="_id";
    public static final String KEY_MESSAGE="_message";


    protected ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE "+ TABLE_NAME + "(id integer PRIMARY KEY AUTOINCREMENT, message String)");
        Log.i("ChatDatabaseHelper","Calling onCreate");
    }

    public void onUpgrade(SQLiteDatabase db,int oldver, int newVer){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper","Calling onUpgrade, oldVersion="+oldver+"newVersion="+newVer);
    }

}
