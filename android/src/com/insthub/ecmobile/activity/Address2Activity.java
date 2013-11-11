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


import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.SpinnerAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class Address2Activity extends BaseActivity implements BusinessResponse {

	private TextView title;
	private ListView listView;
	private SpinnerAdapter spinnerAdapter;
	private AddressModel addressModel;
	private int i = 0;
	
	private String country_id="";
	private String province_id="";
	private String city_id="";
	private String county_id="";
	
	private String country_name="";
	private String province_name="";
	private String city_name="";
	private String county_name="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_b);
		
		title = (TextView) findViewById(R.id.address_title);
		listView = (ListView) findViewById(R.id.address_list);
        Resources resource = (Resources) getBaseContext().getResources();
        String scoun=resource.getString(R.string.addressb_country );
        title.setText(scoun);
		
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		addressModel.region("0", i);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(i == 1) {
					country_id = addressModel.regionsList0.get(position).id;
					country_name = addressModel.regionsList0.get(position).name;
				} else if(i == 2) {
					province_id = addressModel.regionsList0.get(position).id;
					province_name = addressModel.regionsList0.get(position).name;
				} else if(i == 3) {
					city_id = addressModel.regionsList0.get(position).id;
					city_name = addressModel.regionsList0.get(position).name;
				} else if(i == 4) {
					county_id = addressModel.regionsList0.get(position).id;
					county_name = addressModel.regionsList0.get(position).name;
				}
				addressModel.region(addressModel.regionsList0.get(position).id, i);
				
			}
		});
	}
	
	
	public void setCountry() {
        Resources resource = (Resources) getBaseContext().getResources();
        String spro=resource.getString(R.string.select_province ) ;
        String scity=resource.getString(R.string.select_city );
        String sarea=resource.getString(R.string.select_area );

		if(addressModel.regionsList0.size() == 0) {
			Intent intent = new Intent();
			intent.putExtra("country_id", country_id);
			intent.putExtra("province_id", province_id);
			intent.putExtra("city_id", city_id);
			intent.putExtra("county_id", county_id);
			
			intent.putExtra("country_name", country_name);
			intent.putExtra("province_name", province_name);
			intent.putExtra("city_name", city_name);
			intent.putExtra("county_name", county_name);
			setResult(Activity.RESULT_OK, intent);  
            finish(); 
		}
		i++;
		if(i == 2) {
			title.setText(spro);
		} else if(i == 3) {
			title.setText(scity);
		} else if(i == 4) {
			title.setText(sarea);
		}
		
		spinnerAdapter = new SpinnerAdapter(this, addressModel.regionsList0); 
		listView.setAdapter(spinnerAdapter);
		
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if(url.endsWith(ProtocolConst.REGION)) {
			setCountry();
		}
	}
	
}
