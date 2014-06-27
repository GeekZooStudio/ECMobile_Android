
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ADDRESS")
public class ADDRESS  extends Model
{

     @Column(name = "default_address")
     public int default_address;

     @Column(name = "sign_building")
     public String sign_building;

     @Column(name = "city_name")
     public String city_name;

     @Column(name = "consignee")
     public String consignee;

     @Column(name = "tel")
     public String tel;

     @Column(name = "zipcode")
     public String zipcode;

     @Column(name = "country_name")
     public String country_name;

     @Column(name = "country")
     public String country;

     @Column(name = "city")
     public String city;

     @Column(name = "ADDRESS_id",unique = true)
     public int id;

     @Column(name = "province_name")
     public String province_name;

     @Column(name = "district_name")
     public String district_name;

     @Column(name = "email")
     public String email;

     @Column(name = "address")
     public String address;

     @Column(name = "province")
     public String province;

     @Column(name = "district")
     public String district;

     @Column(name = "best_time")
     public String best_time;

     @Column(name = "mobile")
     public String mobile;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.default_address = jsonObject.optInt("default_address");

     this.sign_building = jsonObject.optString("sign_building");

     this.city_name = jsonObject.optString("city_name");

     this.consignee = jsonObject.optString("consignee");

     this.tel = jsonObject.optString("tel");

     this.zipcode = jsonObject.optString("zipcode");

     this.country_name = jsonObject.optString("country_name");

     this.country = jsonObject.optString("country");

     this.city = jsonObject.optString("city");

     this.id = jsonObject.optInt("id");

     this.province_name = jsonObject.optString("province_name");

     this.district_name = jsonObject.optString("district_name");

     this.email = jsonObject.optString("email");

     this.address = jsonObject.optString("address");

     this.province = jsonObject.optString("province");

     this.district = jsonObject.optString("district");

     this.best_time = jsonObject.optString("best_time");

     this.mobile = jsonObject.optString("mobile");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("default_address", default_address);
     localItemObject.put("sign_building", sign_building);
     localItemObject.put("city_name", city_name);
     localItemObject.put("consignee", consignee);
     localItemObject.put("tel", tel);
     localItemObject.put("zipcode", zipcode);
     localItemObject.put("country_name", country_name);
     localItemObject.put("country", country);
     localItemObject.put("city", city);
     localItemObject.put("id", id);
     localItemObject.put("province_name", province_name);
     localItemObject.put("district_name", district_name);
     localItemObject.put("email", email);
     localItemObject.put("address", address);
     localItemObject.put("province", province);
     localItemObject.put("district", district);
     localItemObject.put("best_time", best_time);
     localItemObject.put("mobile", mobile);
     return localItemObject;
 }

}
