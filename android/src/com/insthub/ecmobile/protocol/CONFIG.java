
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "CONFIG")
public class CONFIG  extends Model
{
    @Column(name = "service_phone")
    public String service_phone;

    @Column(name = "site_url")
    public String site_url;

    @Column(name = "shop_desc")
    public String shop_desc;

     @Column(name = "shop_closed")
     public String shop_closed;

     @Column(name = "close_comment")
     public String close_comment;

     @Column(name = "shop_reg_closed")
     public String shop_reg_closed;
     
     @Column(name = "goods_url")
     public String goods_url;

    @Column(name = "time_format")
    public String time_format;

    @Column(name = "currency_format")
    public String currency_format;


 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.service_phone   = jsonObject.optString("service_phone");

     this.site_url         = jsonObject.optString("site_url");

     this.shop_desc        = jsonObject.optString("shop_desc");

     this.shop_closed = jsonObject.optString("shop_closed");

     this.close_comment = jsonObject.optString("close_comment");

     this.shop_reg_closed = jsonObject.optString("shop_reg_closed");

     this.goods_url = jsonObject.optString("goods_url");

     this.time_format = jsonObject.optString("time_format");

     this.currency_format = jsonObject.optString("currency_format");


     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();

     localItemObject.put("service_phone",service_phone);
     localItemObject.put("site_url",site_url);
     localItemObject.put("shop_desc",shop_desc);
     localItemObject.put("shop_closed", shop_closed);
     localItemObject.put("close_comment", close_comment);
     localItemObject.put("shop_reg_closed", shop_reg_closed);
     localItemObject.put("goods_url", goods_url);
     localItemObject.put("time_format", time_format);
     localItemObject.put("currency_format", currency_format);
     return localItemObject;
 }

}
