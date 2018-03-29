package com.example.chen.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Quiz_Creater extends Activity {
    private final String ACTIVITY_NAME = "Quiz_Creater";
    private Context context;
    private ListView optionList;
    private ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple__choice__quiz__creater);

        context=Quiz_Creater.this;
        optionList=findViewById(R.id.listView);
        list.add("Multiple choice");
        list.add("Numeric answer");
        list.add("True/False question");
    }


    private class ChatAdapter extends ArrayAdapter<String>{
        private View view;
        private Context context;


        public ChatAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return position;
        }

    }
 }
