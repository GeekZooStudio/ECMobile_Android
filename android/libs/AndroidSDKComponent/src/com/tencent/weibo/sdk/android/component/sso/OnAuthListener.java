package com.tencent.weibo.sdk.android.component.sso;

public interface OnAuthListener {

	/**
	 * 微博客户端未安装
	 */
	void onWeiBoNotInstalled();

	/**
	 * 微博客户端版本不匹配，需要升级
	 */
	void onWeiboVersionMisMatch();

	/**
	 * 授权失败
	 */
	void onAuthFail(int result, String msg);

	/**
	 * 授权成功
	 * 
	 * @param token
	 */
	void onAuthPassed(String name, WeiboToken token);

}
