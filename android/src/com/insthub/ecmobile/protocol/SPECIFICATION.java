
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SPECIFICATION")
public class SPECIFICATION  extends Model
{
    public static String  SINGLE_SELECT = "1"; //单选属性
    public static String  MULTIPLE_SELECT = "2";//复选属性

     @Column(name = "name")
     public String name;

     public ArrayList<SPECIFICATION_VALUE>   value = new ArrayList<SPECIFICATION_VALUE>();

     @Column(name = "attr_type")
     public String attr_type;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.name = jsonObject.optString("name");

     subItemArray = jsonObject.optJSONArray("value");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
              JSONObject subItemObject = subItemArray.getJSONObject(i);
              SPECIFICATION_VALUE subItem = new SPECIFICATION_VALUE();
              subItem.fromJson(subItemObject);
              subItem.specification = this;
              this.value.add(subItem);
         }
     }


     this.attr_type = jsonObject.optString("attr_type");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("name", name);

     for(int i =0; i< value.size(); i++)
     {
         SPECIFICATION_VALUE itemData =value.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("value", itemJSONArray);
     localItemObject.put("attr_type", attr_type);
     return localItemObject;
 }

}
