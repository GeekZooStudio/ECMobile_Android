
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "INV_LIST")
public class INV_LIST  extends Model
{

     @Column(name = "INV_LIST_id",unique = true)
     public String id;

     @Column(name = "value")
     public String value;

 public static INV_LIST fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     INV_LIST   localItem = new INV_LIST();

     JSONArray subItemArray;

     localItem.id = jsonObject.optString("id");

     localItem.value = jsonObject.optString("value");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("value", value);
     return localItemObject;
 }

}
