
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
     public String id;

     @Column(name = "name")
     public String name;

     public ArrayList<CATEGORY>   children = new ArrayList<CATEGORY>();

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.id = jsonObject.optString("id");

     this.name = jsonObject.optString("name");

     subItemArray = jsonObject.optJSONArray("children");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             CATEGORY subItem = new CATEGORY();
             subItem.fromJson(subItemObject);
             this.children.add(subItem);
         }
     }

     return ;
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
