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

import java.util.List;

import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.external.activeandroid.query.Select;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.AddressManageAdapter;
import com.insthub.ecmobile.adapter.ShoppingCartAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.ADDRESS;
/**
 * 收货地址管理
 */
public class AddressManageActivity extends BaseActivity implements BusinessResponse {
	
	private ImageView back;
	private ImageView add;
	private ListView listView;
	private ImageView bg;
	private AddressManageAdapter addressManageAdapter;
	
	private AddressModel addressModel;
	
	public  Handler messageHandler;
	
	public int flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_manage);
		
		Intent intent = getIntent();
		flag = intent.getIntExtra("flag", 0);
		
		back = (ImageView) findViewById(R.id.address_manage_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            finish(); 
			}
		});
		
		add = (ImageView) findViewById(R.id.address_manage_add);
		listView = (ListView) findViewById(R.id.address_manage_list);
		bg = (ImageView) findViewById(R.id.address_list_bg);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddressManageActivity.this, AddAddressActivity.class);
				startActivity(intent);
			}
		});
		
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		
		messageHandler = new Handler(){

            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    Integer address_id = (Integer) msg.arg1;
                    addressModel.addressDefault(address_id+"");
                }
                          
            }
        };
		
	}
	
	public void setAddress() {
		
		if(addressModel.addressList.size() == 0) {
			listView.setVisibility(View.GONE);
            Resources resource = (Resources) getBaseContext().getResources();
            String non=resource.getString(R.string.non_address);
            ToastView toast = new ToastView(this, non);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
	        bg.setVisibility(View.VISIBLE);
		} else {
			bg.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			addressManageAdapter = new AddressManageAdapter(this, addressModel.addressList, flag);
			listView.setAdapter(addressManageAdapter);
			
			addressManageAdapter.parentHandler = messageHandler;
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		addressModel.getAddressList();
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.ADDRESS_LIST)) {
			setAddress();
		} else if(url.endsWith(ProtocolConst.ADDRESS_DEFAULT)) {
			Intent intent = new Intent();
			intent.putExtra("address", "address");
			setResult(Activity.RESULT_OK, intent); 
			finish();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
            finish(); 
		}
		return true;
	}
	
	public static List<ADDRESS> getAll() {
		return new Select().from(ADDRESS.class).execute();
	}
}
