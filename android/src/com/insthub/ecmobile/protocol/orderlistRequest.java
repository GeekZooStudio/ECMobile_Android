
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "orderlistRequest")
public class orderlistRequest  extends Model
{

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "type")
     public String   type;

     @Column(name = "pagination")
     public PAGINATION   pagination;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;

          this.type = jsonObject.optString("type");
          PAGINATION  pagination = new PAGINATION();
          pagination.fromJson(jsonObject.optJSONObject("pagination"));
          this.pagination = pagination;
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
          localItemObject.put("type", type);
          if(null != pagination)
          {
            localItemObject.put("pagination", pagination.toJson());
          }
          return localItemObject;
     }

}
