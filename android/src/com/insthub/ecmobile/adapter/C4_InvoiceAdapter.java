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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.INV_LIST;

public class C4_InvoiceAdapter extends BaseAdapter {
	
	private Context context;
	private List<INV_LIST> list;
	private LayoutInflater inflater;
	public int flag = -1;
	private int id;
	
	public C4_InvoiceAdapter(Context context, List<INV_LIST> list, int id) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.flag = -1;
		this.id = id;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		 
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.c4_invoice_cell, null);
			holder.name = (TextView) convertView.findViewById(R.id.invoice_item_text);
			holder.select = (ImageView) convertView.findViewById(R.id.invoice_item_select);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(list.get(position).value);
		
		if(flag != -1) {
			if(flag == position) {
				holder.select.setVisibility(View.VISIBLE);
			} else {
				holder.select.setVisibility(View.GONE);
			}
		} else {
				
				if(list.get(position).id==id) {
					holder.select.setVisibility(View.VISIBLE); 
				} else {
					holder.select.setVisibility(View.GONE);
				}
				

		}
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView name;
		private ImageView select;
	}
}
