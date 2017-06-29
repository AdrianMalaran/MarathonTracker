package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

import java.util.UUID;

/**
 * Created by sam on 2016-03-26.
 */
public class BeaconStats {

    private UUID id;
    private int mileMarker;
    private String beaconType;

    public BeaconStats(){}


    public BeaconStats(int mileMarker, String beaconType){
        this.mileMarker = mileMarker;
        this.beaconType = beaconType;
    }

    public int getMileMarker() { return mileMarker; }

    public String getbeaconType() {
        return beaconType;
    }

    public BeaconStats grabById(UUID id){
        //Search through db
        mileMarker = 2;
        beaconType = "as";

        return new BeaconStats(mileMarker, beaconType);
    }
    @Override
    public String toString() {
        return "[mileMarker: " + getMileMarker() + ", beacon type: " + getbeaconType() + "]";
    }





}
