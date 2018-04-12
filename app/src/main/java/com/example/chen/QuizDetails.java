package com.example.chen;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.chen.final_project.R;

public class QuizDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_details);

            Bundle bundle = getIntent().getBundleExtra("QuizItem");
            QuizFragment Fragment = new QuizFragment();
            Fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.quiz_details, Fragment).commit();
    }
}
