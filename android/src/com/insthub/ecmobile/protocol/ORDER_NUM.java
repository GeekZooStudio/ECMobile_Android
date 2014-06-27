
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ORDER_NUM")
public class ORDER_NUM  extends Model
{

    @Column(name = "shipped")
    public String   shipped;

    @Column(name = "await_ship")
    public String   await_ship;

    @Column(name = "await_pay")
    public String   await_pay;

    @Column(name = "finished")
    public String   finished;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.shipped = jsonObject.optString("shipped");

        this.await_ship = jsonObject.optString("await_ship");

        this.await_pay = jsonObject.optString("await_pay");

        this.finished = jsonObject.optString("finished");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("shipped", shipped);
        localItemObject.put("await_ship", await_ship);
        localItemObject.put("await_pay", await_pay);
        localItemObject.put("finished", finished);
        return localItemObject;
    }

}
