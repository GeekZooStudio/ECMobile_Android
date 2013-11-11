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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.activity.WebViewActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.ConfigModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.protocol.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements OnClickListener,BusinessResponse {
	
	private TextView title;
	private ImageView back;
	
	private LinearLayout type1; //智能模式
	private LinearLayout type2; //高质量模式
	private LinearLayout type3; //普通模式
	
	private ImageView invoice1;
	private ImageView invoice2;
	private ImageView invoice3;
	
	private TextView mobile;
	private LinearLayout official_web;
	private LinearLayout aboutApp;
    private LinearLayout settingMobileLayout;
    private LinearLayout about;
    private LinearLayout settingSupport;
    private LinearLayout beeSupport;


    private LinearLayout settingWebLayout;
	
	private Button exitLogin;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	private MyDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();

        if (null == ConfigModel.getInstance())
        {
            ConfigModel configModel = new ConfigModel(this);
            configModel.addResponseListener(this);
            ConfigModel.getInstance().getConfig();
        }
        else if (null == ConfigModel.getInstance().config)
        {
            ConfigModel.getInstance().addResponseListener(this);
            ConfigModel.getInstance().getConfig();
        }

		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String set=resource.getString(R.string.setting);
		title.setText(set);
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(this);
		
		type1 = (LinearLayout) findViewById(R.id.setting_type1);
		type2 = (LinearLayout) findViewById(R.id.setting_type2);
		type3 = (LinearLayout) findViewById(R.id.setting_type3);
		
		invoice1 = (ImageView) findViewById(R.id.setting_invoice1);
		invoice2 = (ImageView) findViewById(R.id.setting_invoice2);
		invoice3 = (ImageView) findViewById(R.id.setting_invoice3);

        settingMobileLayout = (LinearLayout)findViewById(R.id.setting_mobile_layout);
        settingMobileLayout.setOnClickListener(this);
        settingWebLayout = (LinearLayout)findViewById(R.id.setting_web_layout);
        settingWebLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SettingActivity.this, WebViewActivity.class);
                it.putExtra(WebViewActivity.WEBURL, "http://www.68ecshop.com");
                it.putExtra(WebViewActivity.WEBTITLE, "经销商介绍");
                startActivity(it);
            }
        });
        
        settingSupport = (LinearLayout) findViewById(R.id.setting_support);
        settingSupport.setOnClickListener(this);

        beeSupport = (LinearLayout)findViewById(R.id.bee_support);
        beeSupport.setOnClickListener(this);

		mobile = (TextView) findViewById(R.id.setting_mobile);

        if (null != ConfigModel.getInstance().config &&
                null != ConfigModel.getInstance().config.service_phone)
        {
            mobile.setText(ConfigModel.getInstance().config.service_phone);
        }

		official_web = (LinearLayout) findViewById(R.id.setting_official_web);
		aboutApp = (LinearLayout) findViewById(R.id.setting_aboutApp);
		about = (LinearLayout) findViewById(R.id.setting_about);
		exitLogin = (Button) findViewById(R.id.setting_exitLogin);
		
		type1.setOnClickListener(this);
		type2.setOnClickListener(this);
		type3.setOnClickListener(this);
		
		official_web.setOnClickListener(this);
		aboutApp.setOnClickListener(this);
		about.setOnClickListener(this);
		exitLogin.setOnClickListener(this);
		
		String imageType = shared.getString("imageType", "mind");
		if(imageType.equals("high")) {
			invoice1.setVisibility(View.GONE);
			invoice2.setVisibility(View.VISIBLE);
			invoice3.setVisibility(View.GONE);
		} else if(imageType.equals("low")) {
			invoice1.setVisibility(View.GONE);
			invoice2.setVisibility(View.GONE);
			invoice3.setVisibility(View.VISIBLE);
		} else {
			invoice1.setVisibility(View.VISIBLE);
			invoice2.setVisibility(View.GONE);
			invoice3.setVisibility(View.GONE);
		}
		
		String uid = shared.getString("uid", "");
		if (uid.equals("")) {
			exitLogin.setVisibility(View.GONE);
		} else {
			exitLogin.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.top_view_back:
			finish();
			break;
		case R.id.setting_type1:
			invoice1.setVisibility(View.VISIBLE);
			invoice2.setVisibility(View.GONE);
			invoice3.setVisibility(View.GONE);
			editor.putString("imageType", "mind");
			editor.commit();
			break;
		case R.id.setting_type2:
			invoice1.setVisibility(View.GONE);
			invoice2.setVisibility(View.VISIBLE);
			invoice3.setVisibility(View.GONE);
			editor.putString("imageType", "high");
			editor.commit();
			break;
		case R.id.setting_type3:
			invoice1.setVisibility(View.GONE);
			invoice2.setVisibility(View.GONE);
			invoice3.setVisibility(View.VISIBLE);
			editor.putString("imageType", "low");
			editor.commit();
			break;
		case R.id.setting_official_web:
        {
            Intent it = new Intent(SettingActivity.this, WebViewActivity.class);
            it.putExtra(WebViewActivity.WEBURL, ConfigModel.getInstance().config.site_url);
            Resources resource = (Resources) getBaseContext().getResources();
            String off=resource.getString(R.string.setting_website);
            it.putExtra(WebViewActivity.WEBTITLE, off);
            startActivity(it);
            break;
        }
		case R.id.setting_aboutApp:
        {
            Intent it = new Intent(SettingActivity.this, WebViewActivity.class);
            it.putExtra(WebViewActivity.WEBURL, ConfigModel.getInstance().config.site_url);
            Resources resource = (Resources) getBaseContext().getResources();
            String tech=resource.getString(R.string.setting_tech);
            it.putExtra(WebViewActivity.WEBTITLE, tech);
            startActivity(it);
            break;
        }
        case R.id.setting_mobile_layout:
        {
            Resources resource = (Resources) getBaseContext().getResources();
            String call=resource.getString(R.string.call_or_not);
            mDialog = new MyDialog(SettingActivity.this, call, ConfigModel.getInstance().config.service_phone);
            mDialog.show();
            mDialog.positive.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDialog.dismiss();
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ConfigModel.getInstance().config.service_phone));
		            startActivity(intent);
				}
			});
            mDialog.negative.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDialog.dismiss();
				}
			});
            
           break;
        }
        
        case R.id.setting_about:
        {
        	Intent it = new Intent(SettingActivity.this, WebViewActivity.class);
            it.putExtra(WebViewActivity.WEBURL, "http://ecmobile.me");
            it.putExtra(WebViewActivity.WEBTITLE, "Geek-Zoo介绍");
            startActivity(it);
        	break;
        }
        case R.id.setting_support:
        {
        	Intent intent = new Intent(SettingActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.WEBURL, "http://ecmobile.me");
            intent.putExtra(WebViewActivity.WEBTITLE, "Geek Zoo Studio");
            startActivity(intent);
        	break;
        }
        case R.id.bee_support:
       {
            Intent it = new Intent(SettingActivity.this, WebViewActivity.class);
            it.putExtra(WebViewActivity.WEBURL, "http://bee-framework.com/");
            it.putExtra(WebViewActivity.WEBTITLE, "BeeFramework");
            startActivity(it);
            break;
        }
		case R.id.setting_exitLogin:

            Resources resource = (Resources) getBaseContext().getResources();
			String exit=resource.getString(R.string.exit);
            String exiten=resource.getString(R.string.ensure_exit);
			mDialog = new MyDialog(SettingActivity.this, exit, exiten);
            mDialog.show();
            mDialog.positive.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDialog.dismiss();
					editor.putString("uid", "");
		            editor.putString("sid", "");
		            editor.commit();
		            SESSION.getInstance().uid = shared.getString("uid", "");
		            SESSION.getInstance().sid = shared.getString("sid", "");
		           
		            Intent intent = new Intent(SettingActivity.this, EcmobileMainActivity.class);
		            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		            startActivity(intent);
		            finish();
				}
			});
            mDialog.negative.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDialog.dismiss();
				}
			});
			
			break;
		}
		
	}

    @Override

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
        if (url.endsWith(ProtocolConst.CONFIG))
        {
            if (null != ConfigModel.getInstance().config &&
                    null != ConfigModel.getInstance().config.service_phone)
            {
                mobile.setText(ConfigModel.getInstance().config.service_phone);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        ConfigModel.getInstance().removeResponseListener(this);
        super.onDestroy();
    }
}
