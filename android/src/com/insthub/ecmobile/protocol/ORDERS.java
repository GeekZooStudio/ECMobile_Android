
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ORDERS")
public class ORDERS  extends Model
{

     @Column(name = "sign_building")
     public String sign_building;

     @Column(name = "formated_total_fee")
     public String formated_total_fee;

     @Column(name = "tel")
     public String tel;

     @Column(name = "pay_fee")
     public int pay_fee;

     @Column(name = "formated_money_paid")
     public String formated_money_paid;

     @Column(name = "card_id")
     public int card_id;

     @Column(name = "pack_fee")
     public int pack_fee;

     @Column(name = "city")
     public int city;

     @Column(name = "integral")
     public int integral;

     @Column(name = "extension_id")
     public int extension_id;

     @Column(name = "formated_discount")
     public String formated_discount;

     @Column(name = "total_fee")
     public int total_fee;

     @Column(name = "province")
     public int province;

     @Column(name = "money_paid")
     public int money_paid;

     @Column(name = "inv_content")
     public String inv_content;

     @Column(name = "pay_id")
     public int pay_id;

     @Column(name = "formated_pay_fee")
     public String formated_pay_fee;

     @Column(name = "integral_money")
     public int integral_money;

     @Column(name = "parent_id")
     public int parent_id;

     @Column(name = "allow_update_address")
     public int allow_update_address;

     @Column(name = "pay_online")
     public String pay_online;

     @Column(name = "formated_integral_money")
     public String formated_integral_money;

     @Column(name = "pay_note")
     public String pay_note;

     @Column(name = "pay_time")
     public String pay_time;

     @Column(name = "formated_surplus")
     public String formated_surplus;

     @Column(name = "shipping_fee")
     public int shipping_fee;

     @Column(name = "order_status")
     public String order_status;

     @Column(name = "card_name")
     public String card_name;

     @Column(name = "discount")
     public int discount;

     @Column(name = "country")
     public int country;

     @Column(name = "user_name")
     public String user_name;

     @Column(name = "exist_real_goods")
     public int exist_real_goods;

     @Column(name = "tax")
     public int tax;

     @Column(name = "email")
     public String email;

     @Column(name = "order_sn")
     public String order_sn;

     @Column(name = "bonus")
     public int bonus;

     @Column(name = "referer")
     public String referer;

     @Column(name = "invoice_no")
     public String invoice_no;

     @Column(name = "how_oos_name")
     public String how_oos_name;

     @Column(name = "confirm_time")
     public String confirm_time;

     @Column(name = "mobile")
     public String mobile;

     @Column(name = "inv_type")
     public String inv_type;

     @Column(name = "inv_payee")
     public String inv_payee;

     @Column(name = "postscript")
     public String postscript;

     @Column(name = "consignee")
     public String consignee;

     @Column(name = "insure_fee")
     public int insure_fee;

     @Column(name = "card_message")
     public String card_message;

     @Column(name = "how_surplus")
     public String how_surplus;

     @Column(name = "card_fee")
     public int card_fee;

     @Column(name = "formated_pack_fee")
     public String formated_pack_fee;

     @Column(name = "pay_status")
     public String pay_status;

     @Column(name = "goods_amount")
     public int goods_amount;

     @Column(name = "ORDERS_id")
     public int id;

     @Column(name = "agency_id")
     public int agency_id;

     @Column(name = "to_buyer")
     public String to_buyer;

     @Column(name = "order_amount")
     public int order_amount;

     @Column(name = "formated_insure_fee")
     public String formated_insure_fee;

     @Column(name = "extension_code")
     public String extension_code;

     @Column(name = "shipping_status")
     public String shipping_status;

     @Column(name = "user_id")
     public int user_id;

     @Column(name = "formated_shipping_fee")
     public String formated_shipping_fee;

     @Column(name = "district")
     public int district;

     @Column(name = "surplus")
     public int surplus;

