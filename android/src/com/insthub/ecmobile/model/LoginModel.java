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

import android.content.res.Resources;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.R;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.AppConst;
import com.insthub.BeeFramework.Utils.Utils;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.activity.AppOutActivity;
import com.insthub.ecmobile.protocol.CLIENT;
import com.insthub.ecmobile.protocol.CODEVALID;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;
import com.insthub.ecmobile.protocol.USER;

public class LoginModel extends BaseModel {

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	public LoginModel(Context context) {
		super(context);
		shared = context.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
	}

	public STATUS responseStatus;
	public void login(String name, String password) {
		String url = ProtocolConst.SIGNIN;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				LoginModel.this.callback(url, jo, status);
				try {
					
					responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

                    Resources resource = mContext.getResources();
                    String user_not_exit = resource.getString(R.string.user_not_exist);
                    String invalid_password = resource.getString(R.string.invalid_password);
					
					if(responseStatus.succeed == ErrorCodeConst.ResponseSucceed) {
						
						JSONObject data = jo.optJSONObject("data");
						SESSION session = SESSION.fromJson(data.optJSONObject("session"));
						USER user = USER.fromJson(data.optJSONObject("user"));
						user.save();
						
						editor.putString("uid", session.uid);
	                    editor.putString("sid", session.sid);
	                    editor.commit();
						
					}
                    else if (responseStatus.error_code == ErrorCodeConst.InvalidUsernameOrPassword)
                    {
                        ToastView toast = new ToastView(mContext, invalid_password);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

					LoginModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("password", password);

		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);

	}
}
