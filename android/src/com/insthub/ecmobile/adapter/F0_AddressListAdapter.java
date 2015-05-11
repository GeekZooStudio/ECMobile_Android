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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.F2_EditAddressActivity;
import com.insthub.ecmobile.protocol.ADDRESS;

public class F0_AddressListAdapter extends BaseAdapter {

	private Context context;
	private List<ADDRESS> list;
	private LayoutInflater inflater;
	public int a = 0;
	public Handler parentHandler;
	
	public static Map<Integer, Boolean> isSelected; 
	private int flag;
	
	public F0_AddressListAdapter(Context context, List<ADDRESS> list, int flag) {
		this.context = context;
		this.list = list;
		this.flag = flag;
		inflater = LayoutInflater.from(context);
		init(a);
	}
	
	private void init(int position) {
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++){
			if(i == position) {
				isSelected.put(i, true);
			} else {
				isSelected.put(i, false);
			}
		}
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
		 
		return position;
	}

	@Override
	public int getViewTypeCount() {
		 
		int count = list != null ? list.size() : 0;
		return count;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.f0_address_cell, null);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.address_manage_item_layout);
			holder.name = (TextView) convertView.findViewById(R.id.address_manage_item_name);
			holder.province = (TextView) convertView.findViewById(R.id.address_manage_item_province);
			holder.city = (TextView) convertView.findViewById(R.id.address_manage_item_city);
			holder.county = (TextView) convertView.findViewById(R.id.address_manage_item_county);
			holder.detail = (TextView) convertView.findViewById(R.id.address_manage_item_detail);
			holder.select = (ImageView) convertView.findViewById(R.id.address_manage_itme_select);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final ADDRESS address = list.get(position);
		
		if(address.default_address == 1) {
			init(position);
		}
		
		holder.name.setText(address.consignee);
		holder.province.setText(address.province_name);
		if(!address.city_name.equals("null")) {
			holder.city.setText(address.city_name);
		}
		if(!address.district_name.equals("null")) {
			holder.county.setText(address.district_name);
		}
		
		holder.detail.setText(address.address);
		
		if(isSelected.get(position)) {
			holder.select.setVisibility(View.VISIBLE);
			holder.name.setTextColor(Color.parseColor("#666699"));
			holder.province.setTextColor(Color.parseColor("#666699"));
			holder.city.setTextColor(Color.parseColor("#666699"));
			holder.county.setTextColor(Color.parseColor("#666699"));
			holder.detail.setTextColor(Color.parseColor("#666699"));
		} else {
			holder.select.setVisibility(View.GONE);
			holder.name.setTextColor(Color.parseColor("#000000"));
			holder.province.setTextColor(Color.parseColor("#000000"));
			holder.city.setTextColor(Color.parseColor("#000000"));
			holder.county.setTextColor(Color.parseColor("#000000"));
			holder.detail.setTextColor(Color.parseColor("#000000"));
		}
		
		holder.layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(flag == 1) {
					Message msg = new Message();
	                msg.what = 1;
	                msg.arg1 = Integer.valueOf(address.id);
	                parentHandler.handleMessage(msg);
				} else {
					Intent intent = new Intent(context, F2_EditAddressActivity.class);
					intent.putExtra("address_id", address.id+"");
					context.startActivity(intent);
				}
				
			}
		});
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView name;
		private TextView province;
		private TextView city;
		private TextView county;
		private TextView detail;
		private ImageView select;
		private LinearLayout layout;
	}

}
