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

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.external.alipay.*;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.ORDER_INFO;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 模拟商户应用的商品列表，交易步骤。
 * 
 * 1. 将商户ID，收款帐号，外部订单号，商品名称，商品介绍，价格，通知地址封装成订单信息 2. 对订单信息进行签名 3.
 * 将订单信息，签名，签名方式封装成请求参数 4. 调用pay方法
 * 
 * @version v4_0413 2012-03-02
 */
public class AlixPayActivity extends Activity {
	static String TAG = "AppDemo";


	private ProgressDialog mProgress = null;

	private int mPosition = -1;

    ORDER_INFO order_info ;
    
    PartnerConfig partnerConfig;

	//
	// Called when the activity is first created.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");

		partnerConfig = new PartnerConfig(this);
		
		//
		// check to see if the MobileSecurePay is already installed.
		// 检测安全支付服务是否被安装
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
		mspHelper.detectMobile_sp();

		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
		filter.addDataScheme("package");
		registerReceiver(mPackageInstallationListener, filter);



	}

	private BroadcastReceiver mPackageInstallationListener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String packageName = intent.getDataString();
			if (!TextUtils
					.equals(packageName, "package:com.alipay.android.app")) {
				return;
			}
            performPay();
		}
	};



	/**
	 * get the selected order info for pay. 获取商品订单信息
	 * 
	 * @param position
	 *            商品在列表中的位置
	 * @return
	 */
	// String getOrderInfo(int position) {
	// String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
	// strOrderInfo += "&";
	// strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
	// strOrderInfo += "&";
	// strOrderInfo += "out_trade_no=" + "\"" + "20120614070627" + "\"";
	// strOrderInfo += "&";
	// strOrderInfo += "subject=" + "\"" + "10"
	// + "\"";
	// strOrderInfo += "&";
	// strOrderInfo += "body=" + "\"" + "10"+ "\"";
	// strOrderInfo += "&";
	// strOrderInfo += "total_fee=" + "\""
	// + "12" + "\"";
	// strOrderInfo += "&";
	// strOrderInfo += "notify_url=" + "\""
	// + "" + "\"";
	//
	// return strOrderInfo;
	// }
	String getOrderInfo() {
		String strOrderInfo = "partner=" + "\"" + partnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + partnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + order_info.order_sn + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + order_info.subject
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + order_info.desc+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""
				+ order_info.order_amount + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\""
				+ partnerConfig.ALIPAY_CALLBACK + "\"";

		return strOrderInfo;
	}

	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 * @return
	 */
	String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String strKey = format.format(date);

		java.util.Random r = new java.util.Random();
		strKey = strKey + r.nextInt();
		strKey = strKey.substring(0, 15);
		return strKey;
	}

	//
	//
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param signType
	 *            签名方式
	 * @param content
	 *            待签名订单信息
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

	/**
	 * get the char set we use. 获取字符集
	 * 
	 * @return
	 */
	String getCharset() {
		String charset = "charset=" + "\"" + "utf-8" + "\"";
		return charset;
	}


	protected void performPay() {
		//
		// check to see if the MobileSecurePay is already installed.
		// 检测安全支付服务是否安装
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		if (!isMobile_spExist) {
			return;
		}

		// check some info.
		// 检测配置信息
		if (!checkInfo()) {
			BaseHelper
					.showDialog(
                            AlixPayActivity.this,
                            getResources().getString(R.string.prompt),
                            "缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。",
                            R.drawable.infoicon);
			return;
		}

		// start pay for this order.
		// 根据订单信息开始进行支付
		try {
			// prepare the order info.
			// 准备订单信息
			String orderInfo = getOrderInfo();
			// 这里根据签名方式对订单信息进行签名
			String signType = getSignType();
			String strsign = sign(signType, orderInfo);
			Log.v("sign:", strsign);
			// 对签名进行编码
			strsign = URLEncoder.encode(strsign, "UTF-8");
			// 组装好参数
			String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
					+ getSignType();
			Log.v("orderInfo:", info);
			// start the pay.
			// 调用pay方法进行支付
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this);

			if (bRet) {
				// show the progress bar to indicate that we have started
				// paying.
				// 显示“正在支付”进度条
				closeProgress();
				mProgress = BaseHelper.showProgress(this, null, "正在支付", false,
						true);
			} else
				;
		} catch (Exception ex) {
			Toast.makeText(AlixPayActivity.this, R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
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

	//
	// the handler use to receive the pay result.
	// 这里接收支付结果，支付宝手机端同步通知
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String ret = (String) msg.obj;

				Log.e(TAG, ret); // strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
				switch (msg.what) {
				case AlixId.RQF_PAY: {
                    Resources resource = (Resources) getBaseContext().getResources();
					//
					closeProgress();

					BaseHelper.log(TAG, ret);

					// 处理交易结果
					try {
						// 获取交易状态码，具体状态代码请参看文档
						String tradeStatus = "resultStatus={";
						int imemoStart = ret.indexOf("resultStatus=");
						imemoStart += tradeStatus.length();
						int imemoEnd = ret.indexOf("};memo=");
						tradeStatus = ret.substring(imemoStart, imemoEnd);

						// 先验签通知
						ResultChecker resultChecker = new ResultChecker(ret);
						int retVal = resultChecker.checkSign(AlixPayActivity.this);
						// 验签失败
						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
							BaseHelper.showDialog(
									AlixPayActivity.this,
                                    getResources().getString(R.string.prompt),
									getResources().getString(
											R.string.check_sign_failed),
									android.R.drawable.ic_dialog_alert);
						}
                        else
                        {// 验签成功。验签成功后再判断交易状态码
							if (tradeStatus.equals("9000"))// 判断交易状态码，只有9000表示交易成功
                            {
                                ToastView toast = new ToastView(AlixPayActivity.this, getResources().getString(R.string.pay_success));
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
							else
                            {
                                ToastView toast = new ToastView(AlixPayActivity.this, getResources().getString(R.string.pay_failed));
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK, intent);
                            finish();
						}

					} catch (Exception e) {
						e.printStackTrace();
						BaseHelper.showDialog(AlixPayActivity.this,  getResources().getString(R.string.prompt), ret,
								R.drawable.infoicon);
					}
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	//
	//
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

	//
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

		unregisterReceiver(mPackageInstallationListener);

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}