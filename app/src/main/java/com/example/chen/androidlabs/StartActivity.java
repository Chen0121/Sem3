package com.example.chen.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_start);

        Button btn = findViewById(R.id.ButtonText);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 50);
            }
        });

        Button btn_chat = findViewById(R.id.start_chat);
        btn_chat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start ChatUser clicked Start Chat");
                Intent chat = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(chat);
            }
        });

        Button btn_weather=findViewById(R.id.weather);
        btn_weather.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent weather=new Intent(StartActivity.this,WeatherForecast.class);
                startActivity(weather);
            }}
        );

        Button btn_toolbar=findViewById(R.id.toolbar_button);
        btn_toolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toolbar=new Intent(StartActivity.this,TestToolbar.class);
                startActivity(toolbar);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode == 50)
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        if (responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(StartActivity.this,messagePassed,Toast.LENGTH_LONG);
            toast.show();
        }
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