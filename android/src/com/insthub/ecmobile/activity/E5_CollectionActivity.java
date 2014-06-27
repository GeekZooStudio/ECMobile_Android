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

import java.util.ArrayList;

import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;
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
import com.insthub.ecmobile.adapter.E5_CollectionAdapter;
import com.insthub.ecmobile.model.CollectListModel;
import com.insthub.ecmobile.model.ProtocolConst;
/**
 * 收藏页面
 */
public class E5_CollectionActivity extends BaseActivity implements BusinessResponse, IXListViewListener {
	
	private ImageView back;
	private Button edit;
	private XListView xlistView;
	private E5_CollectionAdapter collectAdapter;
	private boolean isEdit = false;
	private CollectListModel collectListModel;
	public  Handler messageHandler;
	private int position;
	private View null_pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e5_collection);
		
		back = (ImageView) findViewById(R.id.collect_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		null_pager = findViewById(R.id.null_pager);
		edit = (Button) findViewById(R.id.collect_edit);
		xlistView = (XListView) findViewById(R.id.collect_list);
		xlistView.setPullLoadEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
                Resources resource = (Resources) getBaseContext().getResources();
                String fin=resource.getString(R.string.manage2_over);
                String com=resource.getString(R.string.collect_compile);

				if(!isEdit) {
					collectAdapter.flag = 2;
					collectAdapter.notifyDataSetChanged();
					isEdit = true;
					edit.setText(fin);
				} else {
					collectAdapter.flag = 1;
					collectAdapter.notifyDataSetChanged();
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
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
			edit.setEnabled(false);
			if(collectAdapter != null) {
				collectAdapter.list = collectListModel.collectList;
				collectAdapter.notifyDataSetChanged();
				edit.setText(com);
			}
			null_pager.setVisibility(View.VISIBLE);
			xlistView.setVisibility(View.GONE);
			
		} else {
			xlistView.setVisibility(View.VISIBLE);
			null_pager.setVisibility(View.GONE);
			edit.setEnabled(true);
			if(collectAdapter == null) {
				collectAdapter = new E5_CollectionAdapter(this, collectListModel.collectList, 1);
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
		if(url.endsWith(ApiInterface.USER_COLLECT_LIST)) {
			xlistView.setRefreshTime();
			xlistView.stopRefresh();
			xlistView.stopLoadMore();
			if(collectListModel.paginated.more == 0) {
				xlistView.setPullLoadEnable(false);
			} else {
				xlistView.setPullLoadEnable(true);
			}
			setCollectList();
		} else if(url.endsWith(ApiInterface.USER_COLLECT_DELETE)) {
			collectListModel.collectList.remove(position);
			collectAdapter.list = collectListModel.collectList;
			collectAdapter.notifyDataSetChanged();
		} 
		
	}

	@Override
	public void onRefresh(int id) {				
		collectListModel.getCollectList();
	}

	@Override
	public void onLoadMore(int id) {		
		collectListModel.getCollectListMore();
	}
    @Override
    public void onResume() {
        super.onResume();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageStart("Collect");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this),"");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageEnd("Collect");
            MobclickAgent.onPause(this);
        }
    }
}
