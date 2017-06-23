package com.example.michael.smarthealth;

/**
 * Created by Robin on 4/15/2017.
 */

import android.app.AlertDialog;
import android.content.Context;

import java.util.Arrays;
import java.util.Random;

import android.database.Cursor;

/******************************************************************************
 * Create a DecisionMatrix for each exercise
 * i.e.  DecisionMatrix pushup = new DecisionMatrix(int userID, int exerciseID)
 *
 * Decision Matrix:
 * --------------------------------------------------------------------------
 * factors # of positive/week Average success Scores Weights 0.4 0.6
 *
 * Levels(1) y z 0.4*x + 0.6*y Badges(2) y z 0.4*x + 0.6*y Facts(3) y z
 * 0.4*x + 0.6*y Friends(4) y z 0.4*x + 0.6*y Competition(5) y z 0.4*x +
 * 0.6*y
 * --------------------------------------------------------------------------
 * percentages for each = their Total/(sum of all Totals)
 *
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
 *******************************************************************************/

public class DecisionMatrix {
    private Context context;
    public DatabaseHelper db;

    private int userID; // user ID
    private double[] totalSR; // Running total of success rates
    private int[] numTimes; // count of how many of each was done;
    private double[] avgSR; // average success rates
    private double[][] weekSR; // Keep track of last 7 success rates
    private int[] weekNumSuccess; // # of weekly successes
    private double[] scores; // scores
    private double sum; // Sum of Totals
    private double[] weights; // Weights
    private int[] previousReps;
    private int[] currentReps;
    private double[] currentSR;

