package com.insthub.ecmobile.model;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import java.util.HashMap;
import java.util.Map;

import com.insthub.ecmobile.R;
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
			
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(mContext.getResources().getString(R.string.hold_on));
		aq.progress(pd).ajax(cb);
		
	}

}
