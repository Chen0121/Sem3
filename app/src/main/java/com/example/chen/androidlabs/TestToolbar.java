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
