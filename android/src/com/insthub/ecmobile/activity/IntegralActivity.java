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
import org.json.JSONArray;
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
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.SHIPPING;

public class IntegralActivity extends BaseActivity implements BusinessResponse {
	
	private ImageView back;
	private Button submit;
	private TextView num;
	private EditText input;
	private String integral;
	private String max_integral;
	
	private ShoppingCartModel shoppingCartModel;
	
	private int min_inteagral;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.integral);
		
		Intent intent = getIntent();
		String s = intent.getStringExtra("payment");
		
		try{
			JSONObject jo = new JSONObject(s);
			
			integral = jo.get("your_integral").toString();
			max_integral = jo.get("order_max_integral").toString();
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		min_inteagral = Math.min(Integer.valueOf(integral), Integer.valueOf(max_integral));
		
		shoppingCartModel = new ShoppingCartModel(this);
		shoppingCartModel.addResponseListener(this);
		
		back = (ImageView) findViewById(R.id.integral_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		input = (EditText) findViewById(R.id.integral_input);
		num = (TextView) findViewById(R.id.integral_num);
		
		submit = (Button) findViewById(R.id.integral_submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Resources resource = (Resources) getBaseContext().getResources();
                String ent=resource.getString(R.string.enter_score);
                String scr=resource.getString(R.string.score_not_zero);
				if(!input.getText().toString().equals("")) {
					if(Integer.valueOf(input.getText().toString()) > min_inteagral) {
						//Toast toast = Toast.makeText(IntegralActivity.this, ent, 0);
						ToastView toast = new ToastView(IntegralActivity.this, ent);
				        toast.setGravity(Gravity.CENTER, 0, 0);
				        toast.show();
					} else if(input.getText().toString().equals("0")) {
						//Toast toast = Toast.makeText(IntegralActivity.this, scr, 0);
						ToastView toast = new ToastView(IntegralActivity.this, scr);
				        toast.setGravity(Gravity.CENTER, 0, 0);
				        toast.show();
					} else {
						shoppingCartModel.integral(input.getText().toString());
					}
				} else {
					//Toast toast = Toast.makeText(IntegralActivity.this, ent, 0);
					ToastView toast = new ToastView(IntegralActivity.this, ent);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				}
				
			}
		});

        Resources resource = (Resources) getBaseContext().getResources();
        String all=resource.getString(R.string.all_of_you);
        String can=resource.getString(R.string.can_use) ;
        String inte=resource.getString(R.string.integral);
		num.setText(all+integral+inte);
		input.setHint(can+min_inteagral+inte);
		
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException
    {
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.VALIDATE_INTEGRAL))
        {
            JSONObject data = jo.getJSONObject("data");
            String bonus = data.getString("bonus").toString();
            String bonus_formated = data.getString("bonus_formated").toString();
			Intent intent = new Intent();
			intent.putExtra("input", input.getText().toString());
			intent.putExtra("bonus", bonus);
			intent.putExtra("bonus_formated", bonus_formated);
			setResult(Activity.RESULT_OK, intent);  
            finish(); 
		}
		
		
	}

}
