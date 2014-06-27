package com.insthub.ecmobile.model;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.app.ProgressDialog;
import android.content.Context;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvanceSearchModel extends BaseModel{

    public ArrayList<BRAND> brandList = new ArrayList<BRAND>();
    public ArrayList<PRICE_RANGE> priceRangeArrayList = new ArrayList<PRICE_RANGE>();
    public ArrayList<CATEGORY> categoryArrayList = new ArrayList<CATEGORY>();

    public AdvanceSearchModel(Context context)
    {
        super(context);
    }

    public void getAllBrand(String category_id)
    {
        String url = ProtocolConst.BRAND;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AdvanceSearchModel.this.callback(url, jo, status);
                try {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

                    if(responseStatus.succeed == 1) {
                        JSONArray dataJsonArray = jo.optJSONArray("data");
                        if (null != dataJsonArray && dataJsonArray.length() > 0) {

                            brandList.clear();
                            BRAND allBrand = new BRAND();
                            allBrand.brand_id = 0;
                            allBrand.brand_name = mContext.getResources().getString(R.string.all_brand);
                            brandList.add(allBrand);
                            for (int i = 0; i < dataJsonArray.length(); i++)
                            {
                                JSONObject brandJsonObject = dataJsonArray.getJSONObject(i);
                                BRAND brandItem = BRAND.fromJson(brandJsonObject);
                                brandList.add(brandItem);
                            }
                        }
                        AdvanceSearchModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {
                     
                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();

        JSONObject requestJsonObject = new JSONObject();

        Map<String, String> params = new HashMap<String, String>();
        try
        {
            requestJsonObject.put("session",session.toJson());
            if (null != category_id)
            {
                requestJsonObject.put("category_id",category_id);
            }

        } catch (JSONException e) {
            // TODO: handle exception
        }

        params.put("json",requestJsonObject.toString());

        cb.url(url).type(JSONObject.class).params(params);
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd).ajax(cb);
    }

    public void getPriceRange(int categoryId)
    {
        String url = ProtocolConst.PRICE_RANGE;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AdvanceSearchModel.this.callback(url, jo, status);
                try {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

                    if(responseStatus.succeed == 1) {
                        JSONArray dataJsonArray = jo.optJSONArray("data");
                        if (null != dataJsonArray && dataJsonArray.length() > 0) {
                            priceRangeArrayList.clear();

                            PRICE_RANGE allPrice = new PRICE_RANGE();
                            allPrice.price_min = -1;
                            allPrice.price_max = -1;
                            priceRangeArrayList.add(allPrice);
                            for (int i = 0; i < dataJsonArray.length(); i++)
                            {
                                JSONObject priceJsonObject = dataJsonArray.getJSONObject(i);
                                PRICE_RANGE brandItem = PRICE_RANGE.fromJson(priceJsonObject);
                                priceRangeArrayList.add(brandItem);
                            }
                        }
                        AdvanceSearchModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {
                     
                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();

        JSONObject requestJsonObject = new JSONObject();

        Map<String, String> params = new HashMap<String, String>();
        try
        {
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("category_id",categoryId);
        } catch (JSONException e) {
            // TODO: handle exception
        }



        params.put("json",requestJsonObject.toString());

        cb.url(url).type(JSONObject.class).params(params);
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd).ajax(cb);
        priceRangeArrayList.clear();
    }

    public void getCategory()
    {
        String url = ProtocolConst.CATEGORY;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AdvanceSearchModel.this.callback(url, jo, status);
                try {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    if(responseStatus.succeed == 1)
                    {
                        categoryArrayList.clear();
                        CATEGORY allCategory = new CATEGORY();
                        allCategory.id = 0;
                        allCategory.name = mContext.getString(R.string.all_category);

                        categoryArrayList.add(allCategory);

                        JSONArray categoryJSONArray = jo.optJSONArray("data");

                        if (null != categoryJSONArray && categoryJSONArray.length() > 0)
                        {
                            for (int i = 0; i < categoryJSONArray.length(); i++)
                            {
                                JSONObject categoryObject = categoryJSONArray.getJSONObject(i);
                                CATEGORY category = CATEGORY.fromJson(categoryObject);
                                categoryArrayList.add(category);
                            }
                        }

                        AdvanceSearchModel.this.OnMessageResponse(url, jo, status);

                    }

                } catch (JSONException e) {
                     
                    e.printStackTrace();
                }
            }

        };

        cb.url(url).type(JSONObject.class);
        aq.ajax(cb);
    }


}
