package com.insthub.ecmobile.activity;

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

import android.content.Context;

import android.content.res.Resources;

import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.protocol.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
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
import com.external.imagezoom.ImageViewTouch;
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

public class B3_ProductPhotoActivity extends BaseActivity implements BusinessResponse {

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
    

    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b3_product_photo);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        
        photoListView = new ArrayList<View>();
        photoPageAdapter = new Bee_PageAdapter(photoListView);

        backImageView = (ImageView)findViewById(R.id.nav_back_button);
        backImageView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
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
                Intent it = new Intent(B3_ProductPhotoActivity.this, C0_ShoppingCartActivity.class);
                B3_ProductPhotoActivity.this.startActivity(it);
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

        dataModel.cartCreate(Integer.parseInt(dataModel.goodId),specIdList,GoodDetailDraft.getInstance().goodQuantity);
    }


    public void addBannerView()
    {

        if (GoodDetailDraft.getInstance().goodDetail.pictures.size() > 0)
        {
            photoListView.clear();
            for (int i = 0; i < GoodDetailDraft.getInstance().goodDetail.pictures.size(); i++)
            {
                PHOTO photo = GoodDetailDraft.getInstance().goodDetail.pictures.get(i);
                ImageViewTouch viewOne =  (ImageViewTouch) LayoutInflater.from(this).inflate(R.layout.b3_product_photo_banner_cell,null);
                
                shared = getSharedPreferences("userInfo", 0); 
        		editor = shared.edit();
        		String imageType = shared.getString("imageType", "mind");

        		if(imageType.equals("high")) {
                    imageLoader.displayImage(photo.url,viewOne,EcmobileApp.options);
        		} else if(imageType.equals("low")) {
                    imageLoader.displayImage(photo.thumb,viewOne,EcmobileApp.options);
        		} else {
        			String netType = shared.getString("netType", "wifi");
        			if(netType.equals("wifi")) {
                        imageLoader.displayImage(photo.url,viewOne,EcmobileApp.options);
        			} else {
                        imageLoader.displayImage(photo.thumb,viewOne,EcmobileApp.options);
        			}
        		}

                photoListView.add(viewOne);


            }

            mIndicator.notifyDataSetChanged();
            photoPageAdapter.notifyDataSetChanged();
        }
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
        if (url.endsWith(ApiInterface.CART_DELETE))
        {   STATUS responseStatus =new STATUS();
            responseStatus.fromJson(jo.optJSONObject("status"));
            if (responseStatus.succeed == 1)
            {
                ToastView toast = new ToastView(B3_ProductPhotoActivity.this,  R.string.add_to_cart_success);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
