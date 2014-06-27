
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "REGIONS")
public class REGIONS  extends Model
{

     @Column(name = "REGIONS_id",unique = true)
     public String id;

     @Column(name = "name")
     public String name;

     @Column(name = "parent_id")
     public String parent_id;

 public static REGIONS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     REGIONS   localItem = new REGIONS();

     JSONArray subItemArray;

     localItem.id = jsonObject.optString("id");

     localItem.name = jsonObject.optString("name");

     localItem.parent_id = jsonObject.optString("parent_id");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("name", name);
     localItemObject.put("parent_id", parent_id);
     return localItemObject;
 }

}
