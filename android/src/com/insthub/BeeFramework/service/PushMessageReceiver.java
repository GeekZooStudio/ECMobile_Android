package com.insthub.BeeFramework.service;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import java.util.List;

import com.insthub.BeeFramework.BeeFrameworkConst;
import com.insthub.ecmobile.ECMobileAppConst;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.activity.BannerWebActivity;
import com.insthub.ecmobile.protocol.FILTER;

public class PushMessageReceiver extends FrontiaPushMessageReceiver {

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	@Override
	public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
		shared = context.getSharedPreferences("userInfo", 0);
		editor = shared.edit();
		if (errorCode == 0) {
			editor.putString("UUID", userId);
			editor.commit();
			EcmobileManager.setBaiduUUID(context, userId, ECMobileAppConst.AppId, ECMobileAppConst.AppKey);
		}
	}

	@Override
	public void onUnbind(Context context, int i, String s) {

	}

	@Override
	public void onSetTags(Context context, int i, List<String> strings, List<String> strings2, String s) {

	}

	@Override
	public void onDelTags(Context context, int i, List<String> strings, List<String> strings2, String s) {

	}

	@Override
	public void onListTags(Context context, int i, List<String> strings, String s) {

	}

	@Override
	public void onMessage(Context context, String message, String customContentString) {
		String messageString = "透传消息 message=" + message + " customContentString=" + customContentString;
		//System.out.println("messageString:"+messageString);
	}

	@Override
	public void onNotificationClicked(Context context, String title, String description, String customContentString) {
		String notifyString = "通知点击 title=" + title + " description=" + description + " customContent=" + customContentString;
		//System.out.println("notifyString:"+notifyString);
		
		Intent responseIntent = null;
        responseIntent = new Intent(EcmobileMainActivity.ACTION_PUSHCLICK);
        responseIntent.setClass(context, EcmobileMainActivity.class);
        responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(customContentString != null)
        {
            responseIntent.putExtra(EcmobileMainActivity.CUSTOM_CONTENT, customContentString);
        }
        context.startActivity(responseIntent);
	}
}
