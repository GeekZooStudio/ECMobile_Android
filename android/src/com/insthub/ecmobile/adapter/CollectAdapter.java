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

import java.util.ArrayList;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.activity.GoodDetailActivity;
import com.insthub.ecmobile.protocol.COLLECT_LIST;

public class CollectAdapter extends BaseAdapter {

	private Context context;
	public List<COLLECT_LIST> list;
	public int flag;
	private LayoutInflater inflater;
	
	private ArrayList<String> items = new ArrayList<String>();
	
	public Handler parentHandler;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	public CollectAdapter(Context context, List<COLLECT_LIST> list, int flag) {
		this.context = context;
		this.list = list;
		this.flag = flag;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list.size()%2>0) {
			return list.size()/2+1;
		} else {
			return list.size()/2;
		}
		
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.collect_item, null);
			holder.layout1 = (LinearLayout) convertView.findViewById(R.id.collect_item_layout1);
			holder.frame1 = (FrameLayout) convertView.findViewById(R.id.collect_item_frame1);
			holder.image1 = (WebImageView) convertView.findViewById(R.id.collect_item_image1);
			holder.text1 = (TextView) convertView.findViewById(R.id.collect_item_text1);
			holder.price1 = (TextView) convertView.findViewById(R.id.collect_item_price1);
			holder.remove1 = (ImageView) convertView.findViewById(R.id.collect_item_remove1);
			
			holder.layout2 = (LinearLayout) convertView.findViewById(R.id.collect_item_layout2);
			holder.frame2 = (FrameLayout) convertView.findViewById(R.id.collect_item_frame2);
			holder.image2 = (WebImageView) convertView.findViewById(R.id.collect_item_image2);
			holder.text2 = (TextView) convertView.findViewById(R.id.collect_item_text2);
			holder.price2 = (TextView) convertView.findViewById(R.id.collect_item_price2);
			holder.remove2 = (ImageView) convertView.findViewById(R.id.collect_item_remove2);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		shared = context.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");
		
		if(position*2+1 < list.size()) {
			
			final COLLECT_LIST collect1 = list.get(position*2);
			final COLLECT_LIST collect2 = list.get(position*2+1);
			
			holder.layout2.setVisibility(View.VISIBLE);
			if(flag == 1) {
				
				holder.layout1.setEnabled(true);
				holder.layout2.setEnabled(true);
				
				holder.remove1.setVisibility(View.GONE);
				holder.remove2.setVisibility(View.GONE);
				
				holder.frame1.clearAnimation();
				holder.frame2.clearAnimation();
				
			} else if(flag == 2) {
				
				holder.layout1.setEnabled(false);
				holder.layout2.setEnabled(false);
				
				holder.remove1.setVisibility(View.VISIBLE);
				holder.remove2.setVisibility(View.VISIBLE);
				
				Animation adim = AnimationUtils.loadAnimation(context, R.anim.rotate);
				holder.frame1.startAnimation(adim);
				holder.frame2.startAnimation(adim);
			}
			
			holder.text1.setText(collect1.name);
			holder.text2.setText(collect2.name);
			
			if(imageType.equals("high")) {
				holder.image1.setImageWithURL(context, collect1.img.thumb, R.drawable.default_image);
				holder.image2.setImageWithURL(context, collect2.img.thumb, R.drawable.default_image);
			} else if(imageType.equals("low")) {
				holder.image1.setImageWithURL(context, collect1.img.small, R.drawable.default_image);
				holder.image2.setImageWithURL(context, collect2.img.small, R.drawable.default_image);
			} else {
				String netType = shared.getString("netType", "wifi");
				if(netType.equals("wifi")) {
					holder.image1.setImageWithURL(context, collect1.img.thumb, R.drawable.default_image);
					holder.image2.setImageWithURL(context, collect2.img.thumb, R.drawable.default_image);
				} else {
					holder.image1.setImageWithURL(context, collect1.img.small, R.drawable.default_image);
					holder.image2.setImageWithURL(context, collect2.img.small, R.drawable.default_image);
				}
			}
			
			holder.price1.setText(collect1.market_price);
			holder.price2.setText(collect2.market_price);
			
			holder.remove1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
	                msg.what = 1;
	                msg.arg1 = Integer.valueOf(collect1.rec_id);
	                msg.arg2 = position*2;
                    parentHandler.handleMessage(msg);
				}
			});
			
			holder.remove2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
	                msg.what = 1;
	                msg.arg1 = Integer.valueOf(collect2.rec_id);
	                msg.arg2 = position*2+1;
                    parentHandler.handleMessage(msg);
				}
			});
			
			holder.layout1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, GoodDetailActivity.class);
					intent.putExtra("good_id", collect1.goods_id);
					context.startActivity(intent);
				}
			});
			
			holder.layout2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, GoodDetailActivity.class);
					intent.putExtra("good_id", collect2.goods_id);
					context.startActivity(intent);
				}
			});
			
			
		} else {
			final COLLECT_LIST collect1 = list.get(position*2);
			holder.layout2.setVisibility(View.INVISIBLE);
			if(flag == 1) {
				holder.layout1.setEnabled(true);
				holder.remove1.setVisibility(View.GONE);
				holder.frame1.clearAnimation();
			} else if(flag == 2) {
				holder.layout1.setEnabled(false);
				holder.remove1.setVisibility(View.VISIBLE);
				Animation adim = AnimationUtils.loadAnimation(context, R.anim.rotate);
				holder.frame1.startAnimation(adim);
			}
			
			holder.text1.setText(collect1.name);
			
			if(imageType.equals("high")) {
				holder.image1.setImageWithURL(context, collect1.img.thumb, R.drawable.default_image);
			} else if(imageType.equals("low")) {
				holder.image1.setImageWithURL(context, collect1.img.small, R.drawable.default_image);
			} else {
				String netType = shared.getString("netType", "wifi");
				if(netType.equals("wifi")) {
					holder.image1.setImageWithURL(context, collect1.img.thumb, R.drawable.default_image);
				} else {
					holder.image1.setImageWithURL(context, collect1.img.small, R.drawable.default_image);
				}
			}
			
			holder.price1.setText(collect1.market_price);
			
			holder.remove1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
	                msg.what = 1;
	                msg.arg1 = Integer.valueOf(collect1.rec_id);
                    parentHandler.handleMessage(msg);
				}
			});
			
			holder.layout1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, GoodDetailActivity.class);
					intent.putExtra("good_id", collect1.goods_id);
					context.startActivity(intent);
				}
			});
			
		}
		
		return convertView;
	}
	
	class ViewHolder {
		
		private LinearLayout layout1;
		private FrameLayout frame1;
		private WebImageView image1;
		private TextView text1;
		private TextView price1;
		private ImageView remove1;
		
		private LinearLayout layout2;
		private FrameLayout frame2;
		private WebImageView image2;
		private TextView text2;
		private TextView price2;
		private ImageView remove2;
		
	}

}
