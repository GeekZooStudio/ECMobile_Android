
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "COMMENTS")
public class COMMENTS  extends Model
{

     @Column(name = "content")
     public String content;

     @Column(name = "COMMENTS_id",unique = true)
     public String id;

     @Column(name = "re_content")
     public String re_content;

     @Column(name = "author")
     public String author;

     @Column(name = "created")
     public String create;

 public static COMMENTS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     COMMENTS   localItem = new COMMENTS();

     JSONArray subItemArray;

     localItem.content = jsonObject.optString("content");

     localItem.id = jsonObject.optString("id");

     localItem.re_content = jsonObject.optString("re_content");

     localItem.author = jsonObject.optString("author");

     localItem.create = jsonObject.optString("create");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("content", content);
     localItemObject.put("id", id);
     localItemObject.put("re_content", re_content);
     localItemObject.put("author", author);
     localItemObject.put("create", create);
     return localItemObject;
 }

}
