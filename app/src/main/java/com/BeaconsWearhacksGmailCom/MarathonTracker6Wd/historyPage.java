package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
<<<<<<< HEAD
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
=======
import android.os.Bundle;
>>>>>>> 52862da9da43e5230559f39db355df2dc68faa3f

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 2016-03-26.
 */
public class historyPage extends Activity {


    Database db;
    SQLiteDatabase database;
    TextView summary;

    Map<Integer, Double> calories;
    Map<Integer, Integer> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);


<<<<<<< HEAD
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtGhost.setTypeface(tf);
        //double distanceTravelled, double caloriesBurned, double stepCount, double maxSpeed, String timeTaken, int section
        Log.d("OUT", "starting async");
        db = new Database(this);
        database = db.getReadableDatabase();
        Log.d("db", "opened " + database.toString());
        summary = (TextView) findViewById(R.id.sum);
    }

    private class async extends AsyncTask<String, Void,  Map<Integer, Integer>>  {

        protected  Map<Integer, Integer> doInBackground(String... str)  {

            Map<Integer, Integer> temp = new HashMap<>();
            String[] args = {str[0]};
            Cursor cs = db.getAllData(database, "historical_data", args, 10);
            while(!cs.moveToFirst())
                try {
                    Log.d("", "waiting...");
                    wait(1);
                }catch(InterruptedException e){return temp;}
            Log.d("OUT", cs.getColumnNames().length + "");// + " " + cs.isNull(1));
                do {
                   // Log.d("db", cs.getCount() + "");
                    temp.put(cs.getInt(cs.getColumnIndex("SECTION")), cs.getInt(2));//cs.getInt(cs.getColumnIndex("STEP_COUNT")));// cs.getDouble(cs.getColumnIndex(str[0])));
                } while (cs.moveToNext());
                cs.close();
                db.close();
                database.close();

            return temp;
        }


        protected void onPostExecute(Map<Integer, Double> a) {
            Log.e("ERR", "DONE");
        }

        protected void onPreExecute() {
        }

        protected void onProgressUpdate(Void... values) {
        }
    }


    public void onStart() {
        super.onStart();
      //  steps = new HashMap<>();
        try {
            steps = new async().execute("STEP_COUNT").get();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        summary.setText(steps.toString());


    }

    public void updateCal(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                summary.append(text);
            }
        });

=======
>>>>>>> 52862da9da43e5230559f39db355df2dc68faa3f
    }
}
