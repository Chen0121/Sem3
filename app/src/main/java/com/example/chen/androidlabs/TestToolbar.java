package com.example.chen.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TestToolbar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
    }

    //create toolbar by inflating it from xml file
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater.inflate(R.menu.toolbar_menu, m);
        return true;
    }

    //response items being selecetd
    public boolean onOptionsItemSelected(MenuItem mi){
        int id= mi.getItemId();

        switch (id) {
            case R.id.option_one:
                Log.d("Toolbar","Option 1 selected");
                break;
            case R.id.option_two:
                Log.d("Toolbar","Option 2 selected");
                break;
            case R.id.option_three:
                Log.d("Toolbar","Option 3 selected");
                break;
            default:
                Log.d("default","you should choose one, two, or three");
                break;
        }
    }

}
