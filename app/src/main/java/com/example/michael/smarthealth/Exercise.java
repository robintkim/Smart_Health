package com.example.michael.smarthealth;

/**
 * Created by Robin on 4/15/2017.
 */

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
/**
 * When calling methods, use exerciseID's
 * Exercise IDs
 * 1. push ups
 * 2. sit ups
 * 3. pull ups
 * 4. squats
 *
 * Gamification IDs
 * 1. Level
 * 2. Badge
 * 3. Fact
 * 4. Friend
 * 5. Competition
 */

public class Exercise {
    private int pushup = 1;
    private int situp = 2;
    private int pullup = 3;
    private int squat = 4;
    private int numExercises = 4;

    //instruction variables
    private String[] instructions;

    //image variables
    private Drawable[] images;

    public Exercise() {
        //set up Instructions
        instructions = new String[numExercises];
        instructions[0] =
                "1. Lie face down on a flat surface\n" +
                "2. Keep your body straight and balance on your hands and toes\n" +
                "3. Extend your arms\n" +
                "4. For each repetition: bend your arms 90 degrees and then extend your arms";
        instructions[1] =
                "1. Lie down on your stomach on a flat surface\n" +
                "2. Put your feet flat on the surface so that your knees are bent\n" +
                "3. Cross your arms over your chest and put your hands on your shoulders\n" +
                "4. For each repetition: bend your body so your arms touch your knees";
        instructions[2] =
                "1. Find a bar or a ledge that can support your weight\n" +
                "2. Place your hands on the bar so when your arms are bent to 90 degrees, your upper arm is parallel to the floor\n" +
                "3. Hang on the bar so your feet are not touching the floor\n" +
                "4. For each repetition: pull yourself up to the bar by bending your arms";
        instructions[3] =
                "1. Stand on a flat surface with your feet shoulder width apart\n" +
                "2. Cross your arms over your chest and put your hands on your shoulders\n" +
                "3. For each repetition: bend knees to 90 degrees while keeping your upper body straight up";

        //set up images
        images = new Drawable[numExercises+1];
        // images[0] = R.drawable.pushup;
        // images[1] = R.drawable.situp;
        // images[2] = R.drawable.pullup;
        // images[3] = R.drawable.squatup;
        // images[4] = R.drawable.null;

    }

    public String getInstruction(int exerciseID){
        if (exerciseID <= numExercises && exerciseID > 0)
            return instructions[exerciseID-1];
        else
            return "invalid exerciseID";
    }

    public Drawable getImage(int exerciseID){
        if (exerciseID <= numExercises && exerciseID > 0)
            return images[exerciseID-1];
        else
            return images[numExercises];
    }



    /********************************************
     * private tool for class
     ********************************************/

}

