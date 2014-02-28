package com.insthub.ecmobile.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MESSAGE_RESPONSE {

	public int succeed;
    public int error_code;
    public String error_desc;
    public int total;
    public ArrayList<MESSAGE> messages = new ArrayList<MESSAGE>();
    
    public static MESSAGE_RESPONSE fromJson(JSONObject jsonObject) throws JSONException {
        if(null == jsonObject){
        	return null;
        }
        
        MESSAGE_RESPONSE localItem = new MESSAGE_RESPONSE();
        localItem.succeed = jsonObject.optInt("succeed");
        localItem.error_code = jsonObject.optInt("error_code");
        localItem.error_desc = jsonObject.optString("error_desc");
        localItem.total = jsonObject.optInt("total");
        
        JSONArray subItemArray = jsonObject.optJSONArray("messages");
        if(null != subItemArray) {
            for(int i = 0;i < subItemArray.length();i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                MESSAGE subItem = MESSAGE.fromJson(subItemObject);
                subItem.save();
                localItem.messages.add(subItem);
            }
        }
        return localItem;
    }
    
    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("succeed", succeed);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        localItemObject.put("total", total);
        
        JSONArray itemJSONArray = new JSONArray();
        for(int i=0; i<messages.size(); i++) {
        	MESSAGE itemData = messages.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("messages", itemJSONArray);
        return localItemObject;
    }
	
}
