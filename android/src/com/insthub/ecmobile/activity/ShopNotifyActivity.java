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

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.ShopNotifityAdapter;
import com.insthub.ecmobile.model.MsgModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.MESSAGE;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopNotifyActivity extends BaseActivity implements IXListViewListener,BusinessResponse {

	private TextView title;
	private ImageView back;
	
	private XListView xlistView;
	private ShopNotifityAdapter shopNotifyAdapter;
	
	private MsgModel dataModel;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_notify);

        Resources resource = (Resources) getBaseContext().getResources();
        String mes=resource.getString(R.string.profile_message);
		title = (TextView) findViewById(R.id.top_view_text);
		title.setText(mes);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

        dataModel = MsgModel.getInstance();
        dataModel.addResponseListener(this);
        dataModel.fetchPre();
		
		xlistView = (XListView) findViewById(R.id.shop_notify_list);
		xlistView.setPullLoadEnable(true);
        xlistView.setPullRefreshEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
        xlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MESSAGE message = dataModel.msg_list.get(position - 1);
                if (0 == message.action.compareTo("search"))
                {
                    String parameter = message.parameter;

                    Intent it = new Intent(ShopNotifyActivity.this, GoodsListActivity.class);
                    FILTER filter = new FILTER();
                    filter.keywords = parameter;
                    try
                    {
                        it.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                    }
                    catch (JSONException e)
                    {

                    }

                    startActivity(it);
                }
            }
        });
        setCont();

		
		
	}

	@Override
	public void onRefresh(int id) {
		
		dataModel.fetchPre();
		
	}

	@Override
	public void onLoadMore(int id) {
		dataModel.fetchNext();
		
	}
	
	public void setCont() {
		
		if(dataModel.msg_list.size() > 0 ) {
			xlistView.setVisibility(View.VISIBLE);
			if(shopNotifyAdapter == null) {
				shopNotifyAdapter = new ShopNotifityAdapter(this, dataModel.msg_list);
				xlistView.setAdapter(shopNotifyAdapter);
			} else {
				shopNotifyAdapter.list = dataModel.msg_list;
				shopNotifyAdapter.notifyDataSetChanged();
			}
		} else {
			xlistView.setVisibility(View.GONE);
		}
		
		 
		
	}

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
       
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataModel.removeResponseListener(this);
    }
}
