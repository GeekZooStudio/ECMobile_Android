
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ORDER_EXPRESS_DATA")
public class ORDER_EXPRESS_DATA  extends Model
{

     public ArrayList<EXPRESS>   content = new ArrayList<EXPRESS>();

     @Column(name = "shipping_name")
     public String   shipping_name;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          subItemArray = jsonObject.optJSONArray("content");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  EXPRESS subItem = new EXPRESS();
                  subItem.fromJson(subItemObject);
                  this.content.add(subItem);
               }
           }


          this.shipping_name = jsonObject.optString("shipping_name");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          for(int i =0; i< content.size(); i++)
          {
              EXPRESS itemData =content.get(i);
              JSONObject itemJSONObject = itemData.toJson();
              itemJSONArray.put(itemJSONObject);
          }
          localItemObject.put("content", itemJSONArray);
          localItemObject.put("shipping_name", shipping_name);
          return localItemObject;
     }

}
