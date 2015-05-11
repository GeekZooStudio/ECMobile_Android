package com.insthub.ecmobile.model;
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

import android.os.Environment;

public class ProtocolConst {
	
	public static String FILEPATH = Environment.getExternalStorageDirectory()+ "/ECMobile/cache";
	
	public static String HOMEDATA = "home/data";
	public static String CATEGORYGOODS ="home/category";
	public static String SEARCH = "search";
    public static String SHOPHELP = "shopHelp";
    public static String GOODSDETAIL = "goods";
    
    public static String GOODSDESC = "goods/desc";					// 商品详情，商品描述
    
    public static String SIGNIN = "user/signin";					// 登录
    
    public static String SIGNUPFIELDS  = "user/signupFields";		// 注册字段
    
    public static String SIGNUP = "user/signup";					// 注册
    
    public static String SEARCHKEYWORDS = "searchKeywords";			// 获取搜索推荐关键字
    
    public static String CARTLIST = "cart/list";					// 购物车列表
    
    public static String USERINFO = "user/info";					// 获取用户中心相关信息

    public static String COLLECT_LIST = "user/collect/list";		// 收藏列表
    
    public static String COLLECT_DELETE = "user/collect/delete";	// 收藏删除

    public static String CARTCREATE = "cart/create";				// 添加到购物车
    
    public static String CARTDELETE = "cart/delete";				// 从购物车删除一件商品
    
    public static String CARTUPDATA = "cart/update";				// 更新购物车
    
    public static String ADDRESS_LIST = "address/list";				// 获取用户地址列表
    
    public static String ADDRESS_ADD = "address/add";				// 添加用户地址
    
    public static String REGION = "region";							// 获取地区城市
    
    public static String CHECKORDER = "flow/checkOrder";			// 提交订单前的订单预览
    
    public static String ADDRESS_INFO = "address/info";				// 读取地址详情
    
    public static String ADDRESS_DEFAULT = "address/setDefault";	// 设置该地址为默认
    
    public static String ADDRESS_DELETE = "address/delete";			// 删除一个地址
    
    public static String ADDRESS_UPDATE = "address/update";			// 更新地址
    
    public static String COLLECTION_CREATE = "user/collect/create";	// 收藏
    
    public static String FLOW_DOWN = "flow/done";					// 订单生成
    
    public static String ORDER_LIST = "order/list";					// 订单列表
    
    public static String VALIDATE_INTEGRAL = "validate/integral";	// 验证积分
    
    public static String VALIDATE_BONUS = "validate/bonus";			// 验证红包
    
    public static String ORDER_PAY = "order/pay";					// 在线支付
    
    public static String ORDER_CANCLE = "order/cancel";				// 取消订单
    
    public static String AFFIRMRECEIVED = "order/affirmReceived";	// 确认收货
    
    public static String EXPRESS = "order/express";					// 查看物流
    
    public static String ARTICLE = "article";						// 获取文章内容
    
    public static String COMMENTS = "comments";						// 获取评论列表
    public static String CONFIG = "config";						// 获取配置
    public static String CATEGORY = "category";                  //获取所有分类
    public static String BRAND = "brand";                       //获取所有品牌
    public static String PRICE_RANGE = "price_range";           //根据分类获取价格区间
    public static String ORDER_WAP_PAY="order/wapPay";          //wap支付

    
}
