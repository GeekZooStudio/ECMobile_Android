
package com.insthub.ecmobile.protocol;
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
     
     @Column(name = "custom_data")
     public String custom_data;
     
     @Column(name = "created_at")
     public String created_at;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     MESSAGE localItem = new MESSAGE();

     this.content = jsonObject.optString("content");

     this.action = jsonObject.optString("action");

     this.parameter = jsonObject.optString("parameter");

     this.time = jsonObject.optString("time");

     this.id = jsonObject.optInt("id");

     this.custom_data = jsonObject.optString("custom_data");
     this.created_at = jsonObject.optString("created_at");

     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();

     localItemObject.put("content", content);
     localItemObject.put("action", action);
     localItemObject.put("parameter",parameter);
     localItemObject.put("time", time);
     localItemObject.put("id",id);
     localItemObject.put("custom_data", custom_data);
     localItemObject.put("created_at", created_at);

     return localItemObject;
 }

}
