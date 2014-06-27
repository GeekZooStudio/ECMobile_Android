
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "HOME_DATA")
public class HOME_DATA  extends Model
{

     public ArrayList<PLAYER>   player = new ArrayList<PLAYER>();

     public ArrayList<SIMPLEGOODS>   promote_goods = new ArrayList<SIMPLEGOODS>();

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          subItemArray = jsonObject.optJSONArray("player");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  PLAYER subItem = new PLAYER();
                  subItem.fromJson(subItemObject);
                  this.player.add(subItem);
               }
           }


          subItemArray = jsonObject.optJSONArray("promote_goods");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  SIMPLEGOODS subItem = new SIMPLEGOODS();
                  subItem.fromJson(subItemObject);
                  this.promote_goods.add(subItem);
               }
           }

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          for(int i =0; i< player.size(); i++)
          {
              PLAYER itemData =player.get(i);
              JSONObject itemJSONObject = itemData.toJson();
              itemJSONArray.put(itemJSONObject);
          }
          localItemObject.put("player", itemJSONArray);

          for(int i =0; i< promote_goods.size(); i++)
          {
              SIMPLEGOODS itemData =promote_goods.get(i);
              JSONObject itemJSONObject = itemData.toJson();
              itemJSONArray.put(itemJSONObject);
          }
          localItemObject.put("promote_goods", itemJSONArray);
          return localItemObject;
     }

}
