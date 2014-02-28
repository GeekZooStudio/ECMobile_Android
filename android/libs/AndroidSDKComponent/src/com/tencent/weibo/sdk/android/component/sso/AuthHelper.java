package com.tencent.weibo.sdk.android.component.sso;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.sso.tools.Base64;
import com.tencent.weibo.sdk.android.component.sso.tools.Cryptor;
import com.tencent.weibo.sdk.android.component.sso.tools.MD5Tools;

public final class AuthHelper {

	static final String WEIBO_PACKAGE = "com.tencent.WBlog";
	static final int SUPPORT_WEIBO_MIN_VERSION = 44;
	static final String WEIBO_AUTH_URL = "TencentAuth://weibo";
	static final String ENCRYPT_KEY = "&-*)Wb5_U,[^!9'+";
	static final byte SDK_VERSION = 1;

	static final int WEIBO_VALIDATE_OK = 0;
	static final int ERROR_WEIBO_UPGRADE_NEEDED = -1;
	static final int ERROR_WEIBO_INSTALL_NEEDED = -2;

	protected static long appid;

	protected static String appSecret;

	public static OnAuthListener listener;

	private static AuthReceiver mReceiver = new AuthReceiver();

	/**
	 * 注册回调
	 * 
	 * @param context
	 *            上下文
	 * @param appid
	 *            AppId
	 * @param secret
	 *            AppSecret
	 * @param listener
	 *            回调
	 */
	public static final void register(Context context, long appid,
			String secret, OnAuthListener listener) {
		AuthHelper.appid = appid;
		AuthHelper.appSecret = secret;
		AuthHelper.listener = listener;

		IntentFilter filter = new IntentFilter("com.tencent.sso.AUTH");
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		context.registerReceiver(mReceiver, filter);
	}

	/**
	 * 反注册
	 * 
	 * @param context
	 *            上下文接口，应该与注册的上下文保持一致
	 */
	public static final void unregister(Context context) {
		context.unregisterReceiver(mReceiver);
	}

	/**
	 * 授权
	 * 
	 * @param context
	 *            上下文
	 * @param reserver
	 *            预埋字段，不需要传空字符串
	 * @return
	 */

	public static final boolean auth(Context context, String reserver) {
		final int checkResult = validateWeiboApp(context);
		if (checkResult == WEIBO_VALIDATE_OK) {
			final long current = System.currentTimeMillis() / 1000;
			final long nonce = Math.abs(new Random().nextInt());
			final String apkSignature = getApkSignature(context);
			byte[] signature = generateSignature(appid, appSecret, current,
					nonce);
			if (signature == null) {
				if (listener != null) {
					listener.onAuthFail(-1, "");
				}
				return false;
			} else {
				// 将签名再加密传输 (似乎没必要, iPhone是因为放在剪帖板中，其他APP也能读到才加密)
				signature = encypt(signature);
			}

			final String packageName = context.getPackageName();

			PackageManager packageManager = context.getPackageManager();
			String thisAppName = "";
			try {
				ApplicationInfo thisApp = packageManager.getApplicationInfo(
						packageName, 0);
				thisAppName = packageManager.getApplicationLabel(thisApp)
						.toString();
			} catch (NameNotFoundException e) {
				// sdk总是需要依赖于一个Android应用程序的，所以这里是不可能发生的
				// so无须处理
			}

			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(WEIBO_AUTH_URL));
			intent.putExtra("com.tencent.sso.APP_ID", appid);
			intent.putExtra("com.tencent.sso.TIMESTAMP", current);
			intent.putExtra("com.tencent.sso.NONCE", nonce);
			intent.putExtra("com.tencent.sso.SDK_VERSION", SDK_VERSION);
			intent.putExtra("com.tencent.sso.PACKAGE_NAME", packageName); // 代替bunderID
			intent.putExtra("com.tencent.sso.ICON_MD5", apkSignature); // apk签名
			intent.putExtra("com.tencent.sso.APP_NAME", thisAppName);
			intent.putExtra("com.tencent.sso.SIGNATURE", signature);
			intent.putExtra("com.tencent.sso.RESERVER", reserver);
			context.startActivity(intent);
			
			return true;
		} else if (checkResult == ERROR_WEIBO_UPGRADE_NEEDED) {
			if (listener != null) {
				listener.onWeiboVersionMisMatch();
			}
			return false;
		} else if (checkResult == ERROR_WEIBO_INSTALL_NEEDED) {
			if (listener != null) {
				listener.onWeiBoNotInstalled();
			}
			return false;
		}
		return false;
	}

	/**
	 * 检查系统中是否已安装支持sso登陆的微博客户端
	 * 
	 * @param context
	 * @return 0：正常 -1:微博客户端需要升级 -2：未安装微博客户端
	 */
	private static int validateWeiboApp(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo pi = packageManager.getPackageInfo(WEIBO_PACKAGE,
					PackageManager.GET_INSTRUMENTATION);
			// 先通过版本号排除掉一些客户端版本
			if (pi != null && pi.versionCode >= SUPPORT_WEIBO_MIN_VERSION) {
				// 查询符合条件的客户端版本是否可以支持授权登陆
				final Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(WEIBO_AUTH_URL));
				List<ResolveInfo> list = packageManager.queryIntentActivities(
						intent, PackageManager.MATCH_DEFAULT_ONLY);
				if (list.size() > 0) {
					return WEIBO_VALIDATE_OK;
				}
			}
			return ERROR_WEIBO_UPGRADE_NEEDED;
		} catch (NameNotFoundException e) {
			return ERROR_WEIBO_INSTALL_NEEDED;
		}

	}

	/**
	 * appid+时间戳 +随机数+sdk版本号 组成的字符串,通过HmacSHA1加密，作为数字签名
	 * 
	 * @param appid
	 * @param current
	 * @param nonce
	 * @return
	 */
	private static byte[] generateSignature(long appid, String appSecret,
			long current, long nonce) {
		byte[] signature = null;
		StringBuffer sb = new StringBuffer();
		sb.append(appid); // appid
		sb.append(current); // 时间戳
		sb.append(nonce); // 随机数
		sb.append(SDK_VERSION); // sdk版本号
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret = new SecretKeySpec(
					appSecret.getBytes("UTF-8"), mac.getAlgorithm());
			mac.init(secret);
			signature = mac.doFinal(sb.toString().getBytes("UTF-8"));
		} catch (Exception e) {

		}
		signature = Base64.encode(signature).getBytes();
		return signature;
	}

	/**
	 * 将签名加密
	 * 
	 * @param signature
	 * @return
	 */
	private static byte[] encypt(byte[] signature) {
		return new Cryptor().encrypt(signature, ENCRYPT_KEY.getBytes());
	}

	/**
	 * 获取apk的签名
	 * 
	 * @param context
	 * @return
	 */
	private static String getApkSignature(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[] signs = pi.signatures;
			Signature sign = signs[0];
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(sign
							.toByteArray()));
			StringBuffer sb = new StringBuffer();
			sb.append(cert.getPublicKey().toString());
			sb.append(cert.getSerialNumber().toString());
			return MD5Tools.toMD5(sb.toString());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
