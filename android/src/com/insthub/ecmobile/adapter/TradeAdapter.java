package com.insthub.ecmobile.adapter;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.ORDER_GOODS_LIST;

public class TradeAdapter extends BaseAdapter {

	private Context context;
	public List<GOODORDER> list;
	public int flag;
	
	private LayoutInflater inflater;
	
	public Handler parentHandler;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	public TradeAdapter(Context context, List<GOODORDER> list, int flag) {
		this.context = context;
		this.list = list;
		this.flag = flag;
		inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		//if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.trade_item, null);
			holder.sno = (TextView) convertView.findViewById(R.id.trade_item_sno);
			holder.time = (TextView) convertView.findViewById(R.id.trade_item_time);
			holder.body = (LinearLayout) convertView.findViewById(R.id.trade_item_body);
			holder.fee = (TextView) convertView.findViewById(R.id.trade_item_fee);
			holder.red_paper = (TextView) convertView.findViewById(R.id.trade_item_redPaper);
			holder.integral = (TextView) convertView.findViewById(R.id.trade_item_integral);
			holder.total = (TextView) convertView.findViewById(R.id.trade_item_total);
			holder.check = (Button) convertView.findViewById(R.id.trade_item_check);
			holder.ok = (Button) convertView.findViewById(R.id.trade_item_ok);
			
			ArrayList<ORDER_GOODS_LIST> goods_list = list.get(position).goods_list;
			
			for(int i=0;i<goods_list.size();i++) {
				View view = LayoutInflater.from(context).inflate(R.layout.trade_body, null);
				WebImageView image = (WebImageView) view.findViewById(R.id.trade_body_image);
				TextView text = (TextView) view.findViewById(R.id.trade_body_text);
				TextView total = (TextView) view.findViewById(R.id.trade_body_total);
				TextView num = (TextView) view.findViewById(R.id.trade_body_num);
				holder.body.addView(view);
				
				shared = context.getSharedPreferences("userInfo", 0); 
				editor = shared.edit();
				String imageType = shared.getString("imageType", "mind");
				
				if(imageType.equals("high")) {
					image.setImageWithURL(context, goods_list.get(i).img.thumb, R.drawable.default_image);
				} else if(imageType.equals("low")) {
					image.setImageWithURL(context, goods_list.get(i).img.small, R.drawable.default_image);
				} else {
					String netType = shared.getString("netType", "wifi");
					if(netType.equals("wifi")) {
						image.setImageWithURL(context, goods_list.get(i).img.thumb, R.drawable.default_image);
					} else {
						image.setImageWithURL(context, goods_list.get(i).img.small, R.drawable.default_image);
					}
				}
				
				text.setText(goods_list.get(i).name);
				total.setText(goods_list.get(i).subtotal);
				num.setText("X "+goods_list.get(i).goods_number);
				
			}

//		
		final GOODORDER order = list.get(position);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date(order.order_time);
		holder.time.setText(format.format(currentTime));
		holder.sno.setText(order.order_sn);
		holder.fee.setText(order.formated_shipping_fee);
		holder.red_paper.setText("-"+order.formated_bonus);
		holder.integral.setText("-"+order.formated_integral_money);
		holder.total.setText(order.total_fee);
		
		if(flag == 1) {
			holder.ok.setBackgroundResource(R.drawable.trade_list_unpay_payment_btn_red);
			holder.ok.setText("付款");
			holder.check.setText("取消订单");
            holder.check.setVisibility(View.GONE);
			holder.ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
	                msg.what = 1;
                    msg.obj = order;
                    parentHandler.handleMessage(msg);
					
				}
			});
			
			holder.check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
	                msg.what = 2;
                    msg.obj = order;
                    parentHandler.handleMessage(msg);
				}
			});
			
		} else if(flag == 2) {
			holder.ok.setVisibility(View.GONE);
			holder.check.setVisibility(View.GONE);
		}
        else if(flag == 3)
        {
            holder.check.setVisibility(View.GONE);
			holder.ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
	                msg.what = 3;
                    msg.obj = order;
                    parentHandler.handleMessage(msg);
				}
			});
		} else if(flag == 4) {
			holder.ok.setVisibility(View.GONE);
            holder.check.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView sno;
		private TextView time;
		private LinearLayout body;
		private TextView fee;
		private TextView red_paper;
		private TextView integral;
		private TextView total;
		private Button check;
		private Button ok;
	}
}
