#第一步、后台配置

1. 打开工程，找到 ECMobileAppConst.java
2. 修改如下代码为ECMobile API地址：

   		public static final String SERVER_PRODUCTION = "http://shop.ecmobile.me/ecmobile/?url=";
	    public static final String SERVER_DEVELOPMENT = "http://shop.ecmobile.me/ecmobile/?url=";
3. 找到BeeQuery.java，修改软件开发环境

		public static int environment() 
		{
			return ENVIROMENT_DEVELOPMENT;
		}
	
    

#第二步、服务配置

1. 打开工程，找到 ECMobileManager.java

	配置微信

		// 获取微信id
		public static String getWeixinAppId(Context context)
	   	{
	       	return "xxx";
		}	
		// 获取微信key
		public static String getWeixinAppKey(Context context)
	    {
	   	    return "xxx";
		}
	
	配置新浪
	
		// 获取新浪key
		public static String getSinaKey(Context context)
	   	{
	   	    return "xxx";
		}
	
		// 获取新浪secret
		public static String getSinaSecret(Context context)
	    {
	   	    return "xxx";
		}
	
		// 获取新浪的回调地址
	    public static String getSinaCallback(Context context)
	   	{
	       	return "xxx";
		 }

	配置腾讯

		// 获取腾讯key
		public static String getTencentKey(Context context)
	   	{
	       	return "xxx";
		}
	
		// 获取腾讯secret
		public static String getTencentSecret(Context context)
	   	{
	       	return "xxx";
		}
	
		// 获取腾讯callback
		public static String getTencentCallback(Context context)
	   	{
	       	return "xxx";
		}

	配置支付宝
	
		// 获取支付宝parterID(合作者身份)	
		public static String getAlipayParterId(Context context)
	    {
	        return "xxx";
		}
		
		// 获取支付宝sellerID(收款账户)
		public static String getAlipaySellerId(Context context)
	    {
	        return "xxx";
		}
		
		// 获取支付宝key
		public static String getAlipayKey(Context context)
	    {
	        return "xxx";
		}
		
		// 获取支付宝rsa_alipay_public(公钥)
		public static String getRsaAlipayPublic(Context context)
	    {
	        return "xxx";
		}
		
		// 获取支付宝rsa_private(私钥)
		public static String getRsaPrivate(Context context)
	    {
	        return "xxx";
		}
		
		// 获取支付宝回调地址
		public static String getAlipayCallback(Context context)
	    {
	        return "xxx";
		}


#二次开发

1. 阅读 `ECMobile_源码说明.pdf`
2. 打开工程，找到 /res/layout，修改需要调整的 .xml 界面布局及样式
3. 找到 /res/drawable-hdpi，修改图片

#联系方式

官方论坛：http://bbs.ecmobile.cn/    

QQ群1：329673575    
QQ群2：239571314    
QQ群3：347624547    