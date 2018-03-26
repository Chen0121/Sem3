package com.example.chen.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }

        Bundle bundle = new Bundle();
        bundle.putString("Message", getIntent().getStringExtra("Message"));
        bundle.putString("MessageID", getIntent().getStringExtra("MessageID"));

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.phone_frameLayout, fragment);
        ft.commit();
    }
}