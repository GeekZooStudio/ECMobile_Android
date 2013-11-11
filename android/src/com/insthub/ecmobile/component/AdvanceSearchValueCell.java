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
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;

public class AdvanceSearchValueCell extends LinearLayout{

    private Context mContext;
    public TextView specOne;
    public TextView specTwo;
    public ImageView image1;
    public ImageView image2;

    public AdvanceSearchValueCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init()
    {
        if (null == specOne)
        {
            image1 = (ImageView) findViewById(R.id.specification_value_img_one);
            specOne = (TextView)findViewById(R.id.specification_value_text_one);
            specOne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {

                }
            });
        }

        if (null == specTwo)
        {
            image2 = (ImageView) findViewById(R.id.specification_value_img_two);
            specTwo = (TextView)findViewById(R.id.specification_value_text_two);
            specTwo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

    }


}
