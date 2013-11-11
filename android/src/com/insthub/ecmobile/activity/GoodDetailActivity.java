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
import com.insthub.ecmobile.adapter.GoodDetailPhotoAdapter;
import com.insthub.ecmobile.model.ConfigModel;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.model.GoodDetailModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;
import com.insthub.ecmobile.protocol.STATUS;

public class GoodDetailActivity extends BaseActivity implements BusinessResponse, IXListViewListener
{
    private GoodDetailPhotoAdapter photoListAdapter;
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

    private Dialog dialog;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.good_detail_list);
        
        shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
        
        xlistView = (XListView) findViewById(R.id.good_detail_list);

        headView = LayoutInflater.from(this).inflate(R.layout.good_detail_activity, null);

        xlistView.addHeaderView(headView);
        xlistView.setPullLoadEnable(false);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		xlistView.setAdapter(null);

        dataModel = new GoodDetailModel(this);
        dataModel.addResponseListener(this);
        dataModel.goodId =  getIntent().getIntExtra("good_id",0);
        
        goodDetailPhotoList = (HorizontalVariableListView)headView.findViewById(R.id.good_detail_photo_list);
        photoListAdapter = new GoodDetailPhotoAdapter(this,dataModel.goodDetail.pictures);
        goodDetailPhotoList.setAdapter(photoListAdapter);
        goodDetailPhotoList.setSelectionMode( HorizontalVariableListView.SelectionMode.Single );

        goodDetailPhotoList.setOverScrollMode( HorizontalVariableListView.OVER_SCROLL_NEVER );
        goodDetailPhotoList.setEdgeGravityY( Gravity.CENTER );

        dataModel.fetchGoodDetail(dataModel.goodId);

        final Resources resource = (Resources) getBaseContext().getResources();

        share = (ImageView) findViewById(R.id.top_view_share);
        share.setVisibility(View.VISIBLE);
        
        title = (TextView) findViewById(R.id.top_view_text);
        String det=resource.getString(R.string.gooddetail_product);
        title.setText(det);
        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
                    Intent it = new Intent(GoodDetailActivity.this,SpecificationActivity.class);
                    it.putExtra("num", Integer.valueOf(dataModel.goodDetail.goods_number));
                    startActivity(it);
                    overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
                    
            	} else {
                    String che=resource.getString(R.string.check_the_network);
            		//Toast.makeText(GoodDetailActivity.this,che, 0).show();
            		ToastView toast = new ToastView(GoodDetailActivity.this, che);
            		toast.setGravity(Gravity.CENTER, 0, 0);
            		toast.show();
            	}
                
            }
        });

        goodBasicParameterLayout = (LinearLayout)headView.findViewById(R.id.good_basic_parameter);
        goodBasicParameterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(GoodDetailActivity.this, GoodPropertyActivity.class);
                startActivity(it);
            }
        });
        
        goodsDesc = (LinearLayout) headView.findViewById(R.id.goods_desc);
        goodsDesc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GoodDetailActivity.this, GoodsDescActivity.class);
				intent.putExtra("id", dataModel.goodDetail.id);
				startActivity(intent);
			}
		});
        
        goodsComment = (LinearLayout) headView.findViewById(R.id.goods_comment);
        goodsComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GoodDetailActivity.this, CommentActivity.class);
				intent.putExtra("id", dataModel.goodDetail.id);
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
                    Intent intent = new Intent(GoodDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);
                    //Toast toast = Toast.makeText(GoodDetailActivity.this, nol, 0);
                    ToastView toast = new ToastView(GoodDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
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
                    Intent intent = new Intent(GoodDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);
                    //Toast toast = Toast.makeText(GoodDetailActivity.this,nol , 0);
                    ToastView toast = new ToastView(GoodDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                	cartCreate();
                    isBuyNow = true;
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
                    Intent intent = new Intent(GoodDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);
                    //Toast toast = Toast.makeText(GoodDetailActivity.this, nol, 0);
                    ToastView toast = new ToastView(GoodDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                	dataModel.collectCreate(dataModel.goodId);
                    collectionButton.setEnabled(false);
                	collectionButton.setImageResource(R.drawable.item_info_collection_btn);
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
                    Intent intent = new Intent(GoodDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    String nol=resource.getString(R.string.no_login);
                    //Toast toast = Toast.makeText(GoodDetailActivity.this, nol, 0);
                    ToastView toast = new ToastView(GoodDetailActivity.this, nol);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                } else {
                	Intent it = new Intent(GoodDetailActivity.this,ShoppingCartActivity.class);
                    startActivityForResult(it, 1);
                }
                    
            }
        });

        good_detail_shopping_cart_num = (TextView) findViewById(R.id.good_detail_shopping_cart_num);
        good_detail_shopping_cart_num_bg = (LinearLayout) findViewById(R.id.good_detail_shopping_cart_num_bg);
        
        pager = (ImageView) findViewById(R.id.good_detail_pager);
        pager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
            		//Toast.makeText(this, R.string.select_specification_first, 0).show();
            		ToastView toast = new ToastView(this, R.string.select_specification_first);
            		toast.setGravity(Gravity.CENTER, 0, 0);
            		toast.show();
            		
                    GoodDetailDraft.getInstance().goodDetail = dataModel.goodDetail;
                    Intent it = new Intent(this,SpecificationActivity.class);
                    it.putExtra("num", Integer.valueOf(dataModel.goodDetail.goods_number));
                    startActivity(it);
                    overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
            	} else {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String che=resource.getString(R.string.check_the_network);
            		//Toast toast = Toast.makeText(GoodDetailActivity.this, che, 0);
            		ToastView toast = new ToastView(GoodDetailActivity.this, che);
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

        dataModel.cartCreate(dataModel.goodId,specIdList,GoodDetailDraft.getInstance().goodQuantity);
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
        if (url.endsWith(ProtocolConst.GOODSDETAIL))
        {
        	//collectionButton
        	pager.setVisibility(View.GONE);
        	xlistView.setRefreshTime();
            GoodDetailDraft.getInstance().clear();
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
                collectionButton.setImageResource(R.drawable.item_info_collection_btn);
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
            
            if(dataModel.goodDetail.promote_price.equals("0")) {
            	//contentString += "<br>促销价格：无</br>";
            	goodPromotePriceTextView.setText( dataModel.goodDetail.shop_price+"");
            } else {
            	contentString += "<br>"+brp + dataModel.goodDetail.formated_promote_price+"</br>";
            }
            
            for(int i=0;i<dataModel.goodDetail.rank_prices.size();i++) {
            	contentString += "<br>"+dataModel.goodDetail.rank_prices.get(i).rank_name+"：" + dataModel.goodDetail.rank_prices.get(i).price+"</br>";
            }

            Spanned htmlString = Html.fromHtml(contentString);
            
            goodPropertyTextView.setText(htmlString);
            goodCategoryTextView.setText(getSpeciationDesc());

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
        else if (url.endsWith(ProtocolConst.CARTCREATE))
        {   STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
            if (responseStatus.succeed == 1)
            {
                if (isBuyNow)
                {
                    Intent it = new Intent(GoodDetailActivity.this, ShoppingCartActivity.class);
                    GoodDetailActivity.this.startActivityForResult(it, 1);
                    //更新购物车数量
                    ShoppingCartModel.getInstance().goods_num += GoodDetailDraft.getInstance().goodQuantity;
                    good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
                	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
                }
                else
                {
                    //Toast toast = Toast.makeText(GoodDetailActivity.this, R.string.add_to_cart_success, 0);
                    ToastView toast = new ToastView(this, R.string.add_to_cart_success);
        	        toast.setGravity(Gravity.CENTER, 0, 0);
        	        toast.show();
                    //更新购物车数量
                    ShoppingCartModel.getInstance().goods_num += GoodDetailDraft.getInstance().goodQuantity;
                    good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
                	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
                    
                }
            }

        } else if(url.endsWith(ProtocolConst.COLLECTION_CREATE)) {
        	//Toast toast = Toast.makeText(GoodDetailActivity.this, R.string.collection_success, 0);
        	ToastView toast = new ToastView(this, R.string.collection_success);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        goodCategoryTextView.setText(getSpeciationDesc());
        isBuyNow = false;
    }

    public String getSpeciationDesc()
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
                 if (0 == specification.name.compareTo(localValue.specification.name))
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
                    for (int j = 0; j < specification.value.size(); j++)
                    {
                        SPECIFICATION_VALUE value = specification.value.get(j);
                        speciationDesc += value.label;
                        if (j != specification.value.size() -1)
                        {
                            speciationDesc+="、";
                        }
                    }
                }
                else
                {
                    speciationDesc += none;
                }

            }

            speciationDesc +="\n";
        }
        resource = (Resources) getBaseContext().getResources();
        String num=resource.getString(R.string.number);
        speciationDesc += num + GoodDetailDraft.getInstance().goodQuantity;
        //speciationDesc += "\n总价：" + GoodDetailDraft.getInstance().getTotalPrice();
        return speciationDesc;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GoodDetailDraft.getInstance().clear();
    }

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		
		dataModel.fetchGoodDetail(dataModel.goodId);
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1) {
			if(ShoppingCartModel.getInstance().goods_num == 0) {
				good_detail_shopping_cart_num_bg.setVisibility(View.GONE);
            } else {
            	good_detail_shopping_cart_num_bg.setVisibility(View.VISIBLE);
            	good_detail_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
            }
		}
	}


}
