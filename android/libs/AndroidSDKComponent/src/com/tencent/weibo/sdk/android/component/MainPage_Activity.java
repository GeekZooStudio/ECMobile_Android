package com.tencent.weibo.sdk.android.component;
import org.json.JSONObject;


import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
public class MainPage_Activity extends Activity {
	private Button authorize =null;
	private Button add =null;
	private Button readd =null;
	private Context context = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.main_layout);
		context = this.getApplicationContext();
		this.init();
	}
	public void init(){
		authorize = (Button)findViewById(R.id.authorize);
		authorize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				/**
				 * 跳转到授权组件
				 */
				Intent i = new Intent(MainPage_Activity.this,Authorize.class);
				startActivity(i);
			}
		});
		
		add = (Button)findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				//startReq();
				
				/**
				 * 跳转到一键转播组件
				 */
				Intent i = new Intent(MainPage_Activity.this,PublishActivity.class);
				startActivity(i);
			}
		});
		
		readd = (Button)findViewById(R.id.readd);
		readd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				//startReq();
				/**
				 * 跳转到一键转播组件
				 * 可以传递一些参数
				 * 如content为转播内容
				 * video_url为转播视频URL
				 * pic_url为转播图片URL
				 */
				Intent i = new Intent(MainPage_Activity.this,ReAddActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("content", "Make U happy");
				bundle.putString("video_url", "http://www.tudou.com/programs/view/b-4VQLxwoX4/");
				bundle.putString("pic_url", "http://t2.qpic.cn/mblogpic/9c7e34358608bb61a696/2000");
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		Button delAuthorize = (Button)findViewById(R.id.exit);
		delAuthorize.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Util.clearSharePersistent(context);
				Toast.makeText(MainPage_Activity.this,"注销成功", Toast.LENGTH_SHORT)
	            .show();
			}
		});
		
		Button comInterface = (Button)findViewById(R.id.commoninterface);
		comInterface.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Intent i = new Intent(MainPage_Activity.this,GeneralInterfaceActivity.class);
				startActivity(i);
			}
		});
		 
	}
}
