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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodItemLargeCell extends LinearLayout
{
    private ImageView item_photo;
    private TextView    briefTextView;
    private TextView    priceContent;
    private TextView marketContent;
    Context mContext;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public GoodItemLargeCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    void init()
    {
         if (null == item_photo)
         {
             item_photo = (ImageView)findViewById(R.id.gooditem_photo);
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
            imageLoader.displayImage(simplegoods.img.url,item_photo, EcmobileApp.options);

		} else if(imageType.equals("low")) {
            imageLoader.displayImage(simplegoods.img.thumb,item_photo, EcmobileApp.options);
		} else {
			String netType = shared.getString("netType", "wifi");
			if(netType.equals("wifi")) {
                imageLoader.displayImage(simplegoods.img.url,item_photo, EcmobileApp.options);

			} else {
                imageLoader.displayImage(simplegoods.img.thumb,item_photo, EcmobileApp.options);
			}
		}

        briefTextView.setText(simplegoods.name);

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
				 
	        	Intent it = new Intent(mContext,B2_ProductDetailActivity.class);
	        	it.putExtra("good_id", simplegoods.id);
	            mContext.startActivity(it);
			
			}
		});

    }
}
