package com.external.alipay;

import org.json.JSONObject;

import android.content.Context;

/**
 * 对签名进行验签
 * 
 */
public class ResultChecker {

	public static final int RESULT_INVALID_PARAM = 0;
	public static final int RESULT_CHECK_SIGN_FAILED = 1;
	public static final int RESULT_CHECK_SIGN_SUCCEED = 2;

	String mContent;

	public ResultChecker(String content) {
		this.mContent = content;
	}

//	/**
//	 * 从验签内容中获取成功状态
//	 * 
//	 * @return
//	 */
//	private String getSuccess() {
//		String success = null;
//
//		try {
//			JSONObject objContent = BaseHelper.string2JSON(this.mContent, ";");
//			String result = objContent.getString("result");
//			result = result.substring(1, result.length() - 1);
//
//			JSONObject objResult = BaseHelper.string2JSON(result, "&");
//			success = objResult.getString("success");
//			success = success.replace("\"", "");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return success;
//	}

	/**
	 * 对签名进行验签
	 * 
	 * @return
	 */
	public int checkSign(Context context) {
		int retVal = RESULT_CHECK_SIGN_SUCCEED;
		PartnerConfig partnerConfig = new PartnerConfig(context);

		try {
			JSONObject objContent = BaseHelper.string2JSON(this.mContent, ";");
			String result = objContent.getString("result");
			result = result.substring(1, result.length() - 1);
			// 获取待签名数据
			int iSignContentEnd = result.indexOf("&sign_type=");
			String signContent = result.substring(0, iSignContentEnd);
			// 获取签名
			JSONObject objResult = BaseHelper.string2JSON(result, "&");
			String signType = objResult.getString("sign_type");
			signType = signType.replace("\"", "");

			String sign = objResult.getString("sign");
			sign = sign.replace("\"", "");
			// 进行验签 返回验签结果
			if (signType.equalsIgnoreCase("RSA")) {
				if(partnerConfig.RSA_ALIPAY_PUBLIC == null) {
					retVal = RESULT_INVALID_PARAM;
				} else {
					if (!Rsa.doCheck(signContent, sign,
							partnerConfig.RSA_ALIPAY_PUBLIC))
						retVal = RESULT_CHECK_SIGN_FAILED;
				}
				
			}
		} catch (Exception e) {
			retVal = RESULT_INVALID_PARAM;
			e.printStackTrace();
		}

		return retVal;
	}

//	boolean isPayOk() {
//		boolean isPayOk = false;
//
//		String success = getSuccess();
//		if (success.equalsIgnoreCase("true")
//				&& checkSign() == RESULT_CHECK_SIGN_SUCCEED)
//			isPayOk = true;
//
//		return isPayOk;
//	}
}