
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "searchRequest")
public class searchRequest  extends Model
{

     @Column(name = "pagination")
     public PAGINATION   pagination;

     @Column(name = "filter")
     public FILTER   filter;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          PAGINATION  pagination = new PAGINATION();
          pagination.fromJson(jsonObject.optJSONObject("pagination"));
          this.pagination = pagination;
          FILTER  filter = new FILTER();
          filter.fromJson(jsonObject.optJSONObject("filter"));
          this.filter = filter;
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          if(null != pagination)
          {
            localItemObject.put("pagination", pagination.toJson());
          }
          if(null != filter)
          {
            localItemObject.put("filter", filter.toJson());
          }
          return localItemObject;
     }

}
