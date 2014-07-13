package com.stanislavsikorsyi.longrunningservice.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.stanislavsikorsyi.longrunningservice.Constants;

import java.util.Random;

/**
 * Created by stanislavsikorsyi on 03.07.14.
 */
public class LongRunningService extends Service {

    private final static String TAG = "LongRunningService";

    //For Messanger communication
    public static final int MSG_SAY_HELLO = 1;
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LongRunningService getService() {

            //Return this instance of LocalService so clients can call public methods
            return LongRunningService.this;
        }
    }

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());





    //Worker thread for service
    private Runnable refresh = new Runnable() {
        public void run() {
           for(int i = 0; i < Integer.MAX_VALUE; i++ ) {
            Log.d(TAG, "LongRunningService service works");
                   try {
                       Thread.sleep(3000);
                       Intent intent = new Intent(Constants.longRunningServiceIntent);

                       //Send local broadcast
                       intent.putExtra(Constants.MESSAGE, "LongRunningService LocalBroadcast = " + i);
                       LocalBroadcastManager.getInstance(LongRunningService.this).sendBroadcast(intent);

                       //Send Global broadcast
                       intent.putExtra(Constants.MESSAGE, "LongRunningService GlobalBroacast = " + i);
                       sendBroadcast(intent);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        new Thread(refresh).start();
        return START_STICKY;
    }




    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    /** Mwthod for synchroniouse asking service */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

}
