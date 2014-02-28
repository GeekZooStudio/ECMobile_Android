package com.tencent.weibo.sdk.android.model;
/**
 * 存储授权后的用户信息
 * */
public class AccountModel {
	
	private String accessToken;//授权accessToken
	
	private long expiresIn;
	
	private String openID;
	
	private String openKey;//appkey
	
	private String refreshToken;
	
	private String name;
	
	private String nike;//用户昵称
	public AccountModel(){
		
	}
	public AccountModel(String token){
		accessToken = token;
	}
	public String getAccessToken() {
		return accessToken;
	} 
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getOpenKey() {
		return openKey;
	}
	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNike() {
		return nike;
	}
	public void setNike(String nike) {
		this.nike = nike;
	}
	
}
