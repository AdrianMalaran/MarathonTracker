package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by swagpanduhbur on 3/26/2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tracker.db";
    public static final String TABLE_NAME = "runner_tracker";
    public static final String COL_1 = "RACE_NAME";
    public static final String COL_2 = "DISTANCE_TRAVELLED";
    public static final String COL_3 = "CALORIES_BURNED";
    public static final String COL_4 = "STEP_COUNT";
    public static final String COL_5 = "MAX_SPEED";
    public static final String COL_6 = "TOTAL_TIME_TAKEN";
    public static final String COL_7 = "SECTION";

    //BEACON TABLE
    public static final String TABLE_NAME1 = "beacon_info";
    public static final String COL1_1 = "ID";
    public static final String COL1_2 = "MILE_MARKER";
    public static final String COL1_3 = "LONGITUDE";
    public static final String COL1_4 = "LATITUDE";
    public static final String COL1_5 = "TYPE";

    //Historical Table
    public static final String TABLE_NAME2 = "historical_data";
    public static final String COL2_1 = "RACE_NAME";
    public static final String COL2_2 = "DISTANCE_TRAVELLED";
    public static final String COL2_3 = "CALORIES_BURNED";
    public static final String COL2_4 = "STEP_COUNT";
    public static final String COL2_5 = "MAX_SPEED";
    public static final String COL2_6 = "SPLIT_TIME";

    SQLiteDatabase db;

    public Database(Context context){
        super(context, DATABASE_NAME, null, 1);

    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (DISTANCE_TRAVELLED DOUBLE, CALORIES_BURNED DOUBLE, STEP_COUNT INTEGER, MAX_SPEED DOUBLE, TOTAL_TIME_TAKEN DOUBLE, SECTION INT PRIMARY KEY AUTOINCREMENT)");
        db.execSQL("create table " + TABLE_NAME2 + " (DISTANCE_TRAVELLED DOUBLE, CALORIES_BURNED DOUBLE, STEP_COUNT INTEGER, MAX_SPEED DOUBLE, TOTAL_TIME_TAKEN DOUBLE, SECTION INT PRIMARY KEY AUTOINCREMENT)");
    }

    public void createHistoric() {
        db.execSQL("create table " + TABLE_NAME2 + " (DISTANCE_TRAVELLED DOUBLE, CALORIES_BURNED DOUBLE, STEP_COUNT INTEGER, MAX_SPEED DOUBLE, TOTAL_TIME_TAKEN DOUBLE, SECTION INT PRIMARY KEY AUTOINCREMENT)");
    }

    public void createLocal() {
        db.execSQL("create table " + TABLE_NAME + " (DISTANCE_TRAVELLED DOUBLE, CALORIES_BURNED DOUBLE, STEP_COUNT INTEGER, MAX_SPEED DOUBLE, TOTAL_TIME_TAKEN DOUBLE, SECTION INT PRIMARY KEY AUTOINCREMENT)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String distanceTravelled, String caloriesBurned, String stepCount, String maxSpeed, String timeTaken, String section ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, distanceTravelled); // DISTANCE TRAVELLED
        contentValues.put(COL_2, caloriesBurned); // CALORIES BURNED
        contentValues.put(COL_3, stepCount); // STEP COUNT
        contentValues.put(COL_4, maxSpeed); // MAX SPEED
        contentValues.put(COL_5, timeTaken); // TOTAL TIME TAKEN
        contentValues.put(COL_6, section); // SECTION
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result != -1)
            return true;
        else
            return false;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +  TABLE_NAME, null);
        return res;
    }





    public boolean updateData(String distanceTravelled, String caloriesBurned, String stepCount, String maxSpeed, String timeTaken, String section){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, distanceTravelled); // DISTANCE TRAVELLED
        contentValues.put(COL_2, caloriesBurned); // CALORIES BURNED
        contentValues.put(COL_3, stepCount); // STEP COUNT
        contentValues.put(COL_4, maxSpeed); // MAX SPEED
        contentValues.put(COL_5, timeTaken); // TOTAL TIME TAKEN
        contentValues.put(COL_6, section); // SECTION
        db.update(TABLE_NAME, contentValues, "SECTION = ?", new String[]{section});
        return true;
    }
}
