package com.stanislavsikorsyi.longrunningservice.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.enrique.stackblur.StackBlurManager;
import com.stanislavsikorsyi.longrunningservice.ContextAwareApplication;
import com.stanislavsikorsyi.longrunningservice.R;
import com.stanislavsikorsyi.longrunningservice.events.StopApplicationUsageService;
import com.stanislavsikorsyi.longrunningservice.events.StopLocationUdates;
import com.stanislavsikorsyi.longrunningservice.services.ApplicationsUsageService;
import com.stanislavsikorsyi.longrunningservice.services.LongRunningService;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {

    private final String TAG  = "MainActivity";

    @InjectView(R.id.imageBlured)
    ImageView bluredImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.road_background);
        StackBlurManager _stackBlurManager = new StackBlurManager(bMap);
        _stackBlurManager.process(10*5);
        bluredImage.setImageBitmap(_stackBlurManager.returnBlurredImage());


    }

    public void startService(View view) {
        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void stopService(View view) {
        ContextAwareApplication.getEventBus().post(new StopLocationUdates());
        stopService(new Intent(this, LongRunningService.class));
    }

    public void seeHistory(View view) {
        Intent intent = new Intent(this, LocationHistoryActivity.class);
        startActivity(intent);
    }


    public void startAppUsageService(View view) {
        Log.d(TAG, "startAppUsageService");
        Intent intent = new Intent(this, ApplicationsUsageService.class);
        startService(intent);
    }

    public void stopAppUsageService(View view) {
        Log.d(TAG, "stopAppUsageService");
        ContextAwareApplication.getEventBus().post(new StopApplicationUsageService());
        stopService(new Intent(this, ApplicationsUsageService.class));
    }


    public void startTabbedActivity(View view) {
        Log.d(TAG, "startTabbedActivity");
        Intent intent = new Intent(this, TabbedActivity.class);
        startActivity(intent);
    }

    public void startNavigationActivity(View view) {
        Log.d(TAG, "startNavigationActivity");
        Intent intent = new Intent(this, NavigtionDrawerActivity.class);
        startActivity(intent);
    }
}
