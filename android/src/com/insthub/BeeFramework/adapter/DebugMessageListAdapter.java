package com.insthub.BeeFramework.adapter;

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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.insthub.BeeFramework.model.DebugMessageModel;
import com.external.androidquery.callback.AjaxCallback;

public class DebugMessageListAdapter extends BaseAdapter{
    private Context context;

    public DebugMessageListAdapter( Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
         
        return DebugMessageModel.messageList.size();
    }

    @Override
    public Object getItem(int position) {
         
        return null;
    }

    @Override
    public long getItemId(int position) {
         
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int size = DebugMessageModel.messageList.size();
        AjaxCallback msg = DebugMessageModel.messageList.get(size -1 -position);
        String msgDesc = msg.toString();
        TextView itemView = new TextView(this.context);
        itemView.setText(msgDesc);
        return itemView;
    }
}
