
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "COLLECT_LIST")
public class COLLECT_LIST  extends Model
{

     @Column(name = "shop_price")
     public String shop_price;

     @Column(name = "market_price")
     public String market_price;

     @Column(name = "name")
     public String name;

     @Column(name = "goods_id")
     public int goods_id;

     @Column(name = "img")
     public PHOTO   img;

     @Column(name = "promote_price")
     public String promote_price;

     @Column(name = "rec_id")
     public int rec_id;

 public static COLLECT_LIST fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     COLLECT_LIST   localItem = new COLLECT_LIST();

     JSONArray subItemArray;

     localItem.shop_price = jsonObject.optString("shop_price");

     localItem.market_price = jsonObject.optString("market_price");

     localItem.name = jsonObject.optString("name");

     localItem.goods_id = jsonObject.optInt("goods_id");
     localItem.img = PHOTO.fromJson(jsonObject.optJSONObject("img"));

     localItem.promote_price = jsonObject.optString("promote_price");

     localItem.rec_id = jsonObject.optInt("rec_id");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("shop_price", shop_price);
     localItemObject.put("market_price", market_price);
     localItemObject.put("name", name);
     localItemObject.put("goods_id", goods_id);
     if(null!=img)
     {
       localItemObject.put("img", img.toJson());
     }
     localItemObject.put("promote_price", promote_price);
     localItemObject.put("rec_id", rec_id);
     return localItemObject;
 }

}
