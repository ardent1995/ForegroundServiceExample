package com.example.foregroundserviceexample;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ExampleIntentService extends IntentService {

    private static final String TAG = "ExampleIntentService";
    private PowerManager.WakeLock wakeLock;

    public ExampleIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"ForegroundServiceExample:ExampleIntentServiceWakeLock");
        wakeLock.acquire();
        Log.d(TAG, "onCreate: WakeLock Acquired");

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this,App.CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running....")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_android_24dp)
                    .build();
            startForeground(2,notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        String input = intent.getStringExtra(MainActivity.KEY_INPUT_EXTRA);
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "onHandleIntent: "+input+" - "+i);
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        wakeLock.release();
        Log.d(TAG, "onDestroy: WakeLock Released");
    }
}
