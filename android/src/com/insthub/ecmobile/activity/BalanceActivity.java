package com.insthub.ecmobile.activity;

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

import android.app.Activity;
import android.content.res.Resources;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.protocol.*;
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

public class BalanceActivity extends Activity implements OnClickListener, BusinessResponse {
	
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
	private LinearLayout integral;
	private TextView integral_num;
	
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

    private String integralNum = null; //兑换的积分数
    private String integralChangedMoney = null; //积分兑换的钱
    private String integralChangedMoneyFormated = null; //积分兑换的钱

	private String inv_type = null; //发票类型
	private String inv_content = null; //发票内容
	private String inv_payee = null; //发票抬头

    private MyDialog mDialog;
    private OrderModel orderModel;

    ORDER_INFO order_info;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.balance);
		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String set=resource.getString(R.string.shopcarfooter_settleaccounts);
		title.setText(set);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		integral = (LinearLayout) findViewById(R.id.balance_integral);
		integral_num = (TextView) findViewById(R.id.balance_integral_num);
		
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
		integral.setOnClickListener(this);
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
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId()) {
		case R.id.balance_user:
			intent = new Intent(this, AddressManageActivity.class);
			intent.putExtra("flag", 1);
			startActivityForResult(intent, 1);
			break;
		case R.id.balance_pay:
			intent = new Intent(this, PaymentActivity.class);
			intent.putExtra("payment", paymentJSONString);
			startActivityForResult(intent, 2);
			break;
		case R.id.balance_dis:
			intent = new Intent(this, ShippingActivity.class);
			intent.putExtra("payment", paymentJSONString);
			startActivityForResult(intent, 3);
			break;
		case R.id.balance_invoice:
			intent = new Intent(this, InvoiceActivity.class);
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
                    intent = new Intent(this, RedPacketsActivity.class);
                    intent.putExtra("payment", paymentJSONString);
                    startActivityForResult(intent, 6);
                }
                else
                {
                    Resources resourc = (Resources) getBaseContext().getResources();
                    String not=resourc.getString(R.string.not_support_a_red_envelope);
                    String log_str =  resourc.getString(R.string.crash_log_analysis);
                    ToastView toast2 = new ToastView(BalanceActivity.this, not);
                    toast2.setGravity(Gravity.CENTER, 0, 0);
                    toast2.show();
                }
			}
            catch (JSONException e)
            {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.balance_integral:
			intent = new Intent(this, IntegralActivity.class);
			intent.putExtra("payment", paymentJSONString);
			startActivityForResult(intent, 4);
			break;
		case R.id.balance_submit:

            if (null == payment)
            {
                ToastView toast1 = new ToastView(BalanceActivity.this, "请选择支付方式");
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                return;
            }

            if (null == shipping)
            {
                ToastView toast1 = new ToastView(BalanceActivity.this, "请选择配送方式");
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                return;
            }


            if (checkCashOnDeliverOk(payment, shipping))
            {
                if (null != selectedBONUS)
                {
                    shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, selectedBONUS.bonus_id, integralNum, inv_type, inv_payee, inv_content);
                }
                else
                {
                    shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, null, integralNum, inv_type, inv_payee, inv_content);
                }

            }
            else
            {
                ToastView toast1 = new ToastView(BalanceActivity.this, "该配送方式不支持货到付款");
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
		sbf.append(shoppingCartModel.address.province_name+" ");
		sbf.append(shoppingCartModel.address.city_name+" ");
		sbf.append(shoppingCartModel.address.district_name+" ");
		sbf.append(shoppingCartModel.address.address);
		address.setText(sbf.toString());
		
		body.removeAllViews(); //清除以前添加的子view

		for(int i=0;i<shoppingCartModel.balance_goods_list.size();i++)
        {
			View view = LayoutInflater.from(this).inflate(R.layout.body_item, null);
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
			if(bonus.equals("1"))
            {
				redPaper.setEnabled(true); 
			}
            else
            {
				redPaper.setEnabled(false);
				redPaper.setBackgroundResource(R.drawable.body_cont_bgt);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			JSONObject jo = new JSONObject(paymentJSONString);
			String your_integral = jo.get("your_integral").toString();
			String order_max_integral = jo.get("order_max_integral").toString();
			int min_inteagral = Math.min(Integer.valueOf(your_integral), Integer.valueOf(order_max_integral));
			if(min_inteagral == 0)
            {
				integral.setEnabled(false);
				integral.setBackgroundResource(R.drawable.body_cont_bgb);
			}
            else
            {
				integral.setEnabled(true);
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.CHECKORDER))
        {
            STATUS res_status = STATUS.fromJson(jo.optJSONObject("status"));
			if(res_status.succeed == 1)
            {
				setInfo();
			}
            else if(res_status.error_code == 10001)
            {
				Intent intent = new Intent(this, AddAddressActivity.class);
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
                ToastView toast1 = new ToastView(BalanceActivity.this, "订单已生成，请到订单列表中查看");
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
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                        orderModel.orderPay(finalOrder_id);

                    }
                });
                mDialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                        ToastView toast = new ToastView(BalanceActivity.this, per);
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
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            startActivity(intent);
        }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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
				integralNum = data.getStringExtra("input");
                Resources resource = (Resources) getBaseContext().getResources();
                String use=resource.getString(R.string.use);
                String inte=resource.getString(R.string.integral);
				integral_num.setText(use+integralNum+inte);

                integralChangedMoney = data.getStringExtra("bonus");
                integralChangedMoneyFormated = data.getStringExtra("bonus_formated");
				
				coupon.setText("-"+integralChangedMoneyFormated);
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

        if(null!= integralChangedMoney)
        {
            total_price_show -= Float.valueOf(integralChangedMoney);
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
}
