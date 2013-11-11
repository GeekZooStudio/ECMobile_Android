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

public class CommentAdapter extends BaseAdapter {

	private Context context;
	public List<COMMENTS> list;
	private LayoutInflater inflater;
	
	public CommentAdapter(Context context, List<COMMENTS> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.comment_item, null);
			
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
