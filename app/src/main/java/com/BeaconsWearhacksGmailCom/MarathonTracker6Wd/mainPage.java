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

    private static final String TAG = "mainPage";
    private Button mainActivity;
    private Button pastHistory;
    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();


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

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
