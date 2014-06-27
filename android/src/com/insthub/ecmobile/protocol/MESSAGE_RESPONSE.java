
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "MESSAGE_RESPONSE")
public class MESSAGE_RESPONSE  extends Model
{

    @Column(name = "total")
    public int total;

    @Column(name = "succeed")
    public int succeed;

    @Column(name = "error_code")
    public int error_code;

    public ArrayList<MESSAGE>   messages = new ArrayList<MESSAGE>();

    @Column(name = "error_desc")
    public String   error_desc;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.total = jsonObject.optInt("total");

        this.succeed = jsonObject.optInt("succeed");

        this.error_code = jsonObject.optInt("error_code");

        subItemArray = jsonObject.optJSONArray("messages");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                MESSAGE subItem = new MESSAGE();
                subItem.fromJson(subItemObject);
                this.messages.add(subItem);
            }
        }


        this.error_desc = jsonObject.optString("error_desc");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("total", total);
        localItemObject.put("succeed", succeed);
        localItemObject.put("error_code", error_code);

        for(int i =0; i< messages.size(); i++)
        {
            MESSAGE itemData =messages.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("messages", itemJSONArray);
        localItemObject.put("error_desc", error_desc);
        return localItemObject;
    }

}
