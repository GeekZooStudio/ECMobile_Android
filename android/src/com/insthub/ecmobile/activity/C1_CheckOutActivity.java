package com.insthub.ecmobile.activity;
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.ecmobile.ECMobileAppConst;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.protocol.*;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.ShoppingCartModel;

import java.util.ArrayList;

public class C1_CheckOutActivity extends BaseActivity implements OnClickListener, BusinessResponse {

    private TextView title;
    private ImageView back;

    private LinearLayout user;
    private TextView name;
    private TextView phoneNum;
    private TextView address;

    private LinearLayout pay;
    private TextView pay_type;
    private LinearLayout dis;
    private TextView dis_type;
    private LinearLayout invoice;
    private TextView invoice_message;

    private LinearLayout goods;
    private TextView goods_num;
    private LinearLayout redPaper;
    private TextView redPaper_name;
    private LinearLayout score;
    private TextView score_num;

    private LinearLayout balance_layout;
    private View balance_view;

    private LinearLayout body;
    private TextView fees;
    private TextView bonus_text;
    private TextView coupon;
    private TextView totalPriceTextView;
    private TextView text_balance_redPaper;
    private TextView text_balance_score;
    private ImageView arrow_balance_score;
    private ImageView arrow_balance_redpocket;
    private FrameLayout submit;

    private ShoppingCartModel shoppingCartModel;
    private float totalGoodsPrice;  //总价格

    private String paymentJSONString;

    private PAYMENT payment;
    private SHIPPING shipping;
    private BONUS selectedBONUS;

    private String scoreNum = null; //兑换的积分数
    private String scoreChangedMoney = null; //积分兑换的钱
    private String scoreChangedMoneyFormated = null; //积分兑换的钱

    private int inv_type = -1; //发票类型
    private int inv_content = -1; //发票内容
    private String inv_payee = null; //发票抬头

    private MyDialog mDialog;
    private OrderModel orderModel;
    private String UPPay_mMode = "00";//银联环境设置
    private ORDER_INFO order_info;
    private final static int REQUEST_ADDRESS_LIST = 1;
    private final static int REQUEST_PAYMENT = 2;
    private final static int REQUEST_Distribution = 3;
    private final static int REQUEST_BONUS = 4;
    private final static int REQUEST_INVOICE = 5;
    private final static int REQUEST_RedEnvelope = 6;
    private final static int REQUEST_ALIPAY = 7;
    private final static int REQUEST_Pay_Web = 8;
    private final static int REQUEST_UPPay = 10;

