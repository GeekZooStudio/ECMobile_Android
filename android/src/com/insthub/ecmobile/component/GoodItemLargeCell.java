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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.GoodDetailActivity;
import com.insthub.ecmobile.activity.GoodsListActivity;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;

public class GoodItemLargeCell extends LinearLayout
{
    private WebImageView item_photo;
    private TextView    briefTextView;
    private TextView    priceContent;
    private TextView marketContent;
    Context mContext;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    public GoodItemLargeCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    void init()
    {
         if (null == item_photo)
         {
             item_photo = (WebImageView)findViewById(R.id.gooditem_photo);
         }

        if (null == briefTextView)
        {
            briefTextView = (TextView)findViewById(R.id.brief);
        }

        if (null == priceContent)
        {
            priceContent =    (TextView)findViewById(R.id.price_content);
        }
        
        if(null == marketContent) {
        	marketContent = (TextView) findViewById(R.id.market_content);
        	marketContent.getPaint().setAntiAlias(true);
        	marketContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        
        
    }

    public void bindData(final SIMPLEGOODS simplegoods)
    {
        init();
        
        shared = mContext.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		
		String imageType = shared.getString("imageType", "mind");
		
		if(imageType.equals("high")) {
			item_photo.setImageWithURL(mContext,simplegoods.img.url,R.drawable.default_image);
		} else if(imageType.equals("low")) {
			item_photo.setImageWithURL(mContext,simplegoods.img.thumb,R.drawable.default_image);
		} else {
			String netType = shared.getString("netType", "wifi");
			if(netType.equals("wifi")) {
				item_photo.setImageWithURL(mContext,simplegoods.img.url,R.drawable.default_image);
			} else {
				item_photo.setImageWithURL(mContext,simplegoods.img.thumb,R.drawable.default_image);
			}
		}

        briefTextView.setText(simplegoods.name);

        //priceContent.setText(simplegoods.shop_price);
        
        
        if (null!= simplegoods.promote_price&& simplegoods.promote_price.length() > 0)
        {
        	priceContent.setText("促销价格："+simplegoods.promote_price);
        }
        else
        {
        	priceContent.setText("商店价格："+simplegoods.shop_price);
        }
        
        marketContent.setText("市场价格："+simplegoods.market_price);
        
        
        item_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	Intent it = new Intent(mContext,GoodDetailActivity.class);
	        	it.putExtra("good_id", simplegoods.goods_id);
	            mContext.startActivity(it);
			
			}
		});

    }
}
