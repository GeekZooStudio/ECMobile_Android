package com.insthub.BeeFramework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.DebugMessageModel;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

public class DebugListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	
	public DebugListAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		 
		return DebugMessageModel.messageList.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return DebugMessageModel.messageList.get(position);
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
			convertView = inflater.inflate(R.layout.debug_message_item, null);
			holder.time = (TextView) convertView.findViewById(R.id.debug_item_time);
			holder.message = (TextView) convertView.findViewById(R.id.debug_item_message);
			holder.request = (TextView) convertView.findViewById(R.id.debug_item_request);
			holder.response = (TextView) convertView.findViewById(R.id.debug_item_response);
			holder.netSize = (TextView) convertView.findViewById(R.id.debug_item_netSize);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int size = DebugMessageModel.messageList.size();
		DebugMessageModel.messageList.get(size-1-position).toString();
		holder.time.setText(DebugMessageModel.messageList.get(size-1-position).startTime);
		holder.message.setText(DebugMessageModel.messageList.get(size-1-position).message);
		holder.request.setText(DebugMessageModel.messageList.get(size-1-position).requset);
		holder.response.setText(DebugMessageModel.messageList.get(size-1-position).response);
		holder.netSize.setText(DebugMessageModel.messageList.get(size-1-position).netSize);
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView time;
		private TextView message;
		private TextView request;
		private TextView response;
		private TextView netSize;
	}

}
