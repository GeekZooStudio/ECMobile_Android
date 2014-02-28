package com.tencent.weibo.sdk.android.api;

import android.content.Context;
import android.util.Log;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.HttpReqWeiBo;
import com.tencent.weibo.sdk.android.network.HttpService;
import com.tencent.weibo.sdk.android.network.ReqParam;

public abstract class BaseAPI {

	private AccountModel mAccount;
	private String mAccessToken;
	private HttpReqWeiBo weibo;
	private Context mContext;
	private String mRequestUrl;
	private ReqParam mParams;
	private HttpCallback mmCallBack;
	private Class<? extends BaseVO> mmTargetClass;
	private String mRequestMethod;
	private int mResultType;
	/**
	 * API服务接口调用地址
	 */
	public static final String API_SERVER = "https://open.t.qq.com/api";
	/**
	 * post请求方式
	 */
	public static final String REQUEST_METHOD_GET = "GET";
	/**
	 * post请求方式
	 */
	public static final String REQUEST_METHOD_POST = "POST";
	/**
	 * post请求方式
	 */
	public static final String HTTPMETHOD_POST = "POST";
	/**
	 * get请求方式
	 */
	public static final String HTTPMETHOD_GET = "GET";

	public BaseAPI(AccountModel account) {
		mAccount = account;
		if (mAccount != null) {
			mAccessToken = mAccount.getAccessToken();
		}
	}

	private HttpCallback callback = new HttpCallback() {

		@Override
		public void onResult(Object object) {
			Log.d("sss", object + "");
			if (object != null) {
				ModelResult result = (ModelResult) object;
				// Log.d("backsss", result.getObj().toString()+"sda");
				String params[] = result.getObj().toString().split("&");
				String access_token = params[0].split("=")[1];
				mAccessToken = access_token;
				String expires_in = params[1].split("=")[1];
				String refresh_token = params[2].split("=")[1];
				String openid = params[3].split("=")[1];
				String name = params[4].split("=")[1];
				String nick = params[5].split("=")[1];

				Util.saveSharePersistent(mContext, "ACCESS_TOKEN", access_token);
				Util.saveSharePersistent(mContext, "EXPIRES_IN", expires_in);// accesstoken过期时间，以返回的时间的准，单位为秒，注意过期时提醒用户重新授权
				Util.saveSharePersistent(mContext, "OPEN_ID", openid);
				Util.saveSharePersistent(mContext, "REFRESH_TOKEN",
						refresh_token);
				Util.saveSharePersistent(mContext, "NAME", name);
				Util.saveSharePersistent(mContext, "NICK", nick);
				Util.saveSharePersistent(mContext, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));

				weibo = new HttpReqWeiBo(mContext, mRequestUrl, mmCallBack,
						mmTargetClass, mRequestMethod, mResultType);
				mParams.addParam("access_token", mAccessToken);
				weibo.setParam(mParams);
				HttpService.getInstance().addImmediateReq(weibo);
			} else {

			}
		}
	};

	/*
	 * @param context 上下文
	 * 
	 * @param requestUrl 请求url
	 * 
	 * @param params 请求参数
	 * 
	 * @param mCallBack 回调对象
	 * 
	 * @param mTargetClass 返回对象类型
	 * 
	 * @param requestMethod 请求方式 get 或者 post
	 * 
	 * @param resultType 返回结果类型 BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1
	 * BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	protected void startRequest(Context context, final String requestUrl,
			final ReqParam params, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, String requestMethod,
			int resultType) {
		if (isAuthorizeExpired(context)) {
			mContext = context;
			mRequestUrl = requestUrl;
			mParams = params;
			mmCallBack = mCallBack;
			mmTargetClass = mTargetClass;
			mRequestMethod = requestMethod;
			mResultType = resultType;
			String url = "https://open.t.qq.com/cgi-bin/oauth2/access_token";
			weibo = new HttpReqWeiBo(context, url, callback, null,
					REQUEST_METHOD_GET, BaseVO.TYPE_JSON);
			weibo.setParam(refreshToken(context));
			HttpService.getInstance().addImmediateReq(weibo);
		} else {
			weibo = new HttpReqWeiBo(context, requestUrl, mCallBack,
					mTargetClass, requestMethod, resultType);
			params.addParam("access_token", mAccessToken);
			weibo.setParam(params);
			HttpService.getInstance().addImmediateReq(weibo);
		}

	}

	/*
	 * 授权已过期刷新accesstoken,获取新accesstoken
	 * 
	 * @param context 上下文
	 * 
	 * @return 刷新ACESSSTOKEN的请求参数
	 */
	private ReqParam refreshToken(Context context) {
		ReqParam param = new ReqParam();
		String clientId = Util.getSharePersistent(context, "CLIENT_ID");
		String refreshToken = Util.getSharePersistent(context, "REFRESH_TOKEN");
		param.addParam("client_id", clientId);
		param.addParam("grant_type", "refresh_token");
		param.addParam("refresh_token", refreshToken);
		int state = (int) Math.random() * 1000 + 111;
		param.addParam("state", state);
		return param;

	}

	/**
	 * 判断授权是否过期
	 * 
	 * @param context
	 * @return 授权过期则返回true，否则 返回false
	 */
	public boolean isAuthorizeExpired(Context context) {
		boolean expired = false;
		String authorizeTimeStr = Util.getSharePersistent(context,
				"AUTHORIZETIME");
		System.out.println("===== : " + authorizeTimeStr);
		String expiresTime = Util.getSharePersistent(context, "EXPIRES_IN");
		System.out.println("====== : " + expiresTime);
		long currentTime = System.currentTimeMillis() / 1000;
		if (expiresTime != null && authorizeTimeStr != null) {
			if ((Long.valueOf(authorizeTimeStr) + Long.valueOf(expiresTime)) < currentTime) {
				expired = true;
			}
		}
		return expired;
	}
}
