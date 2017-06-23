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
public class gamification_spinner_decide extends AppCompatActivity{
    //xml ids
    Button buttonContinue;

    public void functionsUI(){

        buttonContinue = (Button)findViewById(R.id.buttonContinue); //reference by xml id
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), exercise_continue.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2

                finish(); //take off stack so user can't return to this page
            }
        }); //end line of buttonContinue

    }

    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamification_spinner_decide);
        functionsUI();
    }
}
