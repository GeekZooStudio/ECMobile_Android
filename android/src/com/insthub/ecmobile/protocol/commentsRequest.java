
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "commentsRequest")
public class commentsRequest  extends Model
{

     @Column(name = "goods_id")
     public int   goods_id;

     @Column(name = "pagination")
     public PAGINATION   pagination;

    @Column(name = "session")
    public SESSION   session;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.goods_id = jsonObject.optInt("goods_id");
          PAGINATION  pagination = new PAGINATION();
          pagination.fromJson(jsonObject.optJSONObject("pagination"));
          this.pagination = pagination;

         SESSION  session = new SESSION();
         session.fromJson(jsonObject.optJSONObject("session"));
         this.session = session;
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("goods_id", goods_id);
          if(null != pagination)
          {
            localItemObject.put("pagination", pagination.toJson());
          }
         if(null != session)
         {
             localItemObject.put("session", session.toJson());
         }
          return localItemObject;
     }

}
