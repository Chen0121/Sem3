package com.example.chen.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ArrayList<String> msgList = new ArrayList<>();
    private ChatDatabaseHelper dhHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ContentValues cValue;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String message=adapterView.getItemAtPosition(position).toString();
                Long messageId=adapterView.getItemIdAtPosition(position);

                if((findViewById(R.id.frame)!=null)){

                    Bundle bundle=new Bundle();
                    bundle.putString("Message",message);
                    bundle.putLong("Message ID",messageId);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.add(R.id.frame, fragment);
                    ft.commit();
                }else{
                    Intent intentPhone=new Intent(ChatWindow.this,MessageDetails.class);
                    intentPhone.putExtra("Message",message);
                    intentPhone.putExtra("Message ID",messageId);
                    startActivityForResult(intentPhone,60);
                }
            }
        });


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
            cursor.moveToPosition(position);
            return cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        }
    }

            @Override
            protected void onDestroy() {
        super.onDestroy();
                Log.i(ACTIVITY_NAME, "In onDestroy()");
        }
}