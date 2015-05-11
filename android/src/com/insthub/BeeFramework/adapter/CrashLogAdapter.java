package com.insthub.BeeFramework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.protocol.CrashMessage;

import java.util.ArrayList;

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
public class CrashLogAdapter extends BeeBaseAdapter{
    public CrashLogAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class LogCellHolder extends BeeCellHolder
    {
        TextView logTimeTextView;
        TextView logContentTextView;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        LogCellHolder holder = new LogCellHolder();
        holder.logTimeTextView = (TextView)cellView.findViewById(R.id.crash_time);
        holder.logContentTextView = (TextView)cellView.findViewById(R.id.crash_content);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
    	int size =dataList.size();
        CrashMessage message = (CrashMessage)dataList.get(size-1-position);
        LogCellHolder holder = (LogCellHolder)h;
        holder.logTimeTextView.setText(message.crashTime);
        holder.logContentTextView.setText(message.crashContent);

        return cellView;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.crash_log_item,null);
    }
}
