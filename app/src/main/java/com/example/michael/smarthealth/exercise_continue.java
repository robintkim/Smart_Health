package com.example.michael.smarthealth;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 5/6/2017.
 */
public class exercise_continue extends AppCompatActivity {
    //xml ids
    ListView listViewWorkout;
    Button buttonDone;
    Button buttonConfirm;
    EditText editTextNumberOfReps;
    TextView textViewDescription;
    ImageView imageViewExercise;
    ViewFlipper viewFlipper;
    //adapters
    ListAdapter listViewWorkoutAdapter;
    ArrayList<String> arrayList;
    //images from the drawable folder
    public Integer images[] = {R.drawable.pullup, R.drawable.pushup, R.drawable.situp, R.drawable.squat};
    public int exerciseImage = 0;

    DecisionMatrix dm = new DecisionMatrix(this);
    Gamification gm = new Gamification(this);
    int sumReps;
    int method; //this has to be brought in from exercise_startup.

    public void functionsUI(){

        dm.updateDM();
        gm.updateG();
        method = gm.getMethod();


        //construct the exercise class to use functions
        final Exercise myExercise = new Exercise();
        //initialize java vars equal to xml items
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        listViewWorkout = (ListView)findViewById(R.id.listViewWorkout); //workout list on original page
        textViewDescription = (TextView)findViewById(R.id.textViewDescription); //description on flipped page
        imageViewExercise = (ImageView)findViewById(R.id.imageViewExercise); //image on flipped page

        //make an adapter for listView to click on individual items
        //populateList() provides a list of items to populate the listViewWorkout
        listViewWorkoutAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, populateList());
        //puts data in listViewWorkout
        listViewWorkout.setAdapter(listViewWorkoutAdapter);
        //this onItemClickListener makes each individual item in the listViewWorkout pop up the description page
        listViewWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //parent = The AdapterView where the click happened.
                //view = The view within the AdapterView that was clicked (this will be a view provided by the adapter)
                //position = The position of the view in the adapter. (integer)
                //id = The row id of the item that was clicked.
                //TODO: display different image and text based on listView exercise clicked

                //convert item clicked into string format
                String thisExercise = (String)(listViewWorkout.getItemAtPosition(position));
                //Toast.makeText(exercise_continue.this, thisExercise, Toast.LENGTH_SHORT).show(); //test: display string as popup message

                //if an exercise name is found in the clicked on item of listViewWorkout, display appropriate image and description
                Boolean foundExerciseName;
                foundExerciseName = thisExercise.contains("Pushups");
                if(foundExerciseName == true){
                    textViewDescription.setText(myExercise.getInstruction(1));
                    imageViewExercise.setImageResource(R.drawable.pushup);
                }

                if(foundExerciseName = thisExercise.contains("Situps")){
                    textViewDescription.setText(myExercise.getInstruction(2));
                    imageViewExercise.setImageResource(R.drawable.situp);
                }

                if(foundExerciseName = thisExercise.contains("Squats")){
                    textViewDescription.setText(myExercise.getInstruction(4));
                    imageViewExercise.setImageResource(R.drawable.squat);
                }

                if(foundExerciseName = thisExercise.contains("Pullups")){
                    textViewDescription.setText(myExercise.getInstruction(3));
                    imageViewExercise.setImageResource(R.drawable.pullup);
                }


                //Set animations and flip to exercise information
                viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_to_left);
                viewFlipper.setDisplayedChild(1); //1 = page to flip to, 0 = original page
            }//end onItemClick override

        });//end listViewWorkout onItemClickListener


        /********************************
        for "confirm" button add to sumReps.
         for "done" call DecisionMatrix update(method, sumReps);
         *******************************/
        editTextNumberOfReps = (EditText)findViewById(R.id.editTextNumberOfReps);
        buttonConfirm = (Button)findViewById(R.id.buttonConfirm); //reference by xml id
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: send workout information into database

                sumReps += Integer.parseInt((editTextNumberOfReps.getText().toString()));

                if(viewFlipper.getDisplayedChild() != 0) {
                    viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_to_left);
                    viewFlipper.setDisplayedChild(0); //0 = original page
                }
            }
        }); //end line of buttonConfirm


        buttonDone = (Button)findViewById(R.id.buttonDone); //reference by xml id
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: send workout information into database

                dm.update(method, sumReps);

                dm.updateDB();
                gm.updateDB();

                Intent intent = new Intent(v.getContext(), exercise_rewards.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
                finish(); // take off stack so user can't return with back
            }
        }); //end line of buttonDone

    }//end functions UI

    //an example of adding items to the listViewWorkout
    public List<String> populateList() {
        List<String> exerciseNames = new ArrayList<String>();
        exerciseNames.add("Pushups");
        exerciseNames.add("Situps");
        exerciseNames.add("Squats");
        exerciseNames.add("Pullups");
        //exerciseNames.add("30 second break");

        return exerciseNames;

    }

    //allows hardware back button to go back to the original screen when on the flipped view
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(viewFlipper.getDisplayedChild() != 0){
                viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_from_right);
                viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_to_left);
                viewFlipper.setDisplayedChild(0); //0 = original page
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }//end onKeyDown

    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_continue);
        dm = new DecisionMatrix(this);
        gm = new Gamification(this);
        functionsUI();

    }//end onCreate
}
