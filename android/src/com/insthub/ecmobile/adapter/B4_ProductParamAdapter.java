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
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.component.SpecificationValueCell;
import com.insthub.ecmobile.protocol.PROPERTY;

import java.util.ArrayList;

public class B4_ProductParamAdapter extends BeeBaseAdapter{
    public B4_ProductParamAdapter(Context c, ArrayList dataList) {
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
        return LayoutInflater.from(mContext).inflate(R.layout.b4_product_param_cell, null);
    }
}
