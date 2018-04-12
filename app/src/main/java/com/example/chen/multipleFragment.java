package com.example.chen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.chen.final_project.R;


public class multipleFragment extends Fragment {
    private boolean isTablet;
    private CheckBox checkA;
    private CheckBox checkB;
    private CheckBox checkC;
    private CheckBox checkD;
    private EditText textA;
    private EditText textB;
    private EditText textC;
    private EditText textD;
    private EditText multipleQuestion;
    private Button btn_update;
    private Button btn_delete;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_multiple_fragment,container,false);
        multipleQuestion=view.findViewById(R.id.multiple_question);
        textA=view.findViewById(R.id.txt_A);
        textB=view.findViewById(R.id.txt_B);
        textC=view.findViewById(R.id.txt_C);
        textD=view.findViewById(R.id.txt_D);
        checkA=view.findViewById(R.id.A_fragment);
        checkB=view.findViewById(R.id.B_fragment);
        checkC=view.findViewById(R.id.C_fragment);
        checkD=view.findViewById(R.id.D_fragment);
        

    }
}
