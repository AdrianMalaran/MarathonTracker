package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconID;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetails;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetailsFactory;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.ProximityContentManager;
import com.estimote.sdk.cloud.model.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 2016-03-26.
 */
public class mainPage extends Activity {

    private ProximityContentManager proximityContentManager;
    private static final String TAG = "mainPage";
    private Button mainActivity;
    private Button pastHistory;
    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        pastHistory = (Button)findViewById(R.id.button2);
        mainActivity = (Button)findViewById(R.id.button);

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainPage.this, MainActivity.class));
            }
        });

        pastHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainPage.this, pastHistory.class));
            }
        });

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 41270, 54969)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    text = "You're in " + beaconDetails.getBeaconName() + "'s range!";
                    backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                } else {
                    text = "No beacons in range.";
                    backgroundColor = null;
                }
                ((TextView) findViewById(R.id.textView)).setText(text);
                findViewById(R.id.relativeLayout).setBackgroundColor(
                        backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }
}
