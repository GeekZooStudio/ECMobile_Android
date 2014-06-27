
package com.insthub.ecmobile.protocol;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ORDER_INFO")
public class ORDER_INFO  extends Model implements Serializable
{

    @Column(name = "order_amount")
    public String   order_amount;

    @Column(name = "desc")
    public String   desc;

    @Column(name = "pay_code")
    public String   pay_code;

    @Column(name = "subject")
    public String   subject;

    @Column(name = "order_sn")
    public String   order_sn;

    @Column(name = "order_id")
    public int order_id;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.order_amount = jsonObject.optString("order_amount");

        this.desc = jsonObject.optString("desc");

        this.pay_code = jsonObject.optString("pay_code");

        this.subject = jsonObject.optString("subject");

        this.order_sn = jsonObject.optString("order_sn");

        this.order_id = jsonObject.optInt("order_id");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("order_amount", order_amount);
        localItemObject.put("desc", desc);
        localItemObject.put("pay_code", pay_code);
        localItemObject.put("subject", subject);
        localItemObject.put("order_sn", order_sn);
        localItemObject.put("order_id", order_id);
        return localItemObject;
    }

}
