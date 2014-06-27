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
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Toast;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoodDetailModel extends BaseModel {
    public ArrayList<PHOTO> photoList = new ArrayList<PHOTO>();
    public String goodId;
    public GOODS goodDetail = new GOODS();

    public GoodDetailModel(Context context) {
        super(context);
    }

    public String goodsWeb;

    public void fetchGoodDetail(int goodId) {
        goodsRequest request = new goodsRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                GoodDetailModel.this.callback(url, jo, status);
                try {
                    goodsResponse response = new goodsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            GOODS goods = response.data;
                            if (null != goods) {
                                GoodDetailModel.this.goodDetail = goods;
                                GoodDetailModel.this.OnMessageResponse(url, jo, status);
                            }

                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }
        };
        request.session = SESSION.getInstance();
        request.goods_id = goodId;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.GOODS).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void cartCreate(int goodId, ArrayList<Integer> specIdList, int goodQuantity) {
        cartcreateRequest request = new cartcreateRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                GoodDetailModel.this.callback(url, jo, status);
                try {
                    categoryResponse response = new categoryResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            GoodDetailModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };


        request.session = SESSION.getInstance();
        request.goods_id = goodId;
        request.number = goodQuantity;
        request.spec = specIdList;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.CART_CREATE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void collectCreate(int goodId) {
        usercollectcreateRequest request = new usercollectcreateRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                GoodDetailModel.this.callback(url, jo, status);
                try {
                    usercollectdeleteResponse response = new usercollectdeleteResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == ErrorCodeConst.ResponseSucceed) {
                            GoodDetailModel.this.OnMessageResponse(url, jo, status);

                        } else if (response.status.error_code == ErrorCodeConst.UnexistInformation) {
                            Resources resource = mContext.getResources();
                            String registration_closed = resource.getString(R.string.unexist_information);
                            ToastView toast = new ToastView(mContext, registration_closed);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }


            }

        };

        request.session = SESSION.getInstance();
        request.goods_id = goodId;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.USER_COLLECT_CREATE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void goodsDesc(int goodId) {
        final goodsdescRequest request = new goodsdescRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                GoodDetailModel.this.callback(url, jo, status);
                try {
                    goodsdescResponse response = new goodsdescResponse();
                    response.fromJson(jo);
                    if (jo != null) {


                        if (response.status.succeed == 1) {
                            goodsWeb = jo.getString("data").toString();
                            GoodDetailModel.this.OnMessageResponse(url, jo, status);


                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        request.goods_id = goodId;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.GOODS_DESC).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

}
