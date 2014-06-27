
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "CHECK_ORDER_DATA")
public class CHECK_ORDER_DATA  extends Model
{

    @Column(name = "allow_use_bonus")
    public int   allow_use_bonus;

    public ArrayList<INV_LIST>   inv_content_list = new ArrayList<INV_LIST>();

    @Column(name = "order_max_integral")
    public int   order_max_integral;

    @Column(name = "allow_use_integral")
    public String   allow_use_integral;

    @Column(name = "consignee")
    public ADDRESS   consignee;

    public ArrayList<PAYMENT>   payment_list = new ArrayList<PAYMENT>();

    public ArrayList<GOODS_LIST>   goods_list = new ArrayList<GOODS_LIST>();

    public ArrayList<BONUS>   bonus = new ArrayList<BONUS>();

    public ArrayList<SHIPPING>   shipping_list = new ArrayList<SHIPPING>();

    @Column(name = "your_integral")
    public String   your_integral;

    public ArrayList<INV_LIST>   inv_type_list = new ArrayList<INV_LIST>();

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.allow_use_bonus = jsonObject.optInt("allow_use_bonus");

        subItemArray = jsonObject.optJSONArray("inv_content_list");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                INV_LIST subItem = new INV_LIST();
                subItem.fromJson(subItemObject);
                this.inv_content_list.add(subItem);
            }
        }


        this.order_max_integral = jsonObject.optInt("order_max_integral");

        this.allow_use_integral = jsonObject.optString("allow_use_integral");
        ADDRESS  consignee = new ADDRESS();
        consignee.fromJson(jsonObject.optJSONObject("consignee"));
        this.consignee = consignee;

        subItemArray = jsonObject.optJSONArray("payment_list");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                PAYMENT subItem = new PAYMENT();
                subItem.fromJson(subItemObject);
                this.payment_list.add(subItem);
            }
        }


        subItemArray = jsonObject.optJSONArray("goods_list");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                GOODS_LIST subItem = new GOODS_LIST();
                subItem.fromJson(subItemObject);
                this.goods_list.add(subItem);
            }
        }


        subItemArray = jsonObject.optJSONArray("bonus");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                BONUS subItem = new BONUS();
                subItem.fromJson(subItemObject);
                this.bonus.add(subItem);
            }
        }


        subItemArray = jsonObject.optJSONArray("shipping_list");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                SHIPPING subItem = new SHIPPING();
                subItem.fromJson(subItemObject);
                this.shipping_list.add(subItem);
            }
        }


        this.your_integral = jsonObject.optString("your_integral");

        subItemArray = jsonObject.optJSONArray("inv_type_list");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                INV_LIST subItem = new INV_LIST();
                subItem.fromJson(subItemObject);
                this.inv_type_list.add(subItem);
            }
        }

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("allow_use_bonus", allow_use_bonus);

        for(int i =0; i< inv_content_list.size(); i++)
        {
            INV_LIST itemData =inv_content_list.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("inv_content_list", itemJSONArray);
        localItemObject.put("order_max_integral", order_max_integral);
        localItemObject.put("allow_use_integral", allow_use_integral);
        if(null != consignee)
        {
            localItemObject.put("consignee", consignee.toJson());
        }

        for(int i =0; i< payment_list.size(); i++)
        {
            PAYMENT itemData =payment_list.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("payment_list", itemJSONArray);

        for(int i =0; i< goods_list.size(); i++)
        {
            GOODS_LIST itemData =goods_list.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("goods_list", itemJSONArray);

        for(int i =0; i< bonus.size(); i++)
        {
            BONUS itemData =bonus.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("bonus", itemJSONArray);

        for(int i =0; i< shipping_list.size(); i++)
        {
            SHIPPING itemData =shipping_list.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("shipping_list", itemJSONArray);
        localItemObject.put("your_integral", your_integral);

        for(int i =0; i< inv_type_list.size(); i++)
        {
            INV_LIST itemData =inv_type_list.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("inv_type_list", itemJSONArray);
        return localItemObject;
    }

}
