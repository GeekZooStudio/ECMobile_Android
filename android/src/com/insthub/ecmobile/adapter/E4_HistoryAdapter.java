package com.insthub.ecmobile.adapter;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.E6_ShippingStatusActivity;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.ORDER_GOODS_LIST;
import com.nostra13.universalimageloader.core.ImageLoader;

public class E4_HistoryAdapter extends BaseAdapter {

	private Context context;
	public List<GOODORDER> list;
	public int flag;
	
	private LayoutInflater inflater;
	
	public Handler parentHandler;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	public E4_HistoryAdapter(Context context, List<GOODORDER> list, int flag) {
		this.context = context;
		this.list = list;
		this.flag = flag;
		inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getCount() {
		 
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		 
		return position;
	}
	

	@Override
	public int getItemViewType(int position) {
		 
		return 1;
	}


	@Override
	public int getViewTypeCount() {
		 
		return 1;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		final ViewHolder holder;
        final Resources resource = (Resources) context.getResources();
		//if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.e4_history_cell, null);
			holder.sno = (TextView) convertView.findViewById(R.id.trade_item_sno);
			holder.time = (TextView) convertView.findViewById(R.id.trade_item_time);
			holder.body = (LinearLayout) convertView.findViewById(R.id.trade_item_body);
			holder.fee = (TextView) convertView.findViewById(R.id.trade_item_fee);
			holder.red_paper = (TextView) convertView.findViewById(R.id.trade_item_redPaper);
			holder.score = (TextView) convertView.findViewById(R.id.trade_item_score);
			holder.total = (TextView) convertView.findViewById(R.id.trade_item_total);
			holder.check = (Button) convertView.findViewById(R.id.trade_item_check);
			holder.ok = (Button) convertView.findViewById(R.id.trade_item_ok);
			
			ArrayList<ORDER_GOODS_LIST> goods_list = list.get(position).goods_list;
			
			for(int i=0;i<goods_list.size();i++) {
				View view = LayoutInflater.from(context).inflate(R.layout.trade_body, null);
				ImageView image = (ImageView) view.findViewById(R.id.trade_body_image);
				TextView text = (TextView) view.findViewById(R.id.trade_body_text);
				TextView total = (TextView) view.findViewById(R.id.trade_body_total);
				TextView num = (TextView) view.findViewById(R.id.trade_body_num);
				holder.body.addView(view);
				
				shared = context.getSharedPreferences("userInfo", 0); 
				editor = shared.edit();
				String imageType = shared.getString("imageType", "mind");
				
				if(imageType.equals("high")) {
                    imageLoader.displayImage(goods_list.get(i).img.thumb,image, EcmobileApp.options);
				} else if(imageType.equals("low")) {
                    imageLoader.displayImage(goods_list.get(i).img.small,image, EcmobileApp.options);
				} else {
					String netType = shared.getString("netType", "wifi");
					if(netType.equals("wifi")) {
                        imageLoader.displayImage(goods_list.get(i).img.thumb,image, EcmobileApp.options);
					} else {
                        imageLoader.displayImage(goods_list.get(i).img.small,image, EcmobileApp.options);
					}
				}
				
				text.setText(goods_list.get(i).name);
				total.setText(goods_list.get(i).subtotal);
				num.setText("X "+goods_list.get(i).goods_number);
				
			}

		final GOODORDER order = list.get(position);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date(order.order_time);
		holder.time.setText(format.format(currentTime));
		holder.sno.setText(order.order_sn);
		holder.fee.setText(order.formated_shipping_fee);
		holder.red_paper.setText("-"+order.formated_bonus);
		holder.score.setText("-"+order.formated_integral_money);
		holder.total.setText(order.total_fee);
		
		if(flag == 1) {
			holder.ok.setBackgroundResource(R.drawable.button_narrow_red);
			holder.ok.setText(resource.getString(R.string.pay));
			holder.check.setText(resource.getString(R.string.balance_cancel));
			holder.ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 
					Message msg = new Message();
	                msg.what = 1;
                    msg.obj = order;
                    parentHandler.handleMessage(msg);
					
				}
			});
			
			holder.check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 
					Message msg = new Message();
	                msg.what = 2;
                    msg.obj = order;
                    parentHandler.handleMessage(msg);
				}
			});
			
		} else if(flag == 2) {
			holder.ok.setVisibility(View.GONE);
			holder.check.setVisibility(View.GONE);
		} else if(flag == 3) {
			holder.ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 
					Message msg = new Message();
	                msg.what = 3;
                    msg.obj = order;
                    parentHandler.handleMessage(msg);
				}
			});
			
			holder.check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 
					Intent intent = new Intent(context, E6_ShippingStatusActivity.class);
					intent.putExtra("order_sn", order.order_sn);
					intent.putExtra("order_id", order.order_id);
					context.startActivity(intent);
					
				}
			});
		} else if(flag == 4) {
			holder.ok.setVisibility(View.GONE);
			holder.check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 
					Intent intent = new Intent(context, E6_ShippingStatusActivity.class);
					intent.putExtra("order_sn", order.order_sn);
					intent.putExtra("order_id", order.order_id);
					context.startActivity(intent);
				}
			});
		}
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView sno;
		private TextView time;
		private LinearLayout body;
		private TextView fee;
		private TextView red_paper;
		private TextView score;
		private TextView total;
		private Button check;
		private Button ok;
	}
}
