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

import android.content.SharedPreferences;
import com.external.activeandroid.query.Select;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.USER;
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

public class F1_NewAddressActivity extends BaseActivity implements BusinessResponse {
	
	private TextView title;
	private ImageView back;
	private EditText name;
	private EditText tel;
	private EditText email;
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
    private SharedPreferences shared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f1_new_address);
		
		Intent intent = getIntent();
		flag = intent.getIntExtra("balance", 0);
        shared =getSharedPreferences("userInfo", 0);
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        title.setText(resource.getString(R.string.address_add));
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
        email.setText(shared.getString("email",""));
		zipCode = (EditText) findViewById(R.id.add_address_zipCode);
		area = (LinearLayout) findViewById(R.id.add_address_area);
		address = (TextView) findViewById(R.id.add_address_address);
		detail = (EditText) findViewById(R.id.add_address_detail);
		add = (FrameLayout) findViewById(R.id.add_address_add);
		
		area.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(F1_NewAddressActivity.this, F3_RegionPickActivity.class);
				startActivityForResult(intent, 1);
				overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
				
			}
		});
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String consignee = name.getText().toString();
				String telNum = tel.getText().toString();
				String mail = email.getText().toString();
				String zipcode = zipCode.getText().toString();
				String address = detail.getText().toString();

                Resources resource = (Resources) getBaseContext().getResources();
                String nameText=resource.getString(R.string.add_name);
                String addtel=resource.getString(R.string.add_tel);
                String emailText=resource.getString(R.string.add_email);
                String cor=resource.getString(R.string.add_correct_email );
                String adda=resource.getString(R.string.add_address);
                String con=resource.getString(R.string.confirm_address);

				if("".equals(consignee)) {
					ToastView toast = new ToastView(F1_NewAddressActivity.this, nameText);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                    name.requestFocus();
				} else if("".equals(telNum)) {
					ToastView toast = new ToastView(F1_NewAddressActivity.this, addtel);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                    tel.requestFocus();
				} else if("".equals(mail)) {
					ToastView toast = new ToastView(F1_NewAddressActivity.this, emailText);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                    email.requestFocus();
				} else if(!ReflectionUtils.isEmail(mail)) {
					ToastView toast = new ToastView(F1_NewAddressActivity.this, cor);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                    email.requestFocus();
				} else if("".equals(address)) {
					ToastView toast = new ToastView(F1_NewAddressActivity.this, adda);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                    detail.requestFocus();
				} else if(country_id == null || province_id == null || city_id == null || county_id == null) {
					ToastView toast = new ToastView(F1_NewAddressActivity.this, con);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
                    Intent intent = new Intent(F1_NewAddressActivity.this, F3_RegionPickActivity.class);
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
				} else {
					addressModel = new AddressModel(F1_NewAddressActivity.this);
					addressModel.addResponseListener(F1_NewAddressActivity.this);
					addressModel.addAddress(consignee, telNum, mail, "", zipcode, address, country_id, province_id, city_id, county_id);
				
				}
				
			}
		});
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
		if(url.endsWith(ApiInterface.ADDRESS_ADD)) {
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
