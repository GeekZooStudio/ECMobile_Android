
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "usersignupRequest")
public class usersignupRequest  extends Model
{

     public ArrayList<FIELD>   field = new ArrayList<FIELD>();

     @Column(name = "email")
     public String   email;

     @Column(name = "name")
     public String   name;

     @Column(name = "password")
     public String   password;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          subItemArray = jsonObject.optJSONArray("field");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  FIELD subItem = new FIELD();
                  subItem.fromJson(subItemObject);
                  this.field.add(subItem);
               }
           }


          this.email = jsonObject.optString("email");

          this.name = jsonObject.optString("name");

          this.password = jsonObject.optString("password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          for(int i =0; i< field.size(); i++)
          {
              FIELD itemData =field.get(i);
              JSONObject itemJSONObject = itemData.toJson();
              itemJSONArray.put(itemJSONObject);
          }
          localItemObject.put("field", itemJSONArray);
          localItemObject.put("email", email);
          localItemObject.put("name", name);
          localItemObject.put("password", password);
          return localItemObject;
     }

}
