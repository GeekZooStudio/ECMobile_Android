package com.insthub.ecmobile.activity;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.external.HorizontalVariableListView.widget.HorizontalListView;
import com.external.activeandroid.util.Log;
import com.external.easing.Back;
import com.external.easing.Cubic;
import com.external.easing.Expo;
import com.external.easing.Sine;
import com.insthub.BeeFramework.Utils.Utils;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.GalleryImageAdapter;

public class GalleryImageActivity extends BaseActivity implements OnGestureListener, OnTouchListener {
	
	
	private ViewPager imagePager;
	private GalleryImageAdapter galleryImageAdapter;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private int pager_num;
    int total_page;
    FrameLayout backgroundLayout;
    HorizontalScrollView background_srcollview;
    HorizontalScrollView layer_srcollview;
    int  backgoundWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_image);
		
		shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
        
        boolean isFirstRun = shared.getBoolean("isFirstRun", true);  
        if(!isFirstRun) {
        	Intent it = new Intent(this,EcmobileMainActivity.class);
            startActivity(it);
            finish();
        }

        initLayout();
        backgroundLayout = (FrameLayout)findViewById(R.id.backgroundLayout);
        background_srcollview = (HorizontalScrollView)findViewById(R.id.background_srcollview);
        background_srcollview.setHorizontalScrollBarEnabled(false);

        layer_srcollview = (HorizontalScrollView)findViewById(R.id.layer_srcollview);
        layer_srcollview.setHorizontalScrollBarEnabled(false);

		imagePager = (ViewPager) findViewById(R.id.image_pager);
        //imagePager.setAlpha((float) 0.5);
		
		galleryImageAdapter = new GalleryImageAdapter(this);
		imagePager.setAdapter(galleryImageAdapter);
		imagePager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				pager_num = position + 1;
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
            {
                float realOffset = Cubic.easeIn(positionOffset, 0, 1, 1);

                total_page = galleryImageAdapter.getCount();
                float offset = (float) ((float)(position + realOffset)*1.0/total_page);
                int offsetPositon = (int)(backgoundWidth*offset);
              //  background_srcollview.scrollTo(offsetPositon,0);

                float layerRealOffset = Sine.easeIn(positionOffset, 0, 1, 1);
                float layerOffset = (float) ((float)(position + layerRealOffset)*1.0/total_page);
                int layerOffsetPositon = (int)(backgoundWidth*layerOffset);
                layer_srcollview.scrollTo(layerOffsetPositon,0);
            }
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
			}
		});
		
		imagePager.setOnTouchListener(this);
		
	}
	
	GestureDetector mygesture = new GestureDetector(this);  
    public boolean onTouch(View v, MotionEvent event) {  
        return mygesture.onTouchEvent(event);  
    }
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > 120) {      
			if(pager_num == 5) {
				Intent intent = new Intent(GalleryImageActivity.this,EcmobileMainActivity.class);
				startActivity(intent);
				finish();
				editor.putBoolean("isFirstRun", false);
	            editor.commit();
			}
        }   
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

    void initLayout()
    {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        backgoundWidth = dm.widthPixels*5;
        ViewGroup.LayoutParams layoutParams;

        ImageView back_image_one = (ImageView)findViewById(R.id.back_image_one);
        layoutParams = back_image_one.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        back_image_one.setLayoutParams( layoutParams);

        ImageView back_image_two = (ImageView)findViewById(R.id.back_image_two);
        layoutParams = back_image_two.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        back_image_two.setLayoutParams( layoutParams);

        ImageView back_image_three = (ImageView)findViewById(R.id.back_image_three);
        layoutParams = back_image_three.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        back_image_three.setLayoutParams( layoutParams);

        ImageView back_image_four = (ImageView)findViewById(R.id.back_image_four);
        layoutParams = back_image_four.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        back_image_four.setLayoutParams( layoutParams);

        ImageView back_image_five = (ImageView)findViewById(R.id.back_image_five);
        layoutParams = back_image_five.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        back_image_five.setLayoutParams( layoutParams);


        FrameLayout.LayoutParams frameLayoutParams;
        ImageView layer_image_one = (ImageView)findViewById(R.id.layer_image_one);
        frameLayoutParams = (FrameLayout.LayoutParams)layer_image_one.getLayoutParams();
        frameLayoutParams.height = dm.heightPixels;
        frameLayoutParams.width = dm.widthPixels;
        layer_image_one.setLayoutParams( frameLayoutParams);

        ImageView layer_image_two = (ImageView)findViewById(R.id.layer_image_two);
        frameLayoutParams = (FrameLayout.LayoutParams)layer_image_two.getLayoutParams();
        frameLayoutParams.height = dm.heightPixels;
        frameLayoutParams.width = dm.widthPixels;
        layer_image_two.setLayoutParams( frameLayoutParams);

        ImageView layer_image_three = (ImageView)findViewById(R.id.layer_image_three);
        frameLayoutParams = (FrameLayout.LayoutParams)layer_image_three.getLayoutParams();
        frameLayoutParams.height = dm.heightPixels;
        frameLayoutParams.width = dm.widthPixels;
        layer_image_three.setLayoutParams( frameLayoutParams);

        ImageView layer_image_four = (ImageView)findViewById(R.id.layer_image_four);
        frameLayoutParams = (FrameLayout.LayoutParams)layer_image_four.getLayoutParams();
        frameLayoutParams.height = dm.heightPixels;
        frameLayoutParams.width = dm.widthPixels;
        layer_image_four.setLayoutParams( frameLayoutParams);

        ImageView layer_image_five = (ImageView)findViewById(R.id.layer_image_five);
        frameLayoutParams = (FrameLayout.LayoutParams)layer_image_five.getLayoutParams();
        frameLayoutParams.height = dm.heightPixels;
        frameLayoutParams.width = dm.widthPixels;
        layer_image_five.setLayoutParams( frameLayoutParams);
    }


}
