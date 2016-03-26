package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.content.Context;

/**
 * Created by Andrew on 2016-03-26.
 */
public class Chronometer implements Runnable {

    public static final long MILLS_TO_MINUTES = 60000;
    public static final long MILLS_TO_HOURS = 3600000;

    private Context mcontext;
    private long mstartTime;

    private boolean isRunning;

    public Chronometer(Context context) {
        mcontext = context;
    }

    public void start() {
        mstartTime = System.currentTimeMillis();
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {

        while(isRunning) {
            long since = System.currentTimeMillis() - mstartTime;

            int seconds = (int) ((since / 1000) % 60);
            int minutes = (int) ((since/MILLS_TO_MINUTES) % 60);
            int hours = (int) ((since/MILLS_TO_HOURS) & 24);
            int millis = (int) (since % 1000);

            ((onRun)mcontext).updateTime(String.format(
                    "%02d:%02d:%02d:%03d", hours, minutes, seconds, millis
            ));
        }
    }
}
