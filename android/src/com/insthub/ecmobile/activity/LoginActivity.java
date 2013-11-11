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
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.fragment.ProfileFragment;
import com.insthub.ecmobile.model.LoginModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class LoginActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	
	private ImageView back;
	private Button login;
	
	private EditText userName;
	private EditText password;
	private Button register;
	
	private String name;
	private String psd;
	
	private LoginModel loginModel;
	private ProgressDialog pd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		back = (ImageView) findViewById(R.id.login_back);
		login = (Button) findViewById(R.id.login_login);
		userName = (EditText) findViewById(R.id.login_name);
		password = (EditText) findViewById(R.id.login_password);
		register = (Button) findViewById(R.id.login_register);
		
		back.setOnClickListener(this);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        Resources resource = (Resources) getBaseContext().getResources();
        String usern=resource.getString(R.string.user_name_cannot_be_empty);
        String pass=resource.getString(R.string.password_cannot_be_empty);
        String hold=resource.getString(R.string.hold_on);
		Intent intent;
		switch(v.getId()) {
		case R.id.login_back:
			finish();
			CloseKeyBoard();
			overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
			break;
		case R.id.login_login:
			name = userName.getText().toString();
			psd = password.getText().toString();
			if("".equals(name)) {
				//Toast toast = Toast.makeText(this, usern, 0);
				ToastView toast = new ToastView(this, usern);
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.show();
			} else if("".equals(psd)) {
				//Toast toast = Toast.makeText(this, pass, 0);
				ToastView toast = new ToastView(this, pass);
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.show();
			} else {
				loginModel = new LoginModel(LoginActivity.this);
				loginModel.addResponseListener(this);
				loginModel.login(name, psd);
				CloseKeyBoard();
				
				pd = new ProgressDialog(LoginActivity.this);
				pd.setMessage(hold);
				pd.show();
				
			}
			break;
		case R.id.login_register:
			intent = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		
		if(pd.isShowing()) {
			pd.dismiss();
		}
		if(loginModel.responseStatus.succeed == 1) {
			
			if(url.endsWith(ProtocolConst.SIGNIN)) {
				Intent intent = new Intent();
				intent.putExtra("login", true);
				setResult(Activity.RESULT_OK, intent);  
	            finish(); 
	            overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
			}
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1) {
			if(data!=null) {
				Intent intent = new Intent();
				intent.putExtra("login", true);
				setResult(Activity.RESULT_OK, intent);  
	            finish(); 
	            overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
    		}
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
		}
		return true;
	}
	
	// 关闭键盘
	public void CloseKeyBoard() {
		userName.clearFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
	}

}
