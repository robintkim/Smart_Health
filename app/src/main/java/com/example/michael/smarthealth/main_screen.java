package com.example.michael.smarthealth;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.*;

/**
 * Created by Michael on 4/2/2017.
 */
public class main_screen extends AppCompatActivity {
    //xml ids
    Button buttonExercise;
    Button buttonSettings;
    Button buttonProgress;
    //Button buttonFriends;

    public void functionsUI(){
        buttonExercise = (Button)findViewById(R.id.buttonExercise); //reference by xml id
        buttonExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), exercise_startup.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
            }
        }); //end line of buttonCreateNewAccount

        buttonSettings = (Button)findViewById(R.id.buttonSettings); //reference by xml id
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), settings.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
            }
        }); //end line of buttonCreateNewAccount

        buttonProgress = (Button)findViewById(R.id.buttonProgress); //reference by xml id
        buttonProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), progress.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
            }
        }); //end line of buttonCreateNewAccount
    }

    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        functionsUI();
    }
}
