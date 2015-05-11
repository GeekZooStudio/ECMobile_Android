
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "PLAYER")
public class PLAYER  extends Model
{

     @Column(name = "description")
     public String description;

     @Column(name = "photo")
     public PHOTO   photo;

     @Column(name = "url")
     public String url;

     @Column(name = "action")
     public String action;

     @Column(name = "action_id")
     public int action_id;


 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     PLAYER   localItem = new PLAYER();

     JSONArray subItemArray;

     this.description = jsonObject.optString("description");
     PHOTO  photo = new PHOTO();
     photo.fromJson(jsonObject.optJSONObject("photo"));
     this.photo = photo;

     this.url = jsonObject.optString("url");
     this.action = jsonObject.optString("action");
     this.action_id = jsonObject.optInt("action_id");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("description", description);
     if(null!=photo)
     {
       localItemObject.put("photo", photo.toJson());
     }
     localItemObject.put("url", url);
     localItemObject.put("action",action);
     localItemObject.put("action_id",action_id);
     return localItemObject;
 }

}
