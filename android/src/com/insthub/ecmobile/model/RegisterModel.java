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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class RegisterModel extends BaseModel {

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public ArrayList<SIGNUPFILEDS> signupfiledslist = new ArrayList<SIGNUPFILEDS>();
    public STATUS responseStatus;

    public RegisterModel(Context context) {
        super(context);

        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }

    public void signupFields() {
        usersignupFieldsRequest request = new usersignupFieldsRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                RegisterModel.this.callback(url, jo, status);
                try {
                    usersignupFieldsResponse response = new usersignupFieldsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        responseStatus = response.status;
                        if (responseStatus.succeed == ErrorCodeConst.ResponseSucceed) {
                            ArrayList<SIGNUPFILEDS> data = response.data;
                            if (null != data && data.size() > 0) {
                                signupfiledslist.clear();
                                signupfiledslist.addAll(data);
                            }
                        } else if (responseStatus.error_code == ErrorCodeConst.UserOrEmailExist) {
                            Resources resource = mContext.getResources();
                            String user_or_email_exists = resource.getString(R.string.user_or_email_exists);
                            ToastView toast = new ToastView(mContext, user_or_email_exists);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        RegisterModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        cb.url(ApiInterface.USER_SIGNUPFIELDS).type(JSONObject.class);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void signup(String name, String password, String email, ArrayList<FIELD> fields) {
        usersignupRequest request = new usersignupRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                RegisterModel.this.callback(url, jo, status);
                try {
                    usersignupResponse response = new usersignupResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            SIGNUP_DATA data = response.data;
                            SESSION session = data.session;
                            SESSION.getInstance().uid=session.uid;
                            SESSION.getInstance().sid = session.sid;
                            USER user = data.user;
                            user.save();
                            editor.putString("uid", session.uid);
                            editor.putString("sid", session.sid);
                            editor.commit();
                            RegisterModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        request.name = name;
        request.password = password;
        request.email = email;
        request.field = fields;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.USER_SIGNUP).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

}
