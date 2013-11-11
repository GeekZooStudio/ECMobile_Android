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

import java.util.List;

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


public class SpecificationValueCell extends LinearLayout{

    private EventBus eventBus;
    private Context mContext;
    private TextView specOne;
    private TextView specTwo;
    private ImageView image1;
    private ImageView image2;
    private   List<SPECIFICATION_VALUE> dataList;
    public SpecificationValueCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        EventBus.getDefault().register(this);
    }


    void init()
    {
        if (null == specOne)
        {
        	image1 = (ImageView) findViewById(R.id.specification_value_img_one);
            specOne = (TextView)findViewById(R.id.specification_value_text_one);
            specOne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPECIFICATION_VALUE specification_value_one = dataList.get(0);
                    
                    // 多选
                    if ( 0 == specification_value_one.specification.attr_type.compareTo(SPECIFICATION.MULTIPLE_SELECT))
                    {
                        if (GoodDetailDraft.getInstance().isHasSpecId(specification_value_one.id))
                        {
                            Resources resource = (Resources) ((BaseActivity)mContext).getBaseContext().getResources();
                            ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);
                            GoodDetailDraft.getInstance().removeSpecId(specification_value_one.id);
                            specOne.setTextColor(normalTextColor);
                            specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                            image1.setVisibility(View.GONE);
                        }
                        else
                        {
                            specOne.setTextColor(Color.RED);
                            specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                            GoodDetailDraft.getInstance().addSelectedSpecification(specification_value_one);
                            image1.setVisibility(View.VISIBLE);
                        }
                    }
                    
                    // 单选
                    if ( 0 == specification_value_one.specification.attr_type.compareTo(SPECIFICATION.SINGLE_SELECT))
                    {
                        if (GoodDetailDraft.getInstance().isHasSpecId(specification_value_one.id))
                        {
                            Resources resource = (Resources) ((BaseActivity)mContext).getBaseContext().getResources();
                            ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);
                            GoodDetailDraft.getInstance().removeSpecId(specification_value_one.id);
                            specOne.setTextColor(normalTextColor);
                            specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                            image1.setVisibility(View.GONE);
                        }
                        else
                        {
                            specOne.setTextColor(Color.RED);
                            specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                            GoodDetailDraft.getInstance().addSelectedSpecification(specification_value_one);
                            image1.setVisibility(View.VISIBLE);
                        }
                    }


                    EventBus.getDefault().post(specification_value_one);



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

                    SPECIFICATION_VALUE specification_value_two = dataList.get(1);
                    
                    // 多选
                    if ( 0 == specification_value_two.specification.attr_type.compareTo(SPECIFICATION.MULTIPLE_SELECT))
                    {
                        if (GoodDetailDraft.getInstance().isHasSpecId(specification_value_two.id))
                        {
                            Resources resource = (Resources) ((BaseActivity)mContext).getBaseContext().getResources();
                            ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);
                            GoodDetailDraft.getInstance().removeSpecId(specification_value_two.id);
                            specTwo.setTextColor(normalTextColor);
                            specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                            image2.setVisibility(View.GONE);
                        }
                        else
                        {
                            specTwo.setTextColor(Color.RED);
                            specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                            GoodDetailDraft.getInstance().addSelectedSpecification(specification_value_two);
                            image2.setVisibility(View.VISIBLE);
                        }
                    }
                    
                    // 单选
                    if ( 0 == specification_value_two.specification.attr_type.compareTo(SPECIFICATION.SINGLE_SELECT))
                    {
                        if (GoodDetailDraft.getInstance().isHasSpecId(specification_value_two.id))
                        {
                            Resources resource = (Resources) ((BaseActivity)mContext).getBaseContext().getResources();
                            ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);
                            GoodDetailDraft.getInstance().removeSpecId(specification_value_two.id);
                            specTwo.setTextColor(normalTextColor);
                            specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                            image2.setVisibility(View.GONE);
                        }
                        else
                        {
                            specTwo.setTextColor(Color.RED);
                            specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                            GoodDetailDraft.getInstance().addSelectedSpecification(specification_value_two);
                            image2.setVisibility(View.VISIBLE);
                        }
                    }


                    EventBus.getDefault().post(specification_value_two);


                }
            });
        }

    }

    public void bindData(List<SPECIFICATION_VALUE> dataList)
    {
       init();
        this.dataList = dataList;
        if (dataList.size() > 0)
        {
            SPECIFICATION_VALUE specification_value = dataList.get(0);

            if (specification_value.price == 0)
            {
                //specOne.setText(specification_value.label);
                specOne.setText(specification_value.label+"\n("+specification_value.format_price+")");
            }
            else
            {
            	//specOne.setText(specification_value.label);
                specOne.setText(specification_value.label+"\n("+specification_value.format_price+")");
            }

            if (GoodDetailDraft.getInstance().isHasSpecId(specification_value.id))
            {
                specOne.setTextColor(Color.RED);
                specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                image1.setVisibility(View.VISIBLE);
            }

            if (dataList.size() > 1)
            {
                specTwo.setVisibility(View.VISIBLE);
                SPECIFICATION_VALUE specification_value_two = dataList.get(1);
                if (specification_value_two.price == 0)
                {
                    //specTwo.setText(specification_value_two.label);
                	specTwo.setText(specification_value_two.label+"\n("+specification_value_two.format_price+")");
                }
                else
                {
                	//specTwo.setText(specification_value_two.label);
                    specTwo.setText(specification_value_two.label+"\n("+specification_value_two.format_price+")");
                }

                if (GoodDetailDraft.getInstance().isHasSpecId(specification_value_two.id))
                {
                    specTwo.setTextColor(Color.RED);
                    specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                    image2.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                specTwo.setVisibility(View.INVISIBLE);
            }


        }

    }

    @Override
    public void removeView(View view) {
        super.removeView(view);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onEvent(Object event)
    {
        if (event.getClass() == SPECIFICATION_VALUE.class)
        {
            Resources resource = (Resources) ((BaseActivity)mContext).getBaseContext().getResources();
            ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);

            if ( 0 == ((SPECIFICATION_VALUE) event).specification.attr_type.compareTo(SPECIFICATION.MULTIPLE_SELECT))
            {
                return; 
            }
            if (dataList.size() > 0)
            {
                SPECIFICATION_VALUE eventDataOne = dataList.get(0);
                if (eventDataOne == event )
                {
                    specOne.setTextColor(Color.RED);
                    specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                    image1.setVisibility(View.VISIBLE);
                }
                else if ( 0 == eventDataOne.specification.name.compareTo(((SPECIFICATION_VALUE) event).specification.name))
                {

                    specOne.setTextColor(normalTextColor);
                    specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                    image1.setVisibility(View.GONE);
                }

                if (dataList.size() > 1)
                {
                    SPECIFICATION_VALUE eventDataTwo = dataList.get(1);
                    if (eventDataTwo == event)
                    {
                        specTwo.setTextColor(Color.RED);
                        specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                        image2.setVisibility(View.VISIBLE);
                    }
                    else  if ( 0 == eventDataTwo.specification.name.compareTo(((SPECIFICATION_VALUE) event).specification.name))
                    {
                        specTwo.setTextColor(normalTextColor);
                        specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                        image2.setVisibility(View.GONE);
                    }
                }
            }

        }
    }
}
