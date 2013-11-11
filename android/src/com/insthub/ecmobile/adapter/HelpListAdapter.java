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

public class HelpListAdapter extends BeeBaseAdapter
{
    protected static final int TYPE_HELPSELL = 2;

    private HelpModel dataModel;

    public HelpListAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public HelpListAdapter(Context c, HelpModel dataModel) {
        super(c, dataModel.shophelpsList);
        this.dataModel = dataModel;
    }


    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View createCellView() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public View getView(final int position, View cellView, ViewGroup parent) {

        BeeCellHolder holder = null;

        if (null == cellView )
        {
            cellView = (CategorySellingCell)LayoutInflater.from(mContext).inflate(R.layout.categoryselling_cell, null);
        }
        else
        {
            return cellView;
        }

        cellView = (ShopHelpCell)LayoutInflater.from(mContext).inflate(R.layout.shophelp_cell, null);

        final SHOPHELP shophelp = (SHOPHELP)dataList.get(position);
        ((ShopHelpCell)cellView).bindData(shophelp,mContext,dataModel.data,position);

        return cellView;
    }

}

