package com.insthub.BeeFramework.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

public class NetworkStateService extends Service {

	private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //System.out.println("网络状态已经改变");
                connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    if(name.equals("WIFI")) {
                    	//System.out.println("WIFI");
                    	
                    	editor.putString("netType", "wifi");
            			editor.commit();
                    	
                    } else {
                    	
                    	editor.putString("netType", "3g");
            			editor.commit();
                    	
                    	//System.out.println("2G/3G");
                    }
                    
                    //System.out.println("当前网络名称:"+name);
                    
                    //Toast.makeText(context, "当前网络名称:"+name, 0).show();
                 
                    //doSomething()
                } else {
                    //System.out.println("没有可用网络");
                    //Toast.makeText(context, "没有可用网络", 0).show();
                    //doSomething()
                }
            }
        }
    };
    
	@Override
	public IBinder onBind(Intent intent) {
		 
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
}
