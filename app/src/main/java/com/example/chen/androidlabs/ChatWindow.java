package com.example.chen.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
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

    private ChatDatabaseHelper chatDBHelper;
    private String chatWindow = ChatWindow.class.getSimpleName();
    private ListView listView;
    private EditText editText;
    private Button buttonSend;
    private ArrayList<String> chatArray;
    private ChatAdapter messageAdapter;
    private Cursor cursor;
    private SQLiteDatabase database;

    class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return chatArray.size();
        }

        public String getItem(int position){
            return chatArray.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));//get the string at position
            return result;
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(chatDBHelper.KEY_ID));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.list_view);
        editText = (EditText) findViewById(R.id.chat_text);
        buttonSend = (Button) findViewById(R.id.send_btn);
        chatArray = new ArrayList<String>();

        //In this case, "this" is the ChatWindow, which is - A Context object
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        //Create a temporary ChatDatabaseHelper object, which then gets a writeable database
        chatDBHelper = new ChatDatabaseHelper(this);

        //User clicks on Send button to send text in chat window
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("") == false) {
                    chatArray.add(editText.getText().toString());

                    chatDBHelper.insertEntry(editText.getText().toString());
                    displaySQL();
                    editText.setText("");
                }
            }
        });

        //User hits Enter key on keyboard to send text in chat window
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            chatArray.add(editText.getText().toString());

                            chatDBHelper.insertEntry(editText.getText().toString());
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

        //When the user clicks on a message of Chat listview, shows the details of the message in a fragment.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String item = adapterView.getItemAtPosition(position).toString();
                Long messageId = adapterView.getItemIdAtPosition(position);

                //Use a Bundle to pass the message string, and the database id of the selected item to the fragment in the FragmentTransaction
                if(findViewById(R.id.frameLayout) != null) {
                    MessageFragment messageFragment = new MessageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Message", item);
                    bundle.putString("MessageID", messageId + "");
                    bundle.putBoolean("isTablet", true);
                    messageFragment.setArguments(bundle);
                    FragmentTransaction ft =  getFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayout, messageFragment);
                    //Call transaction.addToBackStack(String name) if you want to undo this transaction with the back button.
                    ft.addToBackStack("A string");
                    ft.commit();

                    Log.i(chatWindow, "Run on Tablet?");
                } else {
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("Message", item);
                    intent.putExtra("MessageID", messageId + "");
                    startActivityForResult(intent, 1);

                    Log.i(chatWindow, "Run on Phone");
                }

            }
        });
    }

    public void displaySQL() {
        chatArray.clear();
        cursor = chatDBHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(chatWindow, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                chatArray.add(cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            messageAdapter.notifyDataSetChanged();//this restarts the process of getCount()/getView()
            Log.i(chatWindow, "Cursor's column count = " + cursor.getColumnCount());
        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(chatWindow, "Cursor's column name is " + (i + 1) + ". " + cursor.getColumnName(i));
        }
    }

    protected void onResume(){
        super.onResume();
        Log.i(chatWindow, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        chatDBHelper.openDatabase();
        displaySQL();

        Log.i(chatWindow,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(chatWindow,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(chatWindow,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        chatDBHelper.closeDatabase();
        Log.i(chatWindow,"In onDestroy()");
    }

    public void notifyChange() {
        listView.setAdapter(messageAdapter);
        database = chatDBHelper.getWritableDatabase();

        cursor = database.query(false, TABLE_NAME, MESSAGE_FIELDS, null, null, null, null, null, null);
        int numColumns = cursor.getColumnCount();
        int numResults = cursor.getCount();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(chatWindow, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
            chatArray.add(cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        Log.i(chatWindow, "Cursor's column count = " + numColumns);

        cursor.moveToFirst();
        for(int i = 0; i < numResults; i++) {
            Log.i(chatWindow, "The " + i + " row is " + cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String id = data.getStringExtra("MessageID");
            chatDBHelper.deleteItem(id);
        }
    }

}