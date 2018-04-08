package com.example.chen.final_project;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class QuizDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_details);

            Bundle bundle = getIntent().getBundleExtra("ChatItem");
            QuizFragment myFragment = new QuizFragment();
            myFragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.quiz_details, myFragment).commit();
    }
}
