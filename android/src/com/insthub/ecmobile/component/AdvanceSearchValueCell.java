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
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
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
    public FrameLayout specification_layout_two;


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
            specification_layout_two = (FrameLayout)findViewById(R.id.specification_layout_two);
            specTwo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

    }


}
