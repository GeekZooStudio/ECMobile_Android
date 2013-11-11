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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.SHOPHELP;
import com.insthub.ecmobile.protocol.SIGNUPFILEDS;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.insthub.ecmobile.protocol.STATUS;
import com.insthub.ecmobile.protocol.USER;

public class RegisterModel extends BaseModel {

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	public ArrayList<SIGNUPFILEDS> signupfiledslist = new ArrayList<SIGNUPFILEDS>();
	
	public RegisterModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		shared = context.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
	}
	
	public STATUS responseStatus;
	
	public void signupFields() {
		String url = ProtocolConst.SIGNUPFIELDS;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				
				RegisterModel.this.callback(url, jo, status);
				try {
					responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == ErrorCodeConst.ResponseSucceed)
                    {
						JSONArray dataJsonArray = jo.optJSONArray("data");
                        if (null != dataJsonArray && dataJsonArray.length() > 0)
                        {
                        	signupfiledslist.clear();
                            for (int i = 0; i < dataJsonArray.length(); i++)
                            {
                                JSONObject signupfiledsJsonObject = dataJsonArray.getJSONObject(i);
                                SIGNUPFILEDS signupfiledsItem = SIGNUPFILEDS.fromJson(signupfiledsJsonObject);
                                signupfiledslist.add(signupfiledsItem);
                            }
                        }
						
					}
                    else if (responseStatus.error_code == ErrorCodeConst.UserOrEmailExist)
                    {
                        Resources resource = mContext.getResources();
                        String user_or_email_exists = resource.getString(R.string.user_or_email_exists);
                        ToastView toast = new ToastView(mContext, user_or_email_exists);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

					RegisterModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);
		
	}
	
	public void signup(String name, String password, String email, JSONArray field) {
		String url = ProtocolConst.SIGNUP;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				RegisterModel.this.callback(url, jo, status);
				try {
					responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					
					if(responseStatus.succeed == 1) {
						
						JSONObject data = jo.optJSONObject("data");
						SESSION session = SESSION.fromJson(data.optJSONObject("session"));
						USER user = USER.fromJson(data.optJSONObject("user"));
						user.save();
						
						editor.putString("uid", session.uid);
	                    editor.putString("sid", session.sid);
	                    editor.commit();
						
					}
					
					RegisterModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("password", password);
		params.put("email", email);
		params.put("field", field);

		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
}
