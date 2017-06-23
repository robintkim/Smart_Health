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
public class exercise_rewards extends AppCompatActivity {

    Button buttonConfirm;   //confirm button
    TextView grade;
    TextView rewards;
    ImageView badge;


    /************ TESTING VARIABLES ***********/
    // these variables are created before this activity: has to persist throughout app
    int method ;     //this will have been determined by exercise_startup.
    Gamification gm;
    DecisionMatrix dm; //one for each exercise
    /************ END TESTING VARIABLES ***********/


    public void functionsUI(){

        dm.updateDM();
        gm.updateG();
        method = gm.getMethod();

        grade = (TextView)findViewById(R.id.grade);
        rewards = (TextView)findViewById(R.id.rewards);
        badge = (ImageView)findViewById(R.id.badge);

        //current success rate
        double exerciseGrade = dm.getAllCurrentSR();
        String gradeAsString = String.format("%.0f", exerciseGrade);
        grade.setText(gradeAsString + "%");


        //rewards textview and badge imageview will be determined by method
        switch (method) {
            case 1:
                int exp = (int)exerciseGrade;
                rewards.setText(gm.getExpReward(exp));
                break;
            case 2:
                if (exerciseGrade >= 200){
                    rewards.setText("You got badge1!");
                    badge.setImageResource(gm.getBadgeReward(1));
                }
                else if (exerciseGrade >= 150) {
                    rewards.setText("You got badge2!");
                    badge.setImageResource(gm.getBadgeReward(2));
                }
                else if (exerciseGrade >= 100) {
                    rewards.setText("You got badge3!");
                    badge.setImageResource(gm.getBadgeReward(3));
                }
                else if (exerciseGrade >= 50) {
                    rewards.setText("You got badge4!");
                    badge.setImageResource(gm.getBadgeReward(4));
                }
                else {
                    rewards.setText("You need to do better for a badge!");
                }
        }


        buttonConfirm = (Button)findViewById(R.id.buttonConfirm); //reference by xml id
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: send workout information into database
                dm.updateDB();
                gm.updateDB();
                Intent intent = new Intent(v.getContext(), main_screen.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2

                finish(); // take off stack so user can't return to this page
            }
        }); //end line of buttonDone

    }//end functionsUI


    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_rewards);
        gm = new Gamification(this);
        dm = new DecisionMatrix(this);
        functionsUI(); //functions for UI
    }//end onCreate


}
