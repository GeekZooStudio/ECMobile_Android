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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.ShippingAdapter;
import com.insthub.ecmobile.protocol.PAYMENT;
import com.insthub.ecmobile.protocol.SHIPPING;

public class ShippingActivity extends BaseActivity {

	private TextView title;
	private ImageView back;
	
	private ListView listView;
	
	private ArrayList<SHIPPING> list = new ArrayList<SHIPPING>();
	private ShippingAdapter shippingAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);
		
		Intent intent = getIntent();
		String s = intent.getStringExtra("payment");
		
		try{
			JSONObject jo = new JSONObject(s);
			JSONArray paymentArray = jo.optJSONArray("shipping_list");
			if (null != paymentArray && paymentArray.length() > 0) {
				list.clear();
				for (int i = 0; i < paymentArray.length(); i++) {
					JSONObject shippingJsonObject = paymentArray.getJSONObject(i);
					SHIPPING shipping_Item = SHIPPING.fromJson(shippingJsonObject);
					list.add(shipping_Item);
				}
        	}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Resources resource = (Resources) getBaseContext().getResources();
        String way=resource.getString(R.string.balance_shipping);
		title = (TextView) findViewById(R.id.top_view_text);
		title.setText(way);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		listView = (ListView) findViewById(R.id.payment_list);

		if(list.size() > 0) {
			listView.setVisibility(View.VISIBLE);
			shippingAdapter = new ShippingAdapter(this, list);
			listView.setAdapter(shippingAdapter);
		} else {
			listView.setVisibility(View.GONE);
            String noway=resource.getString(R.string.no_mode_of_distribution);
			//Toast toast = Toast.makeText(this, noway, 0);
			ToastView toast = new ToastView(this, noway);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
		}
		
		
		listView.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
            {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                SHIPPING shipping = list.get(position);
                try
                {
                    intent.putExtra("shipping",shipping.toJson().toString());
                }
                catch (JSONException e)
                {

                }

				setResult(Activity.RESULT_OK, intent);  
	            finish(); 
			}
		});
		
		
	}
	
}