    int min_score = 0;
    int bonus = 0;
    private IWXAPI mWeixinAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c1_check_out);

        mWeixinAPI = WXAPIFactory.createWXAPI(this, EcmobileManager.getWeixinAppId(this));
        // 将该app注册到微信
        mWeixinAPI.registerApp(EcmobileManager.getWeixinAppId(this));

        title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String set = resource.getString(R.string.shopcarfooter_settleaccounts);
        title.setText(set);

        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        user = (LinearLayout) findViewById(R.id.balance_user);
        name = (TextView) findViewById(R.id.balance_name);
        phoneNum = (TextView) findViewById(R.id.balance_phoneNum);
        address = (TextView) findViewById(R.id.balance_address);

        pay = (LinearLayout) findViewById(R.id.balance_pay);
        pay_type = (TextView) findViewById(R.id.balance_pay_type);
        dis = (LinearLayout) findViewById(R.id.balance_dis);
        dis_type = (TextView) findViewById(R.id.balance_dis_type);
        invoice = (LinearLayout) findViewById(R.id.balance_invoice);
        invoice_message = (TextView) findViewById(R.id.balance_invoice_message);

        goods = (LinearLayout) findViewById(R.id.balance_goods);
        goods_num = (TextView) findViewById(R.id.balance_goods_num);
        redPaper = (LinearLayout) findViewById(R.id.balance_redPaper);
        redPaper_name = (TextView) findViewById(R.id.balance_redPaper_name);
        score = (LinearLayout) findViewById(R.id.balance_score);
        score_num = (TextView) findViewById(R.id.balance_score_num);


        balance_layout = (LinearLayout) findViewById(R.id.balance_layout);
        balance_view = (View) findViewById(R.id.balance_view);

        fees = (TextView) findViewById(R.id.balance_fees);
        bonus_text = (TextView) findViewById(R.id.balance_bonus);
        coupon = (TextView) findViewById(R.id.balance_coupon);
        totalPriceTextView = (TextView) findViewById(R.id.balance_total);
        submit = (FrameLayout) findViewById(R.id.balance_submit);
        body = (LinearLayout) findViewById(R.id.balance_body);
        text_balance_redPaper = (TextView) findViewById(R.id.text_balance_redPaper);
        text_balance_score = (TextView) findViewById(R.id.text_balance_score);
        arrow_balance_redpocket = (ImageView) findViewById(R.id.arrow_balance_redpocket);
        arrow_balance_score = (ImageView) findViewById(R.id.arrow_balance_score);

        user.setOnClickListener(this);
        pay.setOnClickListener(this);
        dis.setOnClickListener(this);
        invoice.setOnClickListener(this);
        goods.setOnClickListener(this);
        redPaper.setOnClickListener(this);
        score.setOnClickListener(this);
        submit.setOnClickListener(this);


        if (null == shoppingCartModel) {
            shoppingCartModel = new ShoppingCartModel(this);
            shoppingCartModel.addResponseListener(this);
            shoppingCartModel.checkOrder();
        } else {
            setInfo();
        }

        orderModel = new OrderModel(this);
        orderModel.addResponseListener(this);

        EventBus.getDefault().register(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.balance_user:
                intent = new Intent(this, F0_AddressListActivity.class);
                intent.putExtra("flag", 1);
                startActivityForResult(intent, REQUEST_ADDRESS_LIST);
                break;
            case R.id.balance_pay:
                intent = new Intent(this, C2_PaymentActivity.class);
                intent.putExtra("payment", paymentJSONString);
                startActivityForResult(intent, REQUEST_PAYMENT);
                break;
            case R.id.balance_dis:
                intent = new Intent(this, C3_DistributionActivity.class);
                intent.putExtra("payment", paymentJSONString);
                startActivityForResult(intent, REQUEST_Distribution);
                break;
            case R.id.balance_invoice:
                intent = new Intent(this, C4_InvoiceActivity.class);
                intent.putExtra("payment", paymentJSONString);
                intent.putExtra("inv_type", inv_type);
                intent.putExtra("inv_content", inv_content);
                intent.putExtra("inv_payee", inv_payee);

                startActivityForResult(intent, REQUEST_INVOICE);
                break;
            case R.id.balance_goods:

                Resources resource = (Resources) getBaseContext().getResources();
                String list = resource.getString(R.string.balance_list);
                ToastView toast = new ToastView(this, list);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case R.id.balance_redPaper:
                try {
                    JSONObject jo = new JSONObject(shoppingCartModel.orderInfoJsonString);

                    flowcheckOrderResponse response = new flowcheckOrderResponse();

                    response.fromJson(jo);

                    if (response.data.allow_use_bonus == 1) {
                        intent = new Intent(this, C6_RedEnvelopeActivity.class);
                        intent.putExtra("payment", paymentJSONString);
                        startActivityForResult(intent, REQUEST_RedEnvelope);
                    } else {
                        Resources resourc = (Resources) getBaseContext().getResources();
                        String not = resourc.getString(R.string.not_support_a_red_envelope);
                        String log_str = resourc.getString(R.string.crash_log_analysis);
                        ToastView toast2 = new ToastView(C1_CheckOutActivity.this, not);
                        toast2.setGravity(Gravity.CENTER, 0, 0);
                        toast2.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.balance_score:
                intent = new Intent(this, C5_BonusActivity.class);
                intent.putExtra("payment", paymentJSONString);
                if (!"".equals(scoreNum)) {
                    intent.putExtra("scoreNum", scoreNum);
                }
                startActivityForResult(intent, REQUEST_BONUS);
                break;
            case R.id.balance_submit:
                Resources resourc = (Resources) getBaseContext().getResources();
                if (null == payment) {
                    ToastView toast1 = new ToastView(C1_CheckOutActivity.this, resourc.getString(R.string.warn_no_pay));
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                    return;
                }

                if (null == shipping) {
                    ToastView toast1 = new ToastView(C1_CheckOutActivity.this, resourc.getString(R.string.warn_no_shipping));
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                    return;
                }


                if (checkCashOnDeliverOk(payment, shipping)) {
                    if (null != selectedBONUS) {
                        shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, selectedBONUS.bonus_id, scoreNum, inv_type + "", inv_payee, inv_content + "");
                    } else {
                        shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, null, scoreNum, inv_type + "", inv_payee, inv_content + "");
                    }

                } else {
                    ToastView toast1 = new ToastView(C1_CheckOutActivity.this, "该配送方式不支持货到付款");
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }
                break;
        }

    }

    public boolean checkCashOnDeliverOk(PAYMENT payment, SHIPPING shipping) {
        if (null != payment && null != shipping) {
            if (payment.is_cod.equals("1") && shipping.support_cod.equals("0")) {
                return false;
            }
        }
        return true;
    }

    public void setInfo() {

        totalGoodsPrice = 0;

        paymentJSONString = shoppingCartModel.orderInfoJsonString;

        name.setText(shoppingCartModel.address.consignee);
        phoneNum.setText(shoppingCartModel.address.tel);

        StringBuffer sbf = new StringBuffer();
        sbf.append("  " + shoppingCartModel.address.province_name + " ");
        sbf.append(shoppingCartModel.address.city_name + " ");
        sbf.append(shoppingCartModel.address.district_name + " ");
        sbf.append(shoppingCartModel.address.address);
        address.setText(sbf.toString());

        body.removeAllViews(); //清除以前添加的子view

        for (int i = 0; i < shoppingCartModel.balance_goods_list.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.c1_check_out_body_item, null);
            TextView goods_name = (TextView) view.findViewById(R.id.body_goods_name);
            TextView goods_num = (TextView) view.findViewById(R.id.body_goods_num);
            TextView goods_total = (TextView) view.findViewById(R.id.body_goods_total);
            goods_name.setText(shoppingCartModel.balance_goods_list.get(i).goods_name);
            goods_num.setText("X " + shoppingCartModel.balance_goods_list.get(i).goods_number);
            goods_total.setText("￥" + shoppingCartModel.balance_goods_list.get(i).subtotal);
            body.addView(view);
            totalGoodsPrice += Float.valueOf(shoppingCartModel.balance_goods_list.get(i).subtotal);
        }

        totalPriceTextView.setText("￥" + totalGoodsPrice);

