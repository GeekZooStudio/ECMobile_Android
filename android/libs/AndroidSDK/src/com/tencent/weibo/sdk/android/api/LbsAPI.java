package com.tencent.weibo.sdk.android.api;

import android.content.Context;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;


public class LbsAPI  extends BaseAPI {
	/**
	 * 获取身边的人请求url
	 * */
	private static final String SERVER_URL_GetAROUNDPEOPLE = API_SERVER + "/lbs/get_around_people";
	/**
	 * 获取身边最新的微博请求url
	 * */
	private static final String SERVER_URL_GetAROUNDNEW = API_SERVER + "/lbs/get_around_new";
	
	public LbsAPI(AccountModel account) {
		super(account);
		 
	}
	 
	/**
	 * 获取身边的人	 
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param longitude  经度，例如：22.541321
	 * @param latitude 纬度，例如：13.935558
	 * @param pageinfo 翻页参数，由上次请求返回（第一次请求时请填空）
	 * @param pagesize  请求的每页个数（1-25个）
	 * @param gender 性别，0-全部，1-男，2-女
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getAroundPeople(Context context,String format,double longitude,double latitude,String pageinfo,int pagesize,int gender, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("longitude", longitude);
		mParam.addParam("latitude", latitude);
		mParam.addParam("pageinfo", pageinfo);
		mParam.addParam("pagesize", pagesize);
		mParam.addParam("gender", gender);
		startRequest(context,SERVER_URL_GetAROUNDPEOPLE, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_POST, resultType);
	}
	/**
	 * 获取身边最新的微博	 
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param longitude  经度，例如：22.541321
	 * @param latitude 纬度，例如：13.935558
	 * @param pageinfo 翻页参数，由上次请求返回（第一次请求时请填空）
	 * @param pagesize  请求的每页个数（1-25个）	 * 
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getAroundNew(Context context,String format,double longitude,double latitude,String pageinfo,int pagesize,HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("longitude", longitude);
		mParam.addParam("latitude", latitude);
		mParam.addParam("pageinfo", pageinfo);
		mParam.addParam("pagesize", pagesize);
		startRequest(context,SERVER_URL_GetAROUNDNEW, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_POST, resultType);
	}
}
