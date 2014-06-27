
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

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.keywords = jsonObject.optString("keywords");

     this.sort_by = jsonObject.optString("sort_by");

     this.brand_id = jsonObject.optString("brand_id");

     this.category_id = jsonObject.optString("category_id");
     PRICE_RANGE price_range=new PRICE_RANGE();
     price_range.fromJson(jsonObject.optJSONObject("price_range"));
     this.price_range = price_range;
     return ;
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
