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
import android.view.View;
import android.view.ViewGroup;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.GoodDetailModel;


import java.util.ArrayList;

public class B2_ProductDetailAdapter extends BeeBaseAdapter{


    private GoodDetailModel dataModel;

    public B2_ProductDetailAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public B2_ProductDetailAdapter(Context c, GoodDetailModel dataModel) {
        super(c, null);
        this.dataModel = dataModel;
    }

    @Override
    protected BeeBaseAdapter.BeeCellHolder createCellHolder(View cellView) {        
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
        return null;
    }

    @Override
    public View createCellView() {        
        return mInflater.inflate(R.layout.b2_product_detail_cell, null);
    }



}
