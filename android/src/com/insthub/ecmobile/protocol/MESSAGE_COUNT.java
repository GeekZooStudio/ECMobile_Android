
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "MESSAGE_COUNT")
public class MESSAGE_COUNT  extends Model
{

    @Column(name = "succeed")
    public int succeed;

    @Column(name = "unread")
    public int unread;

    @Column(name = "error_code")
    public int error_code;

    @Column(name = "error_desc")
    public String   error_desc;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.succeed = jsonObject.optInt("succeed");

        this.unread = jsonObject.optInt("unread");

        this.error_code = jsonObject.optInt("error_code");

        this.error_desc = jsonObject.optString("error_desc");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("succeed", succeed);
        localItemObject.put("unread", unread);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        return localItemObject;
    }

}
