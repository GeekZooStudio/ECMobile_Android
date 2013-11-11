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

import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.InvoiceAdapter;
import com.insthub.ecmobile.protocol.INV_LIST;

public class InvoiceActivity extends BaseActivity implements OnClickListener {

	private ImageView back;
	private Button save;
	
	private EditText taitou;
	
	private ListView listView1;
	private ListView listView2;
	
	private ArrayList<INV_LIST> list1 = new ArrayList<INV_LIST>();
	private ArrayList<INV_LIST> list2 = new ArrayList<INV_LIST>();
	
	private InvoiceAdapter invoiceAdapter1;
	private InvoiceAdapter invoiceAdapter2;
	
	private String type_id = null;
	private String content_id = null;

	private String inv_payee = null; //发票抬头
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoice);
		
        listView1 = (ListView) findViewById(R.id.invoice_list1);
        listView2 = (ListView) findViewById(R.id.invoice_list2);

		Intent intent = getIntent();
		String s = intent.getStringExtra("payment");
		type_id = intent.getStringExtra("inv_type");
		content_id = intent.getStringExtra("inv_content");
		inv_payee = intent.getStringExtra("inv_payee");
		
		try{
			JSONObject jo = new JSONObject(s);
			JSONArray contentArray = jo.optJSONArray("inv_content_list");

			if (null != contentArray && contentArray.length() > 0)
            {
				list1.clear();
				for (int i = 0; i < contentArray.length(); i++)
                {
					JSONObject contentJsonObject = contentArray.getJSONObject(i);
					INV_LIST content_Item = INV_LIST.fromJson(contentJsonObject);
					list1.add(content_Item);
				}
        	}
            else
            {
                listView1.setVisibility(View.GONE);
            }
			
			JSONArray typetArray = jo.optJSONArray("inv_type_list");
			if (null != typetArray && typetArray.length() > 0)
            {
				list2.clear();
				for (int i = 0; i < typetArray.length(); i++)
                {
					JSONObject typeJsonObject = typetArray.getJSONObject(i);
					INV_LIST type_Item = INV_LIST.fromJson(typeJsonObject);
					list2.add(type_Item);
				}
        	}
            else
            {
                listView2.setVisibility(View.GONE);
            }
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		back = (ImageView) findViewById(R.id.invoice_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		save = (Button) findViewById(R.id.invoice_save);
		save.setOnClickListener(this);
		taitou = (EditText) findViewById(R.id.invoice_taitou);
		
		taitou.setText(inv_payee);
		
		invoiceAdapter1 = new InvoiceAdapter(this, list1, type_id);
		listView1.setAdapter(invoiceAdapter1);
		
		invoiceAdapter2 = new InvoiceAdapter(this, list2, content_id);
		listView2.setAdapter(invoiceAdapter2);
		
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
//				for(int i=0;i<list1.size();i++) {
//					if(i == position) {
//						view.setBackgroundColor(Color.parseColor("#cccccc"));
//					} else {
//						parent.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
//					}
//				}
				
				invoiceAdapter1.flag = position;
				invoiceAdapter1.notifyDataSetChanged();
				
				type_id = list1.get(position).id;
				
			}
		});
		
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
//				for(int i=0;i<list2.size();i++) {
//					if(i == position) {
//						view.setBackgroundColor(Color.parseColor("#cccccc"));
//					} else {
//						parent.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
//					}
//				}
				invoiceAdapter2.flag = position;
				invoiceAdapter2.notifyDataSetChanged();
				content_id = list2.get(position).id;
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.invoice_save:
			Intent intent = new Intent();
			intent.putExtra("inv_type", type_id);
			intent.putExtra("inv_content", content_id);
			intent.putExtra("inv_payee", taitou.getText().toString());
			setResult(Activity.RESULT_OK, intent);  
            finish(); 
			break;
		}
		
	}
	
}
