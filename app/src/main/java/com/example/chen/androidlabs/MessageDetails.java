package com.example.chen.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = getIntent().getBundleExtra("ChatItem");

        MessageFragment myFragment = new MessageFragment();
        myFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_layout, myFragment).commit();


    }

}
