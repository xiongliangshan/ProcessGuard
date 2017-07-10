package com.xls.processguard;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class LocalService extends Service {

    private Timer timer;
    private static final String TAG = "process";
    private long count = 0;
    public LocalService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(timer==null){
            timer = new Timer(true);
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG,"LocalService is running "+count++);
            }
        },0,1000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("LocalService xiong");
        builder.setContentTitle("hhaa");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(PendingIntent.getActivity(this,0,intent,0));
        startForeground(startId,builder.build());
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction("xiong.liang.shan");
        sendBroadcast(intent);
        if(timer!=null){
            timer.cancel();
        }

    }
}
