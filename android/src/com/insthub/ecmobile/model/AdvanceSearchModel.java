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
import com.insthub.BeeFramework.view.MyProgressDialog;
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
        brandRequest request=new brandRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AdvanceSearchModel.this.callback(url, jo, status);
                try {
                    brandResponse response = new brandResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                             ArrayList<BRAND>  data = response.data;
                            if (null != data && data.size() > 0) {
                                brandList.clear();
                                BRAND allBrand = new BRAND();
                                allBrand.brand_id = "0";
                                allBrand.brand_name = mContext.getResources().getString(R.string.all_brand);
                                brandList.add(allBrand);
                                brandList.addAll(data);

                            }
                            AdvanceSearchModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        request.category_id=category_id;
        request.session=SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            params.put("json",request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.BRAND).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void getPriceRange(int categoryId)
    {
        final price_rangeRequest request=new price_rangeRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AdvanceSearchModel.this.callback(url, jo, status);
                try {
                    price_rangeResponse response = new price_rangeResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<PRICE_RANGE> price_ranges = response.data;
                            if (null != price_ranges && price_ranges.size() > 0) {
                                priceRangeArrayList.clear();
                                PRICE_RANGE allPrice = new PRICE_RANGE();
                                allPrice.price_min = -1;
                                allPrice.price_max = -1;
                                priceRangeArrayList.add(allPrice);
                                priceRangeArrayList.addAll(price_ranges);

                            }
                            AdvanceSearchModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        request.category_id=categoryId;
        request.session=SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            params.put("json",request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.PRICE_RANGE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void getCategory()
    {

        categoryRequest request = new categoryRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AdvanceSearchModel.this.callback(url, jo, status);
                try {
                    categoryResponse response = new categoryResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            categoryArrayList.clear();
                            CATEGORY allCategory = new CATEGORY();
                            allCategory.id = "0";
                            allCategory.name = mContext.getString(R.string.all_category);
                            categoryArrayList.add(allCategory);
                            ArrayList<CATEGORY> data = response.data;
                            if (null != data && data.size() > 0) {
                                    categoryArrayList.addAll(data);

                            }
                            AdvanceSearchModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            params.put("json",request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.CATEGORY).type(JSONObject.class);
        aq.ajax(cb);
    }
}
