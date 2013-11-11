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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.activeandroid.query.Delete;
import com.external.activeandroid.util.ReflectionUtils;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.ADDRESS;

public class AddressManage2Activity extends BaseActivity implements BusinessResponse {
	
	private ImageView back;
	private Button change;
	
	private AddressModel addressModel;
	
	private EditText name;
	private EditText tel;
	private EditText email;
	private EditText phoneNum;
	private EditText zipCode;
	private LinearLayout area;
	private TextView address;
	private EditText detail;
	private Button setDefault;
	private Button del;
	
	private String country_id;
	private String province_id;
	private String city_id;
	private String county_id;
	
	private String address_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_manage2);
		
		back = (ImageView) findViewById(R.id.address_manage2_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		del = (Button) findViewById(R.id.address_manage2_del);
		del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null != addressModel.address && addressModel.address.default_address == 1) {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String can=resource.getString(R.string.can_not_delete );
                    ToastView toast = new ToastView(AddressManage2Activity.this, can);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else {
					addressModel.addressDelete(address_id);
				}
			}
		});
		
		name = (EditText) findViewById(R.id.address_manage2_name);
		tel = (EditText) findViewById(R.id.address_manage2_telNum);
		email = (EditText) findViewById(R.id.address_manage2_email);
		phoneNum = (EditText) findViewById(R.id.address_manage2_phoneNum);
		zipCode = (EditText) findViewById(R.id.address_manage2_zipCode);
		area = (LinearLayout) findViewById(R.id.address_manage2_area);
		address = (TextView) findViewById(R.id.address_manage2_address);
		detail = (EditText) findViewById(R.id.address_manage2_detail);
		setDefault = (Button) findViewById(R.id.address_manage2_default);
		change = (Button) findViewById(R.id.address_manage2_change);
		
		area.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddressManage2Activity.this, Address2Activity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		Intent intent = getIntent();
		address_id = intent.getStringExtra("address_id");
		
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		addressModel.getAddressInfo(address_id);
		
		setDefault.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addressModel.addressDefault(address_id);
			}
		});
		
		change.setOnClickListener(new OnClickListener() {
			
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
                String tel=resource.getString(R.string.add_tel);
                String email=resource.getString(R.string.add_email);
                String cor=resource.getString(R.string.add_correct_email);
                String addr=resource.getString(R.string.add_address );
                String con=resource.getString(R.string.confirm_address);

                if("".equals(consignee)) {
					Toast toast = Toast.makeText(AddressManage2Activity.this, name, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(telNum)) {
					Toast toast = Toast.makeText(AddressManage2Activity.this, tel, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(mail)) {
					Toast toast = Toast.makeText(AddressManage2Activity.this, email, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if(!ReflectionUtils.isEmail(mail)) {
					Toast toast = Toast.makeText(AddressManage2Activity.this, cor, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(address)) {
					Toast toast = Toast.makeText(AddressManage2Activity.this, addr, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if(country_id == null || province_id == null || city_id == null || county_id == null) {
					Toast toast = Toast.makeText(AddressManage2Activity.this, con, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else {
					addressModel.addressUpdate(address_id, consignee, telNum, mail, mobile, zipcode, address, country_id, province_id, city_id, county_id);
				}
				
			}
		});
		
	}
	
	public void setAddressInfo() {
		name.setText(addressModel.address.consignee);
		tel.setText(addressModel.address.tel);
		email.setText(addressModel.address.email);
		phoneNum.setText(addressModel.address.mobile);
		zipCode.setText(addressModel.address.zipcode);
		detail.setText(addressModel.address.address);
		
		StringBuffer sbf = new StringBuffer();
		sbf.append(addressModel.address.province_name+" ");
		sbf.append(addressModel.address.city_name+" ");
		sbf.append(addressModel.address.district_name);
		address.setText(sbf.toString());
		
		country_id = addressModel.address.country;
		province_id = addressModel.address.province;
		city_id = addressModel.address.city;
		county_id = addressModel.address.district;
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if(url.endsWith(ProtocolConst.ADDRESS_INFO)) {
			setAddressInfo();
		} else if(url.endsWith(ProtocolConst.ADDRESS_DEFAULT)) {
            finish();
		} else if(url.endsWith(ProtocolConst.ADDRESS_DELETE)) {
			delete();
			finish();
		} else if(url.endsWith(ProtocolConst.ADDRESS_UPDATE)) {
			if(addressModel.address.default_address == 1) {
				addressModel.addressDefault(address_id);
			} else {
                Resources resource = (Resources) getBaseContext().getResources();
                String suc=resource.getString(R.string.successful_operation);
                Toast toast = Toast.makeText(AddressManage2Activity.this, suc, 0);
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.show();
				finish();
			}
			
		}
		
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (data != null) {
				country_id = data.getStringExtra("country_id");
				province_id = data.getStringExtra("province_id");
				city_id = data.getStringExtra("city_id");
				county_id = data.getStringExtra("county_id");
				
				StringBuffer sbf = new StringBuffer();
				sbf.append(data.getStringExtra("province_name")+" ");
				sbf.append(data.getStringExtra("city_name")+" ");
				sbf.append(data.getStringExtra("county_name"));
				address.setText(sbf.toString());
			}
		}
    }
    
    // 根据id删除数据库里的一条记录
    public void delete() {
    	new Delete().from(ADDRESS.class).where("ADDRESS_id = ?", address_id).execute();
    }

}
