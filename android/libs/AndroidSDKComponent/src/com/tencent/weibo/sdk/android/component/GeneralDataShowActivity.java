package com.tencent.weibo.sdk.android.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
/**
 * 展示获取访问常用接口返回json数据
 * 
 * */
public class GeneralDataShowActivity extends Activity{
	private TextView tv ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		String data  = bundle.getString("data");
		tv.setText(data);
		//Log.i("data",data);
		//tv.setScrollbarFadingEnabled(true);
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());
		setContentView(tv);
	} 

}
