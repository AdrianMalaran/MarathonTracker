package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity  extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private final long LOCATION_REFRESH_TIME = 1000;
    private final float LOCATION_REFRESH_DISTANCE = 5;
    private static final String TAG = "MainActivity";
    private GoogleMap mMap;

    private Fragment mMapFragment;
    private LocationManager mLocationManager;
    private Button startRun;
    
    private Button btnShowData;
    private Button btnUpdateData;
   

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    
    Database myDb;

    private final LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(final Location location) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            LatLng currentPos = new LatLng(currentLatitude, currentLongitude);
            mMap.addMarker(new MarkerOptions().position(currentPos).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));



        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        startRun = (Button) findViewById(R.id.button);
        btnShowData = (Button) findViewById(R.id.showdb);
        btnUpdateData = (Button)findViewById(R.id.updatedb);
        myDb = new Database(this);
        
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, onRun.class));
            }
        });
        
        viewAll();
        
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }
    
    public void viewAll() {
        btnShowData.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Cursor res = myDb.getAllData();
                                if (res.getCount() == 0) {
                                    // show message
                                    showMessage("Error:", "Nothing found");
                                    return;
                                }
                                StringBuilder buffer = new StringBuilder();
                                while (res.moveToNext()) {
                                    buffer.append("Distance Travelled:" + res.getString(0) + "\n");
                                    buffer.append("Calories Burned:" + res.getString(1) + "\n");
                                    buffer.append("Step Count:" + res.getString(2) + "\n");
                                    buffer.append("Max Speed:" + res.getString(3) + "\n");
                                    buffer.append("Time Taken:" + res.getString(4) + "\n");
                                    buffer.append("Section:" + res.getString(5) + "\n\n");
                                }
                                showMessage("Data", buffer.toString());
                            }
                        }
                    );
                }


    public void UpdateData(){
        btnUpdateData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                public void onClick(View v){
                        // Test case for updating
                        boolean isUpdate = myDb.updateData("3.7","2.3","5","6.7","1.4","2");
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }


    /**
     * If connected get lat and long
     *
     */
    @Override
    public void onConnected(Bundle bundle) {
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
            }
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }


    public void onConnectionSuspended(int i) {}

    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location;
        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            currentLongitude = location.getLongitude();
            currentLatitude = location.getLatitude();
        }catch(SecurityException e) {
            e.printStackTrace();
        }

        LatLng currentPos = new LatLng(currentLatitude, currentLongitude);
        mMap.addMarker(new MarkerOptions().position(currentPos).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
