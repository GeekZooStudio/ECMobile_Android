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

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.insthub.BeeFramework.Utils.WeixinUtil;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.ShareConst;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FlushedInputStream;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

public class ShareActivity extends Activity implements IWeiboHandler.Response
{
    String shareContent;
    String goods_url;
    String photoUrl;
    LinearLayout sinaWeibo;
    LinearLayout tencentWeibo;
    LinearLayout tencentWeixin;
    private CheckBox isTimelineCb;

    WeiboAuth mWeibo;
    IWeiboShareAPI weiboAPI;

    private IWXAPI weixinAPI = null;
    private WeiboAPI api;//添加weiboAPI
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private Oauth2AccessToken mAccessToken;

    Bitmap shareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.share_dialog);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        sinaWeibo = (LinearLayout)findViewById(R.id.sina_weibo);

        if(EcmobileManager.getSinaKey(this) == null || EcmobileManager.getSinaSecret(this) == null
        		|| EcmobileManager.getSinaCallback(this) == null) {
        	sinaWeibo.setVisibility(View.GONE);
        }
        
        if(EcmobileManager.getSinaKey(ShareActivity.this) != null && EcmobileManager.getSinaCallback(ShareActivity.this) != null) {
    		mWeibo=new WeiboAuth(ShareActivity.this, EcmobileManager.getSinaKey(ShareActivity.this), EcmobileManager.getSinaCallback(ShareActivity.this), ShareConst.SCOPE);
    		weiboAPI=WeiboShareSDK.createWeiboAPI(ShareActivity.this, EcmobileManager.getSinaKey(ShareActivity.this));
    		weiboAPI.registerApp();
    	}
        sinaWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            	if(EcmobileManager.getSinaKey(ShareActivity.this) != null && EcmobileManager.getSinaCallback(ShareActivity.this) != null) {

                    shareSinaContent();
            	}
            }
        });
        
        tencentWeibo = (LinearLayout)findViewById(R.id.tencent_weibo);
        if(EcmobileManager.getTencentKey(this) == null || EcmobileManager.getTencentSecret(this) == null) {
        	tencentWeibo.setVisibility(View.GONE);
        }
        
        tencentWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            	Intent intent = new Intent(getApplicationContext(), ShareTencentActivity.class);
            	intent.putExtra("content", shareContent);
            	intent.putExtra("goods_url", goods_url);
            	intent.putExtra("pic_url", photoUrl);
            	startActivity(intent);

            }
        });

        tencentWeixin = (LinearLayout)findViewById(R.id.tencent_weixin);
        if(EcmobileManager.getWeixinAppId(this) == null) {
        	tencentWeixin.setVisibility(View.GONE);
        }
        tencentWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = goods_url;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.description = shareContent;

                if (null != shareImage)
                {
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(shareImage, 150, 150, true);
                    msg.thumbData = WeixinUtil.bmpToByteArray(thumbBmp, true);
                }

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                if (req.scene == SendMessageToWX.Req.WXSceneTimeline)
                {
                    msg.title = shareContent;
                }
                else
                {
                    msg.title = "分享商品";
                }
              if(weixinAPI.isWXAppInstalled()){
                  if(weixinAPI != null) {
                      weixinAPI.sendReq(req);
                  }
              }else{
                    Toast.makeText(ShareActivity.this, "未安装微信客户端", Toast.LENGTH_LONG).show();
                }
            }
        });

        isTimelineCb = (CheckBox) findViewById(R.id.is_timeline_cb);
        isTimelineCb.setChecked(false);

        if(EcmobileManager.getWeixinAppId(ShareActivity.this)!=null) {
        	weixinAPI = WXAPIFactory.createWXAPI(this, EcmobileManager.getWeixinAppId(ShareActivity.this));
            weixinAPI.registerApp(EcmobileManager.getWeixinAppId(ShareActivity.this));
        }
        
        shareContent = getIntent().getStringExtra("content");
        goods_url = getIntent().getStringExtra("goods_url");
        photoUrl = getIntent().getStringExtra("photoUrl");

        if (null != photoUrl)
        {
             getBitMap(photoUrl);
        }
        
        
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weiboAPI.handleWeiboResponse(intent, this);
    }
    

    private HttpCallback mTencentWeiboCallBack = new HttpCallback() {
        @Override
        public void onResult(Object object) {
            ModelResult result = (ModelResult) object;
            if(result.isExpires())
            {
                Toast.makeText(ShareActivity.this, result.getError_message(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(result.isSuccess())
                {
                    Toast.makeText(ShareActivity.this, R.string.broadcast_success, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ShareActivity.this, result.getError_message(), Toast.LENGTH_SHORT).show();

                }
            }
        }
    };

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			 
			Toast.makeText(ShareActivity.this.getApplicationContext(), "Auth cancel",
                  Toast.LENGTH_LONG).show();
		}

		@Override
		public void onComplete(Bundle values) {
			 
		       mAccessToken = Oauth2AccessToken.parseAccessToken(values);
		       if (mAccessToken.isSessionValid()) 
		       {
		    	   Message message = new Message();
                   message.what = 0;
                   message.obj = mAccessToken;
                   handler.sendMessage(message);
		    	   Toast.makeText(ShareActivity.this, R.string.successful_authentication, Toast.LENGTH_SHORT).show();
		       }
		}

		@Override
		public void onWeiboException(WeiboException e) {			
			Toast.makeText(ShareActivity.this.getApplicationContext(),
                  "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
                  .show();
		}

	}

    public void sinaGetAccessToken(String code)
    {

        String url = "https://api.weibo.com/oauth2/access_token";

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        // 填入各个表单域的值        
        NameValuePair[] data = { new NameValuePair("client_id", EcmobileManager.getSinaKey(this)),
                new NameValuePair("client_secret", EcmobileManager.getSinaSecret(this)),
                new NameValuePair("grant_type", "authorization_code"),
                new NameValuePair("code", code),
                new NameValuePair("redirect_uri", EcmobileManager.getSinaCallback(this)),
                };
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(data);

        new PostThread(httpClient,postMethod,0).start();
        // 执行postMethod
        try
        {
            int statusCode = httpClient.executeMethod(postMethod);
            String response = postMethod.getResponseBodyAsString( );

            if (statusCode == HttpStatus.SC_OK)
            {
                JSONObject jo = new JSONObject(response);
                if (jo.has("access_token"))
                {
                    String access_token = jo.optString("access_token");
                    Message message = new Message();
                    message.what = 0;
                    message.obj = access_token;
                    handler.sendMessage(message);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * A thread that performs a POST.
     */
    static class PostThread extends Thread {

        private final HttpClient httpClient;
        private final HttpContext context;
        private final PostMethod postMethod;
        private final int id;

        public PostThread(HttpClient httpClient, PostMethod postMethod, int id) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.id = id;
            this.postMethod = postMethod;
        }

        /**
         * Executes the GetMethod and prints some status information.
         */
        @Override
        public void run() {
            try {

                // execute the method
                int statusCode = httpClient.executeMethod(postMethod);
                String response = postMethod.getResponseBodyAsString( );

                if (statusCode == HttpStatus.SC_OK)
                {
                    JSONObject jo = new JSONObject(response);
                    if (jo.has("access_token"))
                    {
                        String access_token = jo.optString("access_token");
                    }
                }
            } catch (Exception e) {
                postMethod.abort();
                System.out.println(id + " - error: " + e);
            }
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
//                    String accessToken = (String)msg.obj;

                    shareSinaContent();
                    break;
            }

        }
    };


    public void getBitMap(String imageUrl)
    {
        final String loadUrl = imageUrl;
        new AsyncTask<String, Integer, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                InputStream is = null;
                FlushedInputStream fis = null;

                Bitmap resultBitmap = null;

                try
                {
                    URL url = new URL(loadUrl);
                    URLConnection conn = url.openConnection();

                    is = conn.getInputStream();
                    int length = is.available();

                    fis = new FlushedInputStream(is);

                    resultBitmap = BitmapFactory.decodeStream(fis);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    try
                    {
                        is.close();
                    }
                    catch (Exception ex)
                    {

                    }
                }


                return resultBitmap;

            }
            @Override
            protected void onPreExecute() {                
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(Bitmap result) {

                ShareActivity.this.shareImage = result;


                super.onPostExecute(result);
            }

        }.execute();
    }

    public void shareSinaContent()
    {
        if (weiboAPI.isWeiboAppSupportAPI())
        {
            int supportApi = weiboAPI.getWeiboAppSupportAPI();
            if (supportApi >= 10351)
            {
                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                TextObject textObject = new TextObject();
                textObject.text = shareContent+goods_url;
                weiboMessage.textObject = textObject;
                if (null != photoUrl)
                {
                    ImageObject imageObject = new ImageObject();
                    if (null != shareImage)
                    {
                        imageObject.setImageObject(shareImage);
                    }

                    weiboMessage.imageObject = imageObject;
                }

                SendMultiMessageToWeiboRequest req = new SendMultiMessageToWeiboRequest();
                req.transaction = String.valueOf(System.currentTimeMillis());// 用transaction唯一标识一个请求
                req.multiMessage = weiboMessage;
                // 发送请求消息到微博
                weiboAPI.sendRequest(req);
            }
            else
            {
                WeiboMessage weiboMessage = new WeiboMessage();
                TextObject textObject = new TextObject();
                textObject.text = shareContent+goods_url;
                weiboMessage.mediaObject =  textObject;
                SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
                request.transaction = String.valueOf(System.currentTimeMillis());
                request.message = weiboMessage;
                weiboAPI.sendRequest(request);
            }

        }
        else
        {
            Intent intent = new Intent(ShareActivity.this, ShareWebActivity.class);
            String url = "http://v.t.sina.com.cn/share/share.php?title="+shareContent+"&url="+goods_url;
            intent.putExtra("url", url);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
            this.finish();
        }
    }

    private String buildTransaction(final String type)
    {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void tencentAuth(long appid, String app_secket)
    {
        final Context context = this.getApplicationContext();

        AuthHelper.register(this, appid, app_secket, new OnAuthListener() {

            @Override
            public void onWeiBoNotInstalled() {
                Intent i = new Intent(ShareActivity.this, Authorize.class);
                ShareActivity.this.startActivity(i);
            }

            @Override
            public void onWeiboVersionMisMatch() {
                Intent i = new Intent(ShareActivity.this, Authorize.class);
                startActivity(i);
            }

            @Override
            public void onAuthFail(int result, String err) {
                Toast.makeText(ShareActivity.this, "result : " + result, 1000)
                        .show();
            }

            @Override  
            public void onAuthPassed(String name, WeiboToken token) {
                Toast.makeText(ShareActivity.this, "passed", 1000).show();
                //
                Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
                Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
                Util.saveSharePersistent(context, "OPEN_ID", token.openID);
                Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
                Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
                Util.saveSharePersistent(context, "AUTHORIZETIME",
                        String.valueOf(System.currentTimeMillis() / 1000l));
                String accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");

                AccountModel account = new AccountModel(accessToken);
                api = new WeiboAPI(account);
                api.reAddWeibo(getApplicationContext(),shareContent,photoUrl,null,null,null,null, mTencentWeiboCallBack, null, BaseVO.TYPE_JSON);

            }
        });

        AuthHelper.auth(ShareActivity.this, "");
    }

    /**
     * 从本应用->微博->本应用
     */
    @Override
    public void onResponse( BaseResponse baseResp ) {
        switch (baseResp.errCode) {
            case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_OK:
                ToastView toastOk = new ToastView(ShareActivity.this,R.string.success_share);
                toastOk.setGravity(Gravity.CENTER, 0, 0);
                toastOk.show();
                this.finish();
                break;
            case  com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_CANCEL:
                ToastView toastCancel = new ToastView(ShareActivity.this,R.string.user_cancel);
                toastCancel.setGravity(Gravity.CENTER, 0, 0);
                toastCancel.show();
                this.finish();
                break;
            case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_FAIL:
                ToastView toastErr = new ToastView(ShareActivity.this,R.string.fail_share);
                toastErr.setGravity(Gravity.CENTER, 0, 0);
                toastErr.show();
                break;
        }

    }
    
    @Override
    protected void onStop() {    	
    	super.onStop();
    }
}
