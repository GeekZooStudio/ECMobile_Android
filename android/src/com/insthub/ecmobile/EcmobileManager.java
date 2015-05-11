package com.insthub.ecmobile;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;


public class EcmobileManager {

	private static RegisterApp registerApp;

	private static Context mContext;

	// 获取友盟key
	public static String getUmengKey(Context context)
    {
        return "xxx";
	}
	
	// 获取快递key
	public static String getKuaidiKey(Context context) {
        return "xxx";
	}
	
	// 获取科大讯飞key
	public static String getXunFeiCode(Context context)
    {
        return "xxx";
	}
	
	// 获取百度push的key
	public static String getPushKey(Context context)
    {
        return "xxx";
	}
	
	// 获取百度push的seckey
	public static String getPushSecKey(Context context)
    {
        return "xxx";
	}
	
	// 获取支付宝parterID(合作者身份)
	public static String getAlipayParterId(Context context)
    {
        return "xxx";
	}
	
	// 获取支付宝sellerID(收款账户)
	public static String getAlipaySellerId(Context context)
    {
        return "xxx";
	}
	
	// 获取支付宝key
	public static String getAlipayKey(Context context)
    {
        return "xxx";
	}
	
	// 获取支付宝rsa_alipay_public(公钥)
	public static String getRsaAlipayPublic(Context context)
    {
        return "xxx";
	}
	
	// 获取支付宝rsa_private(私钥)
	public static String getRsaPrivate(Context context)
    {
        return "xxx";
	}
	
	// 获取支付宝回调地址
	public static String getAlipayCallback(Context context)
    {
        return "xxx";
	}
	
	// 获取新浪key
	public static String getSinaKey(Context context)
    {
        return "xxx";
	}
	
	// 获取新浪secret
	public static String getSinaSecret(Context context)
    {
        return "xxx";
	}
	
	// 获取新浪的回调地址
    public static String getSinaCallback(Context context)
    {
        return "xxx";
    }
	
	// 获取微信id
	public static String getWeixinAppId(Context context)
    {
        return "xxx";
	}
	
	// 获取微信key
	public static String getWeixinAppKey(Context context)
    {
        return "xxx";
	}
	public static String getWeixinAppPartnerId(Context context)
	{
		return "xxx";
	}
	
	// 获取腾讯key
	public static String getTencentKey(Context context)
    {
        return "xxx";
	}

	
	// 获取腾讯secret
	public static String getTencentSecret(Context context)
    {
        return "xxx";
	}
	
	// 获取腾讯callback
	public static String getTencentCallback(Context context)
    {
        return "xxx";
	}
	
	public static void registerApp(RegisterApp register) {
		registerApp = register;
	}
	
	public static interface RegisterApp {
		public void onRegisterResponse(boolean success);
	}
	
	// 保存push_token
	public static void setBaiduUUID(Context context, String uuid, String appId, String appKey) {

	}

	
}
