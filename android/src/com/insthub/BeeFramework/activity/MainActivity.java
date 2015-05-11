package com.insthub.BeeFramework.activity;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;
import com.insthub.BeeFramework.BeeFrameworkConst;
import com.insthub.BeeFramework.BeeFrameworkApp;
import com.insthub.BeeFramework.Utils.CustomExceptionHandler;
import com.insthub.BeeFramework.model.BeeQuery;   import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.insthub.ecmobile.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends BaseActivity{
    public static final String RESPONSE_METHOD = "method";
    public static final String RESPONSE_CONTENT = "content";
    public static final String RESPONSE_ERRCODE = "errcode";
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    public static final String ACTION_PUSHCLICK = "bccsclient.action.PUSHCLICK";
    public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "message";
    public static final String CUSTOM_CONTENT ="CustomContent";

    // 在百度开发者中心查询应用的API Key
    public static final String API_KEY = "qGCsDwjNoNRNkg1iuvKZiAkz";
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        shared = this.getSharedPreferences("userInfo", 0);
        editor = shared.edit();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + BeeFrameworkConst.LOG_DIR_PATH;
        File storePath = new File(path);
        storePath.mkdirs();
        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(
                path, null));
    }

    @Override
    protected void onStart()
    {
        super.onStart();


        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY, API_KEY);

    }

    private boolean isExit = false;
    //退出操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isExit==false){
                isExit=true;
                Resources resource = (Resources) getBaseContext().getResources();
                String exi=resource.getString(R.string.exit);
                Toast.makeText(getApplicationContext(), exi, Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(0, 3000);
                if(BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT)
                {
                    BeeFrameworkApp.getInstance().showBug(this);
                }

                return true;
            } else {
                finish();

                return false;
            }
        }
        return true;
    }
    
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit=false;
		}
	};

    @Override
    protected void onNewIntent(Intent intent) {
        // 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句

        setIntent(intent);

        handleIntent(intent);

    }

    /**
     * 处理Intent
     *
     * @param intent
     *            intent
     */
    private void handleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_RESPONSE.equals(action))
        {

            String method = intent.getStringExtra(RESPONSE_METHOD);

            if (PushConstants.METHOD_BIND.equals(method))
            {
                int errorCode = intent.getIntExtra(RESPONSE_ERRCODE, 0);
                if (errorCode == 0) {
                    String content = intent.getStringExtra(RESPONSE_CONTENT);
                    String appid = "";
                    String channelid = "";
                    String userid = "";



                    try {
                        JSONObject jsonContent = new JSONObject(content);
                        JSONObject params = jsonContent
                                .getJSONObject("response_params");
                        appid = params.getString("appid");
                        channelid = params.getString("channel_id");
                        userid = params.getString("user_id");
                        editor.putString("UUID",userid);
                        editor.commit();

                    } catch (JSONException e) {

                    }
                }
                else
                {

                }


            }
        } else if (ACTION_LOGIN.equals(action)) {
            String accessToken = intent.getStringExtra(EXTRA_ACCESS_TOKEN);
            PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_ACCESS_TOKEN, accessToken);

        }
        else if (ACTION_MESSAGE.equals(action))
        {
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            String summary = "Receive message from server:\n\t";
            JSONObject contentJson = null;
            String contentStr = message;
            try {
                contentJson = new JSONObject(message);
                contentStr = contentJson.toString(4);
            } catch (JSONException e) {

            }
            summary += contentStr;
        }
        else if (ACTION_PUSHCLICK.equals(action))
        {
            String message = intent.getStringExtra(CUSTOM_CONTENT);
        }
    }
}
