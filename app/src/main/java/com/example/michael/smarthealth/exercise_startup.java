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
public class exercise_startup extends AppCompatActivity {
    //xml ids
    EditText editTextFeel;
    Button buttonContinue;
    Button buttonHelpMeDecide;
    TextView gamification_text;

    /************ TESTING VARIABLES ***********/
    //this needs to have been created at the start of the app
    DecisionMatrix dm;
    Gamification gm;
    int method;     //this has to persist until exercise_rewards
    /************ END TESTING VARIABLES ***********/

    public void functionsUI(){

        editTextFeel = (EditText)findViewById(R.id.editTextFeel);
        gamification_text = (TextView)findViewById(R.id.gamification_text);
        method = dm.chooseMethod();
        gm.setMethod(method);
        gamification_text.setText(gm.getString(method));


        buttonContinue = (Button)findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm.updateDB();
                gm.updateDB();
                Intent intent = new Intent(v.getContext(), exercise_continue.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
                finish(); //take off stack so user can't return with back button
            }
        }); //end line of buttonContinue

        buttonHelpMeDecide = (Button)findViewById(R.id.buttonHelpMeDecide);
        buttonHelpMeDecide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), gamification_spinner_decide.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
                finish(); //take off stack so user can't return with back
            }
        }); //end line of help me decide button
    }

    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_startup);
        dm = new DecisionMatrix(this);
        dm.updateDM();
        gm = new Gamification(this);    //add "this"
        gm.updateG();

        functionsUI();
    }
}
