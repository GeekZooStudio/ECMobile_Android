
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "PAYMENT")
public class PAYMENT  extends Model
{

     @Column(name = "is_cod")
     public String is_cod;

     @Column(name = "pay_code")
     public String pay_code;

     @Column(name = "pay_fee")
     public String pay_fee;

     @Column(name = "pay_id")
     public String pay_id;

     @Column(name = "formated_pay_fee")
     public String formated_pay_fee;

     @Column(name = "pay_name")
     public String pay_name;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.is_cod = jsonObject.optString("is_cod");

     this.pay_code = jsonObject.optString("pay_code");

     this.pay_fee = jsonObject.optString("pay_fee");

     this.pay_id = jsonObject.optString("pay_id");

     this.formated_pay_fee = jsonObject.optString("formated_pay_fee");

     this.pay_name = jsonObject.optString("pay_name");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("is_cod", is_cod);
     localItemObject.put("pay_code", pay_code);
     localItemObject.put("pay_fee", pay_fee);
     localItemObject.put("pay_id", pay_id);
     localItemObject.put("formated_pay_fee", formated_pay_fee);
     localItemObject.put("pay_name", pay_name);
     return localItemObject;
 }

}
