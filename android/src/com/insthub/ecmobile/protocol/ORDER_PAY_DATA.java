package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "ORDER_PAY_DATA")
public class ORDER_PAY_DATA extends Model {
    @Column(name = "pay_wap")
    public String   pay_wap;

    @Column(name = "pay_online")
    public String   pay_online;

    @Column(name = "upop_tn")
    public String   upop_tn;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.pay_wap = jsonObject.optString("pay_wap");
        this.pay_online = jsonObject.optString("pay_online");
        this.upop_tn = jsonObject.optString("upop_tn");

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("pay_wap", pay_wap);
        localItemObject.put("upop_tn", upop_tn);
        localItemObject.put("pay_online", pay_online);
        return localItemObject;
    }

}
