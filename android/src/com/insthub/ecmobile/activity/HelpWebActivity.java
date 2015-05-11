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

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.protocol.ApiInterface;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.model.ProtocolConst;
public class HelpWebActivity extends BaseActivity implements BusinessResponse {	
	private TextView title;
	private ImageView back;
	private HomeModel homeModel;
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b6_product_desc);
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", 0);
		String t = intent.getStringExtra("title");
		
		title = (TextView) findViewById(R.id.top_view_text);
		title.setText(t);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		
		webView = (WebView) findViewById(R.id.help_web);
		
		webView.setWebViewClient(new WebViewClient() { // 通过webView打开链接，不调用系统浏览器

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {				
				view.loadUrl(url);
				return true;
			}
		});
		
		
		webView.setInitialScale(25);
		
		WebSettings webSettings = webView.getSettings();  
		webSettings.setJavaScriptEnabled(true); 
		webSettings.setBuiltInZoomControls(true);		
		webSettings.setSupportZoom(true);
		
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		
		homeModel = new HomeModel(this);
		homeModel.addResponseListener(this);
		homeModel.helpArticle(id);
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {				
		if(url.endsWith(ApiInterface.ARTICLE)) {
			webView.loadDataWithBaseURL(null,homeModel.web,"text/html","utf-8",null);			
		}
		
	}
}
