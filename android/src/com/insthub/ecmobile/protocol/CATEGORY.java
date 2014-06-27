
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "CATEGORY")
public class CATEGORY  extends Model
{

     @Column(name = "CATEGORY_id")
     public int id;

     @Column(name = "name")
     public String name;

     public ArrayList<CATEGORY>   children = new ArrayList<CATEGORY>();

 public static CATEGORY fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     CATEGORY   localItem = new CATEGORY();

     JSONArray subItemArray;

     localItem.id = jsonObject.optInt("id");

     localItem.name = jsonObject.optString("name");

     subItemArray = jsonObject.optJSONArray("children");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             CATEGORY subItem = CATEGORY.fromJson(subItemObject);
             localItem.children.add(subItem);
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

     for(int i =0; i< children.size(); i++)
     {
         CATEGORY itemData =children.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("children", itemJSONArray);
     return localItemObject;
 }

}
