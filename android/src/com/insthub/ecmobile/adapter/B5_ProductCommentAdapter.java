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
import java.util.Date;
import java.util.List;

import com.insthub.BeeFramework.Utils.TimeUtil;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.COMMENTS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class B5_ProductCommentAdapter extends BaseAdapter {

	private Context context;
	public List<COMMENTS> list;
	private LayoutInflater inflater;
	
	public B5_ProductCommentAdapter(Context context, List<COMMENTS> list) {
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {		
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.b5_product_comment_cell, null);
			
			holder.name = (TextView) convertView.findViewById(R.id.comment_item_name);
			holder.time = (TextView) convertView.findViewById(R.id.comment_item_time);
			holder.cont = (TextView) convertView.findViewById(R.id.comment_item_cont);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		COMMENTS comments = list.get(position);
		holder.name.setText(comments.author+":");
		holder.cont.setText(comments.content);
		
		if(comments.create != null && !comments.create.equals("")) {
			holder.time.setText(TimeUtil.timeAgo(comments.create));
		}
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView name;
		private TextView time;
		private TextView cont;
	}

}
