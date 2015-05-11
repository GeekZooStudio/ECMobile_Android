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

import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;
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

public class F2_EditAddressActivity extends BaseActivity implements BusinessResponse {
	
	private ImageView back;
	private Button change;
	private AddressModel addressModel;
	private EditText name;
	private EditText tel;
	private EditText email;
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
    private static  final int REQUEST_REGION_PICK=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f2_edit_address);
		
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
                    ToastView toast = new ToastView(F2_EditAddressActivity.this, can);
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
		zipCode = (EditText) findViewById(R.id.address_manage2_zipCode);
		area = (LinearLayout) findViewById(R.id.address_manage2_area);
		address = (TextView) findViewById(R.id.address_manage2_address);
		detail = (EditText) findViewById(R.id.address_manage2_detail);
		setDefault = (Button) findViewById(R.id.address_manage2_default);
		change = (Button) findViewById(R.id.address_manage2_change);
		
		area.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent(F2_EditAddressActivity.this, F3_RegionPickActivity.class);
				startActivityForResult(intent, REQUEST_REGION_PICK);
				overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
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
					Toast toast = Toast.makeText(F2_EditAddressActivity.this, name, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(telNum)) {
					Toast toast = Toast.makeText(F2_EditAddressActivity.this, tel, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(mail)) {
					Toast toast = Toast.makeText(F2_EditAddressActivity.this, email, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if(!ReflectionUtils.isEmail(mail)) {
					Toast toast = Toast.makeText(F2_EditAddressActivity.this, cor, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if("".equals(address)) {
					Toast toast = Toast.makeText(F2_EditAddressActivity.this, addr, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else if(country_id == null || province_id == null || city_id == null || county_id == null) {
					Toast toast = Toast.makeText(F2_EditAddressActivity.this, con, 0);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				} else {
					addressModel.addressUpdate(address_id, consignee, telNum, mail, "", zipcode, address, country_id, province_id, city_id, county_id);
				}
				
			}
		});
		
	}
	
	public void setAddressInfo() {		
		name.setText(addressModel.address.consignee);
		tel.setText(addressModel.address.tel);
		email.setText(addressModel.address.email);
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
		if(url.endsWith(ApiInterface.ADDRESS_INFO)) {
			setAddressInfo();
		} else if(url.endsWith(ApiInterface.ADDRESS_SETDEFAULT)) {
            finish();
		} else if(url.endsWith(ApiInterface.ADDRESS_DELETE)) {
			delete();
			finish();
		} else if(url.endsWith(ApiInterface.ADDRESS_UPDATE)) {
			if(addressModel.address.default_address == 1) {
				addressModel.addressDefault(address_id);
			} else {
                Resources resource = (Resources) getBaseContext().getResources();
                String suc=resource.getString(R.string.successful_operation);
		        ToastView toast = new ToastView(getApplicationContext(), suc);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
				finish();
			}
			
		}
		
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    	
    	super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_REGION_PICK) {
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
