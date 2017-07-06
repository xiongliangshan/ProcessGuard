package com.xls.processguard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {

    public MyService() {
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

}
