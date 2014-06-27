
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "GOODS_ATTR")
public class GOODS_ATTR  extends Model
{

     @Column(name = "name")
     public String name;

     @Column(name = "value")
     public String value;

 public static GOODS_ATTR fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     GOODS_ATTR   localItem = new GOODS_ATTR();

     JSONArray subItemArray;

     localItem.name = jsonObject.optString("name");

     localItem.value = jsonObject.optString("value");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("name", name);
     localItemObject.put("value", value);
     return localItemObject;
 }

}
