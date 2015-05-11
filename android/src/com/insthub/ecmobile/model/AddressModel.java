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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class AddressModel extends BaseModel {

    public ArrayList<ADDRESS> addressList = new ArrayList<ADDRESS>();
    public ArrayList<REGIONS> regionsList0 = new ArrayList<REGIONS>();
    public ADDRESS address;

    public AddressModel(Context context) {
        super(context);

    }

    // 获取地址列表
    public void getAddressList() {

        final addresslistRequest request = new addresslistRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    AddressModel.this.callback(url, jo, status);
                    if (jo != null) {
                        addresslistResponse response = new addresslistResponse();
                        response.fromJson(jo);
                        if (response.status.succeed == 1) {
                           addressList.clear();
                           ArrayList<ADDRESS> data = response.data;
                            if (null != data && data.size() > 0) {
                                addressList.addAll(data);
                            }
                        }
                       AddressModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };
        request.session=SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 添加地址
    public void addAddress(String consignee, String tel, String email, String mobile, String zipcode, String address, String country, String province, String city, String district) {
       addressaddRequest request=new addressaddRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                AddressModel.this.callback(url, jo, status);
                try {
                    addressaddResponse response = new addressaddResponse();
                    response.fromJson(jo);
                    if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                    } else {
                        AddressModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        SESSION session = SESSION.getInstance();
        ADDRESS add = new ADDRESS();
        add.consignee = consignee;
        add.tel = tel;
        add.email = email;
        add.mobile = mobile;
        add.zipcode = zipcode;
        add.address = address;
        add.country = country;
        add.province = province;
        add.city = city;
        add.district = district;

       request.session=session;
       request.address=add;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }


        cb.url(ApiInterface.ADDRESS_ADD).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 获取地区城市
    public void region(int parent_id) {
        regionRequest request=new regionRequest();
        request.parent_id=parent_id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    regionResponse response = new regionResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            REGION_DATA data = response.data;
                            ArrayList<REGIONS> regionses = data.regions;
                            regionsList0.clear();
                            if (null != regionses && regionses.size() > 0) {
                                regionsList0.clear();
                                for (int i = 0; i < regionses.size(); i++) {
                                    REGIONS regions = regionses.get(i);
                                    regionsList0.add(regions);
                                }
                            }
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.REGION).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }


    // 获取地址详细信息
    public void getAddressInfo(String address_id) {
        addressinfoRequest request=new addressinfoRequest();
        request.address_id=address_id;
        request.session=SESSION.getInstance();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    addressinfoResponse response = new addressinfoResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            address=response.data;
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_INFO).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 设置默认地址
    public void addressDefault(String address_id) {

        addresssetDefaultRequest request=new addresssetDefaultRequest();
        request.address_id=address_id;
        request.session=SESSION.getInstance();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    addresssetDefaultResponse response = new addresssetDefaultResponse();
                    response.fromJson(jo);
                    if(jo!=null){
                        if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_SETDEFAULT).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 删除地址
    public void addressDelete(String address_id) {
        addressdeleteRequest request=new addressdeleteRequest();
        request.address_id=address_id;
        request.session=SESSION.getInstance();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    addressdeleteResponse response = new addressdeleteResponse();
                    response.fromJson(jo);
                    if(jo!=null) {
                        if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_DELETE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 修改地址
    public void addressUpdate(String address_id, String consignee, String tel, String email, String mobile, String zipcode, String address, String country, String province, String city, String district) {
       addressupdateRequest request=new addressupdateRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                AddressModel.this.callback(url, jo, status);
                try {
                    addressupdateResponse response = new addressupdateResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        ADDRESS add = new ADDRESS();
        add.consignee = consignee;
        add.tel = tel;
        add.email = email;
        add.mobile = mobile;
        add.zipcode = zipcode;
        add.address = address;
        add.country = country;
        add.province = province;
        add.city = city;
        add.district = district;
        request.address=add;
        request.session=SESSION.getInstance();
        request.address_id=address_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.ADDRESS_UPDATE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

}
