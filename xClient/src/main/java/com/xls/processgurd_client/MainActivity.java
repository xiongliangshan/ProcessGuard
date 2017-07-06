package com.xls.processgurd_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xls.processguard.IMyService;

public class MainActivity extends AppCompatActivity {

    private IMyService mService;

    private TextInputEditText etOne;
    private TextInputEditText etTwo;
    private Button btn;
    private TextView resultText;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();

        Intent intent = new Intent();
        intent.setAction("com.xls.test.aidl.xiong");
        intent.setPackage("com.xls.processguard");
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);


    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService=null;
        }
    };

    private void initView(){
        etOne = (TextInputEditText) findViewById(R.id.et_one);
        etTwo = (TextInputEditText) findViewById(R.id.et_two);
        btn = (Button) findViewById(R.id.btn_plus);
        resultText = (TextView) findViewById(R.id.tv_result);
        infoText = (TextView) findViewById(R.id.tv_info);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int one = Integer.parseInt(etOne.getText().toString().trim());
                int two = Integer.parseInt(etTwo.getText().toString().trim());
                if(mService==null){
                    Toast.makeText(getApplicationContext(),"远程service不可用",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int result = mService.add(one,two);
                    resultText.setText("结果为:"+result);
                    infoText.setText(mService.getInfo());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
