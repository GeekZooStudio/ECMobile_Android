package com.tencent.weibo.sdk.android.component;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import org.json.JSONObject;

import com.tencent.weibo.sdk.android.api.WeiboAPI;

import com.tencent.weibo.sdk.android.api.adapter.GalleryAdapter;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ImageInfo;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 一键转播组件
 * 
 * 
 * 
 * */
public class ReAddActivity extends Activity {
	private LinearLayout layout = null;//整体布局
	private EditText content = null;//转播内容输入框
	private TextView textView_num;//显示文本剩余字数
	private String contentStr = "";//转播内容
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
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            contentStr = bundle.getString("content");
            videoPath = bundle.getString("video_url");
            picPath = bundle.getString("pic_url");
            musicPath = bundle.getString("music_url");
            musicTitle = bundle.getString("music_title");
            musicAuthor = bundle.getString("music_author");
        }

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics displaysMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics( displaysMetrics );
		String pix=displaysMetrics.widthPixels+"x"+displaysMetrics.heightPixels;
		BackGroudSeletor.setPix(pix);
        this.setContentView(initLayout());

		accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");

		if(accessToken==null || "".equals(accessToken))
        {
            long appid = Long.valueOf(Util.getConfig().getProperty("APP_KEY"));
            String app_secket = Util.getConfig().getProperty("APP_KEY_SEC");
            tencentAuth(appid, app_secket);
			return ;
		}
        else
        {
            AccountModel account = new AccountModel(accessToken);
            api = new WeiboAPI(account);
            contentStr = content.getText().toString();
            api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);
        }


	}
	
	/**
	 * 初始化界面并设置监听
	 */
	public View initLayout(){
		RelativeLayout.LayoutParams fillParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		RelativeLayout.LayoutParams fillWrapParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams wrapParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		layout = new LinearLayout(this);
		layout.setLayoutParams(fillParams);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundDrawable(BackGroudSeletor.getdrawble("readd_bg", getApplication()));
		RelativeLayout cannelLayout = new RelativeLayout(this);
		cannelLayout.setLayoutParams(fillWrapParams);
		cannelLayout.setBackgroundDrawable(BackGroudSeletor.getdrawble("up_bg2x", getApplication()));
		cannelLayout.setGravity(LinearLayout.HORIZONTAL);
		
		Button returnBtn = new Button(this);
		String[] pngArray = new String[]{"quxiao_btn2x","quxiao_btn_hover"};
		returnBtn.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(pngArray, getApplication()));
		returnBtn.setText("取消");
		wrapParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		wrapParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		wrapParams.topMargin=10;
		wrapParams.leftMargin=10;
		wrapParams.bottomMargin=10;
		returnBtn.setLayoutParams(wrapParams);
		returnBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				ReAddActivity.this.finish();
			}
		});
		cannelLayout.addView(returnBtn);
		TextView title = new TextView(this);
		title.setText("转播");
		title.setTextColor(Color.WHITE);
		title.setTextSize(24f);
		RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		title.setLayoutParams(titleParams);
		cannelLayout.addView(title);
		
		Button reAddBtn = new Button(this);
		pngArray = new String[]{"sent_btn2x","sent_btn_hover"};
		reAddBtn.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(pngArray, getApplication()));
		reAddBtn.setText("转播");
		RelativeLayout.LayoutParams wrapParamsRight = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		wrapParamsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		wrapParamsRight.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		wrapParamsRight.topMargin=10;
		wrapParamsRight.rightMargin=10;
		wrapParamsRight.bottomMargin=10;
		reAddBtn.setLayoutParams(wrapParamsRight);
		reAddBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				reAddWeibo();
			}
		});
		cannelLayout.addView(reAddBtn);
		
		RelativeLayout reAddLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams readdParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 240);
		reAddLayout.setLayoutParams(readdParams);
		
		RelativeLayout contentLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams contentParams = new RelativeLayout.LayoutParams(440, RelativeLayout.LayoutParams.FILL_PARENT);
		contentParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		contentParams.topMargin=50;
	//	contentLayout.setBackgroundColor(Color.GRAY);//修改此处 设置背景
		contentLayout.setLayoutParams(contentParams);
		contentLayout.setBackgroundDrawable(BackGroudSeletor.getdrawble("input_bg", getApplication()));
		textView_num = new TextView(this);
		textView_num.setText(contentStr==null?"140":String.valueOf(140-contentStr.length()));
		textView_num.setTextColor(Color.parseColor("#999999"));
		textView_num.setGravity(Gravity.RIGHT);
		textView_num.setTextSize(18f);
		RelativeLayout.LayoutParams params_space = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params_space.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params_space.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params_space.rightMargin=10;
		textView_num.setLayoutParams(params_space);
		contentLayout.addView(textView_num);
		//设置输入框
		content = new EditText(this);
		contentParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		contentParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		contentParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		content.setLayoutParams(contentParams);
		//content.setBackgroundColor(Color.WHITE);
		content.setMaxLines(4);
		content.setMinLines(4);
		//content.setMinEms(4);
		//content.setMaxEms(4);
		content.setScrollbarFadingEnabled(true);
		content.setGravity(Gravity.TOP);
		content.setMovementMethod(ScrollingMovementMethod.getInstance());
		content.setText(contentStr);
		content.setSelection(contentStr.length());
		content.setBackgroundDrawable(null);
		//content.setBackgroundColor(Color.CYAN);
		
		content.addTextChangedListener(new TextWatcher(){
			private CharSequence temp ;
			private int selectionStart;
			private int selectionEnd;
			@Override
			public void afterTextChanged(Editable arg0) {
				 
				selectionStart = content.getSelectionStart();
				selectionEnd = content.getSelectionEnd();
				if(temp.length()>140){
					Toast.makeText(ReAddActivity.this,"最多可输入140字符", Toast.LENGTH_SHORT)
                            .show();
					arg0.delete(selectionStart-1, selectionEnd);
					int tempSelection = selectionStart;
					content.setText(arg0);
					content.setSelection(tempSelection);
				}else{
					//temp = arg0.toString();
					//int textLen = arg0.length();
					textView_num.setText(String.valueOf(140-arg0.length()));
					
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				 
				temp = s;
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				 
				
			}
			
		});
		contentLayout.addView(content);
		reAddLayout.addView(contentLayout);
		
		galleryLayout = new RelativeLayout(this);
		galleryLayout.setLayoutParams(fillParams);
		gallery = new Gallery(this);
		RelativeLayout.LayoutParams galleryParams = new RelativeLayout.LayoutParams(303,203);
		galleryParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE); 
		galleryParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE); 
		galleryParams.topMargin=50;
		gallery.setLayoutParams(galleryParams);
		gallery.setBackgroundDrawable(BackGroudSeletor.getdrawble("pic_biankuang2x", getApplication()));
		requestForGallery();
		galleryLayout.addView(gallery);
		layout.addView(cannelLayout);
		layout.addView(reAddLayout);
		if(picPath!=null && !"".equals(picPath) && videoPath!=null && !"".equals(videoPath))
        {
			layout.addView(galleryLayout);
		}
		return layout;
	}
	/*
	 * 发送多类型微博
	 * */
	protected void reAddWeibo(){
		contentStr = content.getText().toString();
		api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);
	}
	private HttpCallback mCallBack = new HttpCallback() {
   		@Override
   		public void onResult(Object object) {
   			ModelResult result = (ModelResult) object;
   			if(result.isExpires()){
   				Toast.makeText(ReAddActivity.this, result.getError_message(), Toast.LENGTH_SHORT).show();
   			}else{
   				if(result.isSuccess()){
   	   				Toast.makeText(ReAddActivity.this, "转播成功", Toast.LENGTH_SHORT).show();
   	   				ReAddActivity.this.finish();
   	   			}else{
   	   				Toast.makeText(ReAddActivity.this, result.getError_message(), Toast.LENGTH_SHORT).show();
	   				ReAddActivity.this.finish();
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
                Toast.makeText(ReAddActivity.this, "onWeiBoNotInstalled", 1000)
                        .show();
                Intent i = new Intent(ReAddActivity.this, Authorize.class);
                ReAddActivity.this.startActivity(i);
            }

            @Override
            public void onWeiboVersionMisMatch() {
                Toast.makeText(ReAddActivity.this, "onWeiboVersionMisMatch",
                        1000).show();
                Intent i = new Intent(ReAddActivity.this, Authorize.class);
                startActivity(i);
            }

            @Override
            public void onAuthFail(int result, String err) {
                Toast.makeText(ReAddActivity.this, "result : " + result, 1000)
                        .show();
            }

            @Override
            public void onAuthPassed(String name, WeiboToken token) {
                Toast.makeText(ReAddActivity.this, "passed", 1000).show();
                //
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
                contentStr = content.getText().toString();
                api.reAddWeibo(getApplicationContext(),contentStr,picPath,videoPath,musicPath,musicTitle,musicAuthor, mCallBack, null, BaseVO.TYPE_JSON);

            }
        });

        AuthHelper.auth(ReAddActivity.this, "");
    }


}
