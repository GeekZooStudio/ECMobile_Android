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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.AddressManage2Activity;
import com.insthub.ecmobile.adapter.AddressManageAdapter.ViewHolder;
import com.insthub.ecmobile.protocol.ADDRESS;
import com.insthub.ecmobile.protocol.PAYMENT;
import com.insthub.ecmobile.protocol.SHIPPING;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShippingAdapter extends BaseAdapter {
	
	private Context context;
	private List<SHIPPING> list;
	private LayoutInflater inflater;

	public ShippingAdapter(Context context, List<SHIPPING> list) {
		this.context = context;
		this.list = list;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.payment_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.payment_item_name);
			holder.fee = (TextView) convertView.findViewById(R.id.payment_item_fee);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(list.get(position).shipping_name);
		holder.fee.setText(list.get(position).format_shipping_fee);
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView name;
		private TextView fee;
	}

}
