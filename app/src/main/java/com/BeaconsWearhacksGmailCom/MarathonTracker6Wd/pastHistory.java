package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by Andrew on 2016-03-26.
 */
public class pastHistory extends Activity {

    private List<String> pastHistory;
    private Button newRunButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_history);

    }

    public void displayButtons() {
        if(pastHistory.size() == 0) {
            setContentView(R.layout.splash_screen);
            newRunButton = (Button) findViewById(R.id.button4);
            newRunButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(pastHistory.this, onRun.class));
                }
            });
        }
    }
}
