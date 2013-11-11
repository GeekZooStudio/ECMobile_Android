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

import com.insthub.BeeFramework.Utils.TimeUtil;
import com.insthub.ecmobile.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.insthub.ecmobile.protocol.MESSAGE;

public class ShopNotifityAdapter extends BaseAdapter {

	private Context context;
	public ArrayList<MESSAGE> list;
	private LayoutInflater inflater;

    public class NotifyHolder
    {
        public TextView text;
        public TextView time;
    }
	
	public ShopNotifityAdapter(Context context, ArrayList<MESSAGE> list) {
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
	public View getView(int position, View convertView, ViewGroup parent)
    {
		// TODO Auto-generated method stub
		final NotifyHolder holder;
		if(convertView == null)
        {
			holder = new NotifyHolder();
			convertView = inflater.inflate(R.layout.shop_notify_item, null);
			holder.text = (TextView) convertView.findViewById(R.id.shop_notify_item_text);
            holder.time = (TextView)convertView.findViewById(R.id.shop_notify_item_time);
			
			convertView.setTag(holder);
		} else {
			holder = (NotifyHolder) convertView.getTag();
		}

        MESSAGE message = list.get(position);
        holder.time.setText(TimeUtil.timeAgo(message.time));
        holder.text.setText(message.content);
		
		return convertView;
	}

}
