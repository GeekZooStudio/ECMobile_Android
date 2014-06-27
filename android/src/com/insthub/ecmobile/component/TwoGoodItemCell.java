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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class TwoGoodItemCell extends LinearLayout
{
    Context mContext;
    private ImageView good_cell_photo_one;
    private ImageView good_cell_photo_two;
    private TextView    good_cell_price_one;
    private TextView    good_cell_price_two;
    private TextView market_price_one;
    private TextView market_price_two;
    private TextView    good_cell_desc_one;
    private TextView    good_cell_desc_two;
    private LinearLayout good_cell_one;
    private LinearLayout  good_cell_two;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

	public TwoGoodItemCell(Context context, AttributeSet attrs) {
		super(context, attrs);
        mContext = context;
	}

    void init()
    {
        if (null == good_cell_one)
        {
            good_cell_one = (LinearLayout)findViewById(R.id.good_item_one);
        }

        if (null == good_cell_two)
        {
            good_cell_two = (LinearLayout)findViewById(R.id.good_item_two);
        }

        if (null == good_cell_photo_one)
        {
            good_cell_photo_one = (ImageView)good_cell_one.findViewById(R.id.gooditem_photo);
        }

        if (null == good_cell_photo_two)
        {
            good_cell_photo_two = (ImageView)good_cell_two.findViewById(R.id.gooditem_photo);
        }

        if (null == good_cell_price_one)
        {
            good_cell_price_one = (TextView)good_cell_one.findViewById(R.id.shop_price);
        }

        if (null == good_cell_price_two)
        {
            good_cell_price_two = (TextView)good_cell_two.findViewById(R.id.shop_price);
        }

        if (null == good_cell_desc_one)
        {
            good_cell_desc_one = (TextView)good_cell_one.findViewById(R.id.good_desc);
        }

        if (null == good_cell_desc_two)
        {
            good_cell_desc_two = (TextView)good_cell_two.findViewById(R.id.good_desc);
        }
        
        if (null == market_price_one)
        {
        	market_price_one = (TextView)good_cell_one.findViewById(R.id.promote_price);
        	market_price_one.getPaint().setAntiAlias(true);
        	market_price_one.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        
        if (null == market_price_two)
        {
        	market_price_two = (TextView)good_cell_two.findViewById(R.id.promote_price);
        	market_price_two.getPaint().setAntiAlias(true);
        	market_price_two.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    public void bindData(List<SIMPLEGOODS> listData)
    {
        init();
        
        shared = mContext.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		
		String imageType = shared.getString("imageType", "mind");
		
		

        if (listData.size() >0)
        {
            final SIMPLEGOODS goodOne = listData.get(0);
            if (null != goodOne && null != goodOne.img && null != goodOne.img.thumb && null != goodOne.img.small)
            {
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
                
            }

            if (null!= goodOne.promote_price&& goodOne.promote_price.length() > 0)
            {
                good_cell_price_one.setText(goodOne.promote_price);
            }
            else
            {
                good_cell_price_one.setText(goodOne.shop_price);
            }


            market_price_one.setText(goodOne.market_price);
            good_cell_desc_one.setText(goodOne.name);
            
            good_cell_photo_one.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 
					Intent it = new Intent(mContext,B2_ProductDetailActivity.class);
		        	it.putExtra("good_id", goodOne.goods_id);
		            mContext.startActivity(it);
				}
			});

            if (listData.size() >1)
            {
                good_cell_two.setVisibility(View.VISIBLE);
                final SIMPLEGOODS goodTwo = listData.get(1);
                if (null != goodTwo && null != goodTwo.img && null != goodTwo.img.thumb && null != goodTwo.img.small)
                {
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
                }

                if (null != goodTwo.promote_price && goodTwo.promote_price.length() > 0)
                {
                    good_cell_price_two.setText(goodTwo.promote_price);
                }
                else
                {
                    good_cell_price_two.setText(goodTwo.shop_price);
                }

                market_price_two.setText(goodTwo.market_price);
                good_cell_desc_two.setText(goodTwo.name);
                good_cell_photo_two.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 
						Intent it = new Intent(mContext,B2_ProductDetailActivity.class);
			        	it.putExtra("good_id", goodTwo.goods_id);
			            mContext.startActivity(it);
					}
				});
                
            }
            else
            {
                good_cell_two.setVisibility(View.INVISIBLE);
            }
        }
    }
	

}
