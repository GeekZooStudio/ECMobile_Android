package com.tencent.weibo.sdk.android.component.sso;

public class WeiboToken {

	/** AccessToken */
	public String accessToken;

	/** AccessToken过期时间 */
	public long expiresIn;

	/** 用来换新的AccessToken */
	public String refreshToken;

	/** 兼容OpenID协议 */
	public String openID;

	/** OmasToken */
	public String omasToken;

	/** omasKey */
	public String omasKey;

}
