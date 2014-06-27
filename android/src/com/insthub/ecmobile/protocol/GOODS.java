
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
     public String  click_count;

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
     public int  promote_price;
     
     @Column(name = "formated_promote_price")
     public String formated_promote_price;

     @Column(name = "integral")
     public int integral;

     @Column(name = "GOODS_id")
     public String id;

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

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.promote_end_date = jsonObject.optString("promote_end_date");

     this.click_count = jsonObject.optString("click_count");

     this.goods_sn = jsonObject.optString("goods_sn");

     this.promote_start_date = jsonObject.optString("promote_start_date");

     this.goods_number = jsonObject.optString("goods_number");

     subItemArray = jsonObject.optJSONArray("rank_prices");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              PRICE subItem = new PRICE();
              subItem.fromJson(subItemObject);
              this.rank_prices.add(subItem);
         }
     }
     PHOTO photo=new PHOTO();
     photo.fromJson(jsonObject.optJSONObject("img"));
     this.img = photo;

     this.brand_id = jsonObject.optString("brand_id");

     subItemArray = jsonObject.optJSONArray("pictures");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              PHOTO subItem = new PHOTO();
              subItem.fromJson(subItemObject);
              this.pictures.add(subItem);
         }
     }


     this.goods_name = jsonObject.optString("goods_name");

     subItemArray = jsonObject.optJSONArray("properties");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              PROPERTY subItem = new PROPERTY();
              subItem.fromJson(subItemObject);
              this.properties.add(subItem);
         }
     }


     this.goods_weight = jsonObject.optString("goods_weight");

     this.promote_price = jsonObject.optInt("promote_price");

     this.formated_promote_price = jsonObject.optString("formated_promote_price");

     this.integral = jsonObject.optInt("integral");

     this.id = jsonObject.optString("id");

     this.cat_id = jsonObject.optString("cat_id");

     this.is_shipping = jsonObject.optString("is_shipping");

     this.shop_price = jsonObject.optString("shop_price");

     this.market_price = jsonObject.optString("market_price");

     this.collected=jsonObject.optInt("collected");

     subItemArray = jsonObject.optJSONArray("specification");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              SPECIFICATION subItem = new SPECIFICATION();
              subItem.fromJson(subItemObject);
              this.specification.add(subItem);
         }
     }

     return ;
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
