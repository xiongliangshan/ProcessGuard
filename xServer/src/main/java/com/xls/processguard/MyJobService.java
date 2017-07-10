package com.xls.processguard;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    private static final String TAG = "process";
    private int kJobId = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleJob(getJobInfo());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG,"onStartJob ---------");
        boolean isLocalServiceWork = isServiceWork(this,LocalService.class.getName());
        boolean isRemoteServiceWork = isServiceWork(this,MyService.class.getName());
        if(!isLocalServiceWork || !isRemoteServiceWork){
            this.startService(new Intent(this,LocalService.class));
            this.startService(new Intent(this,MyService.class));
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG,"onStopJob ---------");
        scheduleJob(getJobInfo());
        return true;
    }


    //将任务作业发送到作业调度中去
    public void scheduleJob(JobInfo t) {
        Log.i(TAG, "调度job");
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo(){
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, MyJobService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        //间隔100毫秒
        builder.setPeriodic(100);
        return builder.build();
    }



    public boolean isServiceWork(Context context, String serviceName){
        boolean isWork = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(20);
        if(runningServiceInfos.size()<=0){
            return false;
        }
        for (int i=0;i<runningServiceInfos.size();i++){
            String name = runningServiceInfos.get(i).service.getClassName();
            Log.i(TAG,"name = "+name);
            if(name.equals(serviceName)){
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
