package com.example.chen;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.chen.final_project.R;

public class multipleDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_details);

        Bundle bundle = getIntent().getBundleExtra("QuizItem");
        multipleFragment Fragment = new multipleFragment();
        Fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, Fragment).commit();
    }
}
