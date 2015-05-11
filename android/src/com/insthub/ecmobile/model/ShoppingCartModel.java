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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.insthub.BeeFramework.model.BeeQuery;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.ECMobileAppConst;
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
import com.insthub.BeeFramework.view.ToastView;

public class ShoppingCartModel extends BaseModel {

    public ArrayList<GOODS_LIST> goods_list = new ArrayList<GOODS_LIST>();
    public TOTAL total;
    public int goods_num;

    // 结算（提交订单前的订单预览）
    public ADDRESS address;
    public ArrayList<GOODS_LIST> balance_goods_list = new ArrayList<GOODS_LIST>();
    public ArrayList<PAYMENT> payment_list = new ArrayList<PAYMENT>();
    public ArrayList<SHIPPING> shipping_list = new ArrayList<SHIPPING>();

    public String orderInfoJsonString;

    private static ShoppingCartModel instance;
    public int order_id;


    private Context mContext;

    public static ShoppingCartModel getInstance() {
        return instance;
    }

    public ShoppingCartModel() {
        super();
    }

    public ShoppingCartModel(Context context) {
        super(context);
        mContext = context;
        instance = this;
    }

    // 获取购物车列表
    public void cartList() {
        cartlistRequest request = new cartlistRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                ShoppingCartModel.this.callback(url, jo, status);
                try {
                    cartlistResponse response = new cartlistResponse();
                    response.fromJson(jo);
                    if (null != jo) {
                        if (response.status.succeed == 1) {
                            CART_LIST_DATA data = response.data;

                            total = data.total;
                            ArrayList<GOODS_LIST> goods_lists = data.goods_list;

                            goods_list.clear();
                            ShoppingCartModel.this.goods_num = 0;
                            if (null != goods_lists && goods_lists.size() > 0) {
                                goods_list.clear();
                                for (int i = 0; i < goods_lists.size(); i++) {
                                    GOODS_LIST goods_list_Item = goods_lists.get(i);
                                    goods_list.add(goods_list_Item);
                                    ShoppingCartModel.this.goods_num += Integer.valueOf(goods_list_Item.goods_number);
                                }
                            }
                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.CART_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 在首页获取购物车列表，存成单件
    public void homeCartList() {
        cartlistRequest request = new cartlistRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                try {
                    cartlistResponse response = new cartlistResponse();
                    response.fromJson(jo);
                    if (null != jo) {
                        if (response.status.succeed == 1) {
                            CART_LIST_DATA data = response.data;

                            total = data.total;
                            ArrayList<GOODS_LIST> goods_lists = data.goods_list;

                            goods_list.clear();
                            ShoppingCartModel.this.goods_num = 0;
                            if (null != goods_lists && goods_lists.size() > 0) {
                                goods_list.clear();
                                for (int i = 0; i < goods_lists.size(); i++) {
                                    GOODS_LIST goods_list_Item = goods_lists.get(i);
                                    goods_list.add(goods_list_Item);
                                    ShoppingCartModel.this.goods_num += Integer.valueOf(goods_list_Item.goods_number);
                                }
                            }
                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.CART_LIST).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }


    public void deleteGoods(int rec_id) {
        cartdeleteRequest request = new cartdeleteRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                ShoppingCartModel.this.callback(url, jo, status);
                try {
                    cartdeleteResponse response = new cartdeleteResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {

                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);

                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        request.session = SESSION.getInstance();
        request.rec_id = rec_id;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.CART_DELETE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void updateGoods(int rec_id, int new_number) {
        cartupdateRequest request = new cartupdateRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                ShoppingCartModel.this.callback(url, jo, status);
                try {
                    cartdeleteResponse response = new cartdeleteResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {

                        }
                        ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();
        request.rec_id = rec_id;
        request.new_number = new_number;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.CART_UPDATE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void checkOrder() {
        flowcheckOrderRequest request = new flowcheckOrderRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                ShoppingCartModel.this.callback(url, jo, status);
                try {
                    flowcheckOrderResponse response = new flowcheckOrderResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {

                            CHECK_ORDER_DATA check_order_data = response.data;
                            address = check_order_data.consignee;
                            ArrayList<GOODS_LIST> goods = check_order_data.goods_list;

                            if (null != goods && goods.size() > 0) {

                                balance_goods_list.clear();
                                balance_goods_list.addAll(goods);

                            }

                            orderInfoJsonString = jo.toString();
                            ArrayList<SHIPPING> shipping_lists = check_order_data.shipping_list;
                            if (null != shipping_lists && shipping_lists.size() > 0) {
                                shipping_list.clear();
                                shipping_list.addAll(shipping_lists);

                            }

                            ArrayList<PAYMENT> payments = check_order_data.payment_list;

                            if (null != payments && payments.size() > 0) {
                                payment_list.clear();
                                ;
                                payment_list.addAll(payments);

                            }

                        }

                        ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.FLOW_CHECKORDER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 订单生成
    public void flowDone(String pay_id, String shipping_id, String bonus, String score, String inv_type, String inv_payee, String inv_content) {
        flowdoneRequest request = new flowdoneRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                ShoppingCartModel.this.callback(url, jo, status);
                try {
                    flowdoneResponse response = new flowdoneResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            FLOW_DONE_DATA data = response.data;
                            order_id = data.order_id;
                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();
        request.pay_id = pay_id;
        request.shipping_id = shipping_id;
        request.bonus = bonus;
        request.integral = score;
        if(!inv_content.equals("-1")) {
            request.inv_content = inv_content;
        }
        if(!inv_type.equals("-1")){
            request.inv_type = inv_type;
        }
        request.inv_payee=inv_payee;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.FLOW_DONE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void score(String score) {
        validateintegralRequest request = new validateintegralRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                ShoppingCartModel.this.callback(url, jo, status);
                try {
                    validateintegralResponse response = new validateintegralResponse();
                    response.fromJson(jo);
                    if (jo != null) {


                        if (response.status.succeed == 1) {
                            VALIDATE_INTEGRAL_DATA data = response.data;
                            String bonus = data.bouns;
                            String bonus_formated = data.bonus_formated;
                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();
        request.integral = score;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.VALIDATE_INTEGRAL).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

    // 验证红包
    public void bonus(String bonus_sn) {
        validatebonusRequest request = new validatebonusRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                //ShoppingCartModel.this.callback(url, jo, status);
                try {
                    validatebonusResponse response = new validatebonusResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            VALIDATE_BONUS_DATA data = response.data;
                            String bonus = data.bouns;
                            String bonus_formated = data.bonus_formated;
                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);

                        }

                        if (response.status.error_code == 101) {
                            //Toast toast = Toast.makeText(mContext, "红包输入错误", 0);
                            ToastView toast = new ToastView(mContext, "红包输入错误");
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();
        request.bonus_sn = bonus_sn;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.VALIDATE_BONUS).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

    /**微信预支付订单*/
    public void wxpayWXBeforePay(int order_id){
        wxbeforepayRequest request = new wxbeforepayRequest();
        request.session = SESSION.getInstance();
        request.order_id = String.valueOf(order_id);

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    if (jo != null) {
                        wxbeforepayResponse response = new wxbeforepayResponse();
                        response.fromJson(jo);
                        if(response.succeed == 1){
                            ShoppingCartModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }
        cb.url(ECMobileAppConst.WEIXIN_PAY_REQUEST_URL).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        ((BeeQuery)aq.progress(pd.mDialog)).ajaxAbsolute(cb);

    }

    /**获取设备ID*/
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

}
