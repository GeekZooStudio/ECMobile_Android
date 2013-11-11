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
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CommentAdapter;
import com.insthub.ecmobile.model.CommentModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class CommentActivity extends BaseActivity implements IXListViewListener, BusinessResponse {

	private TextView title;
	private ImageView back;
	
	private XListView xlistView;
	private CommentAdapter commentAdapter;
	
	private int goods_id;
	
	private CommentModel commentModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		Intent intent = getIntent();
		goods_id = intent.getIntExtra("id", 0);
		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String com=resource.getString(R.string.gooddetail_commit);
		title.setText(com);
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		xlistView = (XListView) findViewById(R.id.comment_list);
		xlistView.setPullLoadEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		
		commentModel = new CommentModel(this);
		commentModel.addResponseListener(this);
		commentModel.getCommentList(goods_id);
		
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		
		commentModel.getCommentList(goods_id);
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		commentModel.getCommentsMore(goods_id);
	}
	
	public void setComment() {
		
		if(commentModel.comment_list.size() > 0) {
			xlistView.setVisibility(View.VISIBLE);
			if(commentAdapter == null) {
				commentAdapter = new CommentAdapter(this, commentModel.comment_list);
				xlistView.setAdapter(commentAdapter);
			} else {
				commentAdapter.list = commentModel.comment_list;
				commentAdapter.notifyDataSetChanged();
			}
			
		} else {
			xlistView.setVisibility(View.GONE);
            Resources resource = (Resources) getBaseContext().getResources();
            String noc=resource.getString(R.string.no_commit);
            ToastView toast = new ToastView(this, noc);
			//Toast toast = Toast.makeText(this, noc, 0);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.COMMENTS)) {
			xlistView.setRefreshTime();
			xlistView.stopRefresh();
			xlistView.stopLoadMore();
			if(commentModel.paginated.more == 0) {
				xlistView.setPullLoadEnable(false);
			} else {
				xlistView.setPullLoadEnable(true);
			}
			setComment();
		}
		
	}
	
}
