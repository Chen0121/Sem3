package com.example.chen.androidlabs;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TestToolbar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(tb);
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id=mi.getItemId();

        switch (id){
            case R.id.option_one:
                Log.d("Toolbar","option 1 selected");
                break;
            case R.id.option_two:
                Log.d("Toolbar","option 2 selected");
                break;
            case R.id.option_three:
                Log.d("Toolbar","option 3 selected");
                break;
        }return true;
    }
}
