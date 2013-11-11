
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SIGNUPFILEDS")
public class SIGNUPFILEDS  extends Model
{

     @Column(name = "need")
     public String need;

     @Column(name = "SIGNUPFILEDS_id")
     public int id;

     @Column(name = "name")
     public String name;

 public static SIGNUPFILEDS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     SIGNUPFILEDS   localItem = new SIGNUPFILEDS();

     JSONArray subItemArray;

     localItem.need = jsonObject.optString("need");

     localItem.id = jsonObject.optInt("id");

     localItem.name = jsonObject.optString("name");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("need", need);
     localItemObject.put("id", id);
     localItemObject.put("name", name);
     return localItemObject;
 }

}
