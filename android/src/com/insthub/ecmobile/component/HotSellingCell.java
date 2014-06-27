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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotSellingCell extends LinearLayout
{
	Context mContext;
    private ImageView good_cell_photo_one;
    private ImageView good_cell_photo_two;
    private TextView     good_cell_price_one;
    private TextView     good_cell_price_two;
    private FrameLayout  good_cell_one;
    private FrameLayout  good_cell_two;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    
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
                    Intent it = new Intent(mContext, B2_ProductDetailActivity.class);
                    it.putExtra("good_id",simplegoods.id+"");
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
                    Intent it = new Intent(mContext, B2_ProductDetailActivity.class);
                    it.putExtra("good_id",simplegoods.id+"");
                    mContext.startActivity(it);
                    ((EcmobileMainActivity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
            });
        }

        if (null == good_cell_photo_one)
        {
            good_cell_photo_one = (ImageView)findViewById(R.id.good_cell_photo_one);
        }

        if (null == good_cell_photo_two)
        {
            good_cell_photo_two = (ImageView)findViewById(R.id.good_cell_photo_two);
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
                imageLoader.displayImage(goodOne.img.thumb,good_cell_photo_one, EcmobileApp.options);

    		} else if(imageType.equals("low")) {
                imageLoader.displayImage(goodOne.img.small,good_cell_photo_one, EcmobileApp.options);
    		} else {
    			String netType = shared.getString("netType", "wifi");
    			if(netType.equals("wifi")) {
                    imageLoader.displayImage(goodOne.img.thumb,good_cell_photo_one, EcmobileApp.options);
    			} else {
                    imageLoader.displayImage(goodOne.img.small,good_cell_photo_one, EcmobileApp.options);
    			}
    		}

            good_cell_price_one.setText(goodOne.promote_price);

            if (cellData.size() >1)
            {
                good_cell_two.setVisibility(View.VISIBLE);
                SIMPLEGOODS goodTwo = cellData.get(1);
                
        		if(imageType.equals("high")) {        			
                    imageLoader.displayImage(goodTwo.img.thumb,good_cell_photo_two, EcmobileApp.options);
        		} else if(imageType.equals("low")) {        			
                    imageLoader.displayImage(goodTwo.img.small,good_cell_photo_two, EcmobileApp.options);
        		} else {
        			String netType = shared.getString("netType", "wifi");
        			if(netType.equals("wifi")) {        				
                        imageLoader.displayImage(goodTwo.img.thumb,good_cell_photo_two, EcmobileApp.options);
        			} else {        				
                        imageLoader.displayImage(goodTwo.img.small,good_cell_photo_two, EcmobileApp.options);
        			}
        		}

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
