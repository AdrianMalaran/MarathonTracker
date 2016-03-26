package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Andrew on 2016-03-26.
 */
public class pastHistory extends Activity {

    private List<String> pastHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_history);
    }

    public void displayButtons() {
        if(pastHistory.size() == 0) {
            setContentView(R.layout.splash_screen);
        }
    }
}
