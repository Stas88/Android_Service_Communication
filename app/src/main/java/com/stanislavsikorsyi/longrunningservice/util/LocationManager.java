package com.stanislavsikorsyi.longrunningservice.util;

import android.location.Location;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.stanislavsikorsyi.longrunningservice.ContextAwareApplication;
import com.stanislavsikorsyi.longrunningservice.events.LocationEvent;
import com.stanislavsikorsyi.longrunningservice.model.LocationForSet;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by stanislavsikorsyi on 13.07.14.
 */
public class LocationManager {


    private static final String TAG = "LocationManager";

    public static Set<LocationForSet> locationsSet = new LinkedHashSet<LocationForSet>();

    public LocationManager()
    {
        ContextAwareApplication.getEventBus().register(this);
    }

    @Subscribe
    public void contactAvailable(LocationEvent event) {
        Location location = event.getLocation();
        LocationForSet locationForSet = new LocationForSet(location);
        locationsSet.add(locationForSet);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d(TAG, "latitude: " + latitude +  " longitude: " + longitude);
    }

    public static Set<LocationForSet> getLocationSet() {
        return locationsSet;
    }


}
