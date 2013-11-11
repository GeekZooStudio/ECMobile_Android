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

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;
import com.insthub.BeeFramework.BeeFrameworkApp;
import com.insthub.BeeFramework.model.BeeQuery;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.activity.MainActivity;
import com.insthub.ecmobile.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class EcmobileMainActivity extends FragmentActivity
{

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
    public static final String API_KEY = "hMDGZvmA2RvMFVmOHewdT3Iq";

    public void onCreate(Bundle savedInstanceState)
    {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    Intent intent = new Intent();
		intent.setAction("com.BeeFramework.NetworkStateService");
		startService(intent);
	    
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句

        setIntent(intent);

        handleIntent(intent);

    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_RESPONSE.equals(action))
        {

        } else if (ACTION_LOGIN.equals(action))
        {

        }
        else if (ACTION_MESSAGE.equals(action))
        {

        }
        else if (ACTION_PUSHCLICK.equals(action))
        {
            String message = intent.getStringExtra(CUSTOM_CONTENT);

            try
            {
                JSONObject jsonObject = new JSONObject(message);
                String actionString = jsonObject.optString("action");
                if (0 == actionString.compareTo("search"))
                {
                    String parameter = jsonObject.optString("parameter");
                    if (null != parameter && parameter.length() > 0)
                    {
                        Intent it = new Intent(this, GoodsListActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        FILTER filter = new FILTER();
                        filter.keywords =parameter;
                        try
                        {
                            it.putExtra(GoodsListActivity.FILTER,filter.toJson().toString());
                        }
                        catch (JSONException e)
                        {

                        }

                        startActivity(it);
                    }

                }
            }
            catch (JSONException e)
            {

            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean isExit = false;
    //退出操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isExit==false){
                isExit=true;
                Resources resource = (Resources) getBaseContext().getResources();
                String exit=resource.getString(R.string.again_exit);
                //Toast.makeText(getApplicationContext(), exit, Toast.LENGTH_SHORT).show();
                ToastView toast = new ToastView(getApplicationContext(), exit);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                handler.sendEmptyMessageDelayed(0, 3000);
                if(BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT)
                {
                    BeeFrameworkApp.getInstance().showBug(this);
                }

                return true;
            } else {
            	Intent intent = new Intent();
        		intent.setAction("com.BeeFramework.NetworkStateService");
        		stopService(intent);
                finish();
                ToastView.cancel();
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
    protected void onStop() {
        super.onStop();
    }
    
}