     @Column(name = "log_id")
     public int log_id;

     @Column(name = "best_time")
     public String best_time;

     @Column(name = "how_oos")
     public String how_oos;

     @Column(name = "pack_name")
     public String pack_name;

     @Column(name = "zipcode")
     public String zipcode;

     @Column(name = "formated_card_fee")
     public String formated_card_fee;

     @Column(name = "pack_id")
     public int pack_id;

     @Column(name = "formated_tax")
     public String formated_tax;

     @Column(name = "bonus_id")
     public int bonus_id;

     @Column(name = "formated_add_time")
     public String formated_add_time;

     @Column(name = "from_ad")
     public int from_ad;

     @Column(name = "shipping_id")
     public int shipping_id;

     @Column(name = "is_separate")
     public int is_separate;

     @Column(name = "address")
     public String address;

     @Column(name = "shipping_time")
     public String shipping_time;

     @Column(name = "formated_order_amount")
     public String formated_order_amount;

     @Column(name = "formated_goods_amount")
     public String formated_goods_amount;

     @Column(name = "how_surplus_name")
     public String how_surplus_name;

     @Column(name = "pay_desc")
     public String pay_desc;

     @Column(name = "formated_bonus")
     public String formated_bonus;

     @Column(name = "shipping_name")
     public String shipping_name;

     @Column(name = "add_time")
     public int add_time;

     @Column(name = "pay_name")
     public String pay_name;

 public static ORDERS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     ORDERS   localItem = new ORDERS();

     JSONArray subItemArray;

     localItem.sign_building = jsonObject.optString("sign_building");

     localItem.formated_total_fee = jsonObject.optString("formated_total_fee");

     localItem.tel = jsonObject.optString("tel");

     localItem.pay_fee = jsonObject.optInt("pay_fee");

     localItem.formated_money_paid = jsonObject.optString("formated_money_paid");

     localItem.card_id = jsonObject.optInt("card_id");

     localItem.pack_fee = jsonObject.optInt("pack_fee");

     localItem.city = jsonObject.optInt("city");

     localItem.integral = jsonObject.optInt("integral");

     localItem.extension_id = jsonObject.optInt("extension_id");

     localItem.formated_discount = jsonObject.optString("formated_discount");

     localItem.total_fee = jsonObject.optInt("total_fee");

     localItem.province = jsonObject.optInt("province");

     localItem.money_paid = jsonObject.optInt("money_paid");

     localItem.inv_content = jsonObject.optString("inv_content");

     localItem.pay_id = jsonObject.optInt("pay_id");

     localItem.formated_pay_fee = jsonObject.optString("formated_pay_fee");

     localItem.integral_money = jsonObject.optInt("integral_money");

     localItem.parent_id = jsonObject.optInt("parent_id");

     localItem.allow_update_address = jsonObject.optInt("allow_update_address");

     localItem.pay_online = jsonObject.optString("pay_online");

     localItem.formated_integral_money = jsonObject.optString("formated_integral_money");

     localItem.pay_note = jsonObject.optString("pay_note");

     localItem.pay_time = jsonObject.optString("pay_time");

     localItem.formated_surplus = jsonObject.optString("formated_surplus");

     localItem.shipping_fee = jsonObject.optInt("shipping_fee");

     localItem.order_status = jsonObject.optString("order_status");

     localItem.card_name = jsonObject.optString("card_name");

     localItem.discount = jsonObject.optInt("discount");

     localItem.country = jsonObject.optInt("country");

     localItem.user_name = jsonObject.optString("user_name");

     localItem.exist_real_goods = jsonObject.optInt("exist_real_goods");

     localItem.tax = jsonObject.optInt("tax");

     localItem.email = jsonObject.optString("email");

     localItem.order_sn = jsonObject.optString("order_sn");

     localItem.bonus = jsonObject.optInt("bonus");

     localItem.referer = jsonObject.optString("referer");

