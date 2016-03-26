package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Andrew on 2016-03-26.
 */
public class historyPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);
        // Font path
        String fontPath = "fonts/Steinerlight.ttf";

        // text view label
        TextView txtGhost = (TextView) findViewById(R.id.textView8);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtGhost.setTypeface(tf);
    }
}
