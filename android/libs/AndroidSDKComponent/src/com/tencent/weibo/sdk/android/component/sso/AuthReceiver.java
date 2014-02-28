package com.tencent.weibo.sdk.android.component.sso;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.weibo.sdk.android.component.sso.tools.Cryptor;

public class AuthReceiver extends BroadcastReceiver {

	static final String ACTION = "com.tencent.sso.AUTH";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			String packageName = intent
					.getStringExtra("com.tencent.sso.PACKAGE_NAME");
			if (packageName.equals(context.getPackageName())) { // 是这个app发起的授权请求
				boolean authResult = intent.getBooleanExtra(
						"com.tencent.sso.AUTH_RESULT", false);
				if (authResult) {
					String name = intent
							.getStringExtra("com.tencent.sso.WEIBO_NICK");
					byte[] data = intent
							.getByteArrayExtra("com.tencent.sso.ACCESS_TOKEN");
					data = new Cryptor().decrypt(data,
							AuthHelper.ENCRYPT_KEY.getBytes(), 10);
					WeiboToken token = revert(data);
					if (AuthHelper.listener != null) {
						AuthHelper.listener.onAuthPassed(name, token);
					}
				} else {
					int erroresult = intent.getIntExtra(
							"com.tencent.sso.RESULT", 1);
					String name = intent
							.getStringExtra("com.tencent.sso.WEIBO_NICK");
					if (AuthHelper.listener != null) {
						AuthHelper.listener.onAuthFail(erroresult, name);
					}
				}
			}
		}
	}

	private WeiboToken revert(byte[] data) {
		WeiboToken token = new WeiboToken();
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		try {
			token.accessToken = dis.readUTF();
			token.expiresIn = dis.readLong();
			token.refreshToken = dis.readUTF();
			token.openID = dis.readUTF();
			token.omasToken = dis.readUTF();
			token.omasKey = dis.readUTF();
			return token;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					// do nothing
				}
			}
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return null;
	}

}
