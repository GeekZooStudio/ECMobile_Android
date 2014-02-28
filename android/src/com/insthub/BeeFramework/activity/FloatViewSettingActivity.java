package com.insthub.BeeFramework.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.insthub.BeeFramework.service.MemoryService;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BeeCallback;

public class FloatViewSettingActivity extends BaseActivity {

	private Button on;
	private Button off;
	private TextView title;
    private Button third_genaration;
    private Button second_genaration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floatview_setting);
		
		title = (TextView) findViewById(R.id.navigationbar_title);
        Resources resource = (Resources) getBaseContext().getResources();
        resource.getString(R.string.debughome_floatingwindow);


		
		on = (Button) findViewById(R.id.float_view_setting_on);
		off = (Button) findViewById(R.id.float_view_setting_off);
        third_genaration = (Button)findViewById(R.id.third_genaration_network);
        second_genaration = (Button)findViewById(R.id.second_genaration_network);
        third_genaration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (third_genaration.isSelected())
                {
                    third_genaration.setSelected(false);
                    Resources resource = (Resources) getBaseContext().getResources();
                    resource.getString(R.string.floatView_3g);
                    BeeCallback.setForceThrottleBandwidth(false);
                }
                else
                {
                    third_genaration.setSelected(true);
                    BeeCallback.setForceThrottleBandwidth(true);
                    BeeCallback.setMaxBandwidthPerSecond(14800);
                    Resources resource = (Resources) getBaseContext().getResources();
                    resource.getString(R.string.floatView_cancel3g );

                    if(!isServiceRunning()) {
                        Intent intent = new Intent();
                        intent.setAction(MemoryService.MEMORYSERVICENAME);
                        startService(intent);
                    }
                }
            }
        });

        second_genaration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (second_genaration.isSelected())
                {
                    second_genaration.setSelected(false);
                    Resources resource = (Resources) getBaseContext().getResources();
                    resource.getString(R.string.floatView_2g );

                    BeeCallback.setForceThrottleBandwidth(false);
                }
                else
                {
                    second_genaration.setSelected(true);
                    BeeCallback.setForceThrottleBandwidth(true);
                    BeeCallback.setMaxBandwidthPerSecond(5000);
                    Resources resource = (Resources) getBaseContext().getResources();
                    String flo=resource.getString(R.string.floatView_cancel2g );
                    second_genaration.setText(flo);
                    if(!isServiceRunning()) {
                        Intent intent = new Intent();
                        intent.setAction(MemoryService.MEMORYSERVICENAME);
                        startService(intent);
                    }
                }
            }
        });
		
		on.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				if(!isServiceRunning()) {
					Intent intent = new Intent();
					intent.setAction(MemoryService.MEMORYSERVICENAME);
					startService(intent);
				}
				
			}
		});
		
		off.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				if(isServiceRunning()) {
					Intent intent = new Intent();
					intent.setAction(MemoryService.MEMORYSERVICENAME);
					stopService(intent);
				}
			}
		});
	}
	
	private boolean isServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (MemoryService.MEMORYSERVICENAME.equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
}
