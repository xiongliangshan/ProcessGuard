package com.xls.processguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "process";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.w(TAG,"收到广播:"+action);
        Intent intentR = new Intent(context,MyService.class);
        context.startService(intentR);
        Intent intentL = new Intent(context,LocalService.class);
        context.startService(intentL);

    }
}
