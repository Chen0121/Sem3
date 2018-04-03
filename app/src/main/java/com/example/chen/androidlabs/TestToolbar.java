package com.example.chen.androidlabs;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    private String SnackbarMsg ="You selected item 1.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, SnackbarMsg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                Snackbar.make(findViewById(R.id.option_one), SnackbarMsg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.option_two:
                Log.d("Toolbar","option 2 selected");

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setTitle(R.string.title);
                // Add the buttons
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                        // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.option_three:
                Log.d("Toolbar","option 3 selected");

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                final View view=this.getLayoutInflater().inflate(R.layout.customlayout,null);
                builder1.setView(view);
                builder1.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText message = (EditText)view.findViewById(R.id.new_message);
                        SnackbarMsg = message.getText().toString();
                    }
                });
                builder1.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
                
            case R.id.about:
                CharSequence text = "Version 1.0 by Chen Huang";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this , text, duration);
                toast.show();
        }return true;
    }
}
