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

import android.content.res.Resources;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.protocol.*;
import com.umeng.analytics.MobclickAgent;

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

public class C1_CheckOutActivity extends AlixPayActivity implements OnClickListener, BusinessResponse {
	
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
	
	private LinearLayout body;
	private TextView fees;
	private TextView bonus_text;
	private TextView coupon;
	private TextView totalPriceTextView;
	private FrameLayout submit;
	
	private  ShoppingCartModel shoppingCartModel;
	private float totalGoodsPrice;  //总价格
	
	private String paymentJSONString;

    private PAYMENT payment;
    private SHIPPING shipping;
    BONUS selectedBONUS;

    private String scoreNum = null; //兑换的积分数
    private String scoreChangedMoney = null; //积分兑换的钱
    private String scoreChangedMoneyFormated = null; //积分兑换的钱

	private String inv_type = null; //发票类型
	private String inv_content = null; //发票内容
	private String inv_payee = null; //发票抬头

    private MyDialog mDialog;
    private OrderModel orderModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c1_check_out);
		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String set=resource.getString(R.string.shopcarfooter_settleaccounts);
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
		
		fees = (TextView) findViewById(R.id.balance_fees);
		bonus_text = (TextView) findViewById(R.id.balance_bonus);
		coupon = (TextView) findViewById(R.id.balance_coupon);
        totalPriceTextView = (TextView) findViewById(R.id.balance_total);
		submit = (FrameLayout) findViewById(R.id.balance_submit);
		body = (LinearLayout) findViewById(R.id.balance_body);
		
		user.setOnClickListener(this);
		pay.setOnClickListener(this);
		dis.setOnClickListener(this);
		invoice.setOnClickListener(this);
		goods.setOnClickListener(this);
		redPaper.setOnClickListener(this);
		score.setOnClickListener(this);
		submit.setOnClickListener(this);
		

        if(null == shoppingCartModel)
        {
            shoppingCartModel = new ShoppingCartModel(this);
            shoppingCartModel.addResponseListener(this);
            shoppingCartModel.checkOrder();
        }
        else
        {
            setInfo();
        }

        orderModel = new OrderModel(this);
        orderModel.addResponseListener(this);

		
	}

	@Override
	public void onClick(View v) {		
		Intent intent;
		switch(v.getId()) {
		case R.id.balance_user:
			intent = new Intent(this, F0_AddressListActivity.class);
			intent.putExtra("flag", 1);
			startActivityForResult(intent, 1);
			break;
		case R.id.balance_pay:
			intent = new Intent(this, C2_PaymentActivity.class);
			intent.putExtra("payment", paymentJSONString);
			startActivityForResult(intent, 2);
			break;
		case R.id.balance_dis:
			intent = new Intent(this, C3_DistributionActivity.class);
			intent.putExtra("payment", paymentJSONString);
			startActivityForResult(intent, 3);
			break;
		case R.id.balance_invoice:
			intent = new Intent(this, C4_InvoiceActivity.class);
			intent.putExtra("payment", paymentJSONString);
			intent.putExtra("inv_type", inv_type);
			intent.putExtra("inv_content", inv_content);
			intent.putExtra("inv_payee", inv_payee);
			
			startActivityForResult(intent, 5);
			break;
		case R.id.balance_goods:

            Resources resource = (Resources) getBaseContext().getResources();
            String list=resource.getString(R.string.balance_list);
			ToastView toast = new ToastView(this, list);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.balance_redPaper:
			try
            {
                JSONObject jo = new JSONObject(shoppingCartModel.orderInfoJsonString);
                String bonus = jo.getString("allow_use_bonus");
                if(bonus.equals("1"))
                {
                    intent = new Intent(this, C6_RedEnvelopeActivity.class);
                    intent.putExtra("payment", paymentJSONString);
                    startActivityForResult(intent, 6);
                }
                else
                {
                    Resources resourc = (Resources) getBaseContext().getResources();
                    String not=resourc.getString(R.string.not_support_a_red_envelope);
                    String log_str =  resourc.getString(R.string.crash_log_analysis);
                    ToastView toast2 = new ToastView(C1_CheckOutActivity.this, not);
                    toast2.setGravity(Gravity.CENTER, 0, 0);
                    toast2.show();
                }
			}
            catch (JSONException e)
            {				
				e.printStackTrace();
			}
			break;
		case R.id.balance_score:
			intent = new Intent(this, C5_BonusActivity.class);
			intent.putExtra("payment", paymentJSONString);
			startActivityForResult(intent, 4);
			break;
		case R.id.balance_submit:
            Resources resourc = (Resources) getBaseContext().getResources();
            if (null == payment)
            {
                ToastView toast1 = new ToastView(C1_CheckOutActivity.this, resourc.getString(R.string.warn_no_pay));
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                return;
            }

            if (null == shipping)
            {
                ToastView toast1 = new ToastView(C1_CheckOutActivity.this, resourc.getString(R.string.warn_no_shipping));
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                return;
            }


            if (checkCashOnDeliverOk(payment, shipping))
            {
                if (null != selectedBONUS)
                {
                    shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, selectedBONUS.bonus_id, scoreNum, inv_type, inv_payee, inv_content);
                }
                else
                {
                    shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, null, scoreNum, inv_type, inv_payee, inv_content);
                }

            }
            else
            {
                ToastView toast1 = new ToastView(C1_CheckOutActivity.this, "该配送方式不支持货到付款");
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }
			break;
		}
		
	}

    public boolean checkCashOnDeliverOk(PAYMENT payment,SHIPPING shipping )
    {
        if (null != payment && null != shipping)
        {
            if (payment.is_cod.equals("1") && shipping.support_cod.equals("0"))
            {
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
		sbf.append("  "+shoppingCartModel.address.province_name+" ");
		sbf.append(shoppingCartModel.address.city_name+" ");
		sbf.append(shoppingCartModel.address.district_name+" ");
		sbf.append(shoppingCartModel.address.address);
		address.setText(sbf.toString());
		
		body.removeAllViews(); //清除以前添加的子view

		for(int i=0;i<shoppingCartModel.balance_goods_list.size();i++)
        {
			View view = LayoutInflater.from(this).inflate(R.layout.c1_check_out_body_item, null);
			TextView goods_name = (TextView) view.findViewById(R.id.body_goods_name);
			TextView goods_num = (TextView) view.findViewById(R.id.body_goods_num);
			TextView goods_total = (TextView) view.findViewById(R.id.body_goods_total);
			goods_name.setText(shoppingCartModel.balance_goods_list.get(i).goods_name);
			goods_num.setText("X "+shoppingCartModel.balance_goods_list.get(i).goods_number);
			goods_total.setText("￥"+shoppingCartModel.balance_goods_list.get(i).subtotal);
			body.addView(view);
            totalGoodsPrice+=Float.valueOf(shoppingCartModel.balance_goods_list.get(i).subtotal);
		}

        totalPriceTextView.setText("￥"+totalGoodsPrice);
		
		try {
			JSONObject jo = new JSONObject(shoppingCartModel.orderInfoJsonString);
			String bonus = jo.getString("allow_use_bonus");
			JSONArray bonusArray = jo.optJSONArray("bonus");
			if(bonus.equals("1") && bonusArray != null)
            {
				redPaper.setEnabled(true); 
			}
            else
            {
				redPaper.setEnabled(false);
				redPaper.setBackgroundResource(R.drawable.cell_bg_header_small);
			}
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		try{
			JSONObject jo = new JSONObject(paymentJSONString);
			String your_score = jo.get("your_integral").toString();
			String order_max_score = jo.get("order_max_integral").toString();
			int min_score = Math.min(Integer.valueOf(your_score), Integer.valueOf(order_max_score));
			if(min_score == 0)
            {
				score.setEnabled(false);
				score.setBackgroundResource(R.drawable.cell_bg_footer_small);
			}
            else
            {
				score.setEnabled(true);
			}
		
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
	}

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        String action = intent.getAction();
    }

        @Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {		
		if(url.endsWith(ProtocolConst.CHECKORDER))
        {
            STATUS res_status = STATUS.fromJson(jo.optJSONObject("status"));
			if(res_status.succeed == 1)
            {
				setInfo();
			}
            else if(res_status.error_code == 10001)
            {
				Intent intent = new Intent(this, F1_NewAddressActivity.class);
				intent.putExtra("balance", 1);
				startActivityForResult(intent, 1);
			}
			
		}
        else if(url.endsWith(ProtocolConst.FLOW_DOWN))
        {
            JSONObject json = jo.getJSONObject("data");
            JSONObject orderObject = json.optJSONObject("order_info");
            order_info = ORDER_INFO.fromJson(orderObject);

            Resources resource = (Resources) getBaseContext().getResources();
            String suc=resource.getString(R.string.successful_operation);
            String pay=resource.getString(R.string.pay_or_not);
            final String per=resource.getString(R.string.personal_center);

            if (payment.is_cod.equals("1"))
            {
                ToastView toast1 = new ToastView(C1_CheckOutActivity.this, getString(R.string.check_orders));
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                finish();
            }
            else
            {
                mDialog = new MyDialog(this, suc, pay);
                mDialog.show();
                final int finalOrder_id = order_info.order_id;

                mDialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {                        
                        mDialog.dismiss();

                        if (EcmobileManager.getAlipayCallback(getApplicationContext()) != null 
                        		&& EcmobileManager.getAlipayParterId(getApplicationContext()) != null 
                        		&& EcmobileManager.getAlipaySellerId(getApplicationContext()) != null
                        		&& EcmobileManager.getRsaAlipayPublic(getApplicationContext()) != null
                        		&& EcmobileManager.getRsaPrivate(getApplicationContext()) != null)
                        {
                            if (0 == order_info.pay_code.compareTo("alipay"))
                            {
                                performPay();
                            }
                            else
                            {
                                orderModel.orderPay(finalOrder_id);
                            }
                        }
                        else
                        {
                        	orderModel.orderPay(finalOrder_id);
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
		}
        else if(url.endsWith(ProtocolConst.ORDER_PAY))
        {
            Intent intent = new Intent(this, PayWebActivity.class);

            String data = null;
            try
            {
                data = jo.getString("data").toString();
                intent.putExtra("html", data);
            } catch (JSONException e) {
                e.printStackTrace(); 
            }
            startActivity(intent);
            finish();
        }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1)
        {
			if (data != null)
            {
				shoppingCartModel.checkOrder();
			}
		}
        else if(requestCode == 2)
        {
			if (data != null)
            {
                String paymentString = data.getStringExtra("payment");
                try
                {
                    JSONObject paymentJSONObject = new JSONObject(paymentString);
                    payment = PAYMENT.fromJson(paymentJSONObject);
                    pay_type.setText(payment.pay_name);
                }
                catch (JSONException e)
                {

                }

			}
		}
        else if(requestCode == 3)
        {
			if (data != null)
            {
                String shippingString = data.getStringExtra("shipping");
                try
                {
                   JSONObject shippingJSONObject = new JSONObject(shippingString);
                    shipping = SHIPPING.fromJson(shippingJSONObject);
                    dis_type.setText(shipping.shipping_name);
                    fees.setText(shipping.format_shipping_fee);
                    refreshTotalPrice();
                }
                catch (JSONException e)
                {

                }
			}
		}
        else if(requestCode == 4)
        {
			if (data != null)
            {
				scoreNum = data.getStringExtra("input");
                Resources resource = (Resources) getBaseContext().getResources();
                String use=resource.getString(R.string.use);
                String inte=resource.getString(R.string.score);
				score_num.setText(use+scoreNum+inte);

                scoreChangedMoney = data.getStringExtra("bonus");
                scoreChangedMoneyFormated = data.getStringExtra("bonus_formated");
				
				coupon.setText("-"+scoreChangedMoneyFormated);
			    refreshTotalPrice();
			}
		}
        else if(requestCode == 5)
        {
			if (data != null)
            {
				inv_type = data.getStringExtra("inv_type");
				inv_content = data.getStringExtra("inv_content");
				inv_payee = data.getStringExtra("inv_payee");
				invoice_message.setText(inv_payee);
			}
		}
        else if(requestCode == 6)
        {
			if (data != null)
            {
				String bonusJSONString  = data.getStringExtra("bonus");

                if (null != bonusJSONString)
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(bonusJSONString);
                        selectedBONUS = BONUS.fromJson(jsonObject);
                        redPaper_name.setText(selectedBONUS.type_name+"["+selectedBONUS.bonus_money_formated+"]");
                        bonus_text.setText("-"+selectedBONUS.bonus_money_formated);
                        refreshTotalPrice();
                    }
                    catch (JSONException e)
                    {

                    }
                }

			}
		}
		
	}

    void refreshTotalPrice()
    {
        float total_price_show = totalGoodsPrice;

        if (null != shipping && null != shipping.shipping_fee)
        {
            total_price_show += Float.valueOf(shipping.shipping_fee);
        }

        if(null!= scoreChangedMoney)
        {
            total_price_show -= Float.valueOf(scoreChangedMoney);
        }

        if (null != selectedBONUS && null != selectedBONUS.type_money)
        {
            total_price_show -= Float.valueOf(selectedBONUS.type_money);
        }
        totalPriceTextView.setText("￥"+total_price_show);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageStart("BalancePage");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this),"");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageEnd("BalancePage");
            MobclickAgent.onPause(this);
        }
    }
}
