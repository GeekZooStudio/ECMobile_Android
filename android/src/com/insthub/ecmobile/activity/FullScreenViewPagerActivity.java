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

import java.util.ArrayList;

import android.content.Context;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;
import com.insthub.ecmobile.model.ConfigModel;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.model.GoodDetailModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.PHOTO;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;
import com.insthub.ecmobile.protocol.STATUS;


public class FullScreenViewPagerActivity extends BaseActivity implements BusinessResponse {

	private ImageView share;
    private ViewPager photoViewPager;
    private PageIndicator mIndicator;
    private ArrayList<View> photoListView;
    private Bee_PageAdapter photoPageAdapter;
    private ImageView rightButton;
    private TextView titleTextView;
    private ImageView backImageView;
    private TextView fullScreenAddToCart;

    private GoodDetailModel dataModel;

    private ImageView fullscreenShoppingCart;
    
    private Dialog dialog;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_view_pager);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        
        photoListView = new ArrayList<View>();
        photoPageAdapter = new Bee_PageAdapter(photoListView);

        backImageView = (ImageView)findViewById(R.id.nav_back_button);
        backImageView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                //
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        photoViewPager = (ViewPager)findViewById(R.id.fullscreen_viewpager);
        
        photoViewPager.setAdapter(photoPageAdapter);
        
        photoViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(photoViewPager);
        addBannerView();
        
        photoViewPager.setCurrentItem(position);

        rightButton = (ImageView)findViewById(R.id.item_grid_button);
        rightButton.setVisibility(View.GONE);
        share = (ImageView) findViewById(R.id.item_grid_share);
        share.setVisibility(View.GONE);
        share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//
//				// TODO Auto-generated method stub
//				LayoutInflater inflater = LayoutInflater.from(FullScreenViewPagerActivity.this);
//	            View view = inflater.inflate(R.layout.share_dialog,null);
//	            
//	            dialog = new Dialog(FullScreenViewPagerActivity.this, R.style.dialog);
//	    		dialog.setContentView(view);
//	    		
//	    		//dialog.setCanceledOnTouchOutside(false);
//	    		dialog.show();
//	            
//	            LinearLayout photoA = (LinearLayout) view.findViewById(R.id.register_photoA);
//	            LinearLayout photoB = (LinearLayout) view.findViewById(R.id.register_photoB);
//	            
//	            photoA.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//                        Resources resource = (Resources) getBaseContext().getResources();
//                        String share=resource.getString(R.string.share_blog);
//						String url = "http://v.t.sina.com.cn/share/share.php?title="+share+"&url="+ConfigModel.getInstance().config.goods_url+dataModel.goodDetail.id+"=source=geek-zoo.com";
//						Intent intent = new Intent(FullScreenViewPagerActivity.this, ShareWebActivity.class);
//						intent.putExtra("url", url);
//						startActivity(intent);
//					}
//				});
//	            
//	            photoB.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//
//		                dialog.dismiss();
//                        Resources resource = (Resources) getBaseContext().getResources();
//                        String not=resource.getString(R.string.not_support);
//		                //Toast.makeText(FullScreenViewPagerActivity.this, not, 0).show();
//		                ToastView toast = new ToastView(FullScreenViewPagerActivity.this, not);
//		                toast.show();
//					}
//				});

			}
		});

        titleTextView = (TextView)findViewById(R.id.navigationbar_title);
        titleTextView.setText(GoodDetailDraft.getInstance().goodDetail.goods_name);
        fullScreenAddToCart = (TextView)findViewById(R.id.full_screen_add_to_cart);
        fullScreenAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartCreate();
            }
        });

        dataModel = new GoodDetailModel(this);
        dataModel.addResponseListener(this);
        dataModel.goodDetail = GoodDetailDraft.getInstance().goodDetail;
        dataModel.goodId =  dataModel.goodDetail.id;

        fullscreenShoppingCart = (ImageView) findViewById(R.id.fullscreen_shoping_cart);
        fullscreenShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FullScreenViewPagerActivity.this, ShoppingCartActivity.class);
                FullScreenViewPagerActivity.this.startActivity(it);
            }
        });

    }


    void cartCreate()
    {
        ArrayList<Integer> specIdList = new ArrayList<Integer>();

        boolean isRedrectToSpecification = false;
        if (GoodDetailDraft.getInstance().selectedSpecification.size() == 0)
        {
            for (int i = 0; i < dataModel.goodDetail.specification.size();i ++)
            {
                SPECIFICATION specification = dataModel.goodDetail.specification.get(i);
                if (null != specification.attr_type && 0 == specification.attr_type.compareTo(SPECIFICATION.SINGLE_SELECT))
                {
                    SPECIFICATION_VALUE specification_value_one = specification.value.get(0);
                    GoodDetailDraft.getInstance().addSelectedSpecification( specification_value_one );
                    isRedrectToSpecification = true;
                }
            }

            if (isRedrectToSpecification)
            {
                //Toast.makeText(this, R.string.select_specification_first, 0).show();
                ToastView toast = new ToastView(this, R.string.select_specification_first);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent it = new Intent(this,SpecificationActivity.class);
                startActivity(it);
                return;
            }
        }


        for (int i = 0; i< GoodDetailDraft.getInstance().selectedSpecification.size();i++)
        {
            SPECIFICATION_VALUE specification_value = GoodDetailDraft.getInstance().selectedSpecification.get(i);
            specIdList.add(Integer.valueOf(specification_value.id));
        }

        dataModel.cartCreate(dataModel.goodId,specIdList,GoodDetailDraft.getInstance().goodQuantity);
    }


    public void addBannerView()
    {

        if (GoodDetailDraft.getInstance().goodDetail.pictures.size() > 0)
        {
            photoListView.clear();
            for (int i = 0; i < GoodDetailDraft.getInstance().goodDetail.pictures.size(); i++)
            {
                PHOTO photo = GoodDetailDraft.getInstance().goodDetail.pictures.get(i);
                WebImageView viewOne =  (WebImageView) LayoutInflater.from(this).inflate(R.layout.pager_imageview,null);
                
                shared = getSharedPreferences("userInfo", 0); 
        		editor = shared.edit();
        		String imageType = shared.getString("imageType", "mind");
        		
        		if(imageType.equals("high")) {
        			viewOne.setImageWithURL(this,photo.url,R.drawable.default_image);
        		} else if(imageType.equals("low")) {
        			viewOne.setImageWithURL(this,photo.thumb,R.drawable.default_image);
        		} else {
        			String netType = shared.getString("netType", "wifi");
        			if(netType.equals("wifi")) {
        				viewOne.setImageWithURL(this,photo.url,R.drawable.default_image);
        			} else {
        				viewOne.setImageWithURL(this,photo.thumb,R.drawable.default_image);
        			}
        		}
                
//                if (null != photo.url && photo.url.length() > 0)
//                {
//                    viewOne.setImageWithURL(this,photo.url,R.drawable.default_image);
//                }
//                else
//                {
//                    viewOne.setImageWithURL(this,photo.thumb,R.drawable.default_image);
//                }

                photoListView.add(viewOne);


            }

            mIndicator.notifyDataSetChanged();
            photoPageAdapter.notifyDataSetChanged();
        }
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
        if (url.endsWith(ProtocolConst.CARTCREATE))
        {   STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
            if (responseStatus.succeed == 1)
            {
                //Toast.makeText(getApplicationContext(), getString(R.string.add_to_cart_success), Toast.LENGTH_SHORT).show();
                ToastView toast = new ToastView(FullScreenViewPagerActivity.this, getString(R.string.add_to_cart_success));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

}
