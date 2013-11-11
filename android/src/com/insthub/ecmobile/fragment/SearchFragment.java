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

import android.app.Activity;
import android.widget.*;
import com.external.maxwin.view.XListView;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.activity.AdvanceSearchActivity;
import com.insthub.ecmobile.activity.CategoryChildActivity;
import com.insthub.ecmobile.adapter.CategoryListAdapter;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.FILTER;
import org.json.JSONException;
import android.content.res.Resources;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView.OnEditorActionListener;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyViewGroup;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.GoodsListActivity;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.SearchModel;

public class SearchFragment extends BaseFragment implements OnClickListener, BusinessResponse {
	
	private View view;
	private ImageView search;
	private EditText input;
	
	private SearchModel searchModel;
	
	private Button btn[];
	private MyViewGroup layout;

    private XListView parentListView;

    CategoryListAdapter parentListAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.search, null);
		
		input = (EditText) view.findViewById(R.id.search_input);
		search = (ImageView) view.findViewById(R.id.search_search);
		layout = (MyViewGroup) view.findViewById(R.id.search_layout);

        parentListView = (XListView) view.findViewById(R.id.parent_list);
		
		search.setOnClickListener(this);
        
        input.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    try
                    {
                        Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                        FILTER filter = new FILTER();
                        filter.keywords = input.getText().toString().toString();
                        intent.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_right_in,
                                R.anim.push_right_out);

                    }
                    catch (JSONException e)
                    {

                    }
				}
				return false;
			}
		});

		
		if (null == searchModel)
        {
            searchModel = new SearchModel(getActivity());
            searchModel.searchCategory();
        }

		searchModel.addResponseListener(this);

        parentListAdapter = new CategoryListAdapter(getActivity(),searchModel.categoryArrayList);
        parentListView.setAdapter(parentListAdapter);
        parentListView.setPullLoadEnable(false);
        parentListView.setPullRefreshEnable(false);
        parentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position -1 < searchModel.categoryArrayList.size())
                {
                    CATEGORY category = searchModel.categoryArrayList.get(position -1);
                    try
                    {
                        if (category.children.size() > 0)
                        {
                            Intent it = new Intent(getActivity(),CategoryChildActivity.class);
                            it.putExtra("category",category.toJson().toString());
                            getActivity().startActivity(it);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                            FILTER filter = new FILTER();
                            filter.category_id = String.valueOf(category.id);
                            intent.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

		
		addKeywords();
		
		return view;
	} 

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String tag;
		Intent intent;
		switch(v.getId())
        {
            case R.id.search_search:

                break;
		}
		
	}
	
	// 动态添加button，并设置监听
	public void addKeywords() {
		
		if(searchModel.list.size() > 0) {
			
			layout.removeAllViews();
			btn = new Button[searchModel.list.size()];
			for(int i=0; i<searchModel.list.size(); i++)
            {
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.button_view, null);
				btn[i] = (Button) view.findViewById(R.id.search_keyword);
				btn[i].setText(searchModel.list.get(i).toString());
				layout.addView(view);
			}
			
			for(int k=0;k<searchModel.list.size();k++) {
				btn[k].setTag(k);
				btn[k].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int a = (Integer) v.getTag();
                        try
                        {
                            Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                            FILTER filter = new FILTER();
                            filter.keywords = btn[a].getText().toString();
                            intent.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        catch (JSONException e)
                        {

                        }

					}
				});
			}
		}
		
	}

	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
		if (url.endsWith(ProtocolConst.SEARCHKEYWORDS))
        {
			addKeywords();
		}
        else if (url.endsWith(ProtocolConst.CATEGORY))
        {
            parentListAdapter.notifyDataSetChanged();
        }
	}


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
