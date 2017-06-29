package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String COL_5 = "TIME_SPLIT";
    public static final String COL_6 = "SECTION";

    //BEACON TABLE
    public static final String TABLE_NAME1 = "beacon_info";
    public static final String COL1_1 = "ID";
    public static final String COL1_2 = "MILE_MARKER";
    public static final String COL1_3 = "LONGITUDE";
    public static final String COL1_4 = "LATITUDE";
    public static final String COL1_5 = "TYPE";

    //Historical Table
    public static final String TABLE_NAME2 = "historical_data";
    public static final String COL2_1 = "DISTANCE_TRAVELLED";
    public static final String COL2_2 = "CALORIES_BURNED";
    public static final String COL2_3 = "STEP_COUNT";
    public static final String COL2_4 = "MAX_SPEED";
    public static final String COL2_5 = "TIME_SPLIT";
    public static final String COL2_6 = "SECTION";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    private static SQLiteOpenHelper instance;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (DISTANCE_TRAVELLED DOUBLE, CALORIES_BURNED DOUBLE, STEP_COUNT INTEGER, MAX_SPEED DOUBLE, TIME_SPLIT STRING, SECTION INTEGER PRIMARY KEY)");
        db.execSQL("create table " + TABLE_NAME2 + " (DISTANCE_TRAVELLED DOUBLE, CALORIES_BURNED DOUBLE, STEP_COUNT DOUBLE, MAX_SPEED DOUBLE, TIME_SPLIT STRING, SECTION INTEGER PRIMARY KEY)");
        db.execSQL("create table " + TABLE_NAME1 + " (ID STRING PRIMARY KEY, MILE_MARKER INTEGER, LONGITUDE FLOAT, LATITUDE FLOAT, TYPE STRING)");
    }

    public void onOpen(SQLiteDatabase db){
        //Log.d("db", "opened" + db.toString());
        return;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(SQLiteDatabase db, double distanceTravelled, double caloriesBurned, double stepCount, double maxSpeed, String timeTaken, int section) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, distanceTravelled); // DISTANCE TRAVELLED
        contentValues.put(COL_2, caloriesBurned); // CALORIES BURNED
        contentValues.put(COL_3, stepCount); // STEP COUNT
        contentValues.put(COL_4, maxSpeed); // MAX SPEED
        contentValues.put(COL_5, timeTaken); // TOTAL TIME TAKEN
        contentValues.put(COL_6, section); // RACE SECTION
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertDataHistorical(SQLiteDatabase db, double distanceTravelled, double caloriesBurned, double stepCount, double maxSpeed, String timeTaken, int section) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_1, distanceTravelled); // DISTANCE TRAVELLED
        contentValues.put(COL2_2, caloriesBurned); // CALORIES BURNED
        contentValues.put(COL2_3, stepCount); // STEP COUNT
        contentValues.put(COL2_4, maxSpeed); // MAX SPEED
        contentValues.put(COL2_5, timeTaken); // TOTAL TIME TAKEN
        contentValues.put(COL2_6, section); // RACE SECTION
        Log.d("entering", contentValues.toString());
        long result = db.insert(TABLE_NAME2, null, contentValues);
        return result != -1;
    }
    public Cursor getData(SQLiteDatabase db, String table, String col) {
        Cursor res = db.rawQuery("select section," + col + " from " + table, null);
        return res;
    }
    //(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
    public Cursor getAllData(SQLiteDatabase db, String table, String[] cols, int lim) {
        Cursor res = db.query(table, cols,null,null,null,null,"SECTION","10");
        return res;
    }

    public boolean addNewBeacon(SQLiteDatabase db, String id, Integer mileMarker, float longitude, float latitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_1, id); // DISTANCE TRAVELLED
        contentValues.put(COL1_2, mileMarker); // CALORIES BURNED
        contentValues.put(COL1_3, longitude); // STEP COUNT
        contentValues.put(COL1_4, latitude); // MAX SPEED

        if (mileMarker == 0)
            contentValues.put(COL1_5, "START"); // TOTAL TIME TAKEN
        if (mileMarker == 42)
            contentValues.put(COL1_5, "END"); // TOTAL TIME TAKEN
        else
            contentValues.put(COL1_5, "ON_RACE"); // TOTAL TIME TAKEN
        long result = db.insert(TABLE_NAME1, null, contentValues);
        return result != -1;
    }

}
