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

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import com.external.alipay.BaseHelper;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.activity.WebViewActivity;
import com.insthub.BeeFramework.model.BeeQuery;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ECMobileAppConst;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.SESSION;
import com.umeng.analytics.MobclickAgent;

import java.net.URLDecoder;

public class PayWebActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private WebView webView;

    private String url;
    public static final String PAY_URL = "pay_url";
    private ImageView web_back;
    private ImageView goForward;
    private ImageView reload;
    private String actionName = "";
    private String params1 = "";
    private ProgressBar web_progress;
    private String pay_result="unkown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_web);

        Intent intent = getIntent();
        url = intent.getStringExtra(PAY_URL);

        title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String pay = resource.getString(R.string.pay);
        title.setText(pay);

        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                data.putExtra("pay_result", pay_result);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
        web_progress=(ProgressBar)findViewById(R.id.web_progress);
        webView = (WebView) findViewById(R.id.pay_web);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
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

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //监测callback，
            if (url.startsWith(BeeQuery.wapCallBackUrl())) {
                String params = url.substring(url.lastIndexOf("?") + 1);
                if (params != null && !"".equals(params)) {
                    String param[] = params.split("&");
                    if (param.length == 2) {
                        params1 = param[0];
                        String param1[] = params1.split("=");
                        if (param1.length == 2) {
                            actionName = param1[0];
                            if (actionName.equals("err")) {
                                String errcode = param1[1];
                                if(errcode.equals("0")){
                                    //支付成功
                                    Intent data=new Intent();
                                    pay_result="success";
                                    data.putExtra("pay_result", "success");
                                    setResult(Activity.RESULT_OK, data);
                                    finish();
                                }
                                else if(errcode.equals("1"))
                                {
                                    Intent data=new Intent();
                                    pay_result="fail";
                                    data.putExtra("pay_result", pay_result);
                                    setResult(Activity.RESULT_OK, data);
                                    finish();
                                }
                                else
                                {
                                    Intent data=new Intent();
                                    pay_result="fail";
                                    data.putExtra("pay_result", pay_result);
                                    setResult(Activity.RESULT_OK, data);
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
            super.onPageStarted(view, url, favicon);
        }
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
