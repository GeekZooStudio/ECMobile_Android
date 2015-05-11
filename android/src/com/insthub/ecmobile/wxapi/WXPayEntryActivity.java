package com.insthub.ecmobile.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;

import com.external.activeandroid.util.Log;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ECMobileAppConst;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.R;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.*;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	api = WXAPIFactory.createWXAPI(this, EcmobileManager.getWeixinAppId(this));
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX)
        {

            if(resp.errCode == 0){

                ToastView toast = new ToastView(this, getString(R.string.pay_success));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Message msg = new Message();
                msg.what = ShareConst.WEIXIN_PAY;
                EventBus.getDefault().post(msg);
                this.finish();

            }else if(resp.errCode == -1){
                this.finish();
                ToastView toast = new ToastView(this, "系统繁忙，请稍后再试");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else
            {
                this.finish();
            }
		}
        else {
            this.finish();
		}
	}
}