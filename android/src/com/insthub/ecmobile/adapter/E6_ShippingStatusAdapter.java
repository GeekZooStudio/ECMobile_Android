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

public class E6_ShippingStatusAdapter extends BaseAdapter {

	private Context context;
	private List<ADDRESS> list;
	private LayoutInflater inflater;
	
	public E6_ShippingStatusAdapter(Context context) {
		this.context = context;
		
		inflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public int getCount() {
		 
		return 10;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		 
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.e6_shipping_status_cell, null);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
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
