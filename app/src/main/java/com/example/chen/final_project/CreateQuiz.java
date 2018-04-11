package com.example.chen.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateQuiz extends Activity {
    Button btn_numeric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        btn_numeric=(Button)findViewById(R.id.button_numeric);
        btn_numeric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_numeric=new Intent(CreateQuiz.this,QuizFragment.class);
                startActivity(add_numeric);
            }
        });
    }
}
