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

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

public class OtherPayWebActivity extends BaseActivity {

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
        private TextView title;
        private ImageView back;
        private WebView webView;

        private String data;
        private ImageView web_back;
        private ImageView goForward;
        private ImageView reload;
        private ProgressBar web_progress;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.other_pay_web);

            Intent intent = getIntent();
            data = intent.getStringExtra("html");

            title = (TextView) findViewById(R.id.top_view_text);
            Resources resource = (Resources) getBaseContext().getResources();
            String pay = resource.getString(R.string.pay);
            title.setText(pay);

            back = (ImageView) findViewById(R.id.top_view_back);
            back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            web_progress = (ProgressBar) findViewById(R.id.web_progress);
            webView = (WebView) findViewById(R.id.pay_web);
            webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
            webView.setWebViewClient(new WebViewClient() { // 通过webView打开链接，不调用系统浏览器

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.setWebChromeClient(new MyWebChromeClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            web_back = (ImageView) findViewById(R.id.web_back);
            web_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {

                    }
                }
            });

            goForward = (ImageView) findViewById(R.id.goForward);
            goForward.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    webView.goForward();

                }
            });

            reload = (ImageView) findViewById(R.id.reload);
            reload.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    webView.reload();
                }
            });


        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }
            return super.onKeyDown(keyCode, event);
        }
        private class MyWebChromeClient extends WebChromeClient {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
                web_progress.setVisibility(View.VISIBLE);
                web_progress.setProgress(newProgress);
                if(newProgress >= 100) {
                    web_progress.setVisibility(View.GONE);
                }
            }
        }
    }

