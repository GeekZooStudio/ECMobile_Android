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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import com.insthub.BeeFramework.Utils.TimeUtil;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.HorizontalVariableListView.widget.HorizontalVariableListView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.B3_ProductPhotoAdapter;
import com.insthub.ecmobile.model.ConfigModel;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.model.GoodDetailModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;
import com.insthub.ecmobile.protocol.STATUS;

public class B2_ProductDetailActivity extends BaseActivity implements BusinessResponse, IXListViewListener
{
    private B3_ProductPhotoAdapter photoListAdapter;
    private GoodDetailModel dataModel;
    HorizontalVariableListView goodDetailPhotoList;

    private TextView goodBriefTextView;
    private TextView goodPromotePriceTextView;
    private TextView goodMarketPriceTextView;
    private TextView goodPropertyTextView;
    private TextView goodCategoryTextView;
    private TextView countDownTextView;

    private LinearLayout goodBasicParameterLayout;
    private LinearLayout goodsDesc;
    private LinearLayout goodsComment;

    private TextView title;
    private ImageView back;
    private ImageView share;

    private TextView addToCartTextView;
    private TextView buyNowTextView;
    private Boolean isBuyNow = false;
    private ImageView  collectionButton;
    private ImageView pager;

    private ImageView goodDetailShoppingCart;
    private TextView good_detail_shopping_cart_num;
    private LinearLayout good_detail_shopping_cart_num_bg;
    