    public DecisionMatrix(Context c) {
        context = c;
        db = DatabaseHelper.getInstance(context);
        userID = 1;         //get this from db.
        totalSR = new double[5];
        numTimes = new int[5];
        avgSR = new double[5];
        weekSR = new double[5][7];
        weekNumSuccess = new int[5];
        scores = new double[5];
        sum = 0.0;
        weights = new double[5];
        currentSR = new double[5];
        previousReps = new int[5];
        currentReps = new int[5];

        for (int i = 0; i < 5; i++) {
            totalSR[i] = 0;
            numTimes[i] = 0;
            avgSR[i] = 0;
            weekNumSuccess[i] = 0;
            scores[i] = 0;
            currentSR[i] = 0;
            previousReps[i] = 1;
            currentReps[i] = 1;
        }

        // default weekly successes
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                weekSR[i][j] = 0.1;
            }
        }

        // default weights
        calcWeights();
    }

    /********************************************
     * private tool for class
     ********************************************/
    // calculate weights
    private void calcWeights() {
        sum = 0.0;
        // calculate the sum of scores
        for (int i = 0; i < 5; i++) {
            sum += scores[i];
        }

        // calculate weight for each game method
        for (int i = 0; i < 5; i++) {
            weights[i] = scores[i] / sum;
        }
    }

    /********************************************
     * public methods
     ********************************************/
    public int chooseMethod() {

        /*
        // choose a random number and see which method gets chosen depending on
        // weights
        double num = Math.random(); // random # between 0 and 1
        double limit = 0; // first limit is up to levelsWeight
        int i = 0; // weights array index

        while (num > limit)
        // instead of 0
        {
            limit += weights[i];
            i++;
        }

        return i;
        */

        //choose a pure random method 1-3
        Random r = new Random();
        return r.nextInt(3)+1;

    }


    public void insertDB() {
        //conversions to , separated strings
        String userIDString = Integer.toString(userID);
        String totalSRString = Arrays.toString(totalSR);
        String numTimesString = Arrays.toString(numTimes);
        String avgSRString = Arrays.toString(avgSR);
        String weekSRString = "";
        for (int i=0; i<5; i++)
        {
            weekSRString += "\n"+ Arrays.toString(weekSR[i]);
        }
        String weekNumSuccessString = Arrays.toString(weekNumSuccess);
        String scoresString = Arrays.toString(scores);
        String weightsString = Arrays.toString(weights);
        String currentSRString = Arrays.toString(currentSR);
        String previousRepsString = Arrays.toString(previousReps);
        String currentRepsString = Arrays.toString(currentReps);
        db.insertDataDM(
                userIDString,
                totalSRString,
                numTimesString,
                avgSRString,
                weekSRString,
                weekNumSuccessString,
                scoresString,
                sum,
                weightsString,
                currentSRString,
                previousRepsString,
                currentRepsString);
    }



    public void updateDB() {
        //conversions to , separated strings
        String userIDString = Integer.toString(userID);
        String totalSRString = Arrays.toString(totalSR);
        String numTimesString = Arrays.toString(numTimes);
        String avgSRString = Arrays.toString(avgSR);
        String weekSRString = "";
        for (int i=0; i<5; i++)
        {
            weekSRString += "\n"+ Arrays.toString(weekSR[i]);
        }
        String weekNumSuccessString = Arrays.toString(weekNumSuccess);
        String scoresString = Arrays.toString(scores);
        String weightsString = Arrays.toString(weights);
        String currentSRString = Arrays.toString(currentSR);
        String previousRepsString = Arrays.toString(previousReps);
        String currentRepsString = Arrays.toString(currentReps);

        db.updateDataDM(
                userIDString,
                totalSRString,
                numTimesString,
                avgSRString,
                weekSRString,
                weekNumSuccessString,
                scoresString,
                sum,
                weightsString,
                currentSRString,
                previousRepsString,
                currentRepsString);
    }


    public void updateDM() {

        Cursor res = db.getAllDataDM();
        res.moveToFirst();
        if (res.getCount()==0) {
            showMessage("Error", res.toString());
            return;
        }

        do {
            userID = Integer.parseInt(res.getString(0));

            res.getString(1);

            String x = res.getString(2).replaceAll("\\[","").replaceAll("\\]","");
            String[] s = x.split(",");
            for (int i=0; i<5; i++) {
                    totalSR[i] = Double.parseDouble(s[i]);
            }

            /*
            x = res.getString(3).replaceAll("\\[","").replaceAll("\\]",",");
            s = x.split(",");
            */
            s = res.getString(3).split(",");
            for (int i=0; i<5; i++) {
                if (i==0)
                    numTimes[i] = Integer.parseInt(s[i].substring(1));
            }

            x = res.getString(4).replaceAll("\\[","").replaceAll("\\]","");
            s = x.split(",");
            for (int i=0; i<5; i++) {
                    avgSR[i] = Double.parseDouble(s[i]);
            }

            x = res.getString(5);
            x = x.replace("\n", "").replace("\r","");
            x = x.replaceAll("\\[","").replaceAll("\\]",",");
            String[] y = x.split(",");
            int k = 0;
            for (int i=0; i<5; i++) {
                for (int j = 0; j<7; j++) {

                        weekSR[i][j] = Double.parseDouble(y[k]);

                    k++;
                }
            }
            /*
            x = res.getString(6).replaceAll("\\[","").replaceAll("\\]",",");
            s = x.split(",");
            */
            s = res.getString(6).split(",");
            for (int i=0; i<5; i++) {
                if(i==0)
                    weekNumSuccess[i] = Integer.parseInt(s[i].substring(1));
            }

            x = res.getString(7).replaceAll("\\[","").replaceAll("\\]","");
            s = x.split(",");
            for (int i=0; i<5; i++) {
                    scores[i] = Double.parseDouble(s[i]);
            }

            if (res.getString(8) == null){
                sum = 0;
            }
            else {
                sum = Double.parseDouble(res.getString(8));
            }

            x = res.getString(9).replaceAll("\\[","").replaceAll("\\]","");
            s = x.split(",");
            for (int i=0; i<5; i++) {
                    scores[i] = Double.parseDouble(s[i]);
            }

            x = res.getString(10).replaceAll("\\[","").replaceAll("\\]","");
            s = x.split(",");
            for (int i=0; i<5; i++) {
                currentSR[i] = Double.parseDouble(s[i]);
            }

            //x = res.getString(11).replaceAll("\\[","").replaceAll("\\]","");
            //s = x.split(",");
            s = res.getString(11).split(",");
            for (int i=0; i<5; i++) {
                if (i==0)
                    previousReps[i] = Integer.parseInt(s[i].substring(1));
            }

            //x = res.getString(12).replaceAll("\\[","").replaceAll("\\]","");
            //s = x.split(",");
            s = res.getString(12).split(",");
            for (int i=0; i<5; i++) {
                if (i==0)
                currentReps[i] = Integer.parseInt(s[i].substring(1));
            }

        } while(res.moveToNext());

    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void display(){
        showMessage("Data", toString());
    }

    public void update(int gameID, int reps) {
        previousReps[gameID-1] = currentReps[gameID-1];
        currentReps[gameID-1] = reps;
        currentSR[gameID-1] = (currentReps[gameID-1] - previousReps[gameID-1])/previousReps[gameID-1];
        //take off oldest success rate and add newest success rate
        for (int i=0; i<6; i++)
        {
            weekSR[gameID-1][i] = weekSR[gameID-1][i+1];
        }
        weekSR[gameID-1][6] = currentSR[gameID-1];

        //count # of successes past week and put into weekNumSuccess
        weekNumSuccess[gameID-1] = 0;
        for (int i=0; i<7; i++)
        {
            if(weekSR[gameID-1][i] >= 0)
            {
                weekNumSuccess[gameID-1]++;
            }
        }

        //update avg success rate
        totalSR[gameID-1] += currentSR[gameID-1];		//add to success rate running total
        numTimes[gameID-1]++; 					//increment # of times game method used
        avgSR[gameID-1] = totalSR[gameID-1] / numTimes[gameID-1]; 		//total success / num times used

        //calculate new scores for this game method
        scores[gameID-1] = 0.4*weekNumSuccess[gameID-1] + 0.6*avgSR[gameID-1];

        //calculate new weights
        calcWeights();

        //showMessage("Data", toString());
    }


    public double getAllCurrentSR() {
        double avg = 0.0;
        for (int i=0; i<5; i++){
            avg += currentSR[i];
        }
        avg = avg/5;
        return avg;
    }


    /********************************************
     * getters, setters, toString
     ********************************************/

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double[] getTotalSR() {
        return totalSR;
    }

    public void setTotalSR(double[] totalSR) {
        this.totalSR = totalSR;
    }

    public int[] getNumTimes() {
        return numTimes;
    }

    public void setNumTimes(int[] numTimes) {
        this.numTimes = numTimes;
    }

    public double[] getAvgSR() {
        return avgSR;
    }

    public void setAvgSR(double[] avgSR) {
        this.avgSR = avgSR;
    }

    public double[][] getWeekSR() {
        return weekSR;
    }

    public void setWeekSR(double[][] weekSR) {
        this.weekSR = weekSR;
    }

    public int[] getWeekNumSuccess() {
        return weekNumSuccess;
    }

    public void setWeekNumSuccess(int[] weekNumSuccess) {
        this.weekNumSuccess = weekNumSuccess;
    }

    public double[] getScores() {
        return scores;
    }

    public void setScores(double[] scores) {
        this.scores = scores;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double[] getWeights() { return weights; }

    public void setWeights(double[] weights) { this.weights = weights; }

    public int[] getPreviousReps() { return previousReps; }

    public void setPreviousReps(int[] reps) { this.previousReps = reps; }

    public int[] getCurrentReps() { return currentReps; }

    public void setCurrentReps(int[] reps) { this.currentReps = reps; }

    public double getCurrentSR(int gameID) { return currentSR[gameID-1]; }

    public void setCurrentSR(int gameID, double sr) { this.currentSR[gameID-1] = sr; }

    @Override
    public String toString() {
        String weekSRString = "";
        for (int i=0; i<5; i++)
        {
            weekSRString += "\n"+ Arrays.toString(weekSR[i]);
        }
        return "DecisionMatrix \n"
                + "userID=" + userID + "\n"
                + "totalSR=" + Arrays.toString(totalSR) + "\n"
                + "numTimes=" + Arrays.toString(numTimes) + "\n"
                + "avgSR=" + Arrays.toString(avgSR) + "\n"
                + "weekSR=" + weekSRString + "\n"
                + "weekNumSuccess=" + Arrays.toString(weekNumSuccess) + "\n"
                + "scores=" + Arrays.toString(scores) + "\n"
                + "sum=" + sum + "\n"
                + "weights=" + Arrays.toString(weights);
    }



}