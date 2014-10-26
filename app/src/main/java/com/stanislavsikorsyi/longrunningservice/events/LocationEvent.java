package com.stanislavsikorsyi.longrunningservice.events;

import android.location.Location;

/**
 * Created by stanislavsikorsyi on 13.07.14.
 */
public class LocationEvent {

    private Location location;

    public LocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
