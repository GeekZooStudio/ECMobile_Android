package com.tencent.weibo.sdk.android.api.util;
import com.tencent.weibo.sdk.android.model.AccountModel;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 
 * @description 存储封装类
 */
public class SharePersistent {
	private static SharePersistent instance;
	/**
	 * 用于存储token,openid等数据的文件名称
	 * */
	private final static String FILE_NAME = "ANDROID_SDK"; //数据记录文件名称
	private SharePersistent() {}
	/**
	 * 获取SharePersistent对象
	 * @return 可用的SharePersistent对象
	 * */
	public static SharePersistent getInstance() {
		if (instance == null) {
			instance = new SharePersistent();
		}
		return instance;
	}
 
	
	/**
	 * 设置数据
	 * @param context 上下文
	 * @param key   存储键
	 * @param value 和键对应的值（String类型）
	 * */
	public boolean put(Context context,String key, String value){
		SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0); //读取文件,如果没有则会创建
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	/**
	 * 设置数据
	 * @param context 上下文
	 * @param key   存储键
	 * @param value 和键对应的值（long类型）
	 * */
	public boolean put(Context context,String key, long value){
		SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0); //读取文件,如果没有则会创建
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}
	
	/**
	 * 获取AccountModel
	 * @return 可用的AccountModel对象
	 */
	public AccountModel getAccount(Context context){
		AccountModel account = new AccountModel();
		SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
		account.setAccessToken(settings.getString("ACCESS_TOKEN", ""));
		account.setExpiresIn((settings.getLong("EXPIRES_IN", 0)));
		account.setOpenID((settings.getString("OPEN_ID", "")));
		account.setOpenKey((settings.getString("OPEN_KEY", "")));
		account.setRefreshToken((settings.getString("REFRESH_TOKEN", "")));
		account.setName((settings.getString("NAME", "")));
		account.setNike((settings.getString("NICK", "")));
		return account;
	}
	
	/**
	 * 获取数据（accesstoken,clientip,openid等）
	 * @param context 上下文
	 * @param key   存储数据时所对应的键
	 * @return 和键对应的值（String类型）
	 * */
	public String get(Context context, String key){
		SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
		return settings.getString(key, "");
	}
	
	/**
	 * 获取数据（accesstoken,clientip,openid等）
	 * @param context 上下文
	 * @param key   存储数据时所对应的键
	 * @return 和键对应的值（long类型）
	 * */
	public long getLong(Context context, String key){
		SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
		return settings.getLong(key, 0L);
	}
	
	/**
	 * 清空key对应的数据
	 * @param context 上下文
	 * @param key 情况特定数据对应的键
	 * @return 成功清除返回true 否则 返回false
	 * */
	public boolean clear(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
		return settings.edit().clear().commit();
	}

	
}
