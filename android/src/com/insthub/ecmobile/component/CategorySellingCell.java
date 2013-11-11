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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.activity.GoodDetailActivity;
import com.insthub.ecmobile.activity.GoodsListActivity;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import org.json.JSONException;

public class CategorySellingCell extends LinearLayout 
{
	Context mContext;
    private WebImageView good_cell_photo_one;
    private WebImageView good_cell_photo_two;
    private WebImageView good_cell_photo_three;
    private TextView good_cell_name_one;
    private TextView     good_cell_name_two;
    private TextView     good_cell_name_three;
    private TextView good_cell_price_two;
    private TextView good_cell_price_three;
    
    private LinearLayout good_cell_one;
    private LinearLayout  good_cell_two;
    private LinearLayout  good_cell_three;
    CATEGORYGOODS categorygoods;
    Handler mHandler;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	public CategorySellingCell(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                bindDataDelay();
            }
        };
	}

    void init()
    {
    	
        if (null == good_cell_one)
        {
            good_cell_one = (LinearLayout)findViewById(R.id.good_cell_one);
        }

        if (null == good_cell_two)
        {
            good_cell_two = (LinearLayout)findViewById(R.id.good_cell_two);
        }

        if (null == good_cell_three)
        {
            good_cell_three = (LinearLayout)findViewById(R.id.good_cell_three);
        }

        if (null == good_cell_photo_one)
        {
            good_cell_photo_one = (WebImageView)findViewById(R.id.good_cell_photo_one);
        }

        if (null == good_cell_photo_two)
        {
            good_cell_photo_two = (WebImageView)findViewById(R.id.good_cell_photo_two);
        }

        if (null == good_cell_photo_three)
        {
            good_cell_photo_three = (WebImageView)findViewById(R.id.good_cell_photo_three);
        }

        if (null == good_cell_name_one)
        {
            good_cell_name_one = (TextView)findViewById(R.id.good_cell_name_one);
        }

        if (null == good_cell_name_two)
        {
            good_cell_name_two = (TextView)findViewById(R.id.good_cell_name_two);
        }

        if (null == good_cell_name_three)
        {
            good_cell_name_three = (TextView)findViewById(R.id.good_cell_name_three);
        }
        
        if(null == good_cell_price_two) {
        	good_cell_price_two = (TextView)findViewById(R.id.good_cell_price_two);
        }
        
        if(null == good_cell_price_three) {
        	good_cell_price_three = (TextView)findViewById(R.id.good_cell_price_three);
        }

    }
    int count = 0;

    public void bindDataDelay()
    {
        init();
        ArrayList<SIMPLEGOODS> listData = categorygoods.goods;
        
        
        shared = mContext.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");
		

        if (null != categorygoods.name)
        {
            good_cell_name_one.setText(categorygoods.name);
            count++;
            
            good_cell_one.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				Intent it = new Intent(mContext, GoodsListActivity.class);
                    FILTER filter = new FILTER();
                    filter.category_id = String.valueOf(categorygoods.id);
                    try
                    {
                        it.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                    }
                    catch (JSONException e)
                    {

                    }

                    mContext.startActivity(it);
                    ((Activity) mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    			}
    		});
        }
        
        if (listData.size() >0)
        {
            SIMPLEGOODS goodOne = listData.get(0);
            
            good_cell_photo_one.setVisibility(View.VISIBLE);
            
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
//                
//            }

            if (listData.size() >1)
            {
                good_cell_two.setVisibility(View.VISIBLE);
                final SIMPLEGOODS goodTwo = listData.get(1);
                if (null != goodTwo && null != goodTwo.img && null != goodTwo.img.thumb && null != goodTwo.img.small)
                {
                	if(imageType.equals("high")) {
                		good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.thumb,R.drawable.default_image);
            		} else if(imageType.equals("low")) {
            			good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.small,R.drawable.default_image);
            		} else {
            			String netType = shared.getString("netType", "wifi");
            			if(netType.equals("wifi")) {
            				good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.thumb,R.drawable.default_image);
            			} else {
            				good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.small,R.drawable.default_image);
            			}
            		}
                	
                    //good_cell_photo_two.setImageWithURL(mContext,goodTwo.img.url,R.drawable.default_image);
                    
                    good_cell_two.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							 Intent it = new Intent(mContext, GoodDetailActivity.class);
			                 it.putExtra("good_id",goodTwo.id);
			                 mContext.startActivity(it);
			                 ((EcmobileMainActivity)mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
						}
					});
                    
                }
                good_cell_name_two.setText(goodTwo.name);
                good_cell_price_two.setText(goodTwo.shop_price);

                if (listData.size() > 2)
                {
                    good_cell_three.setVisibility(View.VISIBLE);
                    final SIMPLEGOODS goodThree = listData.get(2);
                    if (null != goodThree && null != goodThree.img && null != goodThree.img.thumb && null != goodThree.img.small)
                    {
                    	
                    	if(imageType.equals("high")) {
                    		good_cell_photo_three.setImageWithURL(mContext,goodThree.img.thumb,R.drawable.default_image);
                		} else if(imageType.equals("low")) {
                			good_cell_photo_three.setImageWithURL(mContext,goodThree.img.small,R.drawable.default_image);
                		} else {
                			String netType = shared.getString("netType", "wifi");
                			if(netType.equals("wifi")) {
                				good_cell_photo_three.setImageWithURL(mContext,goodThree.img.thumb,R.drawable.default_image);
                			} else {
                				good_cell_photo_three.setImageWithURL(mContext,goodThree.img.small,R.drawable.default_image);
                			}
                		}
                    	
                        //good_cell_photo_three.setImageWithURL(mContext,goodThree.img.url,R.drawable.default_image);
                        
                        good_cell_three.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								 Intent it = new Intent(mContext, GoodDetailActivity.class);
			                     it.putExtra("good_id",goodThree.id);
			                     mContext.startActivity(it);
			                     ((EcmobileMainActivity)mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
							}
						});
                       
                    }
                    good_cell_name_three.setText(goodThree.name);
                    good_cell_price_three.setText(goodThree.shop_price);
                }
                else
                {
                    good_cell_three.setVisibility(View.INVISIBLE);
                }
            }
            else
            {
                good_cell_two.setVisibility(View.INVISIBLE);
                good_cell_three.setVisibility(View.INVISIBLE);
            }
        } else {
            good_cell_photo_one.setVisibility(View.INVISIBLE);
            good_cell_two.setVisibility(View.INVISIBLE);
            good_cell_three.setVisibility(View.INVISIBLE);
        }
    }

    public void bindData(CATEGORYGOODS categorygoods)
    {
        this.categorygoods = categorygoods;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);

    	
    }
}
