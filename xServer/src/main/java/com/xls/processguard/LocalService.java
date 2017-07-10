package com.xls.processguard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
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
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        Intent intent = new Intent();
        intent.setAction("shan.liang.xiong");
        sendBroadcast(intent);
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }

    }
}
