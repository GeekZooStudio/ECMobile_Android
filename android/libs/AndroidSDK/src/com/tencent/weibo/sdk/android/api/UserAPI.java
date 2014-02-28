package com.tencent.weibo.sdk.android.api;

import android.content.Context;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;


public class UserAPI extends BaseAPI{
	/**
	 * 获取自己的详细资料请求url
	 * 
	 * */
	private static final String SERVER_URL_USERINFO = API_SERVER
	+ "/user/info";
	/**
	 * 获取其他人资料 请求url
	 * 
	 * */
	private static final String SERVER_URL_USEROTHERINFO = API_SERVER
	+ "/user/other_info";
	/**
	 * 获取一批人的简单资料请求url
	 * 
	 * */
	private static final String SERVER_URL_USERINFOS = API_SERVER
	+ "/user/infos";
	
	public UserAPI(AccountModel account) {
		super(account);
		 
	}
	 
	/**
	 *  获取自己的详细资料	  
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getUserInfo(Context context, String format, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);
		startRequest(context,SERVER_URL_USERINFO, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 *  获取其他人资料 
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param name 他人的帐户名（可选）
	 * @param fopenid 他人的openid（可选）
			  name和fopenid至少选一个，若同时存在则以name值为主
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getUserOtherInfo(Context context, String format, String name, String fopenid, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);
		if(name!=null && !"".equals(name)){
			mParam.addParam("name", name);
		}
		if(fopenid!=null && !"".equals(fopenid)){
			mParam.addParam("fopenid", fopenid);
		}
		
		startRequest(context,SERVER_URL_USEROTHERINFO, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 *  获取一批人的简单资料
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param names  用户id列表，用逗号“,”隔开，如 abc,edf,xxxx（最多30，可选）
	 * @param fopenids 你需要读取的用户openid列表，用下划线“_”隔开，例如：B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB_B624064BA065E01CB73F835017FE96FC（个数与names保持一致，最多30，可选） names和fopenids至少选一个，若同时存在则以names值为主
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getUserInfos(Context context, String format, String names, String fopenids, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);
		if(names!=null && !"".equals(names)){
			mParam.addParam("names", names);
		}
		if(fopenids!=null && !"".equals(fopenids)){
			mParam.addParam("fopenids", fopenids);
		}
		startRequest(context,SERVER_URL_USERINFOS, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTPMETHOD_GET, resultType);
	}
}