     localItem.invoice_no = jsonObject.optString("invoice_no");

     localItem.how_oos_name = jsonObject.optString("how_oos_name");

     localItem.confirm_time = jsonObject.optString("confirm_time");

     localItem.mobile = jsonObject.optString("mobile");

     localItem.inv_type = jsonObject.optString("inv_type");

     localItem.inv_payee = jsonObject.optString("inv_payee");

     localItem.postscript = jsonObject.optString("postscript");

     localItem.consignee = jsonObject.optString("consignee");

     localItem.insure_fee = jsonObject.optInt("insure_fee");

     localItem.card_message = jsonObject.optString("card_message");

     localItem.how_surplus = jsonObject.optString("how_surplus");

     localItem.card_fee = jsonObject.optInt("card_fee");

     localItem.formated_pack_fee = jsonObject.optString("formated_pack_fee");

     localItem.pay_status = jsonObject.optString("pay_status");

     localItem.goods_amount = jsonObject.optInt("goods_amount");

     localItem.id = jsonObject.optInt("id");

     localItem.agency_id = jsonObject.optInt("agency_id");

     localItem.to_buyer = jsonObject.optString("to_buyer");

     localItem.order_amount = jsonObject.optInt("order_amount");

     localItem.formated_insure_fee = jsonObject.optString("formated_insure_fee");

     localItem.extension_code = jsonObject.optString("extension_code");

     localItem.shipping_status = jsonObject.optString("shipping_status");

     localItem.user_id = jsonObject.optInt("user_id");

     localItem.formated_shipping_fee = jsonObject.optString("formated_shipping_fee");

     localItem.district = jsonObject.optInt("district");

     localItem.surplus = jsonObject.optInt("surplus");

     localItem.log_id = jsonObject.optInt("log_id");

     localItem.best_time = jsonObject.optString("best_time");

     localItem.how_oos = jsonObject.optString("how_oos");

     localItem.pack_name = jsonObject.optString("pack_name");

     localItem.zipcode = jsonObject.optString("zipcode");

     localItem.formated_card_fee = jsonObject.optString("formated_card_fee");

     localItem.pack_id = jsonObject.optInt("pack_id");

     localItem.formated_tax = jsonObject.optString("formated_tax");

     localItem.bonus_id = jsonObject.optInt("bonus_id");

     localItem.formated_add_time = jsonObject.optString("formated_add_time");

     localItem.from_ad = jsonObject.optInt("from_ad");

     localItem.shipping_id = jsonObject.optInt("shipping_id");

     localItem.is_separate = jsonObject.optInt("is_separate");

     localItem.address = jsonObject.optString("address");

     localItem.shipping_time = jsonObject.optString("shipping_time");

     localItem.formated_order_amount = jsonObject.optString("formated_order_amount");

     localItem.formated_goods_amount = jsonObject.optString("formated_goods_amount");

     localItem.how_surplus_name = jsonObject.optString("how_surplus_name");

     localItem.pay_desc = jsonObject.optString("pay_desc");

     localItem.formated_bonus = jsonObject.optString("formated_bonus");

     localItem.shipping_name = jsonObject.optString("shipping_name");

     localItem.add_time = jsonObject.optInt("add_time");

