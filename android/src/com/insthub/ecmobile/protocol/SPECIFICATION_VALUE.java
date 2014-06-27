
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SPECIFICATION_VALUE")
public class SPECIFICATION_VALUE  extends Model
{

     @Column(name = "SPECIFICATION_VALUE_id")
     public int id;

     @Column(name = "price")
     public int price;

     @Column(name = "label")
     public String label;

     @Column(name = "format_price")
     public String format_price;

     public SPECIFICATION specification;

 public static SPECIFICATION_VALUE fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     SPECIFICATION_VALUE   localItem = new SPECIFICATION_VALUE();

     JSONArray subItemArray;

     localItem.id = jsonObject.optInt("id");

     localItem.price = jsonObject.optInt("price");

     localItem.label = jsonObject.optString("label");

     localItem.format_price = jsonObject.optString("format_price");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("price", price);
     localItemObject.put("label", label);
     localItemObject.put("format_price", format_price);
     return localItemObject;
 }

}
