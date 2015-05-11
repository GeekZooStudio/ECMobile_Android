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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;

import com.external.alipay.PartnerConfig;
import com.external.alipay.PayResult;
import com.external.alipay.Rsa;
import com.insthub.ecmobile.R;

import com.insthub.ecmobile.protocol.ORDER_INFO;


/**
 * 模拟商户应用的商品列表，交易步骤。
 * 
 * 1. 将商户ID，收款帐号，外部订单号，商品名称，商品介绍，价格，通知地址封装成订单信息 2. 对订单信息进行签名 3.
 * 将订单信息，签名，签名方式封装成请求参数 4. 调用pay方法
 * 
 * @version v4_0413 2012-03-02
 */
public class AlixPayActivity extends Activity  {
    private  static String TAG = "AppDemo";
    private ProgressDialog mProgress = null;
    private  ORDER_INFO order_info;
    private PartnerConfig partnerConfig;
    public static String ORDER_INFO="ONDER_INFO";
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order_info=(ORDER_INFO)getIntent().getSerializableExtra(ORDER_INFO);
        partnerConfig = new PartnerConfig(this);
        performPay();


    }



    /**
     * get the selected order info for pay. 获取商品订单信息
     *
     * @param
     * @return
     */

    String getOrderInfo() {
        String strOrderInfo = "partner=" + "\"" + partnerConfig.PARTNER + "\"";
        strOrderInfo += "&";
        strOrderInfo += "seller_id=" + "\"" + partnerConfig.SELLER + "\"";
        strOrderInfo += "&";
        strOrderInfo += "out_trade_no=" + "\"" + order_info.order_sn + "\"";
        strOrderInfo += "&";
        strOrderInfo += "subject=" + "\"" + order_info.subject
                + "\"";
        strOrderInfo += "&";
        strOrderInfo += "body=" + "\"" + order_info.desc + "\"";
        strOrderInfo += "&";
        strOrderInfo += "total_fee=" + "\""
                + order_info.order_amount + "\"";
        strOrderInfo += "&";
        strOrderInfo += "notify_url=" + "\""
                + partnerConfig.ALIPAY_CALLBACK + "\"";

        // 服务接口名称， 固定值
        strOrderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        strOrderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        strOrderInfo += "&_input_charset=\"utf-8\"";

        return strOrderInfo;
    }
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param signType 签名方式
     * @param content  待签名订单信息
     * @return
     */
    String sign(String signType, String content) {
        return Rsa.sign(content, partnerConfig.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     * @return
     */
    String getSignType() {
        String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
        return getSignType;
    }
    protected void performPay() {


        // start pay for this order.
        // 根据订单信息开始进行支付
            // prepare the order info.
            // 准备订单信息
            String orderInfo = getOrderInfo();
            // 这里根据签名方式对订单信息进行签名
            String signType = getSignType();
            String strsign = sign(signType, orderInfo);
            // 对签名进行编码
        try {
            strsign = URLEncoder.encode(strsign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 组装好参数
            //getOrderInfo
            final String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
                    + getSignType();
            // start the pay.
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(AlixPayActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(info);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

    }

    /**
     * check some info.the partner,seller etc. 检测配置信息
     * partnerid商户id，seller收款帐号不能为空
     *
     * @return
     */
    private boolean checkInfo() {
        String partner = partnerConfig.PARTNER;
        String seller = partnerConfig.SELLER;
        if (partner == null || partner.length() <= 0 || seller == null
                || seller.length() <= 0)
            return false;

        return true;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AlixPayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("pay_result", "success");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AlixPayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(AlixPayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("pay_result", "fail");
                            setResult(Activity.RESULT_OK, intent);
                            finish();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

	/**
	 * the OnCancelListener for lephone platform. lephone系统使用到的取消dialog监听
	 */
	public static class AlixOnCancelListener implements
			DialogInterface.OnCancelListener {
		Activity mcontext;

		public AlixOnCancelListener(Activity context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}
	// close the progress bar
	// 关闭进度框
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	@Override
	public void onDestroy() {
		super.onDestroy();

		Log.v(TAG, "onDestroy");

		try {
			mProgress.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
    public void startActivityForResult(Intent intent, int requestCode)
    {
        super.startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
