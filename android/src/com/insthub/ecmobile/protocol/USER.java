
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "USER")
public class USER  extends Model
{

     @Column(name = "collection_num")
     public String collection_num;

     @Column(name = "USER_id",unique = true)
     public String id;

     @Column(name = "order_num")
     public ORDER_NUM   order_num;

     @Column(name = "rank_name")
     public String rank_name;

     @Column(name = "rank_level")
     public int rank_level;

     @Column(name = "name")
     public String name;

 public static USER fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     USER   localItem = new USER();

     JSONArray subItemArray;

     localItem.collection_num = jsonObject.optString("collection_num");

     localItem.id = jsonObject.optString("id");
     localItem.order_num = ORDER_NUM.fromJson(jsonObject.optJSONObject("order_num"));

     localItem.name = jsonObject.optString("name");
     localItem.rank_name = jsonObject.optString("rank_name");
     localItem.rank_level = jsonObject.optInt("rank_level");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("collection_num", collection_num);
     localItemObject.put("id", id);
     if(null!=order_num)
     {
       localItemObject.put("order_num", order_num.toJson());
     }
     localItemObject.put("name", name);
     localItemObject.put("rank_name",rank_name);
     localItemObject.put("rank_level",rank_level);
     return localItemObject;
 }

}
