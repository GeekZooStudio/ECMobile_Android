
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "articleRequest")
public class articleRequest  extends Model
{

     @Column(name = "article_id")
     public int article_id;

    @Column(name = "session")
    public SESSION   session;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.article_id = jsonObject.optInt("article_id");
         SESSION  session = new SESSION();
         session.fromJson(jsonObject.optJSONObject("session"));
         this.session = session;
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("article_id", article_id);
         if(null != session)
         {
             localItemObject.put("session", session.toJson());
         }
          return localItemObject;
     }

}
