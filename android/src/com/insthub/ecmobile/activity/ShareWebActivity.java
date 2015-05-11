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
import com.insthub.ecmobile.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class ShareWebActivity extends Activity {

	private TextView title;
	private ImageView back;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
        Resources resource = (Resources) getBaseContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_web);
		
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		
		title = (TextView) findViewById(R.id.top_view_text);
        String share=resource.getString(R.string.share_share);
		title.setText(share);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
				 overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
			}
		});
		
		webView = (WebView) findViewById(R.id.pay_web);
		webView.setWebViewClient(new WebViewClient() { // 通过webView打开链接，不调用系统浏览器

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {				
				view.loadUrl(url);
				return true;
			}
		});
		
		WebSettings webSettings = webView.getSettings();  
		webSettings.setJavaScriptEnabled(true); 
		webSettings.setBuiltInZoomControls(true);

		webView.loadUrl(url);
		
	}
}
