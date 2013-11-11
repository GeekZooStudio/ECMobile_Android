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
import android.view.View;
import android.view.ViewGroup;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.GoodDetailModel;


import java.util.ArrayList;

public class GoodDetailAdapter extends BeeBaseAdapter{


    private GoodDetailModel dataModel;

    public GoodDetailAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public GoodDetailAdapter(Context c, GoodDetailModel dataModel) {
        super(c, null);
        this.dataModel = dataModel;
    }

    @Override
    protected BeeBaseAdapter.BeeCellHolder createCellHolder(View cellView) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount()
    {
        return 0;
    }

    public int getItemVIewType(int position)
    {
          return 0;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent,
                            BeeBaseAdapter.BeeCellHolder h) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View createCellView() {
        // TODO Auto-generated method stub
        return mInflater.inflate(R.layout.gooditemlarge, null);
    }



}
