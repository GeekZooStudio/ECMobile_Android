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
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class E6_ShippingStatusActivity extends BaseActivity implements BusinessResponse {
	
	private TextView title;
	private ImageView back;
	private TextView name;
	private TextView order_sn;
	private LinearLayout info;
	private String order_id;
	private OrderModel orderModel;
	private LinearLayout express_linear;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e6_shipping_status_list);
		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String del=resource.getString(R.string.deliverStatus);
		title.setText(del);
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		
		Intent intent = getIntent();
		String num = intent.getStringExtra("order_sn");
		order_id = intent.getStringExtra("order_id");
		name = (TextView) findViewById(R.id.express_name);
		order_sn = (TextView) findViewById(R.id.express_orderNum);
		info = (LinearLayout) findViewById(R.id.express_info);
		express_linear = (LinearLayout) findViewById(R.id.express_linear);

        String ord=resource.getString(R.string.order_number);
		order_sn.setText(ord+num);
		
		orderModel = new OrderModel(this);
		orderModel.addResponseListener(this);
		orderModel.orderExpress(order_id);
		
	}
	
	public void setExpressInfo() {
		
		name.setText(orderModel.shipping_name);
		if(orderModel.express_list.size()>0) {
			info.removeAllViews();
			express_linear.setVisibility(View.VISIBLE);
			for(int i=0;i<orderModel.express_list.size();i++) {
				View view = LayoutInflater.from(this).inflate(R.layout.e6_shipping_status_cell, null);
				ImageView icon = (ImageView) view.findViewById(R.id.express_item_time_icon);
				TextView text = (TextView) view.findViewById(R.id.express_item_text);
				TextView time = (TextView) view.findViewById(R.id.express_item_time);
				ImageView bg = (ImageView) view.findViewById(R.id.express_item_bg);
				ImageView bg2 = (ImageView) view.findViewById(R.id.express_item_bg2);
				info.addView(view);
				text.setText(orderModel.express_list.get(i).context);
				time.setText(orderModel.express_list.get(i).time);
				if(i == 0) {
					icon.setImageResource(R.drawable.trade_info_stream_logistics_time_active_icon);
					bg.setVisibility(View.VISIBLE);
					text.setTextColor(Color.RED);
					time.setTextColor(Color.RED);
				} else {
					icon.setImageResource(R.drawable.trade_info_stream_logistics_time_icon);
					bg.setVisibility(View.GONE);
					text.setTextColor(Color.parseColor("#666666"));
					time.setTextColor(Color.parseColor("#666666"));
				}
				
				if(i == orderModel.express_list.size()-1) {
					bg2.setVisibility(View.VISIBLE);
				} else {
					bg2.setVisibility(View.GONE);
				}				
			}
			
		} else {
			express_linear.setVisibility(View.GONE);
		}		
	}
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		
		if(url.endsWith(ApiInterface.ORDER_EXPRESS)) {
			setExpressInfo();
		}
		
	}
}
