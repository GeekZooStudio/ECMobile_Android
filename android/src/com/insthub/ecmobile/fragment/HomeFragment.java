package com.insthub.ecmobile.fragment;

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

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.*;
import com.insthub.ecmobile.activity.GoodDetailActivity;
import com.insthub.ecmobile.activity.ShopNotifyActivity;
import com.insthub.ecmobile.model.*;
import com.insthub.ecmobile.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyListView;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.BannerWebActivity;
import com.insthub.ecmobile.activity.GoodsListActivity;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;
import com.insthub.ecmobile.adapter.HomeFragmentAdapter;
import com.insthub.ecmobile.component.CategorySellingCell;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;
import com.insthub.ecmobile.protocol.PLAYER;


public class HomeFragment extends BaseFragment implements BusinessResponse,XListView.IXListViewListener
{
    private ViewPager bannerViewPager;
    private PageIndicator mIndicator;
    private MyListView mListView;
    private HomeFragmentAdapter listAdapter;

    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;
    FrameLayout bannerView;

    private View mTouchTarget;
    private ShoppingCartModel shoppingCartModel;

	private HomeModel dataModel ;
    private MsgModel msgModel;

	private ImageView back;
	private TextView title;
    private LinearLayout title_right_button;
    private TextView headUnreadTextView;
	
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.home_fragment,null);
        
        back = (ImageView) mainView.findViewById(R.id.top_view_back);
        back.setVisibility(View.GONE);
        title = (TextView) mainView.findViewById(R.id.top_view_text);
        Resources resource = this.getResources();
        String ecmobileStr=resource.getString(R.string.ecmobile);
        title.setText(ecmobileStr);

        title_right_button = (LinearLayout)mainView.findViewById(R.id.top_right_button);
        title_right_button.setVisibility(View.VISIBLE);
        title_right_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                msgModel.unreadCount = 0;
                headUnreadTextView.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), ShopNotifyActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
            }
        });

        headUnreadTextView = (TextView)mainView.findViewById(R.id.head_unread_num);
        
        if (null == dataModel)
        {
            dataModel = new HomeModel(getActivity());
            dataModel.fetchHotSelling();
            dataModel.fetchCategoryGoods();
        }

        if (null == MsgModel.getInstance())
        {
            msgModel = new MsgModel(getActivity());
        }
        else
        {
            msgModel = MsgModel.getInstance();
        }

        msgModel.addResponseListener(this);
        msgModel.getUnreadMessageCount();

        if (null == ConfigModel.getInstance())
        {
            ConfigModel configModel = new ConfigModel(getActivity());
            configModel.getConfig();
        }

        dataModel.addResponseListener(this);

        bannerView = (FrameLayout)LayoutInflater.from(getActivity()).inflate(R.layout.banner_scroll_view, null);

        bannerViewPager = (ViewPager)bannerView.findViewById(R.id.banner_viewpager);
        
        LayoutParams params1 = bannerViewPager.getLayoutParams();
		params1.width = getDisplayMetricsWidth();
		params1.height = (int) (params1.width*1.0/484*200);
		
		bannerViewPager.setLayoutParams(params1);

        bannerListView = new ArrayList<View>();

        
        bannerPageAdapter = new Bee_PageAdapter(bannerListView);

        bannerViewPager.setAdapter(bannerPageAdapter);
        bannerViewPager.setCurrentItem(0);
        
        bannerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            	
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // All of this is to inhibit any scrollable container from consuming our touch events as the user is changing pages
                if (mPreviousState == ViewPager.SCROLL_STATE_IDLE) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        mTouchTarget = bannerViewPager;
                    }
                } else {
                    if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
                        mTouchTarget = null;
                    }
                }

                mPreviousState = state;
            }
        });


        mIndicator = (PageIndicator)bannerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(bannerViewPager);

        mListView = (MyListView)mainView.findViewById(R.id.home_listview);
        mListView.addHeaderView(bannerView);
        mListView.bannerView = bannerView;

        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this,0);
        mListView.setRefreshTime();

        homeSetAdapter();

		ShoppingCartModel shoppingCartModel = new ShoppingCartModel(getActivity());
		shoppingCartModel.addResponseListener(this);
		shoppingCartModel.homeCartList();
		
		
		
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        
        LoginModel loginModel = new LoginModel(getActivity());
		
		ConfigModel configModel = new ConfigModel(getActivity());
        configModel.getConfig();
        
    }

    public void homeSetAdapter() {
    	if(dataModel.homeDataCache() != null) {
          if (null == listAdapter)
          {
              listAdapter = new HomeFragmentAdapter(getActivity(), dataModel);

          }
          mListView.setAdapter(listAdapter);
          addBannerView();
    	}
    	
    	
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
    {
        if (url.endsWith(ProtocolConst.HOMEDATA))
        {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter)
            {
                listAdapter = new HomeFragmentAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        }
        else if (url.endsWith(ProtocolConst.CATEGORYGOODS))
        {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter)
            {
                listAdapter = new HomeFragmentAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        } 
        else if (url.endsWith(ProtocolConst.CARTLIST))
        {
        	TabsFragment.setShoppingcartNum();
		}

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {    
    	super.onDestroy();
    	dataModel.removeResponseListener(this);
    }

    public void onRefresh(int id)
    {

        dataModel.fetchHotSelling();
        dataModel.fetchCategoryGoods();

    }

    @Override
    public void onLoadMore(int id) {

    }

    public void addBannerView()
    {
        bannerListView.clear();
        for (int i = 0; i < dataModel.playersList.size(); i++)
        {
            PLAYER player = dataModel.playersList.get(i);
            WebImageView  viewOne =  (WebImageView)LayoutInflater.from(getActivity()).inflate(R.layout.banner_imageview,null);
           
            shared = getActivity().getSharedPreferences("userInfo", 0); 
    		editor = shared.edit();
    		String imageType = shared.getString("imageType", "mind");
    		
    		if(imageType.equals("high")) {
    			viewOne.setImageWithURL(getActivity(),player.photo.thumb,R.drawable.default_image);
    		} else if(imageType.equals("low")) {
    			viewOne.setImageWithURL(getActivity(),player.photo.small,R.drawable.default_image);
    		} else {
    			String netType = shared.getString("netType", "wifi");
    			if(netType.equals("wifi")) {
    				viewOne.setImageWithURL(getActivity(),player.photo.thumb,R.drawable.default_image);
    			} else {
    				viewOne.setImageWithURL(getActivity(),player.photo.small,R.drawable.default_image);
    			}
    		}

            try
            {
                viewOne.setTag(player.toJson().toString());
            }
            catch (JSONException e)
            {

            }

            bannerListView.add(viewOne);

            viewOne.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String playerJSONString = (String) v.getTag();

                    try {
                        JSONObject jsonObject = new JSONObject(playerJSONString);
                        PLAYER player1 = PLAYER.fromJson(jsonObject);
                        if (null == player1.action)
                        {
                            if (null != player1.url) {
                                Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }
                        else
                        {
                            if (player1.action.equals("goods"))
                            {
                                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                                intent.putExtra("good_id", player1.action_id);
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (player1.action.equals("category"))
                            {
                                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                                FILTER filter = new FILTER();
                                filter.category_id = String.valueOf(player1.action_id);
                                intent.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (null != player1.url)
                            {
                                Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }

                    } catch (JSONException e) {

                    }


                }
            });

        }

        mIndicator.notifyDataSetChanged();
        bannerPageAdapter.mListViews = bannerListView;
        bannerViewPager.setAdapter(bannerPageAdapter);

    }
    
	//获取屏幕宽度
	public int getDisplayMetricsWidth() {
		int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		int j = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		return Math.min(i, j);
	}
}
