package com.stanislavsikorsyi.longrunningservice;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.stanislavsikorsyi.longrunningservice.util.LocationManager;

/**
 * Created by stanislavsikorsyi on 13.07.14.
 */
public class ContextAwareApplication extends Application {


    private static final String TAG = "OAuthApplication";

    private static Bus eventBus;

    public static Bus getEventBus() {
        return eventBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Application");
        eventBus = new Bus(ThreadEnforcer.ANY);
        LocationManager locationManager = new LocationManager();
    }


}
