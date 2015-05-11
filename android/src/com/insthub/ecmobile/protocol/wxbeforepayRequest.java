package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "wxbeforepayRequest")
public class wxbeforepayRequest extends Model{
    @Column(name = "session")
    public SESSION   session;

    @Column(name = "order_id")
    public String order_id;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;
        SESSION  session = new SESSION();
        session.fromJson(jsonObject.optJSONObject("session"));
        this.session = session;

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if(null != session)
        {
            localItemObject.put("session", session.toJson());
        }
        localItemObject.put("order_id", order_id);
        return localItemObject;
    }
}
