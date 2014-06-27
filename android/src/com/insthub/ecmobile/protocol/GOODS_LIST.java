
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "GOODS_LIST")
public class GOODS_LIST  extends Model
{

     @Column(name = "can_handsel")
     public String can_handsel;

     @Column(name = "goods_sn")
     public String goods_sn;

     @Column(name = "formated_subtotal")
     public String formated_subtotal;

     @Column(name = "is_gift")
     public String is_gift;

     @Column(name = "goods_number")
     public String goods_number;

     @Column(name = "is_real")
     public String is_real;

     @Column(name = "img")
     public PHOTO   img;

     @Column(name = "goods_name")
     public String goods_name;

     @Column(name = "pid")
     public String pid;

     @Column(name = "subtotal")
     public String subtotal;

     @Column(name = "is_shipping")
     public String is_shipping;

     @Column(name = "goods_price")
     public String goods_price;

     public ArrayList<GOODS_ATTR>   goods_attr = new ArrayList<GOODS_ATTR>();

     @Column(name = "formated_goods_price")
     public String formated_goods_price;

     @Column(name = "goods_attr_id")
     public String goods_attr_id;

     @Column(name = "market_price")
     public String market_price;

     @Column(name = "rec_type")
     public String rec_type;

     @Column(name = "goods_id")
     public String goods_id;

     @Column(name = "extension_code")
     public String extension_code;

     @Column(name = "formated_market_price")
     public String formated_market_price;

     @Column(name = "rec_id")
     public String rec_id;

     @Column(name = "parent_id")
     public String parent_id;

    @Column(name = "user_id")
    public String user_id;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.can_handsel = jsonObject.optString("can_handsel");

     this.goods_sn = jsonObject.optString("goods_sn");

     this.formated_subtotal = jsonObject.optString("formated_subtotal");

     this.is_gift = jsonObject.optString("is_gift");

     this.goods_number = jsonObject.optString("goods_number");

     this.is_real = jsonObject.optString("is_real");
     PHOTO photo=new PHOTO();
     photo.fromJson(jsonObject.optJSONObject("img"));
     this.img =photo;

     this.goods_name = jsonObject.optString("goods_name");

     this.pid = jsonObject.optString("pid");

     this.subtotal = jsonObject.optString("subtotal");

     this.is_shipping = jsonObject.optString("is_shipping");

     this.goods_price = jsonObject.optString("goods_price");

     subItemArray = jsonObject.optJSONArray("goods_attr");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              GOODS_ATTR subItem = new GOODS_ATTR();
              subItem.fromJson(subItemObject);
              this.goods_attr.add(subItem);
         }
     }


     this.formated_goods_price = jsonObject.optString("formated_goods_price");

     this.goods_attr_id = jsonObject.optString("goods_attr_id");

     this.market_price = jsonObject.optString("market_price");

     this.rec_type = jsonObject.optString("rec_type");

     this.goods_id = jsonObject.optString("goods_id");

     this.extension_code = jsonObject.optString("extension_code");

     this.formated_market_price = jsonObject.optString("formated_market_price");

     this.rec_id = jsonObject.optString("rec_id");

     this.parent_id = jsonObject.optString("parent_id");

     this.user_id = jsonObject.optString("user_id");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("can_handsel", can_handsel);
     localItemObject.put("goods_sn", goods_sn);
     localItemObject.put("formated_subtotal", formated_subtotal);
     localItemObject.put("is_gift", is_gift);
     localItemObject.put("goods_number", goods_number);
     localItemObject.put("is_real", is_real);
     if(null!=img)
     {
       localItemObject.put("img", img.toJson());
     }
     localItemObject.put("goods_name", goods_name);
     localItemObject.put("pid", pid);
     localItemObject.put("subtotal", subtotal);
     localItemObject.put("is_shipping", is_shipping);
     localItemObject.put("goods_price", goods_price);

     for(int i =0; i< goods_attr.size(); i++)
     {
         GOODS_ATTR itemData =goods_attr.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("goods_attr", itemJSONArray);
     localItemObject.put("formated_goods_price", formated_goods_price);
     localItemObject.put("goods_attr_id", goods_attr_id);
     localItemObject.put("market_price", market_price);
     localItemObject.put("rec_type", rec_type);
     localItemObject.put("goods_id", goods_id);
     localItemObject.put("extension_code", extension_code);
     localItemObject.put("formated_market_price", formated_market_price);
     localItemObject.put("rec_id", rec_id);
     localItemObject.put("parent_id", parent_id);
     localItemObject.put("user_id", user_id);
     return localItemObject;
 }

}
