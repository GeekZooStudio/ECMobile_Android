
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
     public String id;

     @Column(name = "price")
    public String price;

     @Column(name = "label")
     public String label;

     @Column(name = "format_price")
     public String format_price;

     public SPECIFICATION specification;

 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.id = jsonObject.optString("id");

     this.price = jsonObject.optString("price");

     this.label = jsonObject.optString("label");

     this.format_price = jsonObject.optString("format_price");

     return ;
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
