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
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.component.SpecificationValueCell;
import com.insthub.ecmobile.protocol.PROPERTY;

import java.util.ArrayList;


public class GoodPropertyAdapter extends BeeBaseAdapter{
    public GoodPropertyAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public class PropertyCellHolder extends BeeCellHolder
    {
        TextView property_name;
        TextView property_value;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        PropertyCellHolder cellHolder = new PropertyCellHolder();
        cellHolder.property_name = (TextView)cellView.findViewById(R.id.property_name);
        cellHolder.property_value = (TextView)cellView.findViewById(R.id.property_value);

        return cellHolder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        PROPERTY property = (PROPERTY)dataList.get(position);
        ((PropertyCellHolder)h).property_name.setText(property.name);
        ((PropertyCellHolder)h).property_value.setText(property.value);

        return cellView;
    }

    @Override
    public View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.good_property_cell, null);
    }
}
