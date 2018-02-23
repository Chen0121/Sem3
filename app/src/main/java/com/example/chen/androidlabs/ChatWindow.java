package com.example.chen.androidlabs;

import android.app.Activity;
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

import static com.example.chen.androidlabs.ChatDatabaseHelper.DATABASE_NAME;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String> msgList = new ArrayList<>();
    ChatDatabaseHelper dhHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        dhHelper = new ChatDatabaseHelper(ChatWindow.this);
        db= dhHelper.getReadableDatabase();

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
                editText.setText("");

            }
        });
        messageAdapter.notifyDataSetChanged();



    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {

            super(ctx, 0);
        }

        //return number of rows in listView, return 1 =1 row
        public int getCount() {

            return msgList.size();
        }

        //show the position
        public String getItem(int position) {

            return msgList.get(position);
        }

        //return this whole listView layout, show how this list view looks
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

        //database needs, get 1 return 1
        public long getItemId(int position){
            return position;}
    }
}