package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconID;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.BeaconStats;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetails;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.EstimoteCloudBeaconDetailsFactory;
import com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote.ProximityContentManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class onRun extends AppCompatActivity implements SensorEventListener {
    TextToSpeech t1;
    EditText ed1;
    private static final String TAG = "onRun";
    private TextView myTimer;
    private ImageButton stopButton;

    private SensorManager sensorManager;
    private TextView count;
    private int stepsTotal;
    private int stepsThisSection;
    private List<Integer> stepsAllSections = new ArrayList<Integer>();
    private int offset;
    boolean start = true;
    boolean activityRunning;

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }


    private ProximityContentManager proximityContentManager;

    private Context mcontext;
    private Chronometer chronometer;
    private Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.during_run);
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
        activityRunning = true;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer != null) {
                    chronometer.stop();
                    thread.interrupt();
                    thread = null;
                    chronometer = null;
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
