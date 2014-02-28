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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.component.CategorySellingCell;
import com.insthub.ecmobile.component.HotSellingCell;
import com.insthub.ecmobile.component.ShopHelpCell;
import com.insthub.ecmobile.model.HelpModel;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;
import com.insthub.ecmobile.protocol.SHOPHELP;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;

import java.util.ArrayList;
import java.util.List;

public class G2_InfoAdapter extends BeeBaseAdapter
{
    protected static final int TYPE_HELPSELL = 2;

    private HelpModel dataModel;

    public G2_InfoAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public G2_InfoAdapter(Context c, HelpModel dataModel) {
        super(c, dataModel.shophelpsList);
        this.dataModel = dataModel;
    }


    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
         
        return null;
    }

    @Override
    public int getViewTypeCount() {

        return 1000;
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }

    public int getItemViewType(int position)
    {
        return position;
    }




    @Override
    protected View bindData(int position, View cellView, ViewGroup parent,
                            BeeCellHolder h) {
         
        return null;
    }

    @Override
    public View createCellView() {
         
        return null;
    }
    @Override
    public View getView(final int position, View cellView, ViewGroup parent) {

        BeeCellHolder holder = null;

        if (null == cellView )
        {
            cellView = (CategorySellingCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_category_cell, null);
        }
        else
        {
            return cellView;
        }

        cellView = (ShopHelpCell)LayoutInflater.from(mContext).inflate(R.layout.g2_info_shop_help_cell, null);

        final SHOPHELP shophelp = (SHOPHELP)dataList.get(position);
        ((ShopHelpCell)cellView).bindData(shophelp,mContext,dataModel.data,position);

        return cellView;
    }

}

