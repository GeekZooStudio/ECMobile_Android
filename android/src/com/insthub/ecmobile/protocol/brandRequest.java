
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "brandRequest")
public class brandRequest  extends Model
{

     @Column(name = "category_id")
     public String category_id;

    @Column(name = "session")
    public SESSION   session;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

         this.category_id = jsonObject.optString("category_id");
         SESSION session = new SESSION();
         session.fromJson(jsonObject.optJSONObject("session"));
         this.session = session;
         return;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("category_id", category_id);
         if(null != session)
         {
             localItemObject.put("session", session.toJson());
         }
          return localItemObject;
     }

}
