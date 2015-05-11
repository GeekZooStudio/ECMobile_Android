
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SHIPPING")
public class SHIPPING  extends Model
{

     @Column(name = "support_cod")
     public String support_cod;

     @Column(name = "shipping_desc")
     public String shipping_desc;

     @Column(name = "shipping_id")
     public String shipping_id;

     @Column(name = "format_shipping_fee")
     public String format_shipping_fee;

     @Column(name = "insure")
     public String insure;

     @Column(name = "insure_formated")
     public String insure_formated;

     @Column(name = "shipping_code")
     public String shipping_code;

     @Column(name = "shipping_name")
     public String shipping_name;

     @Column(name = "free_money")
     public String free_money;

     @Column(name = "shipping_fee")
     public int shipping_fee;

 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.support_cod = jsonObject.optString("support_cod");

     this.shipping_desc = jsonObject.optString("shipping_desc");

     this.shipping_id = jsonObject.optString("shipping_id");

     this.format_shipping_fee = jsonObject.optString("format_shipping_fee");

     this.insure = jsonObject.optString("insure");

     this.insure_formated = jsonObject.optString("insure_formated");

     this.shipping_code = jsonObject.optString("shipping_code");

     this.shipping_name = jsonObject.optString("shipping_name");

     this.free_money = jsonObject.optString("free_money");

     this.shipping_fee = jsonObject.optInt("shipping_fee");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("support_cod", support_cod);
     localItemObject.put("shipping_desc", shipping_desc);
     localItemObject.put("shipping_id", shipping_id);
     localItemObject.put("format_shipping_fee", format_shipping_fee);
     localItemObject.put("insure", insure);
     localItemObject.put("insure_formated", insure_formated);
     localItemObject.put("shipping_code", shipping_code);
     localItemObject.put("shipping_name", shipping_name);
     localItemObject.put("free_money", free_money);
     localItemObject.put("shipping_fee", shipping_fee);
     return localItemObject;
 }

}
