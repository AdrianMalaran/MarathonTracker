package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

import com.estimote.sdk.cloud.model.Color;

import java.util.UUID;

public class EstimoteCloudBeaconDetails {

    private String beaconName;
    private Color beaconColor;
    private UUID id;
    public EstimoteCloudBeaconDetails(String beaconName, Color beaconColor, UUID id) {
        this.beaconName = beaconName;
        this.beaconColor = beaconColor;
        this.id = id;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public Color getBeaconColor() {
        return beaconColor;
    }

    public UUID getId() { return  id; }
    @Override
    public String toString() {
        return "[beaconName: " + getBeaconName() + ", beaconColor: " + getBeaconColor() + "]";
    }
}
