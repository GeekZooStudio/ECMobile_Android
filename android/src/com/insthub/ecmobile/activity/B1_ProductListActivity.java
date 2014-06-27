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

import android.content.Context;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.PAGINATED;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.B1_ProductListAdapter;
import com.insthub.ecmobile.adapter.GoodListLargeListActivityAdapter;
import com.insthub.ecmobile.model.GoodsListModel;
import com.insthub.ecmobile.model.ShoppingCartModel;

public class B1_ProductListActivity extends BaseActivity implements BusinessResponse, IXListViewListener,OnClickListener
{
	private ImageView backImageButton;

    private ImageView item_grid_button;
    private ImageView shopping_cart;
    private TextView good_list_shopping_cart_num;
    private LinearLayout good_list_shopping_cart_num_bg;
	
	private XListView goodlistView;
	private GoodsListModel dataModel;
	private B1_ProductListAdapter listAdapter;
    private GoodListLargeListActivityAdapter largeListActivityAdapter;
    
    private ImageView bg;
    
    private int flag = IS_HOT;
    
    private boolean isSetAdapter = true;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private View null_pager;


    public static final String KEYWORD = "keyword";
    public static final String CATEGORY_ID = "category_id";
    public static final String TITLE = "title";
    public static final String FILTER = "filter";

    public static final int IS_HOT = 0;
    public static final int PRICE_DESC_INT = 1;
    public static final int PRICE_ASC_INT = 2;

    public String predefine_category_id;

	
	protected class TitleCellHolder
	{
		public ImageView 	triangleImageView;
		public TextView 	titleTextView;
		public ImageView	orderIconImageView;
        public RelativeLayout tabRelative;
	}
	
	TitleCellHolder tabOneCellHolder;
	TitleCellHolder tabTwoCellHolder;
	TitleCellHolder tabThreeCellHolder;

    private BeeBaseAdapter currentAdapter;
    
    FILTER filter = new FILTER();

    private ImageView search;
    private EditText input;
    private Button searchFilter;
    private View bottom_line;
	private View top_view;
	public B1_ProductListActivity() 
	{
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b1_product_list);

