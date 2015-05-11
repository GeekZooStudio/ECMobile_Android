
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "orderexpressRequest")
public class orderexpressRequest  extends Model
{

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "order_id")
     public String order_id;

    @Column(name = "app_key")
    public String app_key;
     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;

          this.order_id = jsonObject.optString("order_id");
          this.app_key = jsonObject.optString("app_key");
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
          localItemObject.put("app_key",app_key);
          return localItemObject;
     }

}
