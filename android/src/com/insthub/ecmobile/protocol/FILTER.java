
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "FILTER")
public class FILTER  extends Model
{

     @Column(name = "keywords")
     public String keywords;

     @Column(name = "sort_by")
     public String sort_by;

     @Column(name = "brand_id")
     public String brand_id;

     @Column(name = "category_id")
     public String category_id;

     @Column(name = "price_range")
     public PRICE_RANGE price_range;

 public static FILTER fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     FILTER   localItem = new FILTER();

     JSONArray subItemArray;

     localItem.keywords = jsonObject.optString("keywords");

     localItem.sort_by = jsonObject.optString("sort_by");

     localItem.brand_id = jsonObject.optString("brand_id");

     localItem.category_id = jsonObject.optString("category_id");
     localItem.price_range = PRICE_RANGE.fromJson(jsonObject.optJSONObject("price_range"));
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("keywords", keywords);
     localItemObject.put("sort_by", sort_by);
     localItemObject.put("brand_id", brand_id);
     localItemObject.put("category_id", category_id);

     if (null != price_range)
     {
         localItemObject.put("price_range",price_range.toJson());
     }

     return localItemObject;
 }

}
