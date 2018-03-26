package com.example.chen.androidlabs;

import android.app.Activity;
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

public class  ChatWindow extends Activity {
    private final String ACTIVITY_NAME = "ChatWindow";
    private EditText edittext;
    private ArrayList<String> msgList = new ArrayList<>();
    private boolean isTablet = false;
    private Cursor cursor;

    private SQLiteDatabase db = null;

    String tableName = ChatDatabaseHelper.TABLE_NAME;
    String keyID = ChatDatabaseHelper.KEY_ID;
    String keyMsg = ChatDatabaseHelper.KEY_MESSAGE;
    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        ListView listview = findViewById(R.id.listview_chat);
        edittext = findViewById(R.id.edittext_chat);
        Button btn_send = findViewById(R.id.button_send);

        isTablet = (findViewById(R.id.tablet_framelayout) != null);

        messageAdapter = new ChatAdapter(this);
        listview.setAdapter(messageAdapter);

        String query = "SELECT * FROM " + tableName +";";
        cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String str = cursor.getString( cursor.getColumnIndex( dbHelper.KEY_MESSAGE) );
            Long id = cursor.getLong(cursor.getColumnIndex(dbHelper.KEY_ID));
            msgList.add(str);

            messageAdapter.notifyDataSetChanged();

            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + str );
            Log.i(ACTIVITY_NAME, "ID: " + id);
            cursor.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor’s  column count = " + cursor.getColumnCount());
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Coloumn " + i + " : " + cursor.getColumnName(i));
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = edittext.getText().toString();
                msgList.add(msg);

                messageAdapter.notifyDataSetChanged();
                edittext.setText("");

                ContentValues cv = new ContentValues();
                cv.put(keyMsg, msg);
                db.insert(tableName,null,cv);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String msg = messageAdapter.getItem(position);
                long ID = id;
                Long id_inChat = messageAdapter.getId(position);

                MessageFragment myFragment = new MessageFragment();

                Bundle bundle = new Bundle();
                bundle.putString("Message", msg);
                bundle.putLong("ID", ID);
                bundle.putLong("IDInChat", id_inChat);

                if(isTablet){
                    myFragment.setArguments(bundle);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout,myFragment).commit();
                }else{
                    myFragment.setIsTablet(false);
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("ChatItem", bundle);
                    startActivityForResult(intent, 1, bundle);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            long id = extras.getLong("DeleteID");
            long id_inChat = extras.getLong("IDInChat");
            String query = "DELETE FROM " + tableName +" WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            msgList.remove((int)id_inChat);
            messageAdapter.notifyDataSetChanged();
        }
    }


    public void deleteForTablet(long idInDatabase, long idInChat){
        long id = idInDatabase;
        long id_inChat = idInChat;
        String query = "DELETE FROM " + tableName +" WHERE " + keyID + " = " + id + ";";
        db.execSQL(query);
        msgList.remove((int)id_inChat);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View result;
            TextView message;
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                message = (TextView)result.findViewById(R.id.message_in);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                message = (TextView)result.findViewById(R.id.message_out);
            }
            message.setText(getItem(position));
            return result;
        }

        @Override
        public int getCount(){
            return msgList.size();
        }

        @Override
        public String getItem(int position){
            return msgList.get(position);
        }

        public long getId(int position){
            return position;
        }

        @Override
        public long getItemId(int position){
            Log.d("ChatWindow", "getItemId" + position);
            String query = "SELECT * FROM " + tableName +";";
            cursor = db.rawQuery(query, null);
            cursor.moveToPosition(position);
            int id = cursor.getInt(cursor.getColumnIndex(keyID));
            return id;
        }
    }
}
