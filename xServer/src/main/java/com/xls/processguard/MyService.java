package com.xls.processguard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private Timer timer;
    private static final String TAG = "process";
    private long count = 0;
    public MyService() {
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
                Log.i(TAG,"MyService is running "+count++);
            }
        },0,1000);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    IMyService.Stub mBinder = new IMyService.Stub() {
        @Override
        public int add(int x, int y) throws RemoteException {
            return x+y;
        }

        @Override
        public String getInfo() throws RemoteException {
            return "这是远程服务的方法";
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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