    private View headView;
    private XListView xlistView;

    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    private Timer timer;
    private  Boolean isFresh=true;//是否选择的属性
    private final static int REQUEST_SHOPPINGCAR = 1;
    private final static int REQUEST_SPECIFICATION= 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.b2_product_detail);
        
        shared = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		editor = shared.edit();
        
        xlistView = (XListView) findViewById(R.id.good_detail_list);

        headView = LayoutInflater.from(this).inflate(R.layout.b2_product_detail_head, null);

        xlistView.addHeaderView(headView);
        xlistView.setPullLoadEnable(false);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		xlistView.setAdapter(null);

        dataModel = new GoodDetailModel(this);
        dataModel.addResponseListener(this);
        dataModel.goodId =  getIntent().getStringExtra("good_id");
        dataModel.fetchGoodDetail(Integer.parseInt(dataModel.goodId));

        goodDetailPhotoList = (HorizontalVariableListView)headView.findViewById(R.id.good_detail_photo_list);
        photoListAdapter = new B3_ProductPhotoAdapter(this,dataModel.goodDetail.pictures);
        goodDetailPhotoList.setAdapter(photoListAdapter);
        goodDetailPhotoList.setSelectionMode( HorizontalVariableListView.SelectionMode.Single );

        goodDetailPhotoList.setOverScrollMode( HorizontalVariableListView.OVER_SCROLL_NEVER );
        goodDetailPhotoList.setEdgeGravityY( Gravity.CENTER );

        final Resources resource = (Resources) getBaseContext().getResources();

        share = (ImageView) findViewById(R.id.top_view_share);
        share.setVisibility(View.VISIBLE);
        if((EcmobileManager.getSinaKey(this) == null || EcmobileManager.getSinaSecret(this) == null || EcmobileManager.getSinaCallback(this) == null) 
        		&& (EcmobileManager.getWeixinAppId(this) == null || EcmobileManager.getWeixinAppKey(this) == null) 
        		&& (EcmobileManager.getTencentKey(this) == null || EcmobileManager.getTencentSecret(this) == null || EcmobileManager.getTencentCallback(this) == null)) {
        	share.setVisibility(View.GONE);
        }
        
        share.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

                if(null != ConfigModel.getInstance().config && null != dataModel.goodDetail)
                {
                    Intent it = new Intent(B2_ProductDetailActivity.this,ShareActivity.class);
                    Resources resource = (Resources) getBaseContext().getResources();
                    String share=resource.getString(R.string.share_blog);
                    String url = share;
                    String goods_url = ConfigModel.getInstance().config.goods_url+dataModel.goodDetail.id;
                    it.putExtra("content",url);
                    it.putExtra("goods_url", goods_url);
                    if (null != dataModel.goodDetail.img.thumb)
                    {
                        it.putExtra("photoUrl",dataModel.goodDetail.img.thumb);
                    }

                    startActivity(it);
                    overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);

                }


			}
		});
        
        title = (TextView) findViewById(R.id.top_view_text);
        String det=resource.getString(R.string.gooddetail_product);
        title.setText(det);
        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {                
                finish();
            }
        });

        goodBriefTextView = (TextView)headView.findViewById(R.id.good_brief);
        goodPromotePriceTextView = (TextView)headView.findViewById(R.id.promote_price);
        goodMarketPriceTextView = (TextView)headView.findViewById(R.id.market_price);
        goodMarketPriceTextView.getPaint().setAntiAlias(true);
        goodMarketPriceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        countDownTextView = (TextView)headView.findViewById(R.id.count_down);

        goodPropertyTextView = (TextView)headView.findViewById(R.id.good_property);
        goodCategoryTextView = (TextView)headView.findViewById(R.id.good_category);
        goodCategoryTextView.setSingleLine(false);
        goodCategoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	for (int i = 0; i < dataModel.goodDetail.specification.size();i ++)
                {
                    SPECIFICATION  specification = dataModel.goodDetail.specification.get(i);
                    if (null != specification.attr_type &&
                            0 == specification.attr_type.compareTo(SPECIFICATION.SINGLE_SELECT) &&
                            false == GoodDetailDraft.getInstance().isHasSpecName(specification.name))
                    {
                        SPECIFICATION_VALUE specification_value_one = specification.value.get(0);
                        GoodDetailDraft.getInstance().addSelectedSpecification( specification_value_one );
                    }
                 }

            	if(dataModel.goodDetail.goods_number != null) {
            		GoodDetailDraft.getInstance().goodDetail = dataModel.goodDetail;
                    Intent it = new Intent(B2_ProductDetailActivity.this,SpecificationActivity.class);
                    it.putExtra("num", Integer.valueOf(dataModel.goodDetail.goods_number));
                    startActivity(it);
                    isFresh=false;
                    overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
                    
            	} else {
                    String che=resource.getString(R.string.check_the_network);            		
            		ToastView toast = new ToastView(B2_ProductDetailActivity.this, che);
            		toast.setGravity(Gravity.CENTER, 0, 0);
            		toast.show();
            	}
                
            }
        });

        goodBasicParameterLayout = (LinearLayout)headView.findViewById(R.id.good_basic_parameter);
        goodBasicParameterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(B2_ProductDetailActivity.this, B4_ProductParamActivity.class);
                startActivity(it);
            }
        });
        
        goodsDesc = (LinearLayout) headView.findViewById(R.id.goods_desc);
        goodsDesc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent(B2_ProductDetailActivity.this, B6_ProductDescActivity.class);
				intent.putExtra("id", Integer.parseInt(dataModel.goodDetail.id));
				startActivity(intent);
			}
		});
        
        goodsComment = (LinearLayout) headView.findViewById(R.id.goods_comment);
        goodsComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent(B2_ProductDetailActivity.this, B5_ProductCommentActivity.class);
				intent.putExtra("id", Integer.parseInt(dataModel.goodDetail.id));
				startActivity(intent);
			}
		});

        addToCartTextView = (TextView)findViewById(R.id.add_to_cart);
        addToCartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = shared.getString("uid", "");
                if(uid.equals(""))
                {
                    Intent intent = new Intent(B2_ProductDetailActivity.this, A0_SigninActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);                    
                    ToastView toast = new ToastView(B2_ProductDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                	isBuyNow = false;
                	cartCreate();
                }
            }
        });

        buyNowTextView   = (TextView)findViewById(R.id.buy_now);
        buyNowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String uid = shared.getString("uid", "");
                if(uid.equals(""))
                {
                    Intent intent = new Intent(B2_ProductDetailActivity.this, A0_SigninActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);                    
                    ToastView toast = new ToastView(B2_ProductDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                	isBuyNow = true;
                	cartCreate();
                    
                }

            }
        });

        collectionButton = (ImageView)findViewById(R.id.collection_button);
        collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String uid = shared.getString("uid", "");
                if(uid.equals(""))
                {
                    Intent intent = new Intent(B2_ProductDetailActivity.this, A0_SigninActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);                    
                    ToastView toast = new ToastView(B2_ProductDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                    if(dataModel.goodDetail.collected==1){
                        ToastView toast = new ToastView(B2_ProductDetailActivity.this, R.string.favorite_added);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        dataModel.collectCreate(Integer.parseInt(dataModel.goodId));
                        collectionButton.setImageResource(R.drawable.item_info_pushed_collect_btn);
                    }
                }
                
            }
        });

        goodDetailShoppingCart = (ImageView)findViewById(R.id.good_detail_shopping_cart);
        goodDetailShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String uid = shared.getString("uid", "");
                if(uid.equals(""))
                {
                    Intent intent = new Intent(B2_ProductDetailActivity.this, A0_SigninActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);                    
                    ToastView toast = new ToastView(B2_ProductDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                	Intent it = new Intent(B2_ProductDetailActivity.this,C0_ShoppingCartActivity.class);
                    startActivityForResult(it, REQUEST_SHOPPINGCAR);
                }
                    
            }
        });

        good_detail_shopping_cart_num = (TextView) findViewById(R.id.good_detail_shopping_cart_num);
        good_detail_shopping_cart_num_bg = (LinearLayout) findViewById(R.id.good_detail_shopping_cart_num_bg);
        
        pager = (ImageView) findViewById(R.id.good_detail_pager);
        pager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
			}
		});
        
        //设置购物车数量
        if(ShoppingCartModel.getInstance().goods_num == 0) {
        	good_detail_shopping_cart_num_bg.setVisibility(View.GONE);
        } else {
        	good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
        	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
        }
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    void cartCreate()
    {
        ArrayList<Integer> specIdList = new ArrayList<Integer>();

        boolean isRedrectToSpecification = false;
       if (GoodDetailDraft.getInstance().selectedSpecification.size() == 0)
       {
           for (int i = 0; i < dataModel.goodDetail.specification.size();i ++)
           {
               SPECIFICATION  specification = dataModel.goodDetail.specification.get(i);
               if (null != specification.attr_type && 0 == specification.attr_type.compareTo(SPECIFICATION.SINGLE_SELECT))
               {
                   SPECIFICATION_VALUE specification_value_one = specification.value.get(0);
                   GoodDetailDraft.getInstance().addSelectedSpecification( specification_value_one );
                   isRedrectToSpecification = true;
               }
            }

            if (isRedrectToSpecification)
            {
            	if(dataModel.goodDetail.goods_number != null) {            		
            		ToastView toast = new ToastView(this, R.string.select_specification_first);
            		toast.setGravity(Gravity.CENTER, 0, 0);
            		toast.show();
            		
                    GoodDetailDraft.getInstance().goodDetail = dataModel.goodDetail;
                    Intent it = new Intent(this,SpecificationActivity.class);
                    it.putExtra("num", Integer.valueOf(dataModel.goodDetail.goods_number));
                    it.putExtra("creat_cart", true);
                    startActivityForResult(it, REQUEST_SPECIFICATION);
                    isFresh=false;
                    overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
            	} else {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String che=resource.getString(R.string.check_the_network);            		
            		ToastView toast = new ToastView(B2_ProductDetailActivity.this, che);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
            	}
                
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                return false;
        }
        return true;
    }

    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int flag = msg.what;
            if (flag == 5)
            {
                countDownPromote();
                return;
            }
        }
    };

    public void countDownPromote()
    {
        Resources resource = (Resources) getBaseContext().getResources();
        String will_end=resource.getString(R.string.promote_will_end);
        String end = resource.getString(R.string.end);
        String contentString = "";
        contentString += will_end +"<br>" +  "<font color=#FF0000>"+TimeUtil.timeLeft(dataModel.goodDetail.promote_end_date)+"</font>"+"</br>"+end;
        Spanned htmlString = Html.fromHtml(contentString);
        countDownTextView.setText(htmlString);
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
    	xlistView.stopRefresh();
        Resources resource = (Resources) getBaseContext().getResources();
        if (url.endsWith(ApiInterface.GOODS))
        {
        	pager.setVisibility(View.GONE);
        	xlistView.setRefreshTime();
            GoodDetailDraft.getInstance().goodDetail = dataModel.goodDetail;
            goodBriefTextView.setText(dataModel.goodDetail.goods_name );
            String brp=resource.getString(R.string.formerprice);
            String marketStr=resource.getString(R.string.market_price);
            goodPromotePriceTextView.setText(dataModel.goodDetail.formated_promote_price);
            goodMarketPriceTextView.setText(marketStr+dataModel.goodDetail.market_price);

            if(dataModel.goodDetail.collected == 0)
            {
                collectionButton.setImageResource(R.drawable.item_info_collection_disabled_btn);
            }
            else
            {
                collectionButton.setImageResource(R.drawable.item_info_pushed_collect_btn);
            }
            String contentString = "";
            if (dataModel.goodDetail.is_shipping == "1")
            {
                String exe=resource.getString(R.string.exemption_from_postage);
                contentString += exe;
            }
            else
            {
                String not=resource.getString(R.string.not_pack_mail);
                contentString += not;
            }
            String brs=resource.getString(R.string.brstock);
            String stor=resource.getString(R.string.store_price);
            contentString += "<br>"+brs + dataModel.goodDetail.goods_number+"</br>";
            
            contentString += "<br>"+stor + dataModel.goodDetail.shop_price+"</br>";
            
            contentString += "<br>"+marketStr + dataModel.goodDetail.market_price+"</br>";
            
            
            if(dataModel.goodDetail.promote_price==0){
            	goodPromotePriceTextView.setText( dataModel.goodDetail.shop_price+"");
            } else {
            	contentString += "<br>"+brp + dataModel.goodDetail.formated_promote_price+"</br>";
            }
            
            for(int i=0;i<dataModel.goodDetail.rank_prices.size();i++) {
            	contentString += "<br>"+dataModel.goodDetail.rank_prices.get(i).rank_name+"：" + dataModel.goodDetail.rank_prices.get(i).price+"</br>";
            }

            Spanned htmlString = Html.fromHtml(contentString);
            
            goodPropertyTextView.setText(htmlString);
            goodCategoryTextView.setText(getSpecificationDesc());

            if (null != dataModel.goodDetail.promote_end_date && dataModel.goodDetail.promote_end_date.length() > 0)
            {
                if (TimeUtil.timeLeft(dataModel.goodDetail.promote_end_date).length() == 0)
                {
                    countDownTextView.setVisibility(View.GONE);
                }
                else
                {
                    if (null!= timer)
                    {
                        timer.cancel();
                        timer = null;
                    }

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run()
                        {
                            Message msg = handler.obtainMessage();
                            msg.what = 5;
                            handler.sendMessage(msg);
                        }
                    },new Date(),1000);
                    countDownTextView.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                countDownTextView.setVisibility(View.GONE);
            }

            photoListAdapter.dataList = dataModel.goodDetail.pictures;
            goodDetailPhotoList.setAdapter(photoListAdapter);
            
            //设置购物车数量
            if(ShoppingCartModel.getInstance().goods_num == 0) {
            	good_detail_shopping_cart_num_bg.setVisibility(View.GONE);
            } else {
            	good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
            	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
            }
            
        }
        else if (url.endsWith(ApiInterface.CART_CREATE))
        {   STATUS responseStatus = new STATUS();
            responseStatus.fromJson(jo.optJSONObject("status"));
        
            if (responseStatus.succeed == 1)
            {
                if (isBuyNow)
                {
                    Intent it = new Intent(B2_ProductDetailActivity.this, C0_ShoppingCartActivity.class);
                    B2_ProductDetailActivity.this.startActivityForResult(it, REQUEST_SHOPPINGCAR);
                    //更新购物车数量
                    ShoppingCartModel.getInstance().goods_num += GoodDetailDraft.getInstance().goodQuantity;
                    good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
                	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
                }
                else
                {                    
                    ToastView toast = new ToastView(this, R.string.add_to_cart_success);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                    //更新购物车数量
                    ShoppingCartModel.getInstance().goods_num += GoodDetailDraft.getInstance().goodQuantity;
                    good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
                	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
                    
                }
            }

        } else if(url.endsWith(ApiInterface.USER_COLLECT_CREATE)) {
            dataModel.goodDetail.collected=1;
        	ToastView toast = new ToastView(this, R.string.collection_success);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        goodCategoryTextView.setText(getSpecificationDesc());
        if(isFresh){
            dataModel.fetchGoodDetail(Integer.parseInt(dataModel.goodId));
        }
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageStart("GoodDetail");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this),"");
        }

    }

    public String getSpecificationDesc()
    {
        Resources resource = (Resources) getBaseContext().getResources();
        String none=resource.getString(R.string.none);

        boolean isSelected = GoodDetailDraft.getInstance().selectedSpecification.size() > 0? true:false;

        String speciationDesc = "";

        for (int i = 0; i < dataModel.goodDetail.specification.size();i ++)
        {
            SPECIFICATION  specification = dataModel.goodDetail.specification.get(i);
            speciationDesc += specification.name;
            speciationDesc += " : ";
            
            String selectedSpecificationValue =  "";

            for (int k = 0; k < GoodDetailDraft.getInstance().selectedSpecification.size();k++)
            {
                SPECIFICATION_VALUE localValue = GoodDetailDraft.getInstance().selectedSpecification.get(k);
                 if (null != localValue.specification && 0 == specification.name.compareTo(localValue.specification.name))
                 {
                     selectedSpecificationValue += localValue.label;
                     selectedSpecificationValue +="、";
                 }
            }
            if (null != selectedSpecificationValue && selectedSpecificationValue.length() > 0)
            {
                if (selectedSpecificationValue.endsWith("、"))
                {
                    selectedSpecificationValue = selectedSpecificationValue.substring(0,selectedSpecificationValue.length() -1);
                }
                speciationDesc+=  selectedSpecificationValue;
            }
            else
            {
                if (!isSelected)
                {

                    if (0 == specification.attr_type.compareTo(SPECIFICATION.SINGLE_SELECT) && specification.value.size() == 1)
                    {
                        for (int j = 0; j < specification.value.size(); j++)
                        {
                            SPECIFICATION_VALUE value = specification.value.get(j);
                            speciationDesc += value.label;
                            if (j != specification.value.size() -1)
                            {
                                speciationDesc+="、";
                            }
                        }

                        GoodDetailDraft.getInstance().selectedSpecification.add(specification.value.get(0));
                    }
                    else
                    {
                        speciationDesc += resource.getString(R.string.click_select_specification);
                    }
                }
                else
                {
                    speciationDesc += none;
                }

            }

            speciationDesc +="\n";
        }
        String num=resource.getString(R.string.amount);
        speciationDesc += num + GoodDetailDraft.getInstance().goodQuantity;
        return speciationDesc;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GoodDetailDraft.getInstance().clear();
    }

	@Override
	public void onRefresh(int id) {			
		dataModel.fetchGoodDetail(Integer.parseInt(dataModel.goodId));
	}

	@Override
	public void onLoadMore(int id) {		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_SHOPPINGCAR) {
			if(ShoppingCartModel.getInstance().goods_num == 0) {
				good_detail_shopping_cart_num_bg.setVisibility(View.GONE);
            } else {
            	good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
            	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
            }
		} else if(requestCode == REQUEST_SPECIFICATION) {
			if (resultCode == Activity.RESULT_OK) {
				ArrayList<Integer> specIdList = new ArrayList<Integer>();
				for (int i = 0; i< GoodDetailDraft.getInstance().selectedSpecification.size();i++)
		        {
		            SPECIFICATION_VALUE specification_value = GoodDetailDraft.getInstance().selectedSpecification.get(i);
		            specIdList.add(Integer.valueOf(specification_value.id));
		        }

		        dataModel.cartCreate(Integer.parseInt(dataModel.goodId),specIdList,GoodDetailDraft.getInstance().goodQuantity);
			}
		}
	}

    @Override
    protected void onPause() {
        super.onPause();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageEnd("GoodDetail");
            MobclickAgent.onPause(this);
        }
    }
}
