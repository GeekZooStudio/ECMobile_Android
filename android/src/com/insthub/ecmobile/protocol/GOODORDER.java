
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "GOODORDER")
public class GOODORDER  extends Model
{

     @Column(name = "order_time")
     public String order_time;

     @Column(name = "total_fee")
     public String total_fee;

     public ArrayList<ORDER_GOODS_LIST>   goods_list = new ArrayList<ORDER_GOODS_LIST>();

     @Column(name = "formated_integral_money")
     public String formated_integral_money;

     @Column(name = "formated_bonus")
     public String formated_bonus;

     @Column(name = "order_sn")
     public String order_sn;

     @Column(name = "order_id")
     public String order_id;

     @Column(name = "formated_shipping_fee")
     public String formated_shipping_fee;

    public ORDER_INFO order_info;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.order_time = jsonObject.optString("order_time");

     this.total_fee = jsonObject.optString("total_fee");

     subItemArray = jsonObject.optJSONArray("goods_list");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              ORDER_GOODS_LIST subItem = new ORDER_GOODS_LIST();
              subItem.fromJson(subItemObject);
              this.goods_list.add(subItem);
         }
     }


     this.formated_integral_money = jsonObject.optString("formated_integral_money");

     this.formated_bonus = jsonObject.optString("formated_bonus");

     this.order_sn = jsonObject.optString("order_sn");

     this.order_id = jsonObject.optString("order_id");

     this.formated_shipping_fee = jsonObject.optString("formated_shipping_fee");
     ORDER_INFO orderInfo=new ORDER_INFO();
     orderInfo.fromJson(jsonObject.optJSONObject("order_info"));
     this.order_info=orderInfo;
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("order_time", order_time);
     localItemObject.put("total_fee", total_fee);

     for(int i =0; i< goods_list.size(); i++)
     {
         ORDER_GOODS_LIST itemData =goods_list.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("goods_list", itemJSONArray);
     localItemObject.put("formated_integral_money", formated_integral_money);
     localItemObject.put("formated_bonus", formated_bonus);
     localItemObject.put("order_sn", order_sn);
     localItemObject.put("order_id", order_id);
     localItemObject.put("formated_shipping_fee", formated_shipping_fee);
     if(null != order_info)
     {
         localItemObject.put("order_info", order_info.toJson());
     }
     return localItemObject;
 }

}
