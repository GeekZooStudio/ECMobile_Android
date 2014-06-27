
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "GOODS")
public class GOODS  extends Model
{

     @Column(name = "promote_end_date")
     public String promote_end_date;

     @Column(name = "click_count")
     public int click_count;

     @Column(name = "goods_sn")
     public String goods_sn;

     @Column(name = "promote_start_date")
     public String promote_start_date;

     @Column(name = "goods_number")
     public String goods_number;

     public ArrayList<PRICE>   rank_prices = new ArrayList<PRICE>();

     @Column(name = "img")
     public PHOTO   img;

     @Column(name = "brand_id")
     public String brand_id;

     public ArrayList<PHOTO>   pictures = new ArrayList<PHOTO>();

     @Column(name = "goods_name")
     public String goods_name;

     public ArrayList<PROPERTY>   properties = new ArrayList<PROPERTY>();

     @Column(name = "goods_weight")
     public String goods_weight;

     @Column(name = "promote_price")
     public String promote_price;
     
     @Column(name = "formated_promote_price")
     public String formated_promote_price;

     @Column(name = "integral")
     public String integral;

     @Column(name = "GOODS_id")
     public int id;

     @Column(name = "cat_id")
     public String cat_id;

     @Column(name = "is_shipping")
     public String is_shipping;

     @Column(name = "shop_price")
     public String shop_price;

     @Column(name = "market_price")
     public String market_price;

    @Column(name="collected")
    public int collected;


     public ArrayList<SPECIFICATION>   specification = new ArrayList<SPECIFICATION>();

 public static GOODS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     GOODS   localItem = new GOODS();

     JSONArray subItemArray;

     localItem.promote_end_date = jsonObject.optString("promote_end_date");

     localItem.click_count = jsonObject.optInt("click_count");

     localItem.goods_sn = jsonObject.optString("goods_sn");

     localItem.promote_start_date = jsonObject.optString("promote_start_date");

     localItem.goods_number = jsonObject.optString("goods_number");

     subItemArray = jsonObject.optJSONArray("rank_prices");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             PRICE subItem = PRICE.fromJson(subItemObject);
             localItem.rank_prices.add(subItem);
         }
     }

     localItem.img = PHOTO.fromJson(jsonObject.optJSONObject("img"));

     localItem.brand_id = jsonObject.optString("brand_id");

     subItemArray = jsonObject.optJSONArray("pictures");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             PHOTO subItem = PHOTO.fromJson(subItemObject);
             localItem.pictures.add(subItem);
         }
     }


     localItem.goods_name = jsonObject.optString("goods_name");

     subItemArray = jsonObject.optJSONArray("properties");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             PROPERTY subItem = PROPERTY.fromJson(subItemObject);
             localItem.properties.add(subItem);
         }
     }


     localItem.goods_weight = jsonObject.optString("goods_weight");

     localItem.promote_price = jsonObject.optString("promote_price");
     
     localItem.formated_promote_price = jsonObject.optString("formated_promote_price");

     localItem.integral = jsonObject.optString("integral");

     localItem.id = jsonObject.optInt("id");

     localItem.cat_id = jsonObject.optString("cat_id");

     localItem.is_shipping = jsonObject.optString("is_shipping");

     localItem.shop_price = jsonObject.optString("shop_price");

     localItem.market_price = jsonObject.optString("market_price");

     localItem.collected=jsonObject.optInt("collected");

     subItemArray = jsonObject.optJSONArray("specification");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             SPECIFICATION subItem = SPECIFICATION.fromJson(subItemObject);
             localItem.specification.add(subItem);
         }
     }

     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("promote_end_date", promote_end_date);
     localItemObject.put("click_count", click_count);
     localItemObject.put("goods_sn", goods_sn);
     localItemObject.put("promote_start_date", promote_start_date);
     localItemObject.put("goods_number", goods_number);

     for(int i =0; i< rank_prices.size(); i++)
     {
         PRICE itemData =rank_prices.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("rank_prices", itemJSONArray);
     if(null!=img)
     {
       localItemObject.put("img", img.toJson());
     }
     localItemObject.put("brand_id", brand_id);

     for(int i =0; i< pictures.size(); i++)
     {
         PHOTO itemData =pictures.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("pictures", itemJSONArray);
     localItemObject.put("goods_name", goods_name);

     for(int i =0; i< properties.size(); i++)
     {
         PROPERTY itemData =properties.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("properties", itemJSONArray);
     localItemObject.put("goods_weight", goods_weight);
     localItemObject.put("promote_price", promote_price);
     localItemObject.put("formated_promote_price", formated_promote_price);
     localItemObject.put("integral", integral);
     localItemObject.put("id", id);
     localItemObject.put("cat_id", cat_id);
     localItemObject.put("is_shipping", is_shipping);
     localItemObject.put("shop_price", shop_price);
     localItemObject.put("market_price", market_price);
     localItemObject.put("collected",collected);

     for(int i =0; i< specification.size(); i++)
     {
         SPECIFICATION itemData =specification.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("specification", itemJSONArray);
     return localItemObject;
 }

}
