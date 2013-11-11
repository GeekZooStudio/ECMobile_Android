package com.insthub.BeeFramework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.activity.BaseActivity;

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
public class ActivitylifeCycleAdapter extends BeeBaseAdapter
{
    public ActivitylifeCycleAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class LifeCycleHolder extends BeeCellHolder
    {
        public TextView activityItem;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        LifeCycleHolder holder = new LifeCycleHolder();
        holder.activityItem = (TextView)cellView.findViewById(R.id.activity_name);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        BaseActivity activity_name = (BaseActivity)dataList.get(position);
        LifeCycleHolder holder = (LifeCycleHolder)h;

        String activity_name_str =  activity_name.getClass().toString();
        holder.activityItem.setText(activity_name_str);
        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.activity_lifecycle_item,null);
    }
}
