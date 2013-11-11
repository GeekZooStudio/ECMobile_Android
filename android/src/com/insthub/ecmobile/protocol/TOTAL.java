
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "TOTAL")
public class TOTAL  extends Model
{

     @Column(name = "goods_price")
     public String goods_price;

     @Column(name = "virtual_goods_count")
     public int virtual_goods_count;

     @Column(name = "market_price")
     public String market_price;

     @Column(name = "real_goods_count")
     public int real_goods_count;

     @Column(name = "save_rate")
     public String save_rate;

     @Column(name = "saving")
     public String saving;

     @Column(name = "goods_amount")
     public String goods_amount;

 public static TOTAL fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     TOTAL   localItem = new TOTAL();

     JSONArray subItemArray;

     localItem.goods_price = jsonObject.optString("goods_price");

     localItem.virtual_goods_count = jsonObject.optInt("virtual_goods_count");

     localItem.market_price = jsonObject.optString("market_price");

     localItem.real_goods_count = jsonObject.optInt("real_goods_count");

     localItem.save_rate = jsonObject.optString("save_rate");

     localItem.saving = jsonObject.optString("saving");

     localItem.goods_amount = jsonObject.optString("goods_amount");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("goods_price", goods_price);
     localItemObject.put("virtual_goods_count", virtual_goods_count);
     localItemObject.put("market_price", market_price);
     localItemObject.put("real_goods_count", real_goods_count);
     localItemObject.put("save_rate", save_rate);
     localItemObject.put("saving", saving);
     localItemObject.put("goods_amount", goods_amount);
     return localItemObject;
 }

}
