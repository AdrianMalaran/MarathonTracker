package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconID;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.Database;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetails;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetailsFactory;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.ProximityContentManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    private Button startRun;
    public Button btnShowData;
    public Button btnUpdateData;

    Database myDb;


    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startRun = (Button) findViewById(R.id.button3);
        btnShowData = (Button) findViewById(R.id.showdb);
        btnUpdateData = (Button)findViewById(R.id.updatedb);
        myDb = new Database(this);

        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, historyPage.class));
            }
        });

        viewAll();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        myDb.insertData("5.0", "2.7", "1", "23.4", "2.3", "6");
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


    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.BeaconsWearhacksGmailCom.MarathonTracker6Wd/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.BeaconsWearhacksGmailCom.MarathonTracker6Wd/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
