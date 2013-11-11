package com.insthub.ecmobile.component;

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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.SpecificationValueAdapter;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;

import java.util.List;

public class AdvanceSearchCell extends LinearLayout
{
    private Context mContext;
    private TextView specNameTextView;
    private LinearLayout specValueLayout;

    public AdvanceSearchCell(Context context, AttributeSet attrs) {
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
            AdvanceSearchValueCell itemCell = (AdvanceSearchValueCell) LayoutInflater.from(mContext).inflate(R.layout.advance_search_cell_value, null);

            List<SPECIFICATION_VALUE> itemList = null;

            int distance =  specification.value.size() - i;
            int cellCount = distance >= 2? 2:distance;

            itemList = specification.value.subList(i,i+cellCount);
            specValueLayout.addView(itemCell);
        }


    }
}
