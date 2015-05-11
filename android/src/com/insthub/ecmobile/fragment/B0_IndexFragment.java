package com.insthub.ecmobile.fragment;
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

import com.insthub.ecmobile.ECMobileAppConst;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyListView;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.EcmobileManager.RegisterApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.BannerWebActivity;
import com.insthub.ecmobile.adapter.B0_IndexAdapter;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;
import com.insthub.ecmobile.model.ConfigModel;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.model.LoginModel;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.PLAYER;
import com.umeng.analytics.MobclickAgent;


public class B0_IndexFragment extends BaseFragment implements BusinessResponse,XListView.IXListViewListener, RegisterApp
{
    private ViewPager bannerViewPager;
    private PageIndicator mIndicator;
    private MyListView mListView;
    private B0_IndexAdapter listAdapter;

    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;
    FrameLayout bannerView;

    private View mTouchTarget;
    private ShoppingCartModel shoppingCartModel;

	private HomeModel dataModel ;

	private ImageView back;
	private TextView title;
    private LinearLayout title_right_button;
    private TextView headUnreadTextView;
	
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        View mainView = inflater.inflate(R.layout.b0_index,null);
        
        back = (ImageView) mainView.findViewById(R.id.top_view_back);
        back.setVisibility(View.GONE);
        title = (TextView) mainView.findViewById(R.id.top_view_text);
        Resources resource = this.getResources();
        String ecmobileStr=resource.getString(R.string.ecmobile);
        title.setText(ecmobileStr);


        headUnreadTextView = (TextView)mainView.findViewById(R.id.head_unread_num);
        
        if (null == dataModel)
        {
            dataModel = new HomeModel(getActivity());
            dataModel.fetchHotSelling();
            dataModel.fetchCategoryGoods();
        }

        
        
        if (null == ConfigModel.getInstance())
        {
            ConfigModel configModel = new ConfigModel(getActivity());
            configModel.getConfig();
        }

        dataModel.addResponseListener(this);

        bannerView = (FrameLayout)LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner, null);

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

	public boolean isActive = false;
    @Override
    public void onResume() {
        super.onResume();
       
        if (!isActive) {
            isActive = true;
            EcmobileManager.registerApp(this);
        }
        
        LoginModel loginModel = new LoginModel(getActivity());
		
		ConfigModel configModel = new ConfigModel(getActivity());
        configModel.getConfig();
        MobclickAgent.onPageStart("Home");
    }

    public void homeSetAdapter() {
    	if(dataModel.homeDataCache() != null) {
          if (null == listAdapter)
          {
              listAdapter = new B0_IndexAdapter(getActivity(), dataModel);

          }
          mListView.setAdapter(listAdapter);
          addBannerView();
    	}
    	
    	
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
    {
        if (url.endsWith(ApiInterface.HOME_DATA))
        {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter)
            {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        }
        else if (url.endsWith(ApiInterface.HOME_CATEGORY))
        {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter)
            {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        } 
        else if (url.endsWith(ApiInterface.CART_LIST))
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addBannerView()
    {
        bannerListView.clear();
        for (int i = 0; i < dataModel.playersList.size(); i++)
        {
            PLAYER player = dataModel.playersList.get(i);
            ImageView  viewOne =  (ImageView)LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner_cell,null);

            shared = getActivity().getSharedPreferences("userInfo", 0); 
    		editor = shared.edit();
    		String imageType = shared.getString("imageType", "mind");
    		
    		if(imageType.equals("high")) {
                imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
    		} else if(imageType.equals("low")) {
                imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
    		} else {
    			String netType = shared.getString("netType", "wifi");
    			if(netType.equals("wifi")) {
                    imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
    			} else {
                    imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
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
                     
                    String playerJSONString = (String) v.getTag();

                    try {
                        JSONObject jsonObject = new JSONObject(playerJSONString);
                        PLAYER player1 = new PLAYER();
                         player1.fromJson(jsonObject);
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
                                Intent intent = new Intent(getActivity(), B2_ProductDetailActivity.class);
                                intent.putExtra("good_id", player1.action_id+"");
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (player1.action.equals("category"))
                            {
                                Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                                FILTER filter = new FILTER();
                                filter.category_id = String.valueOf(player1.action_id);
                                intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
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
        mIndicator.setCurrentItem(0);
        bannerPageAdapter.mListViews = bannerListView;
        bannerViewPager.setAdapter(bannerPageAdapter);

    }
    
	//获取屏幕宽度
	public int getDisplayMetricsWidth() {
		int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		int j = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		return Math.min(i, j);
	}


	@Override
	public void onRegisterResponse(boolean success) {
		 
	}


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Home");
    }
    
    @Override
    public void onStop() {
    	 
    	super.onStop();
    	if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;
        }
    }
    
    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getActivity().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getActivity().getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
        	return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }



}