//		try {
//			JSONObject jo = new JSONObject(shoppingCartModel.orderInfoJsonString);
//            flowcheckOrderResponse response = new flowcheckOrderResponse();
//            response.fromJson(jo);
//			int bonus =response.data.allow_use_bonus;
//			ArrayList<BONUS> bonuses =response.data.bonus;
//			if(bonus==1 )
//            {
//				redPaper.setEnabled(true); 
//			}
//            else
//            {
//				redPaper.setEnabled(false);
//                text_balance_redPaper.setTextColor(Color.parseColor("#9B9B9B"));
//                arrow_balance_redpocket.setVisibility(View.INVISIBLE);
//			}
//		} catch (JSONException e) {			
//			e.printStackTrace();
//		}

//		try{
//			JSONObject jo = new JSONObject(paymentJSONString);
//            flowcheckOrderResponse response = new flowcheckOrderResponse();
//            response.fromJson(jo);
//			String your_score = response.data.your_integral;
//			String order_max_score = response.data.order_max_integral+"";
//			int min_score = Math.min(Integer.valueOf(your_score), Integer.valueOf(order_max_score));
//			if(min_score == 0)
//            {
//				score.setEnabled(false);
//                text_balance_score.setTextColor(Color.parseColor("#9B9B9B"));
//                arrow_balance_score.setVisibility(View.INVISIBLE);
//			}
//            else
//            {
//				score.setEnabled(true);
//			}
//		
//		} catch (JSONException e) {			
//			e.printStackTrace();
//		}

        try {

            JSONObject jo = new JSONObject(shoppingCartModel.orderInfoJsonString);
            flowcheckOrderResponse response = new flowcheckOrderResponse();
            response.fromJson(jo);
            bonus = response.data.allow_use_bonus;
            System.out.println("bonus::" + bonus);
            if ("".equals(bonus)) {
                redPaper.setVisibility(View.GONE);
                balance_view.setVisibility(View.GONE);
            } else {
                if (bonus == 1) {
//				if (bonus.equals("1") && bonusArray != null) {
                    //redPaper.setEnabled(true);
                    balance_layout.setVisibility(View.VISIBLE);
                    redPaper.setVisibility(View.VISIBLE);
                } else {
                    redPaper.setVisibility(View.GONE);
                    balance_view.setVisibility(View.GONE);
//					redPaper.setEnabled(false);
//					redPaper.setBackgroundResource(R.drawable.cell_bg_header_small);
                }
            }


//			JSONObject jo = new JSONObject(
//					shoppingCartModel.orderInfoJsonString);
//			
//			if(!"".equals(jo.optString("allow_use_bonus"))){
//				bonus = jo.optString("allow_use_bonus");
//			}
//			JSONArray bonusArray = jo.optJSONArray("bonus");
//			System.out.println("bonus::"+bonus);
//			if ("".equals(bonus)) {
//				redPaper.setVisibility(View.GONE);
//				balance_view.setVisibility(View.GONE);
//			} else {
//				if (bonus.equals("1")) {
////				if (bonus.equals("1") && bonusArray != null) {
//					//redPaper.setEnabled(true);
//					balance_layout.setVisibility(View.VISIBLE);
//					redPaper.setVisibility(View.VISIBLE);
//				} else {
//					redPaper.setVisibility(View.GONE);
//					balance_view.setVisibility(View.GONE);
////					redPaper.setEnabled(false);
////					redPaper.setBackgroundResource(R.drawable.cell_bg_header_small);
//				}
//			}

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jo = new JSONObject(paymentJSONString);
            //String your_score = jo.get("your_integral").toString();
            //String order_max_score = jo.get("order_max_integral").toString();


            flowcheckOrderResponse response = new flowcheckOrderResponse();
            response.fromJson(jo);
            String your_score = response.data.your_integral;
            String order_max_score = response.data.order_max_integral + "";
            min_score = Math.min(Integer.valueOf(your_score), Integer.valueOf(order_max_score));

//				min_score = Math.min(Integer.valueOf(your_score),
//						Integer.valueOf(order_max_score));
            System.out.println("min_score::" + min_score);
            if (min_score == 0) {
                score.setVisibility(View.GONE);
                balance_view.setVisibility(View.GONE);
//					score.setEnabled(false);
//					score.setBackgroundResource(R.drawable.cell_bg_footer_small);
            } else {
                balance_layout.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);
//					score.setEnabled(true);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bonus == 0 && min_score == 0) {
            balance_layout.setVisibility(View.GONE);
            balance_view.setVisibility(View.GONE);
        }

        if (bonus != 0 && min_score != 0) {
            balance_layout.setVisibility(View.VISIBLE);
            balance_view.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if (url.endsWith(ApiInterface.FLOW_CHECKORDER)) {
            STATUS res_status = new STATUS();
            res_status.fromJson(jo.optJSONObject("status"));
            if (res_status.succeed == 1) {
                setInfo();
            } else if (res_status.error_code == 10001) {
                Intent intent = new Intent(this, F1_NewAddressActivity.class);
                intent.putExtra("balance", 1);
                startActivityForResult(intent, REQUEST_ADDRESS_LIST);
            }

        } else if (url.endsWith(ApiInterface.FLOW_DONE)) {
            JSONObject json = jo.getJSONObject("data");
            JSONObject orderObject = json.optJSONObject("order_info");
            order_info = new ORDER_INFO();
            order_info.fromJson(orderObject);

            Resources resource = (Resources) getBaseContext().getResources();
            String suc = resource.getString(R.string.successful_operation);
            String pay = resource.getString(R.string.pay_or_not);
            final String per = resource.getString(R.string.personal_center);

            if (payment.is_cod.equals("1")) {
                ToastView toast1 = new ToastView(C1_CheckOutActivity.this, getString(R.string.check_orders));
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                finish();
            } else {
                mDialog = new MyDialog(this, suc, pay);
                mDialog.show();
                mDialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                            if (0 == order_info.pay_code.compareTo("alipay")) {
                                if (EcmobileManager.getAlipayCallback(getApplicationContext()) != null
                                        && EcmobileManager.getAlipayParterId(getApplicationContext()) != null
                                        && EcmobileManager.getAlipaySellerId(getApplicationContext()) != null
                                        && EcmobileManager.getRsaAlipayPublic(getApplicationContext()) != null
                                        && EcmobileManager.getRsaPrivate(getApplicationContext()) != null) {
                                    showAlipayDialog();
                                }

                            } else if (0 == order_info.pay_code.compareTo("upop")) {
                                orderModel.orderPay(order_info.order_id);
                            } else if (0 == order_info.pay_code.compareTo("tenpay")) {
                                orderModel.orderPay(order_info.order_id);
                            }else if (0 == order_info.pay_code.compareTo("wxpay")){
                                /**微信支付*/
                                shoppingCartModel.wxpayWXBeforePay(order_info.order_id);
                            }
                            else {
                                orderModel.orderPay(order_info.order_id);
                            }
                        }

                });
                mDialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        ToastView toast = new ToastView(C1_CheckOutActivity.this, per);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                    }
                });
            }
        } else if (url.endsWith(ApiInterface.ORDER_PAY)) {
            String pay_wap = orderModel.pay_wap;
            String pay_online = orderModel.pay_online;
            String upop_tn = orderModel.upop_tn;

            if (upop_tn != null && !"".equals(upop_tn)) {
                //银联sdk支付
                UPPayAssistEx.startPayByJAR(C1_CheckOutActivity.this, PayActivity.class, null, null,
                        upop_tn, UPPay_mMode);
            } else if (pay_wap != null && !"".equals(pay_wap)) {
                //wap支付
                Intent intent = new Intent(this, PayWebActivity.class);
                intent.putExtra(PayWebActivity.PAY_URL, pay_wap);
                startActivityForResult(intent, REQUEST_Pay_Web);
            } else if (pay_online != null && !"".equals(pay_online)) {
                //其他方式
                Intent intent = new Intent(this, OtherPayWebActivity.class);
                intent.putExtra("html", pay_online);
                startActivity(intent);
                finish();
            }
        } else if (url.endsWith(ECMobileAppConst.WEIXIN_PAY_REQUEST_URL)) {
            wxbeforepayResponse response = new wxbeforepayResponse();
            response.fromJson(jo);

            PayReq req = new PayReq();
            req.appId = EcmobileManager.getWeixinAppId(this);
            req.partnerId = EcmobileManager.getWeixinAppPartnerId(this);
            req.prepayId = response.prepayid;
            req.nonceStr = response.noncestr;
            req.timeStamp = response.timestamp;
            req.packageValue = response.wx_package;//"Sign=" + packageValue;
            req.sign = response.sign;

            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

            mWeixinAPI.sendReq(req);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADDRESS_LIST) {
            if (data != null) {
                shoppingCartModel.checkOrder();
            }
        } else if (requestCode == REQUEST_PAYMENT) {
            if (data != null) {
                String paymentString = data.getStringExtra("payment");
                try {
                    JSONObject paymentJSONObject = new JSONObject(paymentString);
                    payment = new PAYMENT();
                    payment.fromJson(paymentJSONObject);
                    pay_type.setText(payment.pay_name);
                } catch (JSONException e) {

                }

            }
        } else if (requestCode == REQUEST_Distribution) {
            if (data != null) {
                String shippingString = data.getStringExtra("shipping");
                try {
                    JSONObject shippingJSONObject = new JSONObject(shippingString);
                    shipping = new SHIPPING();
                    shipping.fromJson(shippingJSONObject);
                    dis_type.setText(shipping.shipping_name);
                    fees.setText(shipping.format_shipping_fee);
                    refreshTotalPrice();
                } catch (JSONException e) {

                }
            }
        } else if (requestCode == REQUEST_BONUS) {
            if (data != null) {
                scoreNum = data.getStringExtra("input");
                Resources resource = (Resources) getBaseContext().getResources();
                String use = resource.getString(R.string.use);
                String inte = resource.getString(R.string.score);
                score_num.setText(use + scoreNum + inte);

                scoreChangedMoney = data.getStringExtra("bonus");
                scoreChangedMoneyFormated = data.getStringExtra("bonus_formated");

                coupon.setText("-" + scoreChangedMoneyFormated);
                refreshTotalPrice();
            }
        } else if (requestCode == REQUEST_INVOICE) {
            if (data != null) {
                inv_type = data.getIntExtra("inv_type", 0);
                inv_content = data.getIntExtra("inv_content", 0);
                inv_payee = data.getStringExtra("inv_payee");
                invoice_message.setText(inv_payee);
            }
        } else if (requestCode == REQUEST_RedEnvelope) {
            if (data != null) {
                String bonusJSONString = data.getStringExtra("bonus");

                if (null != bonusJSONString) {
                    try {
                        JSONObject jsonObject = new JSONObject(bonusJSONString);
                        selectedBONUS = new BONUS();
                        selectedBONUS.fromJson(jsonObject);
                        redPaper_name.setText(selectedBONUS.type_name + "[" + selectedBONUS.bonus_money_formated + "]");
                        bonus_text.setText("-" + selectedBONUS.bonus_money_formated);
                        refreshTotalPrice();
                    } catch (JSONException e) {

                    }
                }

            }
        } else if (requestCode == REQUEST_UPPay) {
            if (data == null) {
                return;
            }
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_ship");
                        startActivity(intent);
                        finish();

                    }
                });
            } else if (str.equalsIgnoreCase("fail") || str.equals("cancel")) {
                ToastView toast = new ToastView(C1_CheckOutActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                intent.putExtra("flag", "await_pay");
                startActivity(intent);
                finish();
            }
        } else if (requestCode == REQUEST_ALIPAY) {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_ship");
                        startActivity(intent);
                        finish();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(C1_CheckOutActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                intent.putExtra("flag", "await_pay");
                startActivity(intent);
                finish();
            }
        } else if (requestCode == REQUEST_Pay_Web) {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_ship");
                        startActivity(intent);
                        finish();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(C1_CheckOutActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                intent.putExtra("flag", "await_pay");
                startActivity(intent);
                finish();
            } else {

                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_finished);
                String exiten = resource.getString(R.string.is_pay_success);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_pay");
                        startActivity(intent);
                        finish();

                    }
                });

            }
        }

    }

    void refreshTotalPrice() {
        float total_price_show = totalGoodsPrice;

        if (null != shipping && 0 != shipping.shipping_fee) {
            total_price_show += Float.valueOf(shipping.shipping_fee);
        }

        if (null != scoreChangedMoney) {
            total_price_show -= Float.valueOf(scoreChangedMoney);
        }

        if (null != selectedBONUS && null != selectedBONUS.type_money) {
            total_price_show -= Float.valueOf(selectedBONUS.type_money);
        }
        totalPriceTextView.setText("￥" + total_price_show);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        shoppingCartModel.removeResponseListener(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageStart("BalancePage");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this), "");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageEnd("BalancePage");
            MobclickAgent.onPause(this);
        }
    }

    private void showAlipayDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alipay_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout alipayLayout = (LinearLayout) view.findViewById(R.id.alipay);
        LinearLayout alipayWapLayout = (LinearLayout) view.findViewById(R.id.alipay_wap);

        alipayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(C1_CheckOutActivity.this, AlixPayActivity.class);
                intent.putExtra(AlixPayActivity.ORDER_INFO, order_info);
                startActivityForResult(intent, REQUEST_ALIPAY);
            }
        });

        alipayWapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                orderModel.orderPay(order_info.order_id);
            }
        });
    }

    public void onEvent(Object event){
        Message message = (Message) event;
        if(message.what == ShareConst.WEIXIN_PAY){
            Resources resource = getResources();
            String exit = resource.getString(R.string.pay_success);
            String exiten = resource.getString(R.string.continue_shopping_or_not);
            final MyDialog mDialog = new MyDialog(C1_CheckOutActivity.this, exit, exiten);
            mDialog.show();
            mDialog.positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    Intent it = new Intent(C1_CheckOutActivity.this, EcmobileMainActivity.class);
                    startActivity(it);
                    finish();

                }
            });
            mDialog.negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    Intent intent = new Intent(C1_CheckOutActivity.this, E4_HistoryActivity.class);
                    intent.putExtra("flag", "await_ship");
                    startActivity(intent);
                    finish();

                }
            });
        }
    }
}
