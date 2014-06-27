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

import android.content.res.Resources;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.umeng.analytics.MobclickAgent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class E4_HistoryActivity extends AlixPayActivity implements BusinessResponse, IXListViewListener {
	
	private String flag;
	private TextView title;
	private ImageView back;
	private XListView xlistView;
	private E4_HistoryAdapter tradeAdapter;
		
	private View null_paView;
	
	private ArrayList<String> items = new ArrayList<String>();
	
	private OrderModel orderModel;
	
	public Handler messageHandler;
    private MyDialog mDialog;

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
                            performPay();
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
		if(url.endsWith(ProtocolConst.ORDER_LIST)) {
			xlistView.setRefreshTime();
			if(orderModel.paginated.more == 0) {
				xlistView.setPullLoadEnable(false);
			} else {
				xlistView.setPullLoadEnable(true);
			}
			setOrder();
		} else if(url.endsWith(ProtocolConst.ORDER_PAY))
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
		} else if(url.endsWith(ProtocolConst.ORDER_CANCLE)) {
			orderModel.getOrder(flag);
		} else if(url.endsWith(ProtocolConst.AFFIRMRECEIVED)) {

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
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageEnd(getIntent().getStringExtra("flag"));
            MobclickAgent.onPause(this);
        }
    }

}
