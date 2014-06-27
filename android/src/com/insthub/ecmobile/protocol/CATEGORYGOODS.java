
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "CATEGORYGOODS")
public class CATEGORYGOODS  extends Model
{

     @Column(name = "CATEGORYGOODS_id")
     public int id;

     @Column(name = "name")
     public String name;

     public ArrayList<SIMPLEGOODS>   goods = new ArrayList<SIMPLEGOODS>();

 public static CATEGORYGOODS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     CATEGORYGOODS   localItem = new CATEGORYGOODS();

     JSONArray subItemArray;

     localItem.id = jsonObject.optInt("id");

     localItem.name = jsonObject.optString("name");

     subItemArray = jsonObject.optJSONArray("goods");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             SIMPLEGOODS subItem = SIMPLEGOODS.fromJson(subItemObject);
             localItem.goods.add(subItem);
         }
     }

     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("name", name);

     for(int i =0; i< goods.size(); i++)
     {
         SIMPLEGOODS itemData =goods.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("goods", itemJSONArray);
     return localItemObject;
 }

}
