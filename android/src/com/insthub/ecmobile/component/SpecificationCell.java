package com.insthub.ecmobile.component;

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

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */
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
