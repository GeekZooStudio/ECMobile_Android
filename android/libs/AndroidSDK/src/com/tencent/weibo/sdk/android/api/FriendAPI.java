package com.tencent.weibo.sdk.android.api;

import android.content.Context;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;

public class FriendAPI extends BaseAPI {
	public FriendAPI(AccountModel account) {
		super(account);
		 
	}
 /**
    * 互听关系url
 * */
	private static final String SERVER_URL_MUTUALLIST = API_SERVER + "/friends/mutual_list";
	/**
	 * 收听某个用户url
	 * */
	private static final String SERVER_URL_ADD = API_SERVER + "/friends/add";
	/**
	 * 我收听的人列表url
	 * */
	private static final String SERVER_URL_IDOLLIST = API_SERVER + "/friends/idollist";
	/**
	 * 我的听众列表url
	 * */
	private static final String SERVER_URL_FANSLIST = API_SERVER + "/friends/fanslist";
	/**
	 * 检测是否我的听众或收听的人url
	 * */
	private static final String SERVER_URL_CHECK = API_SERVER + "/friends/check";
	/**
	 * 获取最近联系人列表url
	 * */
	private static final String SERVER_URL_GetINTIMATEFRIENDS = API_SERVER + "/friends/get_intimate_friends";
	 
	/**
	 * 互听关系链列表	 * 
	 * @param context 上下文
	 * @param format  返回数据的格式（json或xml）
	 * @param name 用户帐户名（可选）
	 * @param fopenid 用户openid（可选）
			  		  name和fopenid至少选一个，若同时存在则以name值为主
	 * @param startindex  起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
	 * @param reqnum  请求个数(1-30)
	 * @param install  过滤安装应用好友（可选）
					   0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getMutualList(Context context,String format,String name,String fopenid,int startindex,int reqnum,int install, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("install", install);
		mParam.addParam("startindex", startindex);
		if(name!=null && !"".equals(name)){
			mParam.addParam("name", name);
		}
		if(fopenid!=null && !"".equals(fopenid)){
			mParam.addParam("fopenid", fopenid);
		}
		startRequest(context,SERVER_URL_MUTUALLIST, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 * 收听某个用户
	 * 
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param name  要收听人的微博帐号列表（非昵称），用“,”隔开，例如：abc,bcde,effg（可选，最多30个）
	 * @param fopenids 你需要读取的用户openid列表，用下划线“_”隔开，例如：B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB（可选，最多30个）
					name和fopenids至少选一个，若同时存在则以name值为主
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void addFriend(Context context,String format,String name,String fopenids, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		if(name!=null && !"".equals(name)){
			mParam.addParam("name", name);
		}
		if(fopenids!=null && !"".equals(fopenids)){
			mParam.addParam("fopenids", fopenids);
		}
		startRequest(context,SERVER_URL_ADD, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_POST, resultType);
	}
	
	/**
	 * 我收听的人列表
	 *
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param reqnum 请求个数(1-30)
	 * @param startindex 起始位置（第一页:填0，继续向下翻页：填【reqnum*（page-1）】）
	 * @param mode 获取模式，默认为0
				mode=0，旧模式，新粉丝在前，只能拉取1000个 
				mode=1，新模式，最多可拉取一万粉丝，暂不支持排序
	 * @param install  过滤安装应用好友（可选）
				0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void friendIDolList(Context context,String format,int reqnum,int startindex,int mode,int install, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("startindex", startindex);
		mParam.addParam("mode", mode);
		mParam.addParam("install", install);
		startRequest(context,SERVER_URL_IDOLLIST, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 *我的听众列表
	 * 
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param reqnum 请求个数(1-30)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填上一次请求返回的nextstartpos）
	 * @param mode  获取模式，默认为0
					mode=0，旧模式，新粉丝在前，只能拉取1000个 
					mode=1，新模式，最多可拉取一万粉丝，暂不支持排序
	 * @param install 过滤安装应用好友（可选）
					0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友 
	 * @param sex 按性别过滤标识
					1-男，2-女，0-不进行性别过滤，默认为0（此参数当mode=0时使用，支持排序） 
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void friendFansList(Context context,String format,int reqnum,int startindex,int mode,int install,int sex, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("startindex", startindex);
		mParam.addParam("mode", mode);
		mParam.addParam("install", install);
		mParam.addParam("sex", sex);
		
		startRequest(context,SERVER_URL_FANSLIST, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 *  检测是否我的听众或收听的人
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param names 其他人的帐户名列表，用逗号“,”分隔，如aaa,bbb（最多30个，可选）
	 * @param fopenids  其他人的的用户openid列表，用“_”隔开，例如：B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB（可选，最多30个）
						names和fopenids至少选一个，若同时存在则以names值为主
	 * @param flag  0-检测听众，1-检测收听的人 2-两种关系都检测
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void friendCheck(Context context,String format,String names,String fopenids,int flag, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("names", names);
		mParam.addParam("fopenids", fopenids);
		mParam.addParam("flag", flag);
		startRequest(context,SERVER_URL_CHECK, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_GET, resultType);
	}
	/**
	 * 获取最近联系人列表
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param reqnum 请求个数（1-20）
	 * @param mCallBack 回调对象 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getIntimateFriends(Context context,String format,int reqnum,HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("reqnum", reqnum);
		startRequest(context,SERVER_URL_GetINTIMATEFRIENDS, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTPMETHOD_GET, resultType);
	}
}
