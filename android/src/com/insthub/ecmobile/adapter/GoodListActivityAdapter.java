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

import android.util.Log;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;

import com.insthub.ecmobile.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.insthub.ecmobile.component.HotSellingCell;
import com.insthub.ecmobile.component.TwoGoodItemCell;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;

/**
 * @author mc374
 *
 */
public class GoodListActivityAdapter extends BeeBaseAdapter {


	public GoodListActivityAdapter(Context c, ArrayList dataList) {
		super(c, dataList);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		if(dataList.size()%2>0) {
			return dataList.size()/2+1;
		} else {
			return dataList.size()/2;
		}
		
		//return dataList.size()/2;
	}
    public int getItemViewType(int position)
    {
        return  1;
    }


    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected View bindData(int position, View cellView, ViewGroup parent,
			BeeCellHolder h) {

        List<SIMPLEGOODS> itemList = null;

        int distance =  dataList.size() - position*2;
        int cellCount = distance >= 2? 2:distance;

        itemList = dataList.subList(position*2,position*2+cellCount);

        ((TwoGoodItemCell)cellView).bindData(itemList);
        // TODO Auto-generated method stub
		return null;
	}

	@Override
	public View createCellView() {
		// TODO Auto-generated method stub
		return mInflater.inflate(R.layout.two_good_item, null);
	}

	@Override
	protected BeeCellHolder createCellHolder(View cellView) {
        BeeCellHolder holder = new BeeCellHolder();

		return holder;
	}

    public View getView(int position, View cellView, ViewGroup parent) {

        BeeCellHolder holder = null;
        if (cellView == null )
        {
            cellView = createCellView();
            holder = createCellHolder(cellView);
            if (null != holder)
            {
                cellView.setTag(holder);
            }

        }
        else
        {
            holder = (BeeCellHolder)cellView.getTag();
            if (holder == null)
            {
                Log.v("lottery", "error");
            }
            else
            {
                Log.d("ecmobile", "last position" + holder.position +"    new position" + position+"\n");
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
