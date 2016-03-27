package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconID;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconListAdapter;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.Database;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetails;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetailsFactory;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.NearestBeaconManager;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.ProximityContentManager;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.cloud.model.Color;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sam on 2016-03-26.
 */
public class BeaconConfig extends Activity {
    private static final String TAG = "BeaconConfig";
    private Button createButton;
    private ProximityContentManager proximityContentManager;

    private TextView uuid;
    private TextView beaconName;
    private TextView beaconColour;

    private EditText mileInput;
    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";

    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private BeaconListAdapter adapter;

    private Database db;
    private SQLiteDatabase database;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beaconName = new TextView(this);
        beaconColour = new TextView(this);
        uuid = new TextView(this);
        mileInput = new EditText(this);
        db = new Database(this);
        database = db.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_beacon);

        createButton = (Button) findViewById(R.id.add);
        if (createButton != null) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //POST TO DATABASE -- String id, Integer mileMarker, float longitude, float latitude
                    if (uuid != null && mileInput.length() > 0 && mileInput.length() <= 42)
                        db.addNewBeacon(database, uuid.getText().toString(), Integer.parseInt(mileInput.toString()), (float) (44 + 3 * Math.random()), (float) (44 + 3 * Math.random()));
                    //   Toast.makeText(this, "made" +uuid.getText(), Toast.LENGTH_LONG).show();
                }
            });

        }
        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 23105, 37595)),
                new EstimoteCloudBeaconDetailsFactory());

        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                if (content != null) {
                    final EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if (beaconDetails.getBeaconColor() != Color.UNKNOWN) {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uuid.append(beaconDetails.getId().toString());
                                    beaconName.append(beaconDetails.getBeaconName());
                                    beaconColour.append(beaconDetails.getBeaconColor().toString());
                                }
                            });
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        proximityContentManager.stopContentUpdates();
                        proximityContentManager.destroy();
                    } else {
                        beaconName.setText("searching...");
                    }
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        database.close();
        db.close();
        proximityContentManager.destroy();
    }

    protected void onStop() {
        super.onStop();
        proximityContentManager.destroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onStart() {
        super.onStart();
        proximityContentManager.startContentUpdates();

    }
}
