
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
     public int shop_closed;

     @Column(name = "close_comment")
     public String close_comment;

     @Column(name = "shop_reg_closed")
     public String shop_reg_closed;
     
     @Column(name = "goods_url")
     public String goods_url;


 public static CONFIG fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     CONFIG   localItem = new CONFIG();

     JSONArray subItemArray;

     localItem.service_phone   = jsonObject.optString("service_phone");

     localItem.site_url         = jsonObject.optString("site_url");

     localItem.shop_desc        = jsonObject.optString("shop_desc");

     localItem.shop_closed = jsonObject.optInt("shop_closed");

     localItem.close_comment = jsonObject.optString("close_comment");

     localItem.shop_reg_closed = jsonObject.optString("shop_reg_closed");
     
     localItem.goods_url = jsonObject.optString("goods_url");

     return localItem;
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
     return localItemObject;
 }

}
