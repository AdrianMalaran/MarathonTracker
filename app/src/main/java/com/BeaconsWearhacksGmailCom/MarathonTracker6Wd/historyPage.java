package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 2016-03-26.
 */
public class historyPage extends Activity {


    Database db;
    SQLiteDatabase database;
    TextView summary;

    Map<Integer, Double> calories;
    Map<Integer, Double> distance;
    Map<Integer, Integer> steps;
    Map<Integer, Double> speed;
    Map<Integer, String> timed;
    LineGraphSeries<DataPoint> series;
    GraphView graph;
    TabHost th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);
        th = (TabHost) findViewById(R.id.tabHost);
        //double distanceTravelled, double caloriesBurned, double stepCount, double maxSpeed, String timeTaken, int section
        db = new Database(this);
        database = db.getReadableDatabase();
        summary = (TextView) findViewById(R.id.sum);

        distance = new HashMap<>();
        calories = new HashMap<>();
        speed = new HashMap<>();
        steps = new HashMap<>();


        for (int i = 0; i < 10; i++) {
            distance.put(i, i + 1.4);
            steps.put(i, 500 * i + (int) Math.random() * 300);
        }

        DataPoint[] dvs = new DataPoint[12];
        if (steps != null && distance != null) {
            for (int i = 0; i < steps.size(); i++) {
                if (distance.get(i) != null && steps.get(i) != null)
                    dvs[i] = new DataPoint(distance.get(i), steps.get(i));
            }

            series = new LineGraphSeries<>(dvs);

            if (graph != null && series != null)//.isEmpty());
                graph.addSeries(series);

        }
    }


    private class async extends AsyncTask<String, Void, Map<Integer, Double>> {

        protected Map<Integer, Double> doInBackground(String... str) {
            int i = 0;
            Map<Integer, Double> temp = new HashMap<>();
            String[] args = {str[0]};
            Cursor cs = db.getAllData(database, "historical_data", args, 10);
            while (!cs.moveToFirst())
                try {
                    Log.d("", "waiting...");
                    wait(1);
                } catch (InterruptedException e) {
                    return temp;
                }
            //Log.d("OUT", cs.getColumnNames().length + "");// + " " + cs.isNull(1));
            do {
                // Log.d("db", cs.getCount() + "");
                //temp.put(1,1);

                temp.put(i++, cs.getDouble(cs.getColumnIndex(str[0])));//cs.getInt(cs.getColumnIndex("STEP_COUNT")));// cs.getDouble(cs.getColumnIndex(str[0])));
            } while (cs.moveToNext());
            cs.close();

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

    private class asyncInt extends AsyncTask<String, Void, Map<Integer, Integer>> {

        protected Map<Integer, Integer> doInBackground(String... str) {

            int i = 0;
            Map<Integer, Integer> temp = new HashMap<>();
            String[] args = {str[0]};
            // Log.d("db", cs.getCount() + "");
            //temp.put(1,1);

            Cursor cs = db.getAllData(database, "historical_data", args, 10);
            while (!cs.moveToFirst())
                try {
                    Log.d("", "waiting...");
                    wait(1);
                } catch (InterruptedException e) {
                    return temp;
                }
            //Log.d("OUT", cs.getColumnNames().length + "");// + " " + cs.isNull(1));
            do {
                temp.put(i++, cs.getInt(cs.getColumnIndex(str[0])));
            } while (cs.moveToNext());
            cs.close();

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
    }
        /*try {
            distance = new async().execute("DISTANCE_TRAVELLED").get();
            calories = new async().execute("CALORIES_BURNED").get();
            steps = new asyncInt().execute("STEP_COUNT").get();
            //timed = new async().execute("TIME_SPLIT").get();
            speed = new async().execute("MAX_SPEED").get();

            db.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i : distance.keySet())
            Log.d("" + i,""+ distance.get(i));


            graph = new GraphView(this);
            graph = (GraphView) findViewById(R.id.graph1);
            DataPoint[] newSet = new DataPoint[dvs.length];
            for (int k = 0, j = k; k < dvs.length; k++){
                if ( dvs[k] != null &&  dvs[k] != null)
                    dvs[j++] = dvs[k];
            }

*/


/*    public void updateCal(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                summary.append(text);
            }
        });
    }*/
}
