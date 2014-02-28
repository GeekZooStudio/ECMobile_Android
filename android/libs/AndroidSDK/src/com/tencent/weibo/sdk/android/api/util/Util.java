package com.tencent.weibo.sdk.android.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class Util {
	/**
	 * 判断当前网络是否可用
	 * 
	 * @param activity
	 *            当前Acitivity对象
	 * @return 可用返回true 否则返回false
	 * 
	 * */
	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		} else {
			NetworkInfo info[] = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 存储缓存数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            存储数据特定的键
	 * @param value
	 *            存储的数据 （String类型）
	 */
	public static void saveSharePersistent(Context context, String key,
			String value) {
		SharePersistent mSharePersistent = SharePersistent.getInstance();
		mSharePersistent.put(context, key, value);
	}

	/**
	 * 存储缓存数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            存储数据特定的键
	 * @param value
	 *            存储的数据 （long类型）
	 */
	public static void saveSharePersistent(Context context, String key,
			long value) {
		SharePersistent mSharePersistent = SharePersistent.getInstance();
		mSharePersistent.put(context, key, value);
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            获取数据特定的键
	 * @return 获取的数据 （String类型）
	 */
	public static String getSharePersistent(Context context, String key) {
		SharePersistent mSharePersistent = SharePersistent.getInstance();
		return mSharePersistent.get(context, key);
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            获取数据特定的键
	 * @return 获取的数据 （String类型）
	 */
	public static Long getSharePersistentLong(Context context, String key) {
		SharePersistent mSharePersistent = SharePersistent.getInstance();
		return mSharePersistent.getLong(context, key);
	}

	/**
	 * 清除缓存数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            清除数据特定的键
	 * 
	 */
	public static void clearSharePersistent(Context context, String key) {
		SharePersistent mSharePersistent = SharePersistent.getInstance();
		mSharePersistent.clear(context, key);
	}

	/**
	 * 清除全部缓存数据
	 * 
	 * @param context
	 *            上下文 *
	 * 
	 */
	public static void clearSharePersistent(Context context) {
		SharePersistent mSharePersistent = SharePersistent.getInstance();
		mSharePersistent.clear(context, "ACCESS_TOKEN");
		mSharePersistent.clear(context, "EXPIRES_IN");
		mSharePersistent.clear(context, "OPEN_ID");
		mSharePersistent.clear(context, "OPEN_KEY");
		mSharePersistent.clear(context, "REFRESH_TOKEN");
		mSharePersistent.clear(context, "NAME");
		mSharePersistent.clear(context, "NICK");
		mSharePersistent.clear(context, "CLIENT_ID");
	}

	/**
	 * 获取手机ip
	 * 
	 * @param context
	 *            上下文
	 * @return 可用的ip
	 * 
	 */
	public static String getLocalIPAddress(Context context) {
		// try {
		// for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface
		// .getNetworkInterfaces(); mEnumeration.hasMoreElements();) {
		// NetworkInterface intf = mEnumeration.nextElement();
		// for (Enumeration<InetAddress> enumIPAddr = intf
		// .getInetAddresses(); enumIPAddr.hasMoreElements();) {
		// InetAddress inetAddress = enumIPAddr.nextElement();
		// // 如果不是回环地址
		// if (!inetAddress.isLoopbackAddress()) {
		// // 直接返回本地IP地址
		// int i = Integer.parseInt(inetAddress.getHostAddress());
		// String ipStr=(i & 0xFF)+"."+((i>>8) & 0xFF)+"."+
		// ((i>>16) & 0xFF)+"."+(i>>24 & 0xFF);
		// return ipStr;
		// }
		// }
		// }
		// } catch (SocketException ex) {
		// Log.e("Error", ex.toString());
		// }
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		int i = info.getIpAddress();
		String ipStr = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "."
				+ ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
		return ipStr;

		// return intToIp(ip);
		// return null;
	}

	/**
	 * 获取手机经纬度
	 * 
	 * @param context
	 *            上下文
	 * @return 可用的location 可能为空
	 * 
	 */
	public static Location getLocation(Context context) {
		// LocationManager lm=(LocationManager)
		// context.getSystemService(Context.LOCATION_SERVICE);
		// Criteria criteria = new Criteria();
		// criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
		// criteria.setAltitudeRequired(false);//不要求海拔
		// criteria.setBearingRequired(false);//不要求方位
		// criteria.setCostAllowed(true);//允许有花费
		// criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
		// //从可用的位置提供器中，匹配以上标准的最佳提供器
		// String provider = lm.getBestProvider(criteria, true);
		// if (provider!=null) {
		// Location location=lm.getLastKnownLocation(provider);
		// return location;
		// }else {
		// return null;
		// }
		Location currentLocation = null;
		try {
			// 获取到LocationManager对象
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			// 创建一个Criteria对象
			Criteria criteria = new Criteria();
			// 设置粗略精确度
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			// 设置是否需要返回海拔信息
			criteria.setAltitudeRequired(false);
			// 设置是否需要返回方位信息
			criteria.setBearingRequired(false);
			// 设置是否允许付费服务
			criteria.setCostAllowed(true);
			// 设置电量消耗等级
			criteria.setPowerRequirement(Criteria.POWER_HIGH);
			// 设置是否需要返回速度信息
			criteria.setSpeedRequired(false);

			// 根据设置的Criteria对象，获取最符合此标准的provider对象
			String currentProvider = locationManager.getBestProvider(criteria,
					true);
			Log.d("Location", "currentProvider: " + currentProvider);
			// 根据当前provider对象获取最后一次位置信息
			currentLocation = locationManager
					.getLastKnownLocation(currentProvider);
		} catch (Exception e) {
			currentLocation = null;
		}
		return currentLocation;
	}

	private String intToIp(int i) {
		String ipStr = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "."
				+ ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);

		return ipStr;
	}

	/**
	 * @description 通过网络地址获得图片
	 * @param imageUrl
	 *            图片地址
	 * @return drawable
	 */
	public static Drawable loadImageFromUrl(String imageUrl) {
		try {
			URL u = new URL(imageUrl);
			URLConnection conn = u.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inJustDecodeBounds = true;
			// int sampleSize = computeSampleSize(options, -1, 189*127);
			// options.inSampleSize = 1;
			options.inJustDecodeBounds = false;
			options.inSampleSize = 2;// 宽度和高度设置为原来的1/2
			// options.inJustDecodeBounds = false;
			// options.inSampleSize = 1;// 缩小图片，否则内存溢出
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
			return new BitmapDrawable(bitmap);
		} catch (Exception e) {
			 
			return null;
		}
	}

	/**
	 * @des 得到config.properties配置文件中的所有配置
	 * @return Properties对象
	 */
	public static Properties getConfig() {
		Properties props = new Properties();
		InputStream in = Util.class
				.getResourceAsStream("/config/config.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			 
			Log.e("工具包异常", "获取配置文件异常");
			e.printStackTrace();
		}
		return props;
	}

}
