
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SIMPLEGOODS")
public class SIMPLEGOODS  extends Model
{

     @Column(name = "SIMPLEGOODS_id",unique = true)
     public int id;

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

     @Column(name = "brief")
     public String brief;

     @Column(name = "promote_price")
     public String promote_price;

 public static SIMPLEGOODS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     SIMPLEGOODS   localItem = new SIMPLEGOODS();

     JSONArray subItemArray;

     localItem.id = jsonObject.optInt("id");

     localItem.shop_price = jsonObject.optString("shop_price");

     localItem.market_price = jsonObject.optString("market_price");

     localItem.name = jsonObject.optString("name");

     localItem.goods_id = jsonObject.optInt("goods_id");
     localItem.img = PHOTO.fromJson(jsonObject.optJSONObject("img"));

     localItem.brief = jsonObject.optString("brief");

     localItem.promote_price = jsonObject.optString("promote_price");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("shop_price", shop_price);
     localItemObject.put("market_price", market_price);
     localItemObject.put("name", name);
     localItemObject.put("goods_id", goods_id);
     if(null!=img)
     {
       localItemObject.put("img", img.toJson());
     }
     localItemObject.put("brief", brief);
     localItemObject.put("promote_price", promote_price);
     return localItemObject;
 }

}
