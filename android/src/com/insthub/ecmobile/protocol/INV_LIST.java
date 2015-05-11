
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "INV_LIST")
public class INV_LIST  extends Model
{

     @Column(name = "INV_LIST_id",unique = true)
     public int id;

     @Column(name = "value")
     public String value;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.id = jsonObject.optInt("id");

     this.value = jsonObject.optString("value");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("value", value);
     return localItemObject;
 }

}
