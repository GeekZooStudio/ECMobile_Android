package com.insthub.ecmobile.protocol;

import org.json.JSONException;
import org.json.JSONObject;

public class MESSAGE_COUNT {

    public int succeed;
    public int error_code;
    public String error_desc;
    public int unread;
    
    public static MESSAGE_COUNT fromJson(JSONObject jsonObject) throws JSONException {
        if(null == jsonObject){
        	return null;
        }

        MESSAGE_COUNT localItem = new MESSAGE_COUNT();
        localItem.succeed = jsonObject.optInt("succeed");
        localItem.error_code = jsonObject.optInt("error_code");
        localItem.error_desc = jsonObject.optString("error_desc");
        localItem.unread = jsonObject.optInt("unread");
        return localItem;
    }
    
    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("succeed", succeed);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        localItemObject.put("unread", unread);
        return localItemObject;
    }
	
}
