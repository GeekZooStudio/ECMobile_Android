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

import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.activeandroid.util.ReflectionUtils;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class AddAddressActivity extends BaseActivity implements BusinessResponse {
	
	private TextView title;
	private ImageView back;
	
	private EditText name;
	private EditText tel;
	private EditText email;
	private EditText phoneNum;
	private EditText zipCode;
	private LinearLayout area;
	private TextView address;
	private EditText detail;
	private FrameLayout add;
	
	private String country_id;
	private String province_id;
	private String city_id;
	private String county_id;
	
	private AddressModel addressModel;
	private int flag;
	
	private ProgressDialog pd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_address);
		
		Intent intent = getIntent();
		flag = intent.getIntExtra("balance", 0);
		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        resource.getString(R.string.address_add);

		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		name = (EditText) findViewById(R.id.add_address_name);
		tel = (EditText) findViewById(R.id.add_address_telNum);
		email = (EditText) findViewById(R.id.add_address_email);
		phoneNum = (EditText) findViewById(R.id.add_address_phoneNum);
		zipCode = (EditText) findViewById(R.id.add_address_zipCode);
		area = (LinearLayout) findViewById(R.id.add_address_area);
		address = (TextView) findViewById(R.id.add_address_address);
		detail = (EditText) findViewById(R.id.add_address_detail);
		add = (FrameLayout) findViewById(R.id.add_address_add);
		
		area.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddAddressActivity.this, Address2Activity.class);
				startActivityForResult(intent, 1);
				
			}
		});
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String consignee = name.getText().toString();
				String telNum = tel.getText().toString();
				String mail = email.getText().toString();
				String mobile = phoneNum.getText().toString();
				String zipcode = zipCode.getText().toString();
				String address = detail.getText().toString();

                Resources resource = (Resources) getBaseContext().getResources();
                String name=resource.getString(R.string.add_name);
                String addtel=resource.getString(R.string.add_tel);
                String email=resource.getString(R.string.add_email);
                String cor=resource.getString(R.string.add_correct_email );
                String adda=resource.getString(R.string.add_address);
                String con=resource.getString(R.string.confirm_address);

				if("".equals(consignee)) {
					ToastView toast = new ToastView(AddAddressActivity.this, name);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(telNum)) {
					ToastView toast = new ToastView(AddAddressActivity.this, addtel);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(mail)) {
					ToastView toast = new ToastView(AddAddressActivity.this, email);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if(!ReflectionUtils.isEmail(mail)) {
					ToastView toast = new ToastView(AddAddressActivity.this, cor);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(address)) {
					ToastView toast = new ToastView(AddAddressActivity.this, adda);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if(country_id == null || province_id == null || city_id == null || county_id == null) {
					ToastView toast = new ToastView(AddAddressActivity.this, con);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else {
					addressModel = new AddressModel(AddAddressActivity.this);
					addressModel.addResponseListener(AddAddressActivity.this);
					addressModel.addAddress(consignee, telNum, mail, mobile, zipcode, address, country_id, province_id, city_id, county_id);
				
				}
				
			}
		});
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (data != null) {
				country_id = data.getStringExtra("country_id");
				province_id = data.getStringExtra("province_id");
				city_id = data.getStringExtra("city_id");
				county_id = data.getStringExtra("county_id");
				
				StringBuffer sbf = new StringBuffer();
				sbf.append(data.getStringExtra("country_name")+" ");
				sbf.append(data.getStringExtra("province_name")+" ");
				sbf.append(data.getStringExtra("city_name")+" ");
				sbf.append(data.getStringExtra("county_name"));
				address.setText(sbf.toString());
				
			}
		}
    }

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if(url.endsWith(ProtocolConst.ADDRESS_ADD)) {
			if(flag == 1) {
				Intent intent = new Intent();
				intent.putExtra("ok", "ok");
				setResult(Activity.RESULT_OK, intent);  
                finish(); 
			} else {
				finish();
			}
			
		}
		
	}

}
