
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ARTICLE")
public class ARTICLE  extends Model
{

     @Column(name = "short_title")
     public String short_title;

     @Column(name = "ARTICLE_id")
     public int id;

     @Column(name = "title")
     public String title;

 public static ARTICLE fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     ARTICLE   localItem = new ARTICLE();

     JSONArray subItemArray;

     localItem.short_title = jsonObject.optString("short_title");

     localItem.id = jsonObject.optInt("id");

     localItem.title = jsonObject.optString("title");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("short_title", short_title);
     localItemObject.put("id", id);
     localItemObject.put("title", title);
     return localItemObject;
 }

}
