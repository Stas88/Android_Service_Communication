package com.stanislavsikorsyi.longrunningservice.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.stanislavsikorsyi.longrunningservice.ContextAwareApplication;
import com.stanislavsikorsyi.longrunningservice.events.LocationEvent;
import com.stanislavsikorsyi.longrunningservice.events.StopLocationUdates;

/**
 * Created by stanislavsikorsyi on 03.07.14.
 */
public class LongRunningService extends Service  implements LocationListener {

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    private final static String TAG = "LongRunningService";

    private LocationManager locationManager;

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,"onLocationChanged: " + location);
        ContextAwareApplication.getEventBus().post(new LocationEvent(location));
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        ContextAwareApplication.getEventBus().register(this);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000 , 0,  this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000 , 0,  this);
        return Service.START_STICKY;
    }

    @Subscribe
    public void stopUpdating(StopLocationUdates event) {
        Log.d(TAG, "stopUpdating");
        locationManager.removeUpdates(this);
    }
}
