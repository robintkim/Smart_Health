package com.example.michael.smarthealth;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.*;


/**
 * Created by Michael on 5/6/2017.
 */
//TODO: pull user information from database when the activity is created
public class settings extends AppCompatActivity {
    Button buttonEditSettings;
    Button buttonConfirm;

    public void functionsUI(){

        buttonEditSettings = (Button)findViewById(R.id.buttonEditSettings); //reference by xml id
        //TODO: onClickListener for edit settings button

        buttonConfirm = (Button)findViewById(R.id.buttonConfirm); //reference by xml id
        buttonConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: if changes were made, save new settings in database
                finish(); // end activity
            }
        }); //end line of button

    }//end functions UI
    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        functionsUI();
    }
}
