
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SIMPLEORDER")
public class SIMPLEORDER  extends Model
{

     @Column(name = "SIMPLEORDER_id")
     public int id;

     @Column(name = "order_time")
     public String order_time;

     @Column(name = "total_fee")
     public String total_fee;

     @Column(name = "order_sn")
     public String order_sn;

     @Column(name = "order_status")
     public String order_status;

 public static SIMPLEORDER fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     SIMPLEORDER   localItem = new SIMPLEORDER();

     JSONArray subItemArray;

     localItem.id = jsonObject.optInt("id");

     localItem.order_time = jsonObject.optString("order_time");

     localItem.total_fee = jsonObject.optString("total_fee");

     localItem.order_sn = jsonObject.optString("order_sn");

     localItem.order_status = jsonObject.optString("order_status");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("order_time", order_time);
     localItemObject.put("total_fee", total_fee);
     localItemObject.put("order_sn", order_sn);
     localItemObject.put("order_status", order_status);
     return localItemObject;
 }

}
