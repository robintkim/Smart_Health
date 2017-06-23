package com.example.michael.smarthealth;

/**
 * Created by Michael on 5/14/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;

    //database name
    public static final String DATABASE_NAME = "smarthealth.db";
    //tables used
    public static final String TABLE_G = "Gamification_Table";
    public static final String TABLE_DM= "Decision_Matrix_Table";

    //column names
    public static final String KEY_ID = "id";

    //gamification specific columns
    public static final String LEVEL = "level"; //int
    public static final String CURRENTEXP = "currentexp"; //int
    public static final String MAXEXP = "maxexp"; //int
    public static final String METHOD = "method"; //int

    //decision matrix specific columns
    public static final String USERID = "user"; //INT
    public static final String TOTALSR = "totalSR"; //DOUBLE
    public static final String NUMTIMES = "numTimes"; // INT
    public static final String AVGSR = "avgSR"; //DOUBLE
    public static final String WEEKSR = "weekSR"; //DOUBLE
    public static final String WEEKNUMSUCCESS = "weekNumSuccess"; //INT
    public static final String SCORES = "scores"; //DOUBLE
    public static final String SUM = "sum"; //DOUBLE
    public static final String WEIGHTS = "weights"; //DOUBLE
    public static final String CURRENTSR = "currentSR"; //DOUBLE
    public static final String PREVIOUSREPS = "previousReps"; //INT
    public static final String CURRENTREPS = "currentReps"; //INT

    //Table Creation statements (sqlite statements to be executed with java functions)
    public static final String Create_Table_G= "CREATE TABLE " + TABLE_G + "("
            + KEY_ID + "INTEGER PRIMARY KEY, "
            + LEVEL + " INTEGER, "
            + CURRENTEXP + " INTEGER, "
            + MAXEXP + " INTEGER, "
            + METHOD + " INTEGER"
            + ")";

    public static final String Create_Table_DM = "CREATE TABLE " + TABLE_DM + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + USERID + " STRING, "
            + TOTALSR + " STRING, "
            + NUMTIMES + " STRING, "
            + AVGSR + " STRING, "
            + WEEKSR + " STRING, "
            + WEEKNUMSUCCESS + " STRING, "
            + SCORES + " STRING, "
            + SUM + " DOUBLE, "
            + WEIGHTS + " STRING, "
            + CURRENTSR + " STRING, "
            + PREVIOUSREPS + " STRING, "
            + CURRENTREPS + " STRING"
            + ")";

    public static synchronized DatabaseHelper getInstance(Context context){
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating required tables
        db.execSQL(Create_Table_DM);
        db.execSQL(Create_Table_G);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade, drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_G );

        //Create database tables
        onCreate(db);
    }


    public boolean insertDataDM(String id, String totalSR, String numTimes, String avgSR,
                              String weekSR, String weekNumSuccess, String scores, double sum, String weights,
                                String currentSR, String previousReps, String currentReps) {

        //open db with ability to save
        SQLiteDatabase db = this.getWritableDatabase();
        //store variables into contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTALSR,totalSR);
        contentValues.put(NUMTIMES,numTimes);
        contentValues.put(AVGSR,avgSR);
        contentValues.put(WEEKSR,weekSR);
        contentValues.put(WEEKNUMSUCCESS,weekNumSuccess);
        contentValues.put(SCORES,scores);
        contentValues.put(SUM,sum);
        contentValues.put(WEIGHTS,weights);
        contentValues.put(CURRENTSR,currentSR);
        contentValues.put(PREVIOUSREPS,previousReps);
        contentValues.put(CURRENTREPS,currentReps);
        long result = db.insert(TABLE_DM,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataDM() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DM, null);
        res.moveToFirst();
        return res;
    }

    //can only update if a value exist
    public boolean updateDataDM(String id, String totalSR, String numTimes, String avgSR,
                              String weekSR, String weekNumSuccess, String scores, double sum, String weights,
                              String currentSR, String previousReps, String currentReps) {

        //open db with ability to save
        SQLiteDatabase db = this.getWritableDatabase();
        //store variables into contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTALSR,totalSR);
        contentValues.put(NUMTIMES,numTimes);
        contentValues.put(AVGSR,avgSR);
        contentValues.put(WEEKSR,weekSR);
        contentValues.put(WEEKNUMSUCCESS,weekNumSuccess);
        contentValues.put(SCORES,scores);
        contentValues.put(SUM,sum);
        contentValues.put(WEIGHTS,weights);
        contentValues.put(CURRENTSR,currentSR);
        contentValues.put(PREVIOUSREPS,previousReps);
        contentValues.put(CURRENTREPS,currentReps);
        //update database with values stored in contentValues
        //update arguments -> Database, Contentvalues, where clause, where arguments
        db.update(TABLE_DM, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Cursor getAllDataG(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_G, null);
        return res;
    }

    //can only update if a value exists to update
    public boolean updateDataG(int level, int currentExp, int maxExp, int method){

        //open db with ability to save
        SQLiteDatabase db = this.getWritableDatabase();
        //store variables into contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(LEVEL,level);
        contentValues.put(CURRENTEXP,currentExp);
        contentValues.put(MAXEXP,maxExp);
        contentValues.put(METHOD,method);
        //update database with values stored in content values
        db.update(TABLE_G, contentValues, null, null);
        return true;

    }

    //can only insert if no value is in current slot of db
    public boolean insertDataG(String id, int level, int currentExp, int maxExp, int method){
        //open db with ability to save
        SQLiteDatabase db = this.getWritableDatabase();
        //store variables into contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(LEVEL,level);
        contentValues.put(CURRENTEXP,currentExp);
        contentValues.put(MAXEXP,maxExp);
        contentValues.put(METHOD,method);
        //db.insert returns -1 if it can't insert
        long result = db.insert(TABLE_G,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    
    /*
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }*/
}
