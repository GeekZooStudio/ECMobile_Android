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

import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CollectAdapter;
import com.insthub.ecmobile.model.CollectListModel;
import com.insthub.ecmobile.model.ProtocolConst;
/**
 * 收藏页面
 */
public class CollectActivity extends BaseActivity implements BusinessResponse, IXListViewListener {
	
	private ImageView back;
	private Button edit;
	//private ImageView bg;
	private XListView xlistView;
	private CollectAdapter collectAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private boolean isEdit = false;
	
	private CollectListModel collectListModel;
	
	public  Handler messageHandler;
	
	private int position;
	private View null_pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect);
		
		back = (ImageView) findViewById(R.id.collect_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//bg = (ImageView) findViewById(R.id.collect_list_bg);
		null_pager = findViewById(R.id.null_pager);
		edit = (Button) findViewById(R.id.collect_edit);
		xlistView = (XListView) findViewById(R.id.collect_list);
		xlistView.setPullLoadEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Resources resource = (Resources) getBaseContext().getResources();
                String fin=resource.getString(R.string.manage2_over);
                String com=resource.getString(R.string.collect_compile);

				if(!isEdit) {
					collectAdapter.flag = 2;
					collectAdapter.notifyDataSetChanged();
//					collectAdapter = new CollectAdapter(CollectActivity.this, items, 2);
//					listView.setAdapter(collectAdapter);
					isEdit = true;
					edit.setText(fin);
				} else {
					collectAdapter.flag = 1;
					collectAdapter.notifyDataSetChanged();
//					collectAdapter = new CollectAdapter(CollectActivity.this, items, 1);
//					listView.setAdapter(collectAdapter);
					isEdit = false;
					edit.setText(com);
				}
			}
		});
		
		collectListModel = new CollectListModel(this);
		collectListModel.addResponseListener(this);
		collectListModel.getCollectList();
		
		messageHandler = new Handler(){

            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    int rec_id =  msg.arg1;
                    position = msg.arg2;
                    collectListModel.collectDelete(rec_id+"");
                }            
            }
        };
		
	}
	
	public void setCollectList() {
        Resources resource = (Resources) getBaseContext().getResources();
        String nof=resource.getString(R.string.no_favorite);
        String com=resource.getString(R.string.collect_compile);
		
		if(collectListModel.collectList.size() == 0) {
			ToastView toast = new ToastView(this, nof);
			//Toast toast = Toast.makeText(this, nof, 0);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
			edit.setEnabled(false);
			if(collectAdapter != null) {
				collectAdapter.list = collectListModel.collectList;
				collectAdapter.notifyDataSetChanged();
				edit.setText(com);
			}
			null_pager.setVisibility(View.VISIBLE);
			//bg.setVisibility(View.VISIBLE);
			xlistView.setVisibility(View.GONE);
			
		} else {
			xlistView.setVisibility(View.VISIBLE);
			null_pager.setVisibility(View.GONE);
			//bg.setVisibility(View.GONE);
			edit.setEnabled(true);
			if(collectAdapter == null) {
				collectAdapter = new CollectAdapter(this, collectListModel.collectList, 1);
				xlistView.setAdapter(collectAdapter);
			} else {
				collectAdapter.list = collectListModel.collectList;
				collectAdapter.notifyDataSetChanged();
			}
			collectAdapter.parentHandler = messageHandler;
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.COLLECT_LIST)) {
			xlistView.setRefreshTime();
			xlistView.stopRefresh();
			xlistView.stopLoadMore();
			if(collectListModel.paginated.more == 0) {
				xlistView.setPullLoadEnable(false);
			} else {
				xlistView.setPullLoadEnable(true);
			}
			setCollectList();
		} else if(url.endsWith(ProtocolConst.COLLECT_DELETE)) {
			
			//collectListModel.getCollectList();
			collectListModel.collectList.remove(position);
			collectAdapter.list = collectListModel.collectList;
			collectAdapter.notifyDataSetChanged();
			//xlistView.setAdapter(collectAdapter);
			
		} 
		
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		
		collectListModel.getCollectList();
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		collectListModel.getCollectListMore();
	}

}
