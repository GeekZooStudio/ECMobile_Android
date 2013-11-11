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

import android.content.Context;
import android.content.Intent;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.activity.AppOutActivity;
import com.insthub.ecmobile.protocol.ADDRESS;
import com.insthub.ecmobile.protocol.CONFIG;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfigModel extends BaseModel
{
    public CONFIG config;

    private static ConfigModel instance;
    public static ConfigModel getInstance()
    {
        return instance;
    }

    public ConfigModel()
    {
       super();
    }

    public ConfigModel(Context context)
    {
        super(context);
        instance = this;
    }

    public void getConfig()
    {
        String url = ProtocolConst.CONFIG;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {
                ConfigModel.this.callback(url, jo, status);
                try
                {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

                    if(responseStatus.succeed == 1)
                    {
                        JSONObject dataJsonObject = jo.optJSONObject("data");
                        ConfigModel.this.config = CONFIG.fromJson(dataJsonObject);
                        ConfigModel.this.OnMessageResponse(url, jo, status);
                        
                        if(config.shop_closed == 1) {
                        	Intent intent = new Intent(mContext,AppOutActivity.class);
                        	intent.putExtra("flag", 1);
                        	mContext.startActivity(intent);
                        }
                        
                    }

                }
                catch (JSONException e)
                {
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
        }
        catch (JSONException e)
        {

        }

        params.put("json",requestJsonObject.toString());

        cb.url(url).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }
}
