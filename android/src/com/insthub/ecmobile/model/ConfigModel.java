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


import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.activity.AppOutActivity;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfigModel extends BaseModel {
    public CONFIG config;

    private static ConfigModel instance;

    public static ConfigModel getInstance() {
        return instance;
    }

    public ConfigModel() {
        super();
    }

    public ConfigModel(Context context) {
        super(context);
        instance = this;
    }

    public void getConfig() {
        configRequest request = new configRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                ConfigModel.this.callback(url, jo, status);
                try {
                    configResponse response = new configResponse();
                    response.fromJson(jo);
                    if (jo != null) {


                        if (response.status.succeed == 1) {
                            ConfigModel.this.config = response.data;
                            ConfigModel.this.OnMessageResponse(url, jo, status);

                            if (config.shop_closed .equals("1")) {
                                Intent intent = new Intent(mContext, AppOutActivity.class);
                                intent.putExtra("flag", 1);
                                mContext.startActivity(intent);
                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        request.session = SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.CONFIG).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }
}
