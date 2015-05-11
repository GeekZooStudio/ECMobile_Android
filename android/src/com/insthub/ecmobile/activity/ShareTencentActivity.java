package com.insthub.ecmobile.activity;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.R;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.adapter.GalleryAdapter;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ImageInfo;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

public class ShareTencentActivity extends Activity {
	
	private TextView textView_num;//显示文本剩余字数
	private String contentStr = "";//转播内容
	private String goods_url = "";//商品详情地址
	private String videoPath = "";//视频地址
	private String picPath = "";//图片地址
	private String musicPath = "";//音乐地址 
	private String musicTitle = "";//音乐标题
	private String musicAuthor = "";//音乐演唱者
	
	private Handler mHandler = null;//处理消息
	private Gallery gallery;//显示图片或者
	private WeiboAPI api;//添加weiboAPI
	private String accessToken;//用户访问令牌
	private ArrayList<ImageInfo> imageList = new ArrayList<ImageInfo>();
	private PopupWindow loadingWindow =  null;
	private ProgressBar progressBar = null;
	private RelativeLayout galleryLayout = null;//添加
	
	private ImageView back;
	private EditText cont;
	private TextView num;
	private Button changeUser,share;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_tencent);
		
		Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            contentStr = bundle.getString("content");
            goods_url = bundle.getString("goods_url");
            videoPath = bundle.getString("video_url");
            picPath = bundle.getString("pic_url");
            musicPath = bundle.getString("music_url");
            musicTitle = bundle.getString("music_title");
            musicAuthor = bundle.getString("music_author");
        }
        
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		
		cont = (EditText) findViewById(R.id.share_cont);
		num = (TextView) findViewById(R.id.share_cont_num);
		changeUser = (Button) findViewById(R.id.share_changeUser);
		share = (Button) findViewById(R.id.share_share);
		
		cont.setText(contentStr);
		
		accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");
		
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				if(accessToken==null || "".equals(accessToken))
		        {
		            if(EcmobileManager.getTencentKey(ShareTencentActivity.this) != null && EcmobileManager.getTencentSecret(ShareTencentActivity.this) != null) {
						long appid = Long.valueOf(EcmobileManager.getTencentKey(ShareTencentActivity.this));
			            String app_secket = EcmobileManager.getTencentSecret(ShareTencentActivity.this);
			            tencentAuth(appid, app_secket);
					}
		            
					return ;
				}
		        else
		        {
		            AccountModel account = new AccountModel(accessToken);
		            api = new WeiboAPI(account);
		            contentStr = cont.getText().toString()+goods_url;
		            api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);
		        }
			}
		});
		
		changeUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				Util.clearSharePersistent(getApplicationContext());
				accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");
				if(accessToken==null || "".equals(accessToken))
		        {
		            long appid = Long.valueOf(EcmobileManager.getTencentKey(ShareTencentActivity.this));
		            String app_secket = EcmobileManager.getTencentSecret(ShareTencentActivity.this);;
		            tencentAuth(appid, app_secket);
					return ;
				}
		        else
		        {
		            AccountModel account = new AccountModel(accessToken);
		            api = new WeiboAPI(account);
		            contentStr = cont.getText().toString()+goods_url;
		            api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);
		        }
			}
		});
		
	}
	
	/*
	 * 发送多类型微博
	 * */
	protected void reAddWeibo(){
		contentStr = cont.getText().toString()+goods_url;
		api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);
	}
	private HttpCallback mCallBack = new HttpCallback() {
   		@Override
   		public void onResult(Object object) {
   			ModelResult result = (ModelResult) object;
   			if(result.isExpires()){
   				Toast.makeText(ShareTencentActivity.this, result.getError_message(), Toast.LENGTH_SHORT).show();
   			}else{
   				if(result.isSuccess()){
   	   				Toast.makeText(ShareTencentActivity.this, "转播成功", Toast.LENGTH_SHORT).show();
   	   			ShareTencentActivity.this.finish();
   	   			}else{
   	   				Toast.makeText(ShareTencentActivity.this, result.getError_message(), Toast.LENGTH_SHORT).show();
   	   			ShareTencentActivity.this.finish();
   	   			}
   			}
   		}
	};
	/**
	 * 获取一键转播组件中用于显示的图片信息
	 * */
	public ArrayList<ImageInfo> requestForGallery(){
		
		if(picPath!=null){
			ImageInfo info2 = new ImageInfo();
			info2.setImagePath(picPath);
			imageList.add(info2);
		}
		if(videoPath!=null){
			ImageInfo info1 = new ImageInfo();
            if (null != api)
            {
                api.getVideoInfo(getApplicationContext(), videoPath, videoCallBack,null, BaseVO.TYPE_JSON);
            }

		}
		return imageList ;
	}
	private HttpCallback videoCallBack = new HttpCallback() {
   		@Override
   		public void onResult(Object object) {
   			ModelResult result = (ModelResult) object;
   			if(result!=null){
   				if(!result.isExpires()){
		   			if(result.isSuccess()){
		   				try{
			   				JSONObject json = (JSONObject)result.getObj();
			   				JSONObject data = json.getJSONObject("data");
			   				ImageInfo info1 = new ImageInfo();
			   				info1.setImagePath(data.getString("minipic"));
			   				info1.setImageName(data.getString("title"));
			   				info1.setPlayPath(data.getString("real"));
			   				imageList.add(info1);
			   				GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(),loadingWindow,imageList);
			   				gallery.setAdapter(adapter);
		   				}catch(Exception e){
		   					e.printStackTrace();
		   				}
		   			}
   				}
   			}else{
   				if(loadingWindow!=null && loadingWindow.isShowing()){
   					loadingWindow.dismiss();
   				}
   			}
   		}
	};

	
    private void tencentAuth(long appid, String app_secket)
    {
        final Context context = this.getApplicationContext();

        AuthHelper.register(this, appid, app_secket, new OnAuthListener() {

            @Override
            public void onWeiBoNotInstalled() {
                Intent i = new Intent(ShareTencentActivity.this, Authorize.class);
                i.putExtra("APP_KEY", EcmobileManager.getTencentKey(ShareTencentActivity.this));
                i.putExtra("REDIRECT_URI",EcmobileManager.getTencentCallback(ShareTencentActivity.this));
                ShareTencentActivity.this.startActivity(i);
            }

            @Override
            public void onWeiboVersionMisMatch() {
                Intent i = new Intent(ShareTencentActivity.this, Authorize.class);
                startActivity(i);
            }

            @Override
            public void onAuthFail(int result, String err) {

            }

            @Override
            public void onAuthPassed(String name, WeiboToken token) {                
                Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
                Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
                Util.saveSharePersistent(context, "OPEN_ID", token.openID);
                Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
                Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
                Util.saveSharePersistent(context, "AUTHORIZETIME",
                        String.valueOf(System.currentTimeMillis() / 1000l));
                accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");
                AccountModel account = new AccountModel(accessToken);
                api = new WeiboAPI(account);
                contentStr = cont.getText().toString()+goods_url;
                api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);

            }
        });

        AuthHelper.auth(ShareTencentActivity.this, "");
    }
}
