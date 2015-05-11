
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "PAGINATED")
public class PAGINATED  extends Model
{

     @Column(name = "total")
     public int total;

     @Column(name = "more")
     public int more;

     @Column(name = "count")
     public int count;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.total = jsonObject.optInt("total");

     this.more = jsonObject.optInt("more");

     this.count = jsonObject.optInt("count");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("total", total);
     localItemObject.put("more", more);
     localItemObject.put("count", count);
     return localItemObject;
 }

}
