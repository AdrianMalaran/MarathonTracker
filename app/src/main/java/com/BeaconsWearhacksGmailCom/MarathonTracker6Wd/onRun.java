package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconID;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconStats;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetails;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetailsFactory;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.ProximityContentManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class onRun extends AppCompatActivity implements LocationListener {
    TextToSpeech t1;
    EditText ed1;
    private static final String TAG = "onRun";
    private TextView myTimer;
    private ImageButton stopButton;
    LocationManager lm;
    LocationListener ll;
    public Location location;
    private float speed;
    Location previousLocation = null;
    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    private Context mcontext;
    private Chronometer chronometer;
    private Thread thread;
    private DonutProgress donutProgress;

    private List<String> lapTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.during_run);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {

        }

        this.onLocationChanged(null);

        lapTimes = new ArrayList<>();
        mcontext = this;

        myTimer = (TextView) findViewById(R.id.textView3);
        stopButton = (ImageButton) findViewById(R.id.stopButton);

        if(chronometer == null) {
            chronometer = new Chronometer(mcontext);
            thread = new Thread (chronometer);
            thread.start();
            chronometer.start();
        }
       /* startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer == null) {
                    chronometer = new Chronometer(mcontext);
                    thread = new Thread (chronometer);
                    thread.start();
                    chronometer.start();
                }
            }
        });
*/
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer != null) {
                    chronometer.stop();
                    thread.interrupt();
                    thread = null;
                    chronometer = null;
                    startActivity(new Intent(onRun.this, historyPage.class));
                }
            }
        });

       /* lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lap = String.valueOf(myTimer);
                lapTimes.add(lap);
            }
        });*/

        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(42);


        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });


        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 23105, 37595)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;

                    BeaconStats bs = new BeaconStats();
                    //Read beacon info
                    bs = bs.grabById(beaconDetails.getId());
                    speakOut("You have ran 6 miles, Your average speed is 6");

                    //Write to db


                }
            }
        });

    }

    public void updateTime(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTimer.setText(text);
            }
        });
    }

    @Override
    public void onLocationChanged(Location currentLocation) {
        TextView txt = (TextView) this.findViewById(R.id.textView5);
        speed = 0;
        if (currentLocation == null)
        {
            speed = 0;
            txt.setText("-.- m/s");
        }
        else {
            speed = currentLocation.getSpeed();
            txt.setText(speed + "m/s");
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
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
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


    private void speakOut(String text) {

        final AudioManager audioManager =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                //    audioManager.abandonAudioFocus(afChangeListener);
            }
        };

        int result = audioManager.requestAudioFocus(afChangeListener, audioManager.STREAM_MUSIC, audioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        if(!t1.isSpeaking())
            audioManager.abandonAudioFocus(afChangeListener);
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
