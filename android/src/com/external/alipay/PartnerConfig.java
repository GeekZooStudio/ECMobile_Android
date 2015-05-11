package com.external.alipay;

import com.insthub.ecmobile.EcmobileManager;

import android.content.Context;

public class PartnerConfig {

    // 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
//    public static final String PARTNER = "2088011063375879";
//    // 商户收款的支付宝账号
//   public static final String SELLER = "2088011063375879";
//    // 商户（RSA）私钥
//    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJoiYMl12bNTiRXrAGliVvOKzOmRyXPKaSFRwX+xCBXJ/f58HqdPomgztFkEdsnG+JW3q8O6tPzNmybB2hGlgdIT/rLQ/eq5x1OYy4seu7Ino+SeIoGaGh38sjDi5tbIsxSndh0yLpBAZCLVdR5sEFSKbLhiwwl5lU++hIkjYXV9AgMBAAECgYEAiphzE3QnJ3rr/4tquVg1+5RJoZT34miVk+Jh7iIPtRgGjjipj6Sp0qz7dDfxYIrLqESZ7MwMRm3TH0yce9WpHwqZ8D7VqXj/UgqErfdRQ+aR3LH9G2HXXULTsXSdPpdW3WQYC9BR8plMR6Q2TVQ5XUXtKDhP3EiOGUXrAEBq1MECQQDJeKCIEBunr3azuppxBtZkIx8k4QhKT7DQlapj9lLRW3Ile764Pd0xvMpOdr5mGWzIpJ6OFqvhNca3BBnMhu7ZAkEAw9no2xeZH1A147Up7y0L94nCHKgwe4HC+hSH6127BW/jqCyR/wc7mv3XmZHGDlzvE1hlJtaEviXsj+XRUcidRQJAWMHTtx2hkVYzrSpgL7sbaDIw3kZlKJfDBaFp13AFPEZVGz5Q30oh0G+jkL2vU7uPuTUMxPwn7KeMS8R6uSgYwQJAVP2DZ1BeSpBsUlyTzg8mWk2VxwnVwEMXcZ7nPOR3/GwJxzlQQfPJkgEGRsZTxHff5+08OBZvlHSwq+F3bJ46YQJAdKU9iBUUT9zhx30McrWyyUzDJoqFvULU3bhTZFPGphoDu58+qzUN6hWCiBH0Iy2Ukqf6CSjKEYAnY7GiymmBDQ==";
//    // 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
//    public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPTNAth2DBR5LxEQnzm3Y/kfSKGkJCnAxvZIUqmhybb3atgGV835fX58WGan7gswVk6vkJTXQp/G8d0g358muwt1c6NpFX90vP5WIk97dbGmBjqQREqmvX7x/UuCAKg5dG+hhQngjParDDkjWI9m8xXiLctoJ6z72JRh5qMZ15VwIDAQAB";
//    // 支付宝回调地址
//    public static final String ALIPAY_CALLBACK = "http://shop.ecmobile.me/ecmobile/respond.php";
    // 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致 
    public static final String ALIPAY_PLUGIN_NAME = "alipay_msp.apk";

    public String PARTNER;
    public String SELLER;
    public String RSA_PRIVATE;
    public String RSA_ALIPAY_PUBLIC;
    public String ALIPAY_CALLBACK;
    public Context mContext;
    
    public PartnerConfig(Context context) {
    	PARTNER = EcmobileManager.getAlipayParterId(context);
    	SELLER = EcmobileManager.getAlipaySellerId(context);
    	RSA_PRIVATE = EcmobileManager.getRsaPrivate(context);
    	RSA_ALIPAY_PUBLIC = EcmobileManager.getRsaAlipayPublic(context);
    	ALIPAY_CALLBACK = EcmobileManager.getAlipayCallback(context);
    }


}

