
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "cartcreateRequest")
public class cartcreateRequest  extends Model
{

     public ArrayList<Integer>   spec = new ArrayList<Integer>();

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "goods_id")
     public int   goods_id;

     @Column(name = "number")
     public int   number;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          subItemArray = jsonObject.optJSONArray("spec");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  int subItemObject = subItemArray.optInt(i);
                  int subItem = subItemObject;
                  this.spec.add(subItem);
               }
           }

          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;

          this.goods_id = jsonObject.optInt("goods_id");

          this.number = jsonObject.optInt("number");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          for(int i =0; i< spec.size(); i++)
          {
              int itemData =spec.get(i);
              itemJSONArray.put(itemData);
          }
          localItemObject.put("spec", itemJSONArray);
          if(null != session)
          {
            localItemObject.put("session", session.toJson());
          }
          localItemObject.put("goods_id", goods_id);
          localItemObject.put("number", number);
          return localItemObject;
     }

}
