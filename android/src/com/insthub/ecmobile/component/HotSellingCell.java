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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.activity.GoodDetailActivity;
import com.insthub.ecmobile.activity.GoodsListActivity;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;


public class HotSellingCell extends LinearLayout
{
	Context mContext;
    private WebImageView good_cell_photo_one;
    private WebImageView good_cell_photo_two;
    private TextView     good_cell_price_one;
    private TextView     good_cell_price_two;
    private FrameLayout  good_cell_one;
    private FrameLayout  good_cell_two;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    
    ArrayList<SIMPLEGOODS> cellData = new ArrayList<SIMPLEGOODS>();
    Handler mHandler;

    public HotSellingCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                bindDataDelay();
                return false;
            }
        }) ;

    }

    void init()
    {
        if (null == good_cell_one)
        {
            good_cell_one = (FrameLayout)findViewById(R.id.good_cell_one);
            good_cell_one.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    SIMPLEGOODS simplegoods = cellData.get(0);
                    Intent it = new Intent(mContext, GoodDetailActivity.class);
                    it.putExtra("good_id",simplegoods.id);
                    mContext.startActivity(it);
                    ((EcmobileMainActivity)mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                }
            });
        }

        if (null == good_cell_two)
        {
            good_cell_two = (FrameLayout)findViewById(R.id.good_cell_two);
            good_cell_two.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    SIMPLEGOODS simplegoods = cellData.get(1);
                    Intent it = new Intent(mContext, GoodDetailActivity.class);
                    it.putExtra("good_id",simplegoods.id);
                    mContext.startActivity(it);
                    ((EcmobileMainActivity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
            });
        }

        if (null == good_cell_photo_one)
        {
            good_cell_photo_one = (WebImageView)findViewById(R.id.good_cell_photo_one);
        }

        if (null == good_cell_photo_two)
        {
            good_cell_photo_two = (WebImageView)findViewById(R.id.good_cell_photo_two);
        }

        if (null == good_cell_price_one)
        {
            good_cell_price_one = (TextView)findViewById(R.id.good_cell_price_one);
        }

        if (null == good_cell_price_two)
        {
            good_cell_price_two = (TextView)findViewById(R.id.good_cell_price_two);
        }

    }

    public void bindDataDelay()
    {
        init();
        if (cellData.size() >0)
        {
            SIMPLEGOODS goodOne = cellData.get(0);
            
            shared = mContext.getSharedPreferences("userInfo", 0); 
    		editor = shared.edit();
    		String imageType = shared.getString("imageType", "mind");
    		
    		if(imageType.equals("high")) {
    			good_cell_photo_one.setImageWithURL(mContext,goodOne.img.thumb,R.drawable.default_image);
    		} else if(imageType.equals("low")) {
    			good_cell_photo_one.setImageWithURL(mContext,goodOne.img.small,R.drawable.default_image);
    		} else {
    			String netType = shared.getString("netType", "wifi");
    			if(netType.equals("wifi")) {
    				good_cell_photo_one.setImageWithURL(mContext,goodOne.img.thumb,R.drawable.default_image);
    			} else {
    				good_cell_photo_one.setImageWithURL(mContext,goodOne.img.small,R.drawable.default_image);
    			}
    		}
            
//            if (null != goodOne && null != goodOne.img && null != goodOne.img.url)
//            {
//                good_cell_photo_one.setImageWithURL(mContext,goodOne.img.url,R.drawable.default_image);
//            }

            good_cell_price_one.setText(goodOne.promote_price);

            if (cellData.size() >1)
            {
                good_cell_two.setVisibility(View.VISIBLE);
                SIMPLEGOODS goodTwo = cellData.get(1);
                
        		if(imageType.equals("high")) {
        			//System.out.println("high--wifi");
        			good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.thumb,R.drawable.default_image);
        		} else if(imageType.equals("low")) {
        			//System.out.println("low--3g");
        			good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.small,R.drawable.default_image);
        		} else {
        			String netType = shared.getString("netType", "wifi");
        			if(netType.equals("wifi")) {
        				//System.out.println("mind--wifi");
        				good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.thumb,R.drawable.default_image);
        			} else {
        				//System.out.println("mind--3g");
        				good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.small,R.drawable.default_image);
        			}
        		}
                
//                if (null != goodTwo && null != goodTwo.img && null != goodTwo.img.url)
//                {
//                    good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.url,R.drawable.default_image);
//                }
                good_cell_price_two.setText(goodTwo.promote_price);
            }
            else
            {
                good_cell_two.setVisibility(View.INVISIBLE);
            }
        }
    }
    
    public void bindData(List<SIMPLEGOODS> listData)
    {
        cellData.clear();
        cellData.addAll(listData);
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);
    }
}
