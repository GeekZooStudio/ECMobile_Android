
package com.insthub.ecmobile.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "MESSAGE")
public class MESSAGE extends Model
{
     @Column(name = "message_id")
     public int id;

     @Column(name = "content")
     public String content;

     @Column(name = "action")
     public String action;

    @Column(name = "parameter")
     public String parameter;

     @Column(name = "time")
     public String time;

 public static MESSAGE fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     MESSAGE localItem = new MESSAGE();

     JSONArray subItemArray;

     localItem.content = jsonObject.optString("content");

     localItem.action = jsonObject.optString("action");

     localItem.parameter = jsonObject.optString("parameter");

     localItem.time = jsonObject.optString("time");

     localItem.id = jsonObject.optInt("id");

     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();

     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("content", content);
     localItemObject.put("action", action);
     localItemObject.put("parameter",parameter);
     localItemObject.put("time", time);
     localItemObject.put("id",id);

     return localItemObject;
 }

}
