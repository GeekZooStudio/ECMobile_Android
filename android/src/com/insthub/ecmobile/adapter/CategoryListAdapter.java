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
import android.widget.ImageView;
import android.widget.TextView;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.CATEGORY;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends BeeBaseAdapter{

    public class CategoryHolder extends BeeCellHolder
    {
        TextView categoryName;
        ImageView rightArrow;
    }
    public CategoryListAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }
    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        CategoryHolder holder = new CategoryHolder();
        holder.categoryName = (TextView)cellView.findViewById(R.id.category_name);
        holder.rightArrow = (ImageView)cellView.findViewById(R.id.right_arrow);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        CATEGORY categoryItem = (CATEGORY)dataList.get(position);
        CategoryHolder holder = (CategoryHolder)h;
        holder.categoryName.setText(categoryItem.name);
        if (categoryItem.children.size() > 0)
        {
            holder.rightArrow.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.rightArrow.setVisibility(View.GONE);
        }
        return cellView;
    }

    @Override
    public View createCellView() {
        View cellView = mInflater.inflate(R.layout.category_list_item,null);
        return cellView;
    }
}
