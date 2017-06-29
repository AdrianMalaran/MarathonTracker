package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

/**
 * Created by Hayes on 3/26/2016.
 */

import java.text.DecimalFormat;
import java.util.Calendar;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SpeedActivity extends Activity implements LocationListener {

    LocationManager lm;
    LocationListener ll;
    public Location location;

    Location previousLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {

        }

        this.onLocationChanged(null);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

    @Override
    public void onLocationChanged(Location currentLocation) {
        TextView txt = (TextView) this.findViewById(R.id.textView4);
        if (currentLocation == null)
        {
            txt.setText("-.- m/s");
        }
        else {
            float nCurrentSpeed = currentLocation.getSpeed();
            txt.setText(nCurrentSpeed + "m/s");
            System.out.println(nCurrentSpeed);
        }

    }

    // TODO move to util class?
    private float getAverageSpeed(float distance, float timeTaken) {
        //float minutes = timeTaken/60;
        //float hours = minutes/60;
        float speed = 0;
        if(distance > 0) {
            float distancePerSecond = timeTaken > 0 ? distance/timeTaken : 0;
            float distancePerMinute = distancePerSecond*60;
            float distancePerHour = distancePerMinute*60;
            speed = distancePerHour > 0 ? (distancePerHour/1000) : 0;
        }

        return speed;
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }
}
