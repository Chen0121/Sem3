package com.example.chen.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ArrayList<String> msgList = new ArrayList<>();
    ChatDatabaseHelper dhHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ContentValues cValue;
    private boolean frameExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        if(findViewById(R.id.frame)==null){
        // not load , using phone
        }else{
        //load framelayout, tablet
        }


        dhHelper = new ChatDatabaseHelper(this);
        db = dhHelper.getWritableDatabase();
        String[] mes={ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE};
        cursor = db.query(ChatDatabaseHelper.TABLE_NAME,mes,null,null,null,null,null);
        cursor.moveToFirst();
       while(!cursor.isAfterLast()){
           String message=cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
           msgList.add(message);
           Log.i(ACTIVITY_NAME,"SQL MESSAGE:"+cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
           cursor.moveToNext();
           }

        for(int i=0; i<cursor.getColumnCount();i++){
           cursor.getColumnName(i);
           Log.i(ACTIVITY_NAME,"Cursor's column count="+cursor.getColumnCount());
        }
        db=dhHelper.getWritableDatabase();

        ListView listView = (ListView) findViewById(R.id.list_view);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        Button button = (Button) findViewById(R.id.send_btn);
        final EditText editText = (EditText) findViewById(R.id.chat_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                msgList.add(content);

                cValue = new ContentValues();
                cValue.put(ChatDatabaseHelper.KEY_MESSAGE,editText.getText().toString());
                db.insert(ChatDatabaseHelper.TABLE_NAME,null,cValue);

                editText.setText("");

            }
        });
        messageAdapter.notifyDataSetChanged();

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {

            super(ctx, 0);
        }

        // return number of rows in listView, return 1 =1 row
        public int getCount() {

            return msgList.size();
        }

        // show the position
        public String getItem(int position) {

            return msgList.get(position);
        }

        // return this whole listView layout, show how this list view looks
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            else
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        // database needs, get 1 return 1
        public long getItemId(int position) {
            return position;
        }
    }

            @Override
            protected void onDestroy() {
        super.onDestroy();
                Log.i(ACTIVITY_NAME, "In onDestroy()");
        }
}