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
import android.widget.ImageView;
import android.widget.TextView;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.CATEGORY;

import java.util.ArrayList;
import java.util.List;

public class D0_CategoryAdapter extends BeeBaseAdapter{

    public class CategoryHolder extends BeeCellHolder
    {
        TextView categoryName;
        ImageView rightArrow;
    }
    public D0_CategoryAdapter(Context c, ArrayList dataList) {
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
        View cellView = mInflater.inflate(R.layout.d0_category_cell,null);
        return cellView;
    }
}
