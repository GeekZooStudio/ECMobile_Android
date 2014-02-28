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
 * @author
 *
 */
public class B1_ProductListAdapter extends BeeBaseAdapter {


	public B1_ProductListAdapter(Context c, ArrayList dataList) {
		super(c, dataList);		
	}

	
	@Override
	public int getCount() {	
		
		if(dataList.size()%2>0) {
			return dataList.size()/2+1;
		} else {
			return dataList.size()/2;
		}			
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
		return null;
	}

	@Override
	public long getItemId(int position) {

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
		return null;
	}

	@Override
	public View createCellView() {		
		return mInflater.inflate(R.layout.b1_product_call, null);
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
