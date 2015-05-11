
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "validateintegralRequest")
public class validateintegralRequest  extends Model
{

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "integral")
     public String integral;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;

          this.integral = jsonObject.optString("integral");
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
          localItemObject.put("integral", integral);
          return localItemObject;
     }

}