     localItem.pay_name = jsonObject.optString("pay_name");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("sign_building", sign_building);
     localItemObject.put("formated_total_fee", formated_total_fee);
     localItemObject.put("tel", tel);
     localItemObject.put("pay_fee", pay_fee);
     localItemObject.put("formated_money_paid", formated_money_paid);
     localItemObject.put("card_id", card_id);
     localItemObject.put("pack_fee", pack_fee);
     localItemObject.put("city", city);
     localItemObject.put("integral", integral);
     localItemObject.put("extension_id", extension_id);
     localItemObject.put("formated_discount", formated_discount);
     localItemObject.put("total_fee", total_fee);
     localItemObject.put("province", province);
     localItemObject.put("money_paid", money_paid);
     localItemObject.put("inv_content", inv_content);
     localItemObject.put("pay_id", pay_id);
     localItemObject.put("formated_pay_fee", formated_pay_fee);
     localItemObject.put("integral_money", integral_money);
     localItemObject.put("parent_id", parent_id);
     localItemObject.put("allow_update_address", allow_update_address);
     localItemObject.put("pay_online", pay_online);
     localItemObject.put("formated_integral_money", formated_integral_money);
     localItemObject.put("pay_note", pay_note);
     localItemObject.put("pay_time", pay_time);
     localItemObject.put("formated_surplus", formated_surplus);
     localItemObject.put("shipping_fee", shipping_fee);
     localItemObject.put("order_status", order_status);
     localItemObject.put("card_name", card_name);
     localItemObject.put("discount", discount);
     localItemObject.put("country", country);
     localItemObject.put("user_name", user_name);
     localItemObject.put("exist_real_goods", exist_real_goods);
     localItemObject.put("tax", tax);
     localItemObject.put("email", email);
     localItemObject.put("order_sn", order_sn);
     localItemObject.put("bonus", bonus);
     localItemObject.put("referer", referer);
     localItemObject.put("invoice_no", invoice_no);
     localItemObject.put("how_oos_name", how_oos_name);
     localItemObject.put("confirm_time", confirm_time);
     localItemObject.put("mobile", mobile);
     localItemObject.put("inv_type", inv_type);
     localItemObject.put("inv_payee", inv_payee);
     localItemObject.put("postscript", postscript);
     localItemObject.put("consignee", consignee);
     localItemObject.put("insure_fee", insure_fee);
     localItemObject.put("card_message", card_message);
     localItemObject.put("how_surplus", how_surplus);
     localItemObject.put("card_fee", card_fee);
     localItemObject.put("formated_pack_fee", formated_pack_fee);
     localItemObject.put("pay_status", pay_status);
     localItemObject.put("goods_amount", goods_amount);
     localItemObject.put("id", id);
     localItemObject.put("agency_id", agency_id);
     localItemObject.put("to_buyer", to_buyer);
     localItemObject.put("order_amount", order_amount);
     localItemObject.put("formated_insure_fee", formated_insure_fee);
     localItemObject.put("extension_code", extension_code);
     localItemObject.put("shipping_status", shipping_status);
     localItemObject.put("user_id", user_id);
     localItemObject.put("formated_shipping_fee", formated_shipping_fee);
     localItemObject.put("district", district);
     localItemObject.put("surplus", surplus);
     localItemObject.put("log_id", log_id);
     localItemObject.put("best_time", best_time);
     localItemObject.put("how_oos", how_oos);
     localItemObject.put("pack_name", pack_name);
     localItemObject.put("zipcode", zipcode);
     localItemObject.put("formated_card_fee", formated_card_fee);
     localItemObject.put("pack_id", pack_id);
     localItemObject.put("formated_tax", formated_tax);
     localItemObject.put("bonus_id", bonus_id);
     localItemObject.put("formated_add_time", formated_add_time);
     localItemObject.put("from_ad", from_ad);
     localItemObject.put("shipping_id", shipping_id);
     localItemObject.put("is_separate", is_separate);
     localItemObject.put("address", address);
     localItemObject.put("shipping_time", shipping_time);
     localItemObject.put("formated_order_amount", formated_order_amount);
     localItemObject.put("formated_goods_amount", formated_goods_amount);
     localItemObject.put("how_surplus_name", how_surplus_name);
     localItemObject.put("pay_desc", pay_desc);
     localItemObject.put("formated_bonus", formated_bonus);
     localItemObject.put("shipping_name", shipping_name);
     localItemObject.put("add_time", add_time);
     localItemObject.put("pay_name", pay_name);
     return localItemObject;
 }

}
