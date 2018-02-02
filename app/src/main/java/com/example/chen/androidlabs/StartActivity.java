package com.example.chen.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_start);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 50);
            }
        });
    }

        @Override
        protected void onActivityResult ( int requestCode, int responseCode, Intent data){
            if (requestCode == 50)
                Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }


        @Override
        protected void onResume () {
            super.onResume();
            Log.i(ACTIVITY_NAME, "In onResume()");
        }

        @Override
        protected void onStart () {
            super.onStart();
            Log.i(ACTIVITY_NAME, "In onStart()");
        }

        @Override
        protected void onPause () {
            super.onPause();
            Log.i(ACTIVITY_NAME, "In onPause()");
        }

        @Override
        protected void onStop () {
            super.onStop();
            Log.i(ACTIVITY_NAME, "In onStop()");
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            Log.i(ACTIVITY_NAME, "In onDestroy()");
        }
    }


