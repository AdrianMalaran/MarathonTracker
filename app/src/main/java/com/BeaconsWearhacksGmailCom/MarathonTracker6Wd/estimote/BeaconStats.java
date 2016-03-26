package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

import java.util.UUID;

/**
 * Created by sam on 2016-03-26.
 */
public class BeaconStats {

    private UUID id;
    private int mileMarker;
    private String beaconType;

    BeaconStats(String name){


        //Make table cal
}

    public int getMileMarker() { return mileMarker; }

    public String getbeaconType() {
        return beaconType;
    }


    @Override
    public String toString() {
        return "[mileMarker: " + getMileMarker() + ", beacon type: " + getbeaconType() + "]";
    }





}
