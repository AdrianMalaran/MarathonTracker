package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by swagpanduhbur on 3/26/2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tracker.db";
    public static final String TABLE_NAME = "runner_tracker";
    public static final String COL_1 = "DISTANCE_TRAVELLED";
    public static final String COL_2 = "CALORIES_BURNED";
    public static final String COL_3 = "STEP_COUNT";
    public static final String COL_4 = "MAX_SPEED";
    public static final String COL_5 = "TOTAL_TIME_TAKEN";


    public Database(Context context){
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (DISTANCE_TRAVELLED FLOAT, CALORIES_BURNED FLOAT, STEP_COUNT INTEGER, MAX_SPEED FLOAT, TOTAL_TIME_TAKEN FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Float distanceTravelled, Float caloriesBurned, Integer stepCount, Float maxSpeed, Float timeTaken ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, distanceTravelled); // DISTANCE TRAVELLED
        contentValues.put(COL_2, caloriesBurned); // CALORIES BURNED
        contentValues.put(COL_3, stepCount); // STEP COUNT
        contentValues.put(COL_4, maxSpeed); // MAX SPEED
        contentValues.put(COL_5, timeTaken); // TOTAL TIME TAKEN
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result != -1)
            return true;
        else
            return false;
    }

}
