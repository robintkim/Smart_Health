package com.example.michael.smarthealth;

/**
 * Created by Robin on 5/13/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;

import java.util.Random;

/**
 * Gamification IDs
 * 1. Level
 * 2. Badge
 * 3. Fact
 * 4. Friend
 * 5. Competition
 */

public class Gamification {
    private Context context;
    public DatabaseHelper db;
    //currently have:
    private int level;
    private int currentexp;
    private int maxexp;
    private int method;
    private boolean levelup;

    private int[] badges;

    private String[] facts;
    /*
    Friend
    Competition
     */

    //badges
    private int[] badgeImages;

    //constructor
    public Gamification(Context c) {
        context = c;
        db = DatabaseHelper.getInstance(context);
        level = 0;
        currentexp = 0;
        maxexp = 50;
        method = 0;
        levelup = false;
        badges = new int[4];
        for (int i=0; i<4; i++) {
            badges[i] = 0;
        }
        badgeImages = new int[4];
        badgeImages[0] = R.drawable.redbadge;
        badgeImages[1] = R.drawable.greenbadge;
        badgeImages[2] = R.drawable.purplebadge;
        badgeImages[3] = R.drawable.yellowbadge;

        //facts
        facts = new String[5];
        facts[0] = "Exercising allows you to control your main.  2 hours of moderate exercise, " +
                "or 1 hour of vigorous exercise a week can help maintain your weight.  For " +
                "losing weight including dieting.";
        facts[1] = "2 hours of moderate exercise a week can reduce your risk of cardiovascular " +
                "disease.";
        facts[2] = "2 hours of moderate exercise a week can reduce your risk of developing type " +
                "2 diabetes and metabolic syndrome.";
        facts[3] = "Being physically active lowers your risk of colon cancer and breast cancer.";
        facts[4] = "Regular exercise can help keep your thinking, learning, and judgement skills " +
                "sharp as you age.";
    }

    //increment
    public void addExp(int x){
        currentexp += x;
        if (currentexp >= maxexp) {
            level += 1;
            maxexp = 100*level;
            levelup = true;
        }
    }
    public void addBadge(int x){
        int num = x-1;
        badges[num] += 1;
    }

    private String getFact() {
        Random rn = new Random();
        int x = rn.nextInt(4);
        return facts[x];
    }

    //converts number to string (gamification method)
    public String getString(int x) {
        int num = x-1;
        String method = "";
        switch (num) {
            case 0:
                method = "you will gain levels";
                break;
            case 1:
                method = "you will gain a badge";
                break;
            case 2:
                method = getFact();
                break;
            case 3:
                method = "friends";
                break;
            case 4:
                method = "competition";
                break;
        }
        return method;
    }

    public int getBadgeReward(int x) {
        int num = x-1;
        addBadge(num);
        return badgeImages[num];
    }

    public String getExpReward(int x) {
        String text = "";
        addExp(x);
        if (levelup){
            text = "You've leveled up!";
            levelup = false;
        }
        else
            text = "You've gained " + x + " experience!";

        return text;
    }

    //Getters & Setters

    /***********************
     Database functions
     ***********************/

    public void insertDB(){
        db.insertDataG("1", level, currentexp, maxexp, method);
    }

    //trying to get around the id field in db.update(...)  with "1"
    public void updateDB(){
        db.updateDataG(level, currentexp, maxexp, method);
    }

    public void updateG(){
        Cursor res = db.getAllDataG();
        res.moveToFirst();
        if (res.getCount()==0) {
            showMessage("Error", res.toString() + "G");
            return;
        }
        do {
            level = Integer.parseInt(res.getString(1));
            currentexp = Integer.parseInt(res.getString(2));
            maxexp = Integer.parseInt(res.getString(3));
            method = Integer.parseInt(res.getString(4));
        } while(res.moveToNext());

        //showMessage("Data", level + "\n" + currentexp + "\n" + maxexp + "\n" + method);
    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public int getMethod(){
        return method+1;
    }
    public void setMethod(int m) {
        method = m-1;
    }
}
