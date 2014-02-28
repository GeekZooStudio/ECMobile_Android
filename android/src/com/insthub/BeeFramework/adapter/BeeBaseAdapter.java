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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class BeeBaseAdapter extends BaseAdapter
{
    protected LayoutInflater mInflater = null;
    protected Context mContext;

    public ArrayList dataList = new ArrayList();
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    protected static final int TYPE_HEADER = 2;

    public class BeeCellHolder
    {
        public int position;
    }

    public BeeBaseAdapter(Context c, ArrayList dataList)
    {
        mContext = c;
        mInflater = LayoutInflater.from(c);
        this.dataList = dataList;
    }

    protected abstract BeeCellHolder createCellHolder(View cellView);
    protected abstract View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h);
    public abstract View createCellView();

    /* (non-Javadoc)
     * @see android.widget.BaseAdapter#getViewTypeCount()
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public int getItemViewType(int position)
    {
        return TYPE_ITEM;
    }

    /* (non-Javadoc)
     * @see com.miyoo.lottery.adapter.HummerBaseAdapter#getCount()
     */
    @Override
    public int getCount() {
        int count = dataList != null?dataList.size():0;
        return count;
    }

    /* (non-Javadoc)
     * @see com.miyoo.lottery.adapter.HummerBaseAdapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        if (0 <= position && position < getCount()) {
            return dataList.get(position);
        }
        else {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
         
        return 0;
    }

    protected View dequeueReuseableCellView(int position, View convertView,
                                            ViewGroup parent) {
        return null;
    }

    public void update(int newState)
    {
        notifyDataSetInvalidated();
    }


    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View cellView, ViewGroup parent) {

        BeeCellHolder holder = null;
        if (cellView == null ) {
            cellView = createCellView();
            holder = createCellHolder(cellView);
            if (null != holder) 
            {
            	cellView.setTag(holder);
			}
            
        }
        else {
            holder = (BeeCellHolder)cellView.getTag();
            if (holder == null)
            {
                Log.v("lottery", "error");
            }
        }
        if(null != holder)
        {
        	holder.position = position;
        }
        
        bindData(position, cellView, parent, holder);
        return cellView;
    }
}
