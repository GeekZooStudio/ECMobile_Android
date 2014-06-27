
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

 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     JSONArray subItemArray;


     this.goods_price = jsonObject.optString("goods_price");

     this.virtual_goods_count = jsonObject.optInt("virtual_goods_count");

     this.market_price = jsonObject.optString("market_price");

     this.real_goods_count = jsonObject.optInt("real_goods_count");

     this.save_rate = jsonObject.optString("save_rate");

     this.saving = jsonObject.optString("saving");

     this.goods_amount = jsonObject.optString("goods_amount");
     return ;
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
