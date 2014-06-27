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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessMessage;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.component.AdvanceSearchValueCell;
import com.insthub.ecmobile.model.AdvanceSearchModel;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.*;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class D2_FilterActivity extends BaseActivity implements BusinessResponse {

    AdvanceSearchModel dataModel;
    LinearLayout brandLayout;
    ImageView   brandArrow;
    RelativeLayout brand_title_layout;

    LinearLayout categoryLayout;
    ImageView   categoryArrow;
    RelativeLayout category_title_layout;
    LinearLayout category_parent_layout;


    LinearLayout priceRangeLayout;
    LinearLayout parentPriceLayout;
    ImageView   priceArrow;
    RelativeLayout price_title_layout;

    ScrollView scrollView;

    CATEGORY selectCategory;

    private TextView title;
    private ImageView back;
    TextView topview_message;

    Button advanceSearchButton;

    LinearLayout top_right_button;

    ArrayList<SearchHolder> brandHolderList = new ArrayList<SearchHolder>();
    ArrayList<SearchHolder> categoryHolderList = new ArrayList<SearchHolder>();
    ArrayList<SearchHolder> priceHolderList = new ArrayList<SearchHolder>();

    FILTER filter = new FILTER();

    String  predefine_category_id;

    public class SearchHolder
    {
        TextView searchName;
        ImageView searchImage;
        int       index;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d2_filter);

        try
        {
            String filterJSONString = getIntent().getStringExtra("filter");
            predefine_category_id = getIntent().getStringExtra("predefine_category_id");

            if (null != filterJSONString)
            {
                JSONObject filterJSONObject = new JSONObject(filterJSONString);
                filter = new FILTER();
                filter.fromJson(filterJSONObject);

                if (null == filter.brand_id || filter.brand_id.length() == 0)
                {
                    filter.brand_id = "0";
                }

                if (null == filter.category_id || filter.category_id.length() == 0)
                {
                    filter.category_id = "0";
                }
            }
            else
            {
                filter.brand_id = "0";
                filter.category_id = "0";
            }
        }
        catch (JSONException e)
        {

        }


        title = (TextView) findViewById(R.id.top_view_text);
        title.setText(R.string.filter);

        top_right_button = (LinearLayout)findViewById(R.id.top_right_button);
        topview_message  = (TextView)findViewById(R.id.top_right_text);
        topview_message.setText(R.string.collect_done);
        top_right_button.setVisibility(View.VISIBLE);
        top_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent it = new Intent();
                    it.putExtra("filter",filter.toJson().toString());
                    D2_FilterActivity.this.setResult(RESULT_OK,it);
                    finish();
                }
                catch (JSONException e)
                {

                }
            }
        });
        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {                
                finish();
            }
        });

        advanceSearchButton = (Button)findViewById(R.id.advance_search_done);
        advanceSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent it = new Intent();
                    it.putExtra("filter",filter.toJson().toString());
                    D2_FilterActivity.this.setResult(RESULT_OK,it);
                    finish();
                }
                catch (JSONException e)
                {

                }
            }
        });

        brandLayout = (LinearLayout)findViewById(R.id.brand_value);
        brandArrow  = (ImageView)findViewById(R.id.brand_arrow);
        brand_title_layout = (RelativeLayout)findViewById(R.id.brand_title_layout);
        brand_title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brandLayout.getVisibility() == View.VISIBLE)
                {
                    brandArrow.setImageResource(R.drawable.accsessory_arrow_down);
                    brandLayout.setVisibility(View.GONE);
                }
                else
                {
                    brandArrow.setImageResource(R.drawable.accsessory_arrow_up);
                    brandLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        categoryLayout = (LinearLayout)findViewById(R.id.category_value);

        categoryArrow = (ImageView)findViewById(R.id.category_arrow);
        category_title_layout = (RelativeLayout)findViewById(R.id.category_title_layout);
        category_title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryLayout.getVisibility() == View.VISIBLE)
                {
                    categoryArrow.setImageResource(R.drawable.accsessory_arrow_down);
                    categoryLayout.setVisibility(View.GONE);
                }
                else
                {
                    categoryArrow.setImageResource(R.drawable.accsessory_arrow_up);
                    categoryLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        category_parent_layout = (LinearLayout)findViewById(R.id.category_parent_layout);

        if (null != predefine_category_id && predefine_category_id.length() > 0)
        {
            category_parent_layout.setVisibility(View.GONE);
        }


        parentPriceLayout = (LinearLayout)findViewById(R.id.parent_price_layout);
        priceRangeLayout = (LinearLayout)findViewById(R.id.price_value);
        priceArrow = (ImageView)findViewById(R.id.price_arrow);
        price_title_layout = (RelativeLayout)findViewById(R.id.price_title_layout);
        price_title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceRangeLayout.getVisibility() == View.VISIBLE)
                {
                    priceArrow.setImageResource(R.drawable.accsessory_arrow_down);
                    priceRangeLayout.setVisibility(View.GONE);
                }
                else
                {
                    priceArrow.setImageResource(R.drawable.accsessory_arrow_up);
                    priceRangeLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        scrollView = (ScrollView)findViewById(R.id.search_scroll);

        dataModel = new AdvanceSearchModel(this);
        dataModel.addResponseListener(this);
        dataModel.getAllBrand(predefine_category_id);
        if (null != predefine_category_id && predefine_category_id.length() > 0)
        {
            int categoryId = Integer.valueOf(predefine_category_id);
            dataModel.getPriceRange(categoryId);
        }

        dataModel.getCategory();

    }

    private void RelayoutBrandView()
    {
        Resources resource = (Resources) ((BaseActivity)this).getBaseContext().getResources();
        ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);

        brandLayout.removeAllViewsInLayout();
        brandHolderList.clear();
        for (int i = 0; i< dataModel.brandList.size(); i = i+2)
        {
            AdvanceSearchValueCell itemCell = (AdvanceSearchValueCell) LayoutInflater.from(this).inflate(R.layout.advance_search_cell_value, null);
            itemCell.init();

            BRAND brand = dataModel.brandList.get(i);
            itemCell.specOne.setText(brand.brand_name);
            itemCell.specOne.setTextColor(normalTextColor);
            itemCell.specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
            itemCell.image1.setVisibility(View.GONE);

            SearchHolder searchHolder = new SearchHolder();
            searchHolder.searchName = itemCell.specOne;
            searchHolder.searchImage = itemCell.image1;
            searchHolder.index = i;

            if(null != filter.brand_id && filter.brand_id.equals(String.valueOf(brand.brand_id)))
            {
                searchHolder.searchName.setTextColor(Color.RED);
                searchHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                searchHolder.searchImage.setVisibility(View.VISIBLE);
            }

            brandHolderList.add(searchHolder);

            itemCell.specOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBrandCell(v);
                }
            });

            if (i + 1 < dataModel.brandList.size())
            {
                BRAND brand2 = dataModel.brandList.get(i+1);
                itemCell.specTwo.setText(brand2.brand_name);
                itemCell.specTwo.setTextColor(normalTextColor);
                itemCell.specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                itemCell.image2.setVisibility(View.GONE);
                itemCell.specTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedBrandCell(v);
                    }
                });

                SearchHolder searchHolder2 = new SearchHolder();
                searchHolder2.searchName = itemCell.specTwo;
                searchHolder2.searchImage = itemCell.image2;
                searchHolder2.index = i+1;

                if(null != filter.brand_id && filter.brand_id.equals(String.valueOf(brand2.brand_id)))
                {
                    searchHolder2.searchName.setTextColor(Color.RED);
                    searchHolder2.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                    searchHolder2.searchImage.setVisibility(View.VISIBLE);
                }
                brandHolderList.add(searchHolder2);

            }
            else
            {
                itemCell.specification_layout_two.setVisibility(View.INVISIBLE);
            }

            brandLayout.addView(itemCell);
        }
    }

    public void selectedBrandCell(View v)
    {
        Resources resource = (Resources) ((BaseActivity)this).getBaseContext().getResources();
        ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);

        for (int i = 0; i < brandHolderList.size(); i++)
        {
            SearchHolder itemHolder = brandHolderList.get(i);
            if (itemHolder.searchName == v)
            {
                itemHolder.searchName.setTextColor(Color.RED);
                itemHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                itemHolder.searchImage.setVisibility(View.VISIBLE);

                if (itemHolder.index < dataModel.brandList.size())
                {
                    BRAND brand = dataModel.brandList.get(i);
                    filter.brand_id = String.valueOf(brand.brand_id);
                }
            }
            else
            {
                itemHolder.searchName.setTextColor(normalTextColor);
                itemHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                itemHolder.searchImage.setVisibility(View.GONE);

            }
        }
    }

    public void selectCategoryView(View v)
    {
        Resources resource = (Resources) ((BaseActivity)this).getBaseContext().getResources();
        ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);

        for (int i = 0; i < categoryHolderList.size(); i++)
        {
            SearchHolder itemHolder = categoryHolderList.get(i);
            if (itemHolder.searchName == v)
            {
                itemHolder.searchName.setTextColor(Color.RED);
                itemHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                itemHolder.searchImage.setVisibility(View.VISIBLE);
                selectCategory = (CATEGORY)dataModel.categoryArrayList.get(i);
                filter.category_id = String.valueOf(selectCategory.id);
                filter.price_range = null;

                priceRangeLayout.removeAllViews();
                priceHolderList.clear();
                dataModel.getPriceRange(Integer.parseInt(selectCategory.id));
            }
            else
            {
                itemHolder.searchName.setTextColor(normalTextColor);
                itemHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                itemHolder.searchImage.setVisibility(View.GONE);

            }
        }
    }

    public void selectPriceView(View v)
    {
        Resources resource = (Resources) ((BaseActivity)this).getBaseContext().getResources();
        ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);

        for (int i = 0; i < priceHolderList.size(); i++)
        {
            SearchHolder itemHolder = priceHolderList.get(i);
            if (itemHolder.searchName == v)
            {
                itemHolder.searchName.setTextColor(Color.RED);
                itemHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                itemHolder.searchImage.setVisibility(View.VISIBLE);
                PRICE_RANGE price_range = dataModel.priceRangeArrayList.get(i);
                if (price_range.price_min >= 0 && price_range.price_max >= 0)
                {
                    filter.price_range = price_range;
                }
                else
                {
                    filter.price_range = null;
                }

            }
            else
            {
                itemHolder.searchName.setTextColor(normalTextColor);
                itemHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                itemHolder.searchImage.setVisibility(View.GONE);

            }
        }
    }

    private void RelayoutCategoryView()
    {
        Resources resource = (Resources) ((BaseActivity)this).getBaseContext().getResources();
        ColorStateList normalTextColor = (ColorStateList) resource.getColorStateList(R.color.spec_text_color);

        categoryLayout.removeAllViewsInLayout();
        for (int i = 0; i< dataModel.categoryArrayList.size(); i = i+2)
        {
            AdvanceSearchValueCell itemCell = (AdvanceSearchValueCell) LayoutInflater.from(this).inflate(R.layout.advance_search_cell_value, null);
            itemCell.init();
            CATEGORY category = dataModel.categoryArrayList.get(i);
            itemCell.specOne.setText(category.name);
            itemCell.specOne.setTextColor(normalTextColor);
            itemCell.specOne.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
            itemCell.image1.setVisibility(View.GONE);
            itemCell.specOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCategoryView(v);
                }
            });



            SearchHolder searchHolder = new SearchHolder();
            searchHolder.searchName = itemCell.specOne;
            searchHolder.searchImage = itemCell.image1;
            searchHolder.index = i;

            if (null != filter.category_id && filter.category_id.equals(String.valueOf(category.id)))
            {
                searchHolder.searchName.setTextColor(Color.RED);
                searchHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                searchHolder.searchImage.setVisibility(View.VISIBLE);
                dataModel.getPriceRange(Integer.parseInt(category.id));
            }

            categoryHolderList.add(searchHolder);

            if (i + 1 < dataModel.categoryArrayList.size())
            {
                CATEGORY category2 = dataModel.categoryArrayList.get(i+1);
                itemCell.specTwo.setText(category2.name);
                itemCell.specTwo.setTextColor(normalTextColor);
                itemCell.specTwo.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_bg_grey);
                itemCell.image2.setVisibility(View.GONE);
                itemCell.specTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCategoryView(v);
                    }
                });

                SearchHolder searchHolder2 = new SearchHolder();
                searchHolder2.searchName = itemCell.specTwo;
                searchHolder2.searchImage = itemCell.image2;
                searchHolder2.index = i+1;


                if (null != filter.category_id && filter.category_id.equals(String.valueOf(category2.id)))
                {
                    searchHolder2.searchName.setTextColor(Color.RED);
                    searchHolder2.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                    searchHolder2.searchImage.setVisibility(View.VISIBLE);
                    dataModel.getPriceRange(Integer.parseInt(category2.id));
                }
                categoryHolderList.add(searchHolder2);
            }
            else
            {
                itemCell.specification_layout_two.setVisibility(View.INVISIBLE);
            }


            categoryLayout.addView(itemCell);

        }
    }

    private void RelayoutPriceRangeView()
    {

        priceRangeLayout.removeAllViews();
        priceHolderList.clear();

        if (null != predefine_category_id && predefine_category_id.length() > 0&& dataModel.priceRangeArrayList.size() == 0)
        {
            parentPriceLayout.setVisibility(View.GONE);
        }
        else
        {
            parentPriceLayout.setVisibility(View.VISIBLE);
        }


        for (int i = 0; i< dataModel.priceRangeArrayList.size(); i = i+2)
        {
            AdvanceSearchValueCell itemCell = (AdvanceSearchValueCell) LayoutInflater.from(this).inflate(R.layout.advance_search_cell_value, null);

            itemCell.init();
            PRICE_RANGE price_range = dataModel.priceRangeArrayList.get(i);
            if (price_range.price_max >= 0 && price_range.price_min >= 0)
            {
                itemCell.specOne.setText(price_range.price_min + "-"+price_range.price_max);
            }
            else
            {
                itemCell.specOne.setText(R.string.all_price);
            }

            itemCell.specOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPriceView(v);
                }
            });

            SearchHolder searchHolder = new SearchHolder();
            searchHolder.searchName = itemCell.specOne;
            searchHolder.searchImage = itemCell.image1;
            searchHolder.index = i;

            if (null != filter.price_range && price_range.price_min == filter.price_range.price_min && price_range.price_max == filter.price_range.price_max)
            {
                searchHolder.searchName.setTextColor(Color.RED);
                searchHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                searchHolder.searchImage.setVisibility(View.VISIBLE);
            }
            //未选中时选择全部
            else if (null == filter.price_range && price_range.price_min == -1 && price_range.price_max == -1)
            {
                searchHolder.searchName.setTextColor(Color.RED);
                searchHolder.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                searchHolder.searchImage.setVisibility(View.VISIBLE);
            }

            priceHolderList.add(searchHolder);

            if (i + 1 < dataModel.priceRangeArrayList.size())
            {
                PRICE_RANGE price_range2 = dataModel.priceRangeArrayList.get(i+1);
                if (price_range2.price_min >= 0 && price_range2.price_max >= 0)
                {
                    itemCell.specTwo.setText(price_range2.price_min + "-"+price_range2.price_max);
                }
                else
                {
                    itemCell.specTwo.setText(R.string.all_price);
                }


                itemCell.specTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPriceView(v);
                    }
                });

                SearchHolder searchHolder2 = new SearchHolder();
                searchHolder2.searchName = itemCell.specTwo;
                searchHolder2.searchImage = itemCell.image2;
                searchHolder2.index = i+1;

                if (null != filter.price_range && price_range2.price_min == filter.price_range.price_min && price_range2.price_max == filter.price_range.price_max)
                {
                    searchHolder2.searchName.setTextColor(Color.RED);
                    searchHolder2.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                    searchHolder2.searchImage.setVisibility(View.VISIBLE);
                }
                //未选中时选择全部
                else if (null == filter.price_range && price_range2.price_min == -1 && price_range2.price_max == -1)
                {
                    searchHolder2.searchName.setTextColor(Color.RED);
                    searchHolder2.searchName.setBackgroundResource(R.drawable.item_info_buy_kinds_btn_active_bg);
                    searchHolder2.searchImage.setVisibility(View.VISIBLE);
                }

                priceHolderList.add(searchHolder2);
            }
            else
            {
                itemCell.specification_layout_two.setVisibility(View.INVISIBLE);
            }


            priceRangeLayout.addView(itemCell);
        }
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if(url.endsWith(ApiInterface.BRAND))
        {
            RelayoutBrandView();
        }
        else if (url.endsWith(ApiInterface.CATEGORY))
        {
            RelayoutCategoryView();
        }
        else if (url.endsWith(ApiInterface.PRICE_RANGE))
        {
            RelayoutPriceRangeView();

        }

    }
}
