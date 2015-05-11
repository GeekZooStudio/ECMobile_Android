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

import java.util.ArrayList;

import android.app.Dialog;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.*;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.ECMobileAppConst;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.ORDER_INFO;
import com.insthub.ecmobile.protocol.SESSION;
import com.umeng.analytics.MobclickAgent;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.adapter.C0_ShoppingCartAdapter;
import com.insthub.ecmobile.adapter.E4_HistoryAdapter;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class E4_HistoryActivity extends BaseActivity implements BusinessResponse, IXListViewListener {
	
	private String flag;
	private TextView title;
	private ImageView back;
	private XListView xlistView;
	private E4_HistoryAdapter tradeAdapter;
	private View null_paView;
	private OrderModel orderModel;
	public Handler messageHandler;
    private MyDialog mDialog;
    private String UPPay_mMode = "00";
    private ORDER_INFO order_info;
    private final static int REQUEST_ALIPAY = 7;
    private final static int REQUEST_Pay_Web = 8;
    private final static int REQUEST_UPPay  = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
        Resources resource = (Resources) getBaseContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e4_history);
		
		Intent intent = getIntent();
		flag = intent.getStringExtra("flag");

		title = (TextView) findViewById(R.id.top_view_text);

		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
				
		null_paView = findViewById(R.id.null_pager);
		xlistView = (XListView) findViewById(R.id.trade_list);
		xlistView.setPullLoadEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		
		orderModel = new OrderModel(this);
		orderModel.addResponseListener(this);

        String awa=resource.getString(R.string.await_pay);
        String ship=resource.getString(R.string.await_ship);
        String shipped=resource.getString(R.string.shipped);
        String fin=resource.getString(R.string.profile_history);

        if(flag.equals("await_pay")) {
            title.setText(awa);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("await_pay");
        } else if(flag.equals("await_ship")) {
            title.setText(ship);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("await_ship");

        } else if(flag.equals("shipped")) {
            title.setText(shipped);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("shipped");

        } else if(flag.equals("finished")) {
            title.setText(fin);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("finished");
        }
        messageHandler = new Handler(){

            public void handleMessage(final Message msg) {

                if (msg.what == 1)
                {
                    GOODORDER order = (GOODORDER)msg.obj;
                    order_info = order.order_info;

                    if (EcmobileManager.getAlipayCallback(getApplicationContext()) != null
                            && EcmobileManager.getAlipayParterId(getApplicationContext()) != null
                            && EcmobileManager.getAlipaySellerId(getApplicationContext()) != null
                            && EcmobileManager.getRsaAlipayPublic(getApplicationContext()) != null
                            && EcmobileManager.getRsaPrivate(getApplicationContext()) != null)
                    {
                        if (0 == order_info.pay_code.compareTo("alipay"))
                        {
                            showAlipayDialog();
                        }else if(0==order_info.pay_code.compareTo("upop")){
                            orderModel.orderPay(order_info.order_id);
                        }else if(0==order_info.pay_code.compareTo("tenpay")){
                            orderModel.orderPay(order_info.order_id);
                        }
                        else
                        {
                            orderModel.orderPay(order_info.order_id);
                        }
                    }
                    else
                    {
                        orderModel.orderPay(order_info.order_id);
                    }
                }
                else if (msg.what == 2)
                {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String exit=resource.getString(R.string.balance_cancel_or_not);
                    String exiten=resource.getString(R.string.prompt);
                    final MyDialog cancelOrders = new MyDialog(E4_HistoryActivity.this, exiten, exit);
                    cancelOrders.show();
                    cancelOrders.positive.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelOrders.dismiss();
                            GOODORDER order = (GOODORDER)msg.obj;
                            order_info = order.order_info;
                            orderModel.orderCancle(order_info.order_id);

                        }
                    });
                    cancelOrders.negative.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelOrders.dismiss();
                        }
                    });

                }

                else if(msg.what == 3)
                {
                    GOODORDER order = (GOODORDER)msg.obj;
                    order_info = order.order_info;
                    orderModel.affirmReceived(order_info.order_id);
                }

            }
        };
	}
	
	public void setOrder() {

        Resources resource = (Resources) getBaseContext().getResources();
        String nodata=resource.getString(R.string.no_data);
		if(orderModel.order_list.size() == 0) {
			null_paView.setVisibility(View.VISIBLE);
	        xlistView.setVisibility(View.GONE);
		} else {
			null_paView.setVisibility(View.GONE);
			xlistView.setVisibility(View.VISIBLE);
		}
		
		if(flag.equals("await_pay")) {
			
			if(tradeAdapter == null) {
				tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 1);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}
			tradeAdapter.parentHandler = messageHandler;
			
		} else if(flag.equals("await_ship")) {
			if(tradeAdapter == null) {
				tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 2);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}	
			tradeAdapter.parentHandler = messageHandler;
			
			
		} else if(flag.equals("shipped")) {
			if(tradeAdapter == null) {
				tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 3);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}	
				
			tradeAdapter.parentHandler = messageHandler;
			
		} else if(flag.equals("finished")) {
			
			if(tradeAdapter == null) {
				tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 4);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}	
				
			tradeAdapter.parentHandler = messageHandler;
			
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {		
        Resources resource = (Resources) getBaseContext().getResources();
		xlistView.stopRefresh();
		xlistView.stopLoadMore();
		if(url.endsWith(ApiInterface.ORDER_LIST)) {
			xlistView.setRefreshTime();
			if(orderModel.paginated.more == 0) {
				xlistView.setPullLoadEnable(false);
			} else {
				xlistView.setPullLoadEnable(true);
			}
			setOrder();
		} else if(url.endsWith(ApiInterface.ORDER_PAY))
        {
            String pay_wap= orderModel.pay_wap;
            String pay_online=orderModel.pay_online;
            String upop_tn=orderModel.upop_tn;
            if (upop_tn != null && !"".equals(upop_tn)) {
                //银联sdk支付
                UPPayAssistEx.startPayByJAR(E4_HistoryActivity.this, PayActivity.class, null, null, upop_tn, UPPay_mMode);
            } else if (pay_wap != null && !"".equals(pay_wap)) {
                //wap支付
                Intent intent = new Intent(this, PayWebActivity.class);
                intent.putExtra(PayWebActivity.PAY_URL, pay_wap);
                startActivityForResult(intent,REQUEST_Pay_Web);
            } else if (pay_online != null && !"".equals(pay_online)) {
                //其他方式
                Intent intent = new Intent(this, OtherPayWebActivity.class);
                intent.putExtra("html", pay_online);
                startActivity(intent);
            }
		} else if(url.endsWith(ApiInterface.ORDER_CANCLE)) {
			orderModel.getOrder(flag);
		} else if(url.endsWith(ApiInterface.ORDER_AFFIRMRECEIVED)) {

			String suc=resource.getString(R.string.successful_operation);
            String check=resource.getString(R.string.check_or_not);
			mDialog = new MyDialog(this, suc, check);
            mDialog.show();
            mDialog.positive.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {					
					mDialog.dismiss();
					Intent intent = new Intent(E4_HistoryActivity.this, E4_HistoryActivity.class);
					intent.putExtra("flag", "finished");
					startActivity(intent);
					finish();
				}
			});
            mDialog.negative.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {					
					mDialog.dismiss();
				}
			});
            
            orderModel.getOrder(flag);
            
		}
		
	}

	@Override
	public void onRefresh(int id) {			
		orderModel.getOrder(flag);
	}

	@Override
	public void onLoadMore(int id) {		
		orderModel.getOrderMore(flag);
	}
    @Override
    public void onResume() {
        super.onResume();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageStart(getIntent().getStringExtra("flag"));
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this),"");
        }
}
    @Override
    public void onPause() {
        super.onPause();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageEnd(getIntent().getStringExtra("flag"));
            MobclickAgent.onPause(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPPay) {
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
                final MyDialog mDialog = new MyDialog(E4_HistoryActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(E4_HistoryActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        orderModel.getOrder(flag);
                    }
                });
            } else if (str.equalsIgnoreCase("fail") || str.equals("cancel")) {
                ToastView toast = new ToastView(E4_HistoryActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        else if (requestCode==REQUEST_ALIPAY){
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                orderModel.getOrder(flag);
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(E4_HistoryActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(E4_HistoryActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(E4_HistoryActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }else if (requestCode==REQUEST_Pay_Web){
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                orderModel.getOrder(flag);
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(E4_HistoryActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(E4_HistoryActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(E4_HistoryActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }
    }
    private void showAlipayDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alipay_dialog,null);
        final Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout alipayLayout = (LinearLayout) view.findViewById(R.id.alipay);
        LinearLayout alipayWapLayout = (LinearLayout) view.findViewById(R.id.alipay_wap);

        alipayLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Intent intent =new Intent(E4_HistoryActivity.this,AlixPayActivity.class);
                intent.putExtra(AlixPayActivity.ORDER_INFO,order_info);
                startActivityForResult(intent,REQUEST_ALIPAY);
            }
        });

        alipayWapLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                orderModel.orderPay(order_info.order_id);
            }
        });
    }
}
