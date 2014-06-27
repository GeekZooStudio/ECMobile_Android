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

import android.content.res.Resources;
import com.insthub.BeeFramework.BeeFrameworkConst;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.Utils.Utils;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.activity.AppOutActivity;

public class LoginModel extends BaseModel {

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public STATUS responseStatus;

    public LoginModel(Context context) {
        super(context);
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }

    public void login(String name, String password) {
        usersigninRequest request = new usersigninRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                LoginModel.this.callback(url, jo, status);
                try {
                    usersigninResponse response = new usersigninResponse();
                    response.fromJson(jo);
                    responseStatus=response.status;
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            SIGNIN_DATA data = response.data;
                            SESSION session = data.session;
                            SESSION.getInstance().uid=session.uid;
                            SESSION.getInstance().sid = session.sid;
                            USER user = data.user;
                            user.save();
                            editor.putString("uid", session.uid);
                            editor.putString("sid", session.sid);
                            editor.putString("email",user.email);
                            editor.commit();
                        }
                        LoginModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        request.name = name;
        request.password = password;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.USER_SIGNIN).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }
}
