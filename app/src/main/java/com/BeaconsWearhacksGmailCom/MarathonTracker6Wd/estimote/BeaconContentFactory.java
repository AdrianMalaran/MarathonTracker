package com.BeaconsWearhacksGmailCom.MarathonTracker6Wd.estimote;

public interface BeaconContentFactory {

    void getContent(BeaconID beaconID, Callback callback);

    interface Callback {
        void onContentReady(Object content);
    }
}
