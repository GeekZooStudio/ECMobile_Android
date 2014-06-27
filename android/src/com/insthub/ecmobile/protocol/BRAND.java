
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "BRAND")
public class BRAND  extends Model
{

     @Column(name = "brand_id")
     public String brand_id;

     @Column(name = "brand_name")
     public String brand_name;

     @Column(name = "url")
     public String url;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.brand_id = jsonObject.optString("brand_id");

     this.brand_name = jsonObject.optString("brand_name");

     this.url = jsonObject.optString("url");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("brand_id", brand_id);
     localItemObject.put("brand_name", brand_name);
     localItemObject.put("url", url);
     return localItemObject;
 }

}
