package com.example.michael.smarthealth;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Michael on 2/14/2017.
 */
public class create_new_account extends AppCompatActivity{
    //xml ids
    public Button buttonCreateAccount;
    public EditText editTextUsername;
    public EditText editTextPassword;
    public EditText editTextName;
    public EditText editTextWeight;

    public void functionsUI(){
        buttonCreateAccount = (Button)findViewById(R.id.buttonCreateAccount); //reference to button from xml

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(v.getContext(), MainActivity.class); //intent is the link between pages
                //startActivities(intent); //when button is pressed, move from activity1 to activity2
                finish(); //closes the activity, removes it from stack
            }
        }); //end line of buttonCreateNewAccount

    }

    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_account);
        functionsUI();
    }//end onCreate
}
