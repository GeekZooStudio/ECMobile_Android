package com.insthub.ecmobile.component;
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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;

import java.util.List;

public class SpecificationCell extends LinearLayout
{
    private Context mContext;
    private TextView specNameTextView;
    private LinearLayout specValueLayout;

    public SpecificationCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    void init()
    {
        if (null == specNameTextView)
        {
            specNameTextView = (TextView)findViewById(R.id.specification_name);
        }

        if (null == specValueLayout)
        {
            specValueLayout = (LinearLayout)findViewById(R.id.specification_value);
        }
        else
        {
            specValueLayout.removeAllViews();
        }

    }

    public void bindData(SPECIFICATION specification)
    {
        init();
        if (0 == specification.attr_type.compareTo("1") )
        {
            specNameTextView.setText(specification.name+"(单选)");
        }
        else
        {
            specNameTextView.setText(specification.name+"(复选)");
        }


        for (int i = 0; i< specification.value.size(); i = i+2)
        {
             SpecificationValueCell itemCell = (SpecificationValueCell) LayoutInflater.from(mContext).inflate(R.layout.specification_value_cell, null);

            List<SPECIFICATION_VALUE> itemList = null;

            int distance =  specification.value.size() - i;
            int cellCount = distance >= 2? 2:distance;

            itemList = specification.value.subList(i,i+cellCount);
            itemCell.bindData(itemList);
            specValueLayout.addView(itemCell);
        }


    }
}
