package com.insthub.ecmobile.model;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;
import com.insthub.ecmobile.protocol.USER;

public class UserInfoModel extends BaseModel {

	public USER user;

    public static final  int RANK_LEVEL_NORMAL = 0;
    public static final  int RANK_LEVEL_VIP = 1;
	
	public UserInfoModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void getUserInfo() {
		
		String url = ProtocolConst.USERINFO;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				UserInfoModel.this.callback(url, jo, status);
				
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					
					if(responseStatus.succeed == 1) {
						user = USER.fromJson(jo.optJSONObject("data"));
						user.save();
						UserInfoModel.this.OnMessageResponse(url, jo, status);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

		};

		SESSION session = SESSION.getInstance();
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
		
	}

}
