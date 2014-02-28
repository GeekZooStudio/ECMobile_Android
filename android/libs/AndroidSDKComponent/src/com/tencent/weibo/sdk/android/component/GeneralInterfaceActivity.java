package com.tencent.weibo.sdk.android.component;

import java.io.File;

import com.tencent.weibo.sdk.android.api.FriendAPI;
import com.tencent.weibo.sdk.android.api.LbsAPI;
import com.tencent.weibo.sdk.android.api.TimeLineAPI;
import com.tencent.weibo.sdk.android.api.UserAPI;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

/**
 *  常用数据接口访问实例组件
 * 
 * 
 */
public class GeneralInterfaceActivity extends Activity implements
		View.OnClickListener {
	private Button homeTimeLine;// 主人页时间线
	private Button userTimeLine;// 客人页时间线
	private Button addWeibo;// 普通发表接口
	private Button addPic;// 发表带图微博
	private Button addPicUrl;// 发表带网络图片微博
	private Button htTimeLine;// 话题时间线
	private Button userInfo;// 获取用户信息
	private Button userOtherInfo;// 获取他人信息
	private Button userInfos;// 获取一批人信息
	private Button friendAdd;// 收听某个用户
	private Button friendIdolList;// 获取偶像列表
	private Button friendFunsList;// 获取粉丝列表
	private Button friendMutualList;// 获取互听列表
	private Button friendCheck;// 验证好友关系
	private Button tReList;// 转播获取转播列表
	private Button friendGetIntimateFriend;// 获取最近联系人
	private Button lbsGetAroundPeople;// 获取附近的人
	private Button lbsGetAroundNew;// 获取身边最新的微博
	private Button deviceStatus;// 终端状况
	private Button errorReport;// 错误反馈
	private String accessToken;// 用户访问令牌
	private FriendAPI friendAPI;//好友相关API
	private TimeLineAPI timeLineAPI;//时间线API
	private WeiboAPI weiboAPI;//微博相关API
	private UserAPI userAPI;//帐户相关API
	private LbsAPI lbsAPI;//LBS相关API
	private HttpCallback mCallBack;//回调函数
	private PopupWindow loadingWindow =  null;
	private ProgressBar progressBar = null;
	private ScrollView scrollView = null;
	private Context context=null;
	private String requestFormat = "json";
	private double longitude = 0d;
	private double latitude = 0d;
	private Location mLocation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		accessToken = Util.getSharePersistent(getApplicationContext(),
				"ACCESS_TOKEN");
		if (accessToken == null || "".equals(accessToken)) {
			Toast.makeText(GeneralInterfaceActivity.this, "请先授权",
					Toast.LENGTH_SHORT).show();
			this.finish();
			return ;
		} 
		AccountModel account = new AccountModel(accessToken);
		friendAPI = new FriendAPI(account);
		timeLineAPI = new TimeLineAPI(account);
		weiboAPI = new WeiboAPI(account);
		userAPI = new UserAPI(account);
		lbsAPI = new LbsAPI(account);
		mCallBack = new HttpCallback() {
			@Override
			public void onResult(Object object) {
				ModelResult result = (ModelResult) object;
				if(progressBar!=null && loadingWindow.isShowing()){

					loadingWindow.dismiss();
				}
				if(result!=null){
					Toast.makeText(GeneralInterfaceActivity.this,
							"成功", Toast.LENGTH_SHORT)
							.show();
					Intent i = new Intent(GeneralInterfaceActivity.this,GeneralDataShowActivity.class);
					i.putExtra("data", result.getObj().toString());					
					startActivity(i);
				}else{
					Toast.makeText(GeneralInterfaceActivity.this,
							"发生异常", Toast.LENGTH_SHORT)
							.show();
				}
				
			}
		};
		progressBar = new ProgressBar(this);
		loadingWindow = new PopupWindow(progressBar,100,100);
		context = getApplicationContext();
		mLocation = Util.getLocation(context);
		if(mLocation !=null){
			longitude = mLocation.getLongitude();
			latitude = mLocation.getLatitude();
		}
		this.initInterface();
	}
   /**
    * 初始化界面使用控件
    * 
    * */
	public void initInterface() {
		scrollView = new ScrollView(this);
		TableLayout table = new TableLayout(this);
		TableLayout.LayoutParams paramsTable = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT,
				TableLayout.LayoutParams.FILL_PARENT);
		table.setLayoutParams(paramsTable);
		TableRow row1 = new TableRow(this);
		homeTimeLine = new Button(this);
		homeTimeLine.setText("主人页时间线");
		homeTimeLine.setId(1001);
		homeTimeLine.setOnClickListener(this);
		row1.addView(homeTimeLine);
		userTimeLine = new Button(this);
		userTimeLine.setText("客人页时间线");
		userTimeLine.setId(1002);
		userTimeLine.setOnClickListener(this);
		row1.addView(userTimeLine);
		table.addView(row1);

		TableRow row2 = new TableRow(this);
		addWeibo = new Button(this);
		addWeibo.setText("普通发表接口");
		addWeibo.setId(1003);
		addWeibo.setOnClickListener(this);
		row2.addView(addWeibo);
		addPic = new Button(this);
		addPic.setText("发表带图微博");
		addPic.setId(1004);
		addPic.setOnClickListener(this);
		row2.addView(addPic);
		table.addView(row2);

		TableRow row3 = new TableRow(this);
		addPicUrl = new Button(this);
		addPicUrl.setText("发表带网络图片微博");
		addPicUrl.setId(1005);
		addPicUrl.setOnClickListener(this);
		row3.addView(addPicUrl);
		htTimeLine = new Button(this);
		htTimeLine.setText("话题时间线");
		htTimeLine.setId(1006);
		htTimeLine.setOnClickListener(this);
		row3.addView(htTimeLine);
		table.addView(row3);

		TableRow row4 = new TableRow(this);
		userInfo = new Button(this);
		userInfo.setText("获取用户信息");
		userInfo.setId(1007);
		userInfo.setOnClickListener(this);
		row4.addView(userInfo);
		userOtherInfo = new Button(this);
		userOtherInfo.setText("获取他人信息");
		userOtherInfo.setId(1008);
		userOtherInfo.setOnClickListener(this);
		row4.addView(userOtherInfo);
		table.addView(row4);

		TableRow row5 = new TableRow(this);
		userInfos = new Button(this);
		userInfos.setText("获取一批人信息");
		userInfos.setId(1009);
		userInfos.setOnClickListener(this);
		row5.addView(userInfos);
		friendAdd = new Button(this);
		friendAdd.setText("收听某个用户");
		friendAdd.setId(1010);
		friendAdd.setOnClickListener(this);
		row5.addView(friendAdd);
		table.addView(row5);

		TableRow row6 = new TableRow(this);
		friendIdolList = new Button(this);
		friendIdolList.setText("获取偶像列表");
		friendIdolList.setId(1011);
		friendIdolList.setOnClickListener(this);
		row6.addView(friendIdolList);
		friendFunsList = new Button(this);
		friendFunsList.setText("获取粉丝列表");
		friendFunsList.setId(1012);
		friendFunsList.setOnClickListener(this);
		row6.addView(friendFunsList);
		table.addView(row6);

		

		TableRow row7 = new TableRow(this);
		friendMutualList = new Button(this);
		friendMutualList.setText("获取互听列表");
		friendMutualList.setId(1013);
		friendMutualList.setOnClickListener(this);
		row7.addView(friendMutualList);
		friendCheck = new Button(this);
		friendCheck.setText("验证好友关系");
		friendCheck.setId(1014);
		friendCheck.setOnClickListener(this);
		row7.addView(friendCheck);
		table.addView(row7);

		TableRow row8 = new TableRow(this);
		tReList = new Button(this);
		tReList.setText("转播获取转播列表");
		tReList.setId(1015);
		tReList.setOnClickListener(this);
		row8.addView(tReList);
		friendGetIntimateFriend = new Button(this);
		friendGetIntimateFriend.setText("获取最近联系人");
		friendGetIntimateFriend.setId(1016); 
		friendGetIntimateFriend.setOnClickListener(this);
		row8.addView(friendGetIntimateFriend);
		table.addView(row8);

		TableRow row9 = new TableRow(this);
		lbsGetAroundPeople = new Button(this);
		lbsGetAroundPeople.setText("获取附近的人");
		lbsGetAroundPeople.setId(1017);
		lbsGetAroundPeople.setOnClickListener(this);
		row9.addView(lbsGetAroundPeople);
		lbsGetAroundNew = new Button(this);
		lbsGetAroundNew.setText("获取身边最新的微博");
		lbsGetAroundNew.setId(1018);
		lbsGetAroundNew.setOnClickListener(this);
		row9.addView(lbsGetAroundNew);
		table.addView(row9);

		TableRow row10 = new TableRow(this);
		deviceStatus = new Button(this);
		deviceStatus.setText("终端状况");
		deviceStatus.setId(1019);
		row10.addView(deviceStatus);
		errorReport = new Button(this);
		errorReport.setText("错误反馈");
		errorReport.setId(1020);
		row10.addView(errorReport);
		table.addView(row10);

		scrollView.addView(table);

		this.setContentView(scrollView);

	}

	@Override
	public void onClick(View v) {
		 
		Looper.myQueue().addIdleHandler(new IdleHandler(){
			@Override
			public boolean queueIdle() { 	
				 
					loadingWindow.showAtLocation(scrollView, Gravity.CENTER, 0, 80);		
				return false;  
			}
        });
		switch (v.getId()) {
		case 1001:
			timeLineAPI.getHomeTimeLine(context, 0, 0, 30, 0, 0, requestFormat, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1002:
			timeLineAPI.getUserTimeLine(context, 0, 0, 30, 0, "api_weibo", null, 0, 0, requestFormat, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1003:
			weiboAPI.addWeibo(context, "hello world !", requestFormat, longitude, latitude, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1004:
			try{
				Bitmap bm = BitmapFactory.decodeStream(context.getAssets().open("logo"));//BitmapFactory.decodeFile(pic);
				weiboAPI.addPic(context, "call telephone OKK", requestFormat, longitude, latitude, bm, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);	
			}catch(Exception e){
			}
			break;
		case 1005:
			String picUrl = "http://t2.qpic.cn/mblogpic/9c7e34358608bb61a696/2000";
			weiboAPI.addPicUrl(context, "y phone ", requestFormat, longitude, latitude, picUrl, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1006:
			timeLineAPI.getHTTimeLine(context, requestFormat, 30, "0", "0", 0, 0, "加油", "0", 1, 0x80, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1007://获取用户信息
			userAPI.getUserInfo(context, requestFormat, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1008://获取他人信息
			userAPI.getUserOtherInfo(context, requestFormat, "api_weibo",null, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1009://获取一批人信息
			userAPI.getUserInfos(context, requestFormat, "api_weibo", null, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1010://收听某个用户
			friendAPI.addFriend(context, requestFormat, "api_weibo", null, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1011://获取偶像列表
			friendAPI.friendIDolList(context, requestFormat, 30, 0, 1, 0, mCallBack,null, BaseVO.TYPE_JSON);
			break;
		case 1012://获取粉丝列表
			friendAPI.friendFansList(context, requestFormat, 30, 0, 1, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1013:
			friendAPI.getMutualList(context, requestFormat, "api_weibo", null, 0, 30, 0, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1014://验证好友关系
			friendAPI.friendCheck(context, requestFormat, "api_weibo", null, 2, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1015://转播获取转播列表
			weiboAPI.reList(context, requestFormat, 2, "112714089895346", 0, "0", 30, "0", mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1016://获取最近联系人
			friendAPI.getIntimateFriends(context, requestFormat, 30, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1017://获取附近的人
			lbsAPI.getAroundPeople(context, requestFormat, longitude, latitude, "", 20, 0, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1018://获取身边最新的微博
			lbsAPI.getAroundNew(context, requestFormat, longitude, latitude, "", 20, mCallBack, null, BaseVO.TYPE_JSON);
			break;
		case 1019:

			break;
		case 1020:

			break;
		default:

			break;
		}
	}

}
