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

public class G3_MessageAdapter extends BaseAdapter {

	private Context context;
	public ArrayList<MESSAGE> list;
	private LayoutInflater inflater;

    public class NotifyHolder
    {
        public TextView text;
        public TextView time;
    }
	
	public G3_MessageAdapter(Context context, ArrayList<MESSAGE> list) {
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
	public View getView(int position, View convertView, ViewGroup parent)
    {
		 
		final NotifyHolder holder;
		if(convertView == null)
        {
			holder = new NotifyHolder();
			convertView = inflater.inflate(R.layout.g3_message_cell, null);
			holder.text = (TextView) convertView.findViewById(R.id.shop_notify_item_text);
            holder.time = (TextView)convertView.findViewById(R.id.shop_notify_item_time);
			
			convertView.setTag(holder);
		} else {
			holder = (NotifyHolder) convertView.getTag();
		}

        MESSAGE message = list.get(position);
        holder.time.setText(TimeUtil.timeAgo(message.created_at));
        holder.text.setText(message.content);
		
		return convertView;
	}

}
