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

import java.util.ArrayList;

import android.content.res.Resources;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.ORDER_INFO;
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
import com.insthub.ecmobile.adapter.ShoppingCartAdapter;
import com.insthub.ecmobile.adapter.TradeAdapter;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class TradeActivity extends Activity implements BusinessResponse, IXListViewListener {
	
	private String flag;
	private TextView title;
	private ImageView back;
	private XListView xlistView;
	private TradeAdapter tradeAdapter;
	
	//private ImageView bg;
	private View null_paView;
	
	private ArrayList<String> items = new ArrayList<String>();
	
	private OrderModel orderModel;
	
	public Handler messageHandler;
    private MyDialog mDialog;

    ORDER_INFO order_info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        Resources resource = (Resources) getBaseContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_list);
		
		Intent intent = getIntent();
		flag = intent.getStringExtra("flag");
		
		title = (TextView) findViewById(R.id.top_view_text);

		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//bg = (ImageView) findViewById(R.id.trade_list_bg);
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
//			tradeAdapter = new TradeAdapter(this, items, 1);
//			xlistView.setAdapter(tradeAdapter);
			
		} else if(flag.equals("await_ship")) {
			title.setText(ship);
			/**
			 * 在这里请求数据
			 */
			orderModel.getOrder("await_ship");
//			tradeAdapter = new TradeAdapter(this, items, 2);
//			xlistView.setAdapter(tradeAdapter);
			
		} else if(flag.equals("shipped")) {
			title.setText(shipped);
			/**
			 * 在这里请求数据
			 */
			orderModel.getOrder("shipped");
//			tradeAdapter = new TradeAdapter(this, items, 3);
//			xlistView.setAdapter(tradeAdapter);
			
		} else if(flag.equals("finished")) {
			title.setText(fin);
			/**
			 * 在这里请求数据
			 */
			orderModel.getOrder("finished");
//			tradeAdapter = new TradeAdapter(this, items, 4);
//			xlistView.setAdapter(tradeAdapter);
			
		}
		
		messageHandler = new Handler(){

            public void handleMessage(Message msg) {

                if (msg.what == 1)
                {
                    GOODORDER order = (GOODORDER)msg.obj;
                    order_info = order.order_info;
                    orderModel.orderPay(order_info.order_id);
                }
                else if (msg.what == 2)
                {
                    GOODORDER order = (GOODORDER)msg.obj;
                    order_info = order.order_info;
                    orderModel.orderCancle(order_info.order_id);
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
			//Toast toast = Toast.makeText(this,nodata, 0);
			//ToastView toast = new ToastView(this, nodata);
	        //toast.setGravity(Gravity.CENTER, 0, 0);
	        //toast.show();
	        //bg.setVisibility(View.VISIBLE);
			null_paView.setVisibility(View.VISIBLE);
	        xlistView.setVisibility(View.GONE);
		} else {
			//bg.setVisibility(View.GONE);
			null_paView.setVisibility(View.GONE);
			xlistView.setVisibility(View.VISIBLE);
		}
		
		if(flag.equals("await_pay")) {
			
			if(tradeAdapter == null) {
				tradeAdapter = new TradeAdapter(this, orderModel.order_list, 1);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}
			tradeAdapter.parentHandler = messageHandler;
			
		} else if(flag.equals("await_ship")) {
			if(tradeAdapter == null) {
				tradeAdapter = new TradeAdapter(this, orderModel.order_list, 2);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}	
			tradeAdapter.parentHandler = messageHandler;
			
			
		} else if(flag.equals("shipped")) {
			if(tradeAdapter == null) {
				tradeAdapter = new TradeAdapter(this, orderModel.order_list, 3);
				xlistView.setAdapter(tradeAdapter);
			} else {
				tradeAdapter.list = orderModel.order_list;
				tradeAdapter.notifyDataSetChanged();
			}	
				
			tradeAdapter.parentHandler = messageHandler;
			
		} else if(flag.equals("finished")) {
			
			if(tradeAdapter == null) {
				tradeAdapter = new TradeAdapter(this, orderModel.order_list, 4);
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
		// TODO Auto-generated method stub
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
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
					// TODO Auto-generated method stub
					mDialog.dismiss();
					Intent intent = new Intent(TradeActivity.this, TradeActivity.class);
					intent.putExtra("flag", "finished");
					startActivity(intent);
					finish();
				}
			});
            mDialog.negative.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDialog.dismiss();
				}
			});
            
            orderModel.getOrder(flag);
            
		}
		
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
	
		orderModel.getOrder(flag);
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		orderModel.getOrderMore(flag);
	}

}
