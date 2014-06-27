
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "addresssetDefaultRequest")
public class addresssetDefaultRequest  extends Model
{

     @Column(name = "address_id")
     public String address_id;

     @Column(name = "session")
     public SESSION   session;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.address_id = jsonObject.optString("address_id");
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("address_id", address_id);
          if(null != session)
          {
            localItemObject.put("session", session.toJson());
          }
          return localItemObject;
     }

}
