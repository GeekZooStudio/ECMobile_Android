
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
     public String id;

     @Column(name = "shop_price")
     public String shop_price;

     @Column(name = "market_price")
     public String market_price;

     @Column(name = "name")
     public String name;

     @Column(name = "img")
     public PHOTO   img;

     @Column(name = "brief")
     public String brief;

     @Column(name = "promote_price")
     public String promote_price;

    @Column(name = "goods_id")
    public String goods_id;

 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.id = jsonObject.optString("id");

     this.shop_price = jsonObject.optString("shop_price");

     this.market_price = jsonObject.optString("market_price");

     this.name = jsonObject.optString("name");


     PHOTO  img = new PHOTO();
     img.fromJson(jsonObject.optJSONObject("img"));
     this.img = img;
     this.brief = jsonObject.optString("brief");

     this.promote_price = jsonObject.optString("promote_price");
     this.goods_id=jsonObject.optString("goods_id");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("shop_price", shop_price);
     localItemObject.put("market_price", market_price);
     localItemObject.put("name", name);
     if(null!=img)
     {
       localItemObject.put("img", img.toJson());
     }
     localItemObject.put("brief", brief);
     localItemObject.put("promote_price", promote_price);
     localItemObject.put("goods_id", goods_id);
     return localItemObject;
 }

}
