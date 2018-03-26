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
import android.view.KeyEvent;
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

import static com.example.chen.androidlabs.ChatDatabaseHelper.MESSAGE_FIELDS;
import static com.example.chen.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ChatDatabaseHelper dhHelper;
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> msgList = new ArrayList<>();
    private ChatAdapter messageAdapter;
    private Cursor cursor;
    private SQLiteDatabase db;

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
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            else
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        // database needs, get 1 return 1
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.list_view);
        editText = (EditText) findViewById(R.id.chat_text);
        button = (Button) findViewById(R.id.send_btn);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        dhHelper = new ChatDatabaseHelper(this);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String content = editText.getText().toString();
//                msgList.add(content);
//                cValue = new ContentValues();
//                cValue.put(ChatDatabaseHelper.KEY_MESSAGE,editText.getText().toString());
//                db.insert(ChatDatabaseHelper.TABLE_NAME,null,cValue);
//                editText.setText("");
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("") == false) {
                    msgList.add(editText.getText().toString());

                    dhHelper.insertEntry(editText.getText().toString());
                    displaySQL();
                    editText.setText("");
                }
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            msgList.add(editText.getText().toString());

                            dhHelper.insertEntry(editText.getText().toString());
                            displaySQL();
                            editText.setText("");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String message=adapterView.getItemAtPosition(position).toString();
                Long messageId=adapterView.getItemIdAtPosition(position);

                if((findViewById(R.id.frameLayout)!=null)){
                    MessageFragment messageFragment = new MessageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Message", message);
                    bundle.putLong("MessageID", messageId );
                    bundle.putBoolean("isTablet", true);
                    messageFragment.setArguments(bundle);
                    FragmentTransaction ft =  getFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayout, messageFragment);
                    ft.addToBackStack("A string");
                    ft.commit();

                    Log.i("chatWindow", "on Tablet");
                }else{
                    Intent intentPhone=new Intent(ChatWindow.this,MessageDetails.class);
                    intentPhone.putExtra("Message",message);
                    intentPhone.putExtra("Message ID",messageId);
                    startActivityForResult(intentPhone,1);

                    Log.i("ChatWindow","on Phone");
                }
            }
        });
    }

    public void displaySQL() {
        msgList.clear();
        cursor = dhHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                msgList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            messageAdapter.notifyDataSetChanged();//this restarts the process of getCount()/getView()
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Cursor's column name is " + (i + 1) + ". " + cursor.getColumnName(i));
        }
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        dhHelper.openDatabase();
        displaySQL();

        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dhHelper.closeDatabase();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


    public void notifyChange() {
        listView.setAdapter(messageAdapter);
        db = dhHelper.getWritableDatabase();
        cursor = db.query(false, TABLE_NAME, MESSAGE_FIELDS, null, null, null, null, null, null);
        int numColumns = cursor.getColumnCount();
        int numResults = cursor.getCount();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(dhHelper.KEY_MESSAGE)));
            msgList.add(cursor.getString(cursor.getColumnIndex(dhHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + numColumns);

        cursor.moveToFirst();
        for(int i = 0; i < numResults; i++) {
            Log.i(ACTIVITY_NAME, "The " + i + " row is " + cursor.getString(cursor.getColumnIndex(dhHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
    }

        @Override
         protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
          if (resultCode == 1) {
            String id = data.getStringExtra("MessageID");
            dhHelper.deleteItem(id);
        }
    }
}