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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.adapter.SpinnerAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.REGIONS;

public class AddressActivity extends BaseActivity implements BusinessResponse {
	
	private TextView title;
	private ImageView back;
	
	private AddressModel addressModel;
	private SpinnerAdapter spinnerAdapter0;
	private SpinnerAdapter spinnerAdapter1;
	private SpinnerAdapter spinnerAdapter2;
	private SpinnerAdapter spinnerAdapter3;
	
	
	private Spinner country;
	private Spinner province;
	private Spinner city;
	private Spinner county;
	
	private String country_id;
	private String province_id;
	private String city_id;
	private String county_id;
	
	private String country_name;
	private String province_name;
	private String city_name;
	private String county_name;
	
	private int i = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address);
		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String add=resource.getString(R.string.address_add);
		title.setText(add);
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		});
        String cou=resource.getString(R.string.country );
        String pro=resource.getString(R.string.province);
        String cit=resource.getString(R.string.city);
        String are=resource.getString(R.string.area);

		country = (Spinner) findViewById(R.id.address_country);
		country.setPrompt(cou);
		
		province = (Spinner) findViewById(R.id.address_province);
		province.setPrompt(pro);
		
		city = (Spinner) findViewById(R.id.address_city);
		city.setPrompt(cit);
		
		county = (Spinner) findViewById(R.id.address_county);
		county.setPrompt(are);
		
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		addressModel.region("0", i);
		//addressModel.region("1");
		
	}
	
	public void setCountry() {
		
		spinnerAdapter0 = new SpinnerAdapter(this, addressModel.regionsList0); 
		country.setAdapter(spinnerAdapter0);
		
		country.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				i = 1;
				addressModel.region(addressModel.regionsList0.get(position).id, i);
				country_id = addressModel.regionsList0.get(position).id;
				country_name = addressModel.regionsList0.get(position).name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
	}
	
	public void setProvince() {
		
		spinnerAdapter1 = new SpinnerAdapter(this, addressModel.regionsList1);  
		province.setAdapter(spinnerAdapter1);
		
		province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				i = 2;
				addressModel.region(addressModel.regionsList1.get(position).id, i);
				province_id = addressModel.regionsList1.get(position).id;
				province_name = addressModel.regionsList1.get(position).name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
	}

	public void setCity() {

		spinnerAdapter2 = new SpinnerAdapter(this, addressModel.regionsList2);
		city.setAdapter(spinnerAdapter2);

		city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				i = 3;
				addressModel.region(addressModel.regionsList2.get(position).id, i);
				city_id = addressModel.regionsList2.get(position).id;
				city_name = addressModel.regionsList2.get(position).name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

	}

	public void setCounty() {

		spinnerAdapter3 = new SpinnerAdapter(this, addressModel.regionsList3);
		county.setAdapter(spinnerAdapter3);

		county.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				i = 4;
				county_id = addressModel.regionsList3.get(position).id;
				county_name = addressModel.regionsList3.get(position).name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.REGION)) {
			//Toast.makeText(this, jo+"", 0).show();
			if(i == 0) {
				setCountry();
			} else if(i == 1) {
				setProvince();
			} else if(i == 2) {
				setCity();
			} else if(i == 3) {
				setCounty();
			}
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
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
			return false;
		}
		return true;
	}
	
	

}
