package com.insthub.ecmobile.model;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import android.app.ProgressDialog;
import android.content.Context;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
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
                    // TODO Auto-generated catch block
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
        pd.setMessage("请稍后...");
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
                    // TODO Auto-generated catch block
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
        pd.setMessage("请稍后...");
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
                    categoryArrayList.clear();
                    if(responseStatus.succeed == 1)
                    {
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        cb.url(url).type(JSONObject.class);
        aq.ajax(cb);
    }


}
