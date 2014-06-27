
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "cartupdateRequest")
public class cartupdateRequest  extends Model
{

     @Column(name = "new_number")
     public int new_number;

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "rec_id")
     public int rec_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.new_number = jsonObject.optInt("new_number");
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;

          this.rec_id = jsonObject.optInt("rec_id");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("new_number", new_number);
          if(null != session)
          {
            localItemObject.put("session", session.toJson());
          }
          localItemObject.put("rec_id", rec_id);
          return localItemObject;
     }

}