        input = (EditText) findViewById(R.id.search_input);
        search = (ImageView) findViewById(R.id.search_search);
        searchFilter = (Button )findViewById(R.id.search_filter);
        bottom_line = (View)findViewById(R.id.bottom_line);
        top_view=findViewById(R.id.top_view);
        search.setOnClickListener(this);
        searchFilter.setOnClickListener(this);
        top_view.setOnClickListener(this);
        input.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {                
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = input.getText().toString().toString();
                    if (null != keyword && keyword.length() > 0)
                    {
                        B1_ProductListActivity.this.filter.keywords = keyword;
                        dataModel.fetchPreSearch(B1_ProductListActivity.this.filter);
                    }
                    else
                    {
                        B1_ProductListActivity.this.filter.keywords = null;
                        dataModel.fetchPreSearch(B1_ProductListActivity.this.filter);
                    }
                    CloseKeyBoard();

                }
                return false;
            }
        });

       final LinearLayout mainView = (LinearLayout) findViewById(R.id.keyboardLayout1);
        ViewTreeObserver mainViewObserver =  mainView.getViewTreeObserver();
        if (null != mainViewObserver)
        {
            mainViewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    int heightDiff = mainView.getRootView().getHeight() - mainView.getHeight();
                    if (heightDiff > 100)
                    { // if more than 100 pixels, its probably a keyboard...
                        input.setCursorVisible(true);
                        top_view.setVisibility(View.VISIBLE);
                        searchFilter.setVisibility(View.GONE);
                    }
                    else
                    {
                        input.setCursorVisible(false);
                        top_view.setVisibility(View.INVISIBLE);
                        searchFilter.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


        shared = getSharedPreferences("userInfo", 0);
		editor = shared.edit();

        backImageButton = (ImageView)findViewById(R.id.nav_back_button);
        backImageButton.setOnClickListener(new View.OnClickListener()
		{				
			public void onClick(View v) {				
				finish();
                CloseKeyBoard();
				overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
			}
		});
		
		shopping_cart = (ImageView) findViewById(R.id.good_list_shopping_cart);
		shopping_cart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				String uid = shared.getString("uid", "");
                if(uid.equals(""))
                {
                    Intent intent = new Intent(B1_ProductListActivity.this, A0_SigninActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
                    Resources resource = (Resources) getBaseContext().getResources();
                    String nol=resource.getString(R.string.no_login);                    
                    ToastView toast = new ToastView(B1_ProductListActivity.this, nol);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                } else {
                	Intent it = new Intent(B1_ProductListActivity.this,C0_ShoppingCartActivity.class);
                	startActivity(it);
                }
			}
		});
		
		good_list_shopping_cart_num = (TextView) findViewById(R.id.good_list_shopping_cart_num);
		good_list_shopping_cart_num_bg = (LinearLayout) findViewById(R.id.good_list_shopping_cart_num_bg);
		//设置购物车数量
		
        if(ShoppingCartModel.getInstance().goods_num == 0) {
        	good_list_shopping_cart_num_bg.setVisibility(View.GONE);
        } else {
        	good_list_shopping_cart_num_bg.setVisibility(View.VISIBLE);
            if(ShoppingCartModel.getInstance()!=null){
                good_list_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
            }
        }
        bg = (ImageView) findViewById(R.id.goodslist_bg);
        null_pager = findViewById(R.id.null_pager);
		
		goodlistView = (XListView)findViewById(R.id.goods_listview);
		
		 goodlistView.setPullLoadEnable(true);
	     goodlistView.setRefreshTime();
	     goodlistView.setXListViewListener(this,1);

		dataModel = new GoodsListModel(this);

        String filter_string =  getIntent().getStringExtra(FILTER);
        if (null != filter_string)
        {
            try
            {
                JSONObject filterJSONObject = new JSONObject(filter_string);
                filter =new com.insthub.ecmobile.protocol.FILTER();
                filter.fromJson(filterJSONObject);
                filter.sort_by = dataModel.PRICE_DESC;

                if(null != filter.category_id)
                {
                    predefine_category_id = filter.category_id;
                }

                if (null != filter.keywords)
                {
                    input.setText(filter.keywords);
                    input.setSelection(input.getText().length());
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        dataModel.addResponseListener(this);

        largeListActivityAdapter = new GoodListLargeListActivityAdapter(this,dataModel.simplegoodsList);

        tabOneCellHolder = new TitleCellHolder();
        tabTwoCellHolder = new TitleCellHolder();
        tabThreeCellHolder = new TitleCellHolder();

        tabOneCellHolder.titleTextView = (TextView)findViewById(R.id.filter_title_tabone);
        tabOneCellHolder.orderIconImageView = (ImageView)findViewById(R.id.filter_order_tabone);
        tabOneCellHolder.triangleImageView = (ImageView)findViewById(R.id.filter_triangle_tabone);
        tabOneCellHolder.tabRelative        = (RelativeLayout)findViewById(R.id.tabOne);
        tabOneCellHolder.tabRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(IS_HOT);
            }
        });

        tabTwoCellHolder.titleTextView = (TextView)findViewById(R.id.filter_title_tabtwo);
        tabTwoCellHolder.orderIconImageView = (ImageView)findViewById(R.id.filter_order_tabtwo);
        tabTwoCellHolder.triangleImageView = (ImageView)findViewById(R.id.filter_triangle_tabtwo);
        tabTwoCellHolder.tabRelative        = (RelativeLayout)findViewById(R.id.tabTwo);
        tabTwoCellHolder.tabRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = PRICE_DESC_INT;
                selectedTab(PRICE_DESC_INT);

            }
        });

        tabThreeCellHolder.titleTextView = (TextView)findViewById(R.id.filter_title_tabthree);
        tabThreeCellHolder.orderIconImageView = (ImageView)findViewById(R.id.filter_order_tabthree);
        tabThreeCellHolder.triangleImageView = (ImageView)findViewById(R.id.filter_triangle_tabthree);
        tabThreeCellHolder.tabRelative        = (RelativeLayout)findViewById(R.id.tabThree);
        tabThreeCellHolder.tabRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = PRICE_ASC_INT;
                selectedTab(PRICE_ASC_INT);

            }
        });
        selectedTab(PRICE_ASC_INT);
        flag = PRICE_ASC_INT;
		
	}

    void selectedTab(int index)
    {
    	isSetAdapter = true;
        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList selectedTextColor = (ColorStateList) resource.getColorStateList(R.color.filter_text_color);

        if (index == IS_HOT)
        {
            tabOneCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_active_arrow);
            tabOneCellHolder.orderIconImageView.setWillNotCacheDrawing(true);
            tabOneCellHolder.triangleImageView.setVisibility(View.VISIBLE);
            tabOneCellHolder.titleTextView.setTextColor(Color.WHITE);

            tabTwoCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabTwoCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabTwoCellHolder.titleTextView.setTextColor(selectedTextColor);

            tabThreeCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabThreeCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_up_arrow);
            tabThreeCellHolder.titleTextView.setTextColor(selectedTextColor);

            filter.sort_by = dataModel.IS_HOT;
            dataModel.fetchPreSearch(filter);


        }
        else if (index == PRICE_DESC_INT)
        {
            tabTwoCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_active_arrow);
            tabTwoCellHolder.triangleImageView.setVisibility(View.VISIBLE);
            tabTwoCellHolder.titleTextView.setTextColor(Color.WHITE);

            tabOneCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabOneCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabOneCellHolder.titleTextView.setTextColor(selectedTextColor);

            tabThreeCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabThreeCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_up_arrow);
            tabThreeCellHolder.titleTextView.setTextColor(selectedTextColor);

            filter.sort_by = dataModel.PRICE_DESC;
            dataModel.fetchPreSearch(filter);;
        }
        else
        {

            tabThreeCellHolder.triangleImageView.setVisibility(View.VISIBLE);
            tabThreeCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_up_active_arrow);
            tabThreeCellHolder.titleTextView.setTextColor(Color.WHITE);

            tabOneCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabOneCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabOneCellHolder.titleTextView.setTextColor(selectedTextColor);

            tabTwoCellHolder.orderIconImageView.setImageResource(R.drawable.item_grid_filter_down_arrow);
            tabTwoCellHolder.triangleImageView.setVisibility(View.INVISIBLE);
            tabTwoCellHolder.titleTextView.setTextColor(selectedTextColor);

            filter.sort_by = dataModel.PRICE_ASC;
            dataModel.fetchPreSearch(filter);
            
        }
    }
    
    public void setContent() {
    	
    	if(listAdapter == null) {
    		
    		if(dataModel.simplegoodsList.size() == 0) {
    			bg.setVisibility(View.GONE);
    			null_pager.setVisibility(View.VISIBLE);
                bottom_line.setBackgroundColor(Color.parseColor("#FFFFFF"));

    		} else {
    			bg.setVisibility(View.GONE);
    			null_pager.setVisibility(View.GONE);
                bottom_line.setBackgroundColor(Color.parseColor("#000000"));
    			listAdapter = new B1_ProductListAdapter(this, dataModel.simplegoodsList);
                goodlistView.setAdapter(listAdapter);
    		}
    		
    	} else {
    		
    		if(isSetAdapter == true) {
    			if (currentAdapter == largeListActivityAdapter) {
                    goodlistView.setAdapter(largeListActivityAdapter);
                } else {
                	listAdapter = new B1_ProductListAdapter(this, dataModel.simplegoodsList);
                    goodlistView.setAdapter(listAdapter);
                }
    		} else {
    			listAdapter.dataList = dataModel.simplegoodsList;
        		listAdapter.notifyDataSetChanged();	
    		}
    	}

        //设置购物车数量
        if (ShoppingCartModel.getInstance().goods_num == 0) {
            good_list_shopping_cart_num_bg.setVisibility(View.GONE);
        } else {
            good_list_shopping_cart_num_bg.setVisibility(View.VISIBLE);
            if (ShoppingCartModel .getInstance()!= null) {
                good_list_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num + "");
            }
        }

    }
	
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {    
    	
    	if(url.endsWith(ApiInterface.SEARCH))
        {
    		goodlistView.stopRefresh();
    		goodlistView.stopLoadMore();
    		goodlistView.setRefreshTime();

    		setContent();
            PAGINATED paginated = new PAGINATED();
            paginated.fromJson(jo.optJSONObject("paginated"));
            if (0 == paginated.more)
            {
                goodlistView.setPullLoadEnable(false);
            }
            else
            {
                goodlistView.setPullLoadEnable(true);
            }
    	}
    		
    }

    @Override
    public void onClick(View v) {        
        String tag;
        Intent intent;
        switch(v.getId())
        {
            case R.id.search_search:

                break;
            case R.id.top_view:
                CloseKeyBoard();
                input.setText("");
                break;
            case R.id.search_voice:
            {
//                showRecognizerDialog(); //弹出语音搜索框
                break;
            }
            case R.id.search_filter:
            {
                Intent it = new Intent(this, D2_FilterActivity.class);
                try
                {
                    it.putExtra("filter",filter.toJson().toString());
                    if (null != predefine_category_id)
                    {
                        it.putExtra("predefine_category_id",predefine_category_id);
                    }

                }
                catch (JSONException e)
                {

                }
                startActivityForResult(it, 1);
                break;
            }
        }

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
    
	@Override
	public void onRefresh(int id) {			
		isSetAdapter = true;
        dataModel.fetchPreSearch(filter);
		
	}
	@Override
	public void onLoadMore(int id) {		
		isSetAdapter = false;
        dataModel.fetchPreSearchMore(filter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
            {
                if(null != data)
                {
                    String filter_string =  data.getStringExtra("filter");
                    if (null != filter_string)
                    {
                        try
                        {
                            JSONObject filterJSONObject = new JSONObject(filter_string);
                            FILTER filter = new com.insthub.ecmobile.protocol.FILTER();
                            filter.fromJson(filterJSONObject);
                            this.filter.category_id = filter.category_id;
                            this.filter.price_range = filter.price_range;
                            this.filter.brand_id = filter.brand_id;
                            dataModel.fetchPreSearch(this.filter);
                            input.clearFocus();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

	@Override
	protected void onResume() {		
		super.onResume();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this), "");
            MobclickAgent.onPageStart("FilterPage");
        }
		if(ShoppingCartModel.getInstance().goods_num == 0) {
			good_list_shopping_cart_num_bg.setVisibility(View.GONE);
        } else {
        	good_list_shopping_cart_num_bg.setVisibility(View.VISIBLE);
        	good_list_shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
        }
	}

    // 关闭键盘
    public void CloseKeyBoard() {
        input.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageEnd("FilterPage");
            MobclickAgent.onPause(this);
        }

    }
}
