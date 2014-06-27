
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "FLOW_DONE_DATA")
public class FLOW_DONE_DATA  extends Model
{

     @Column(name = "order_info")
     public ORDER_INFO   order_info;

     @Column(name = "order_sn")
     public String   order_sn;

     @Column(name = "order_id")
     public int order_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          ORDER_INFO  order_info = new ORDER_INFO();
          order_info.fromJson(jsonObject.optJSONObject("order_info"));
          this.order_info = order_info;

          this.order_sn = jsonObject.optString("order_sn");

          this.order_id = jsonObject.optInt("order_id");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          if(null != order_info)
          {
            localItemObject.put("order_info", order_info.toJson());
          }
          localItemObject.put("order_sn", order_sn);
          localItemObject.put("order_id", order_id);
          return localItemObject;
     }

}
