
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SESSION")
public class SESSION  extends Model
{

     @Column(name = "uid")
     public String uid;

     @Column(name = "sid")
     public String sid;
     
     
     private static SESSION instance;
     public static SESSION getInstance()
     {
         if (instance == null) {
             instance = new SESSION();
         }

         return instance;
     }
     

 public static SESSION fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     SESSION   localItem = SESSION.getInstance();

     JSONArray subItemArray;

     localItem.uid = jsonObject.optString("uid");

     localItem.sid = jsonObject.optString("sid");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("uid", uid);
     localItemObject.put("sid", sid);
     return localItemObject;
 }

}
