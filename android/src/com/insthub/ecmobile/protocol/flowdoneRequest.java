
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "flowdoneRequest")
public class flowdoneRequest  extends Model
{

     @Column(name = "inv_type")
     public String   inv_type;

     @Column(name = "inv_payee")
     public String   inv_payee;

     @Column(name = "shipping_id")
     public String shipping_id;

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "inv_content")
     public String   inv_content;

     @Column(name = "bonus")
     public String   bonus;

     @Column(name = "pay_id")
     public String pay_id;

     @Column(name = "integral")
     public String   integral;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.inv_type = jsonObject.optString("inv_type");

          this.inv_payee = jsonObject.optString("inv_payee");

          this.shipping_id = jsonObject.optString("shipping_id");
          SESSION  session = new SESSION();
          session.fromJson(jsonObject.optJSONObject("session"));
          this.session = session;

          this.inv_content = jsonObject.optString("inv_content");

          this.bonus = jsonObject.optString("bonus");

          this.pay_id = jsonObject.optString("pay_id");

          this.integral = jsonObject.optString("integral");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("inv_type", inv_type);
          localItemObject.put("inv_payee", inv_payee);
          localItemObject.put("shipping_id", shipping_id);
          if(null != session)
          {
            localItemObject.put("session", session.toJson());
          }
          localItemObject.put("inv_content", inv_content);
          localItemObject.put("bonus", bonus);
          localItemObject.put("pay_id", pay_id);
          localItemObject.put("integral", integral);
          return localItemObject;
     }

}
