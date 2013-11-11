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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.view.MyViewGroup;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CategoryListAdapter;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class CategoryChildActivity extends BaseActivity implements View.OnClickListener
{

    private ImageView search;
    private EditText input;
    private ImageButton backButton;

    private XListView childListView;
    CategoryListAdapter childListAdapter;
    CATEGORY category ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_child);

        input = (EditText) findViewById(R.id.search_input);
        search = (ImageView) findViewById(R.id.search_search);
        backButton = (ImageButton)findViewById(R.id.back_button);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(this);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    Intent it = new Intent(CategoryChildActivity.this, GoodsListActivity.class);
                    FILTER filter = new FILTER();
                    filter.keywords = input.getText().toString().toString();
                    try
                    {
                        it.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                    }
                    catch (JSONException e)
                    {

                    }

                    startActivity(it);
                }
                return false;
            }
        });


        childListView = (XListView)findViewById(R.id.child_list);
        childListView.setPullLoadEnable(false);
        childListView.setPullRefreshEnable(false);
        String categoryStr = getIntent().getStringExtra("category");

        try
        {
            JSONObject jsonObject = new JSONObject(categoryStr);
            CATEGORY category1 = CATEGORY.fromJson(jsonObject);
            this.category = category1;
            childListAdapter = new CategoryListAdapter(this,this.category.children);
            childListView.setAdapter(childListAdapter);
            childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position - 1 < category.children.size())
                    {
                        CATEGORY item = category.children.get(position - 1);

                        try
                        {
                            Intent intent = new Intent(CategoryChildActivity.this, GoodsListActivity.class);
                            FILTER filter = new FILTER();
                            filter.category_id = String.valueOf(item.id);
                            intent.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        catch (JSONException e)
                        {

                        }

                    }
                }
            });
        }
        catch (JSONException e)
        {

        }



    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String tag;
        Intent intent;
        switch(v.getId()) {
            case R.id.search_search:
                break;
        }

    }

}
