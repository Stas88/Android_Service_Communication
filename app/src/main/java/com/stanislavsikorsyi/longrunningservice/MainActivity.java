package com.stanislavsikorsyi.longrunningservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.stanislavsikorsyi.longrunningservice.services.LongRunningService;


public class MainActivity extends Activity {

    private final String TAG  = "MainActivity";
    Messenger messenger = null;

    LongRunningService mService;
    boolean mBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register BroadcastReceiver as Local
        LocalBroadcastManager.getInstance(this).registerReceiver(longRunningServiceReceiver,
                new IntentFilter(Constants.longRunningServiceIntent));

        //Register BroadcastReceiver as Global
        this.registerReceiver(longRunningServiceReceiver,
                new IntentFilter(Constants.longRunningServiceIntent));

        //Bind to service
        Intent intent = new Intent(this, LongRunningService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);




    }

    @Override
    protected void onStop() {
        super.onStop();

        //Unregister Local BroadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(longRunningServiceReceiver);

        //Unregister Global BroadcastReceiver
        unregisterReceiver(longRunningServiceReceiver);

        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }

    }

    public void stopService(View view) {
        stopService(new Intent(this, LongRunningService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver longRunningServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra(Constants.MESSAGE);
            Log.d(TAG, "received message: " + message);
        }
    };

    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute) */
    public void getNumberFromService(View v) {
        if (!mBound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, LongRunningService.MSG_SAY_HELLO, 0, 0);
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            messenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            messenger = null;
            mBound = false;
        }
    };



}
