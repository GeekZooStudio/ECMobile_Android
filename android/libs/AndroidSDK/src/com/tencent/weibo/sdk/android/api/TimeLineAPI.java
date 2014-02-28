package com.tencent.weibo.sdk.android.api;

import android.content.Context;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;


public class TimeLineAPI extends BaseAPI {
   /**
    *获取主页时间线url 
    * */
	private static final String SERVER_URL_HOMETIMELINE = API_SERVER
			+ "/statuses/home_timeline";
	 /**
	    *其他用户发表时间线url 
	    * */
	private static final String SERVER_URL_USERTIMELINE = API_SERVER
			+ "/statuses/user_timeline";
	 /**
	    *话题时间线url 
	    * */
	private static final String SERVER_URL_HTTIMELINE = API_SERVER
			+ "/statuses/ht_timeline_ext";
	
	public TimeLineAPI(AccountModel account) {
		super(account);
		 
	}
 
	/**
	 * 获取主页时间线	 
	 * @param context 上下文
	 * @param pageFlag 分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pageTime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-70条）
	 * @param type  拉取类型（需填写十进制数字）0x1 原创发表 0x2 转载 如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型 
	 * @param contenttype 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频 建议不使用contenttype为1的类型，如果要拉取只有文本的微博，建议使用0x80
	 * @param format 返回类型 json/xml
	 * @param mCallBack 回调函数 
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getHomeTimeLine(Context context, int pageFlag, int pageTime,
			int reqnum, int type, int contenttype,String format, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);
		mParam.addParam("pageflag", pageFlag);
		mParam.addParam("type", type);
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("pagetime", pageTime);
		mParam.addParam("contenttype", contenttype);
		startRequest(context,SERVER_URL_HOMETIMELINE, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 * 其他用户发表时间线
	 * @param context 上下文
	 * @param pageFlag 分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pageTime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-70条）
	 * @param lastid 用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	 * @param name 你需要读取的用户的用户名（可选）
	 * @param fopenid  你需要读取的用户的openid（可选）name和fopenid至少选一个，若同时存在则以name值为主
	 * @param type 拉取类型（需填写十进制数字）
				 0x1 原创发表 
				 0x2 转载 
				 0x8 回复 
				 0x10 空回 
				 0x20 提及 
				 0x40 点评
				如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 * @param contenttype 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频 ，建议不使用contenttype为1的类型，如果要拉取只有文本的微博，建议使用0x80
	 * @param format 返回数据格式 json/xml
	 * @param mCallBack 回调函数
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getUserTimeLine(Context context, int pageFlag, int pageTime,
			int reqnum,int lastid,String name,String fopenid, int type, int contenttype,String format, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);
		mParam.addParam("pageflag", pageFlag);
		mParam.addParam("pagetime", pageTime);
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("lastid", lastid);//
		if(name!=null && !"".equals(name)){
			mParam.addParam("name", name);	
		}
		if(fopenid!=null && !"".equals(fopenid)){
			mParam.addParam("fopenid", fopenid);	
		}
		mParam.addParam("type", type);
		mParam.addParam("contenttype", contenttype);
		startRequest(context,SERVER_URL_USERTIMELINE, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTPMETHOD_GET, resultType);
	}
	
	/**
	 * 话题时间线	 
	 * @param context 上下文
	 * @param format 返回数据的格式（json或xml）
	 * @param reqnum 请求数量（1-100）
	 * @param tweetid 微博帖子ID（详细用法见pageflag）
	 * @param time 微博帖子生成时间（详细用法见pageflag）
	 * @param pageflag 翻页标识(第一次务必填零)
				pageflag=1表示向下翻页：tweetid和time是上一页的最后一个帖子ID和时间； 
				pageflag=2表示向上翻页：tweetid和time是下一页的第一个帖子ID和时间；
	 * @param flag  是否拉取认证用户，用作筛选。0-拉取所有用户，0x01-拉取认证用户
	 * @param httext 话题内容，长度有限制
	 * @param htid 话题ID（可以通过ht/ids获取指定话题的ID）
				htid和httext这两个参数不能同时填写，如果都填写只拉取htid指定的微博，如果要拉取指定话题的微博，请务必让htid为0 
	 * @param type  1-原创发表，2-转载
	 * @param contenttype 正文类型（按位使用）。1-带文本(这一位一定有)，2-带链接，4-带图片，8-带视频，0x10-带音频
				建议不使用contenttype为1的类型，如果要拉取只有文本的微博，建议使用0x80
	 * @param mCallBack 回调函数
	 * @param mTargetClass 返回对象类，如果返回json则为null
	 * @param resultType BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getHTTimeLine(Context context,String format, int reqnum,String tweetid,String time,int pageflag,int flag, String httext,String htid,
			 int type, int contenttype, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);
		mParam.addParam("pageflag", pageflag);
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("tweetid", tweetid);
		mParam.addParam("time", time);
		mParam.addParam("flag", flag);
		if(httext!=null && !"".equals(httext)){
			mParam.addParam("httext", httext);
		}
		if(htid!=null && !"".equals(htid)  && !"0".equals(htid)){
			mParam.addParam("htid", htid);
		}
		mParam.addParam("type", type);
		mParam.addParam("contenttype", contenttype);
		startRequest(context,SERVER_URL_HTTIMELINE, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTPMETHOD_GET, resultType);
	}
}
