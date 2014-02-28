package com.tencent.weibo.sdk.android.api;
import android.content.Context;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;

public class PublishWeiBoAPI extends BaseAPI {
	/**
	 * 获取互听好友列表
	 * 
	 * */
	public static final String MUTUAL_LIST_URL=API_SERVER+"/friends/mutual_list";
	/**
	 * 
	 * 获取最近使用话题
	 * 
	 * */
	public static final String RECENT_USED_URL=API_SERVER+"/ht/recent_used";
    
	public PublishWeiBoAPI(AccountModel account) {
		super(account);
		 
	}
	
	/**
	 *  获取互听好友列表
	 * @param  context  上下文
	 * @param  callback 回调对象
	 * @param  startindex    起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
	 * @param  install      过滤安装应用好友（可选）0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @param  reqnum        请求个数(1-30)
	 * @param  startindex   （可选）

	 * 
	 * 
	 * */
	public void  mutual_list(Context context, HttpCallback callback,Class<? extends BaseVO> responseType,int fopenid,int startindex,int install,int reqnum){
		 final ReqParam param = new ReqParam();
		param.addParam("format","json");
		param.addParam("oauth_consumer_key",Util.getSharePersistent(context, "CLIENT_ID"));
		param.addParam("oauth_version", "2.a");
		param.addParam("scope", "all");
		param.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		param.addParam("clientip", Util.getLocalIPAddress(context));
		if (fopenid!=0) {
			param.addParam("fopenid", fopenid);
		}
		param.addParam("startindex",startindex);
		param.addParam("install",install);
		param.addParam("reqnum", reqnum);
		param.addParam("name", Util.getSharePersistent(context, "NAME"));	    
		startRequest(context,MUTUAL_LIST_URL, param, callback, responseType, BaseAPI.HTTPMETHOD_GET, BaseVO.TYPE_JSON);
		
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 获取最近使用的话题
	 * @param  context  上下文
	 * @param  callback 回调对象
	 * @param  responseType  解析返回数据存储实体类型 可为null
	 * @param  reqnum   请求个数，最多15个，每页请求个数需保持 一致
	 * @param  page     页码，从1开始，默认1
	 * @param  sorttype 排序类型，0-按照用户在话题下发表广播的数量由多到少排序，1-按照用户在话题下最新一条广播的发表时间由新到旧排序，默认为0
	 * 
	 * 
	 * */
	public void recent_used(Context context, HttpCallback callback,Class<? extends BaseVO> responseType,int reqnum,int page,int sottype){
		ReqParam param = new ReqParam();
		param.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		param.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		param.addParam("clientip", Util.getLocalIPAddress(context));
		param.addParam("oauth_version", "2.a");
		param.addParam("scope", "all");
		param.addParam("format", "json");
		param.addParam("reqnum", reqnum);
		param.addParam("page", page);
		param.addParam("sorttype", sottype);
		startRequest(context,RECENT_USED_URL, param, callback, responseType, BaseAPI.HTTPMETHOD_GET, BaseVO.TYPE_JSON);
	}



}