package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;
/**
 * Created by Hayes on 3/26/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class HayesThing extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView count;
    private int stepsTotal;
    private int stepsThisSection;
    private List<Integer> stepsAllSections = new ArrayList<Integer>();
    private int offset;
    boolean start = true;
    boolean activityRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //count = (TextView) findViewById(R.id.textView3);

        activityRunning = true;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.onResume();
    }

    @Override
    protected void onResume() {
        //offset = Integer.parseInt(count.getText().toString());
        System.out.println(R.id.textView3);
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepsThisSection = (int)event.values[0];

        if (start){
            offset = stepsThisSection;
            start = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // AFTER THE WHOLE TRIP IS DONE
    public int getTotalSteps(){
        return stepsTotal;
    }

    // DURING THE TRIP
    public int getCurrentSteps(){
        int currentSteps = 0;
        for (Integer stepsPerSection: stepsAllSections){
            currentSteps += stepsPerSection;
        }
        currentSteps += stepsThisSection;
        return currentSteps;
    }

    // GET SPECIFIC SECTION
    public int getSteps(int section){
        return stepsAllSections.get(section);
    }

    public void passCheckPoint(){
        stepsAllSections.add(stepsThisSection);
        stepsTotal += stepsThisSection;
        stepsThisSection = 0;
    }


}
