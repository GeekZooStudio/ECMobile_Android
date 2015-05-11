
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SIGNIN_DATA")
public class SIGNIN_DATA  extends Model
{

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "user")
     public USER   user;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;
          USER  user = new USER();
          user.fromJson(jsonObject.optJSONObject("user"));
          this.user = user;
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
          if(null != user)
          {
            localItemObject.put("user", user.toJson());
          }
          return localItemObject;
     }

}
