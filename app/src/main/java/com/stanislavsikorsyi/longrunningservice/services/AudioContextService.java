package com.stanislavsikorsyi.longrunningservice.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.stanislavsikorsyi.longrunningservice.ContextAwareApplication;
import com.stanislavsikorsyi.longrunningservice.events.StopApplicationUsageService;
import com.stanislavsikorsyi.longrunningservice.util.CurrentApplicationManager;

import java.util.Date;
import java.util.List;

/**
 * Created by stanislavsikorsyi on 04.08.14.
 */
public class AudioContextService extends Service {

    private static final String TAG = "ApplicationsUsageService";
    private Handler handler = new Handler();
    private ActivityManager mActivityManager;

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            getForegroundApp();
            handler.postDelayed(this, 2000);
        }
    };

    private void checkForegroundApp() {
        Log.d(TAG, "Check foreground app");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        handler.postDelayed(runnable, 2000);
        return Service.START_STICKY;
    }


    @Override
    public void onCreate() {

        ContextAwareApplication.getEventBus().register(this);
    }

    @Subscribe
    public void stopUpdating(StopApplicationUsageService event) {
        Log.d(TAG, "stopUpdating");
        handler.removeCallbacks(runnable);
    }

    private void getForegroundApp() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                Log.d(TAG, appProcess.processName);
                CurrentApplicationManager.currentActivitiesMap.put(new Date(), appProcess.processName);
            }
        }
    }


}
