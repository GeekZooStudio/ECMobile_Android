
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "usercollectlistRequest")
public class usercollectlistRequest  extends Model
{

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "pagination")
     public PAGINATION   pagination;

     @Column(name = "rec_id")
     public int rec_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;
          PAGINATION  pagination = new PAGINATION();
          pagination.fromJson(jsonObject.optJSONObject("pagination"));
          this.pagination = pagination;

          this.rec_id = jsonObject.optInt("rec_id");
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
          if(null != pagination)
          {
            localItemObject.put("pagination", pagination.toJson());
          }
          localItemObject.put("rec_id", rec_id);
          return localItemObject;
     }

}
