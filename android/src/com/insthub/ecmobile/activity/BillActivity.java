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

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

public class BillActivity extends BaseActivity implements OnClickListener {

	private ImageView back;
	private Button save;
	
	private LinearLayout type1;
	private LinearLayout type2;
	private LinearLayout type3;
	
	private ImageView invoice1;
	private ImageView invoice2;
	private ImageView invoice3;
	
	private LinearLayout work;
	private LinearLayout food;
	private LinearLayout commodity;
	
	private ImageView flag1;
	private ImageView flag2;
	private ImageView flag3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill);
		
		back = (ImageView) findViewById(R.id.bill_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		save = (Button) findViewById(R.id.bill_save);
		
		type1 = (LinearLayout) findViewById(R.id.bill_type1);
		type2 = (LinearLayout) findViewById(R.id.bill_type2);
		type3 = (LinearLayout) findViewById(R.id.bill_type3);
		
		invoice1 = (ImageView) findViewById(R.id.bill_invoice1);
		invoice2 = (ImageView) findViewById(R.id.bill_invoice2);
		invoice3 = (ImageView) findViewById(R.id.bill_invoice3);
		
		work = (LinearLayout) findViewById(R.id.bill_work);
		food = (LinearLayout) findViewById(R.id.bill_food);
		commodity = (LinearLayout) findViewById(R.id.bill_commodity);
		
		flag1 = (ImageView) findViewById(R.id.bill_work_flag);
		flag2 = (ImageView) findViewById(R.id.bill_food_flag);
		flag3 = (ImageView) findViewById(R.id.bill_commodity_flag);
		
		save.setOnClickListener(this);
		
		type1.setOnClickListener(this);
		type2.setOnClickListener(this);
		type3.setOnClickListener(this);
		
		work.setOnClickListener(this);
		food.setOnClickListener(this);
		commodity.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.bill_save:
			finish();
			break;
		case R.id.bill_type1:
			invoice1.setVisibility(View.VISIBLE);
			invoice2.setVisibility(View.GONE);
			invoice3.setVisibility(View.GONE);
			break;
		case R.id.bill_type2:
			invoice1.setVisibility(View.GONE);
			invoice2.setVisibility(View.VISIBLE);
			invoice3.setVisibility(View.GONE);
			break;
		case R.id.bill_type3:
			invoice1.setVisibility(View.GONE);
			invoice2.setVisibility(View.GONE);
			invoice3.setVisibility(View.VISIBLE);
			break;
		case R.id.bill_work:
			flag1.setVisibility(View.VISIBLE);
			flag2.setVisibility(View.GONE);
			flag3.setVisibility(View.GONE);
			break;
		case R.id.bill_food:
			flag1.setVisibility(View.GONE);
			flag2.setVisibility(View.VISIBLE);
			flag3.setVisibility(View.GONE);
			break;
		case R.id.bill_commodity:
			flag1.setVisibility(View.GONE);
			flag2.setVisibility(View.GONE);
			flag3.setVisibility(View.VISIBLE);
			break;
		}
		
	}
	
}
