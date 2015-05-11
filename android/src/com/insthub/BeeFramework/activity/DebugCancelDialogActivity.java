package com.insthub.BeeFramework.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.insthub.ecmobile.R;

public class DebugCancelDialogActivity extends Activity {

	
	private TextView title;
	private TextView message;
	private TextView positive;
	private TextView negative;
	
	public static Handler parentHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_layout);
		
		title = (TextView) findViewById(R.id.dialog_title);
		message = (TextView) findViewById(R.id.dialog_message);
		title.setVisibility(View.GONE);
        Resources resource = (Resources) getBaseContext().getResources();
        String mes=resource.getString(R.string.exit_debug_mode);
        message.setText(mes);

		positive = (TextView) findViewById(R.id.yes);
		negative = (TextView) findViewById(R.id.no);
		
		positive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Message msg = new Message();
                msg.what = 1;
                parentHandler.handleMessage(msg);
                finish();
			}
		});
		
		negative.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				finish();
			}
		});
	}
	
}
