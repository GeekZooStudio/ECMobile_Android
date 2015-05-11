/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.external.alipay;

import java.util.ArrayList;

/**
 * demo展示商品列表的商品信息
 * 
 */
public final class Products {

	public class ProductDetail {
		public String subject;
		public String body;
		public String price;
		public int resId;
	}

	ArrayList<Products.ProductDetail> mProductlist = new ArrayList<Products.ProductDetail>();

	public ArrayList<ProductDetail> retrieveProductInfo() {
		ProductDetail productDetail = null;

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "123456";
		productDetail.body = "2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "魅力香水";
		productDetail.body = "新年特惠 adidas 阿迪达斯走珠 香体止汗走珠 多种香型可选";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "珍珠项链";
		productDetail.body = "【2元包邮】韩版 韩国 流行饰品太阳花小巧雏菊 珍珠项链2M15";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "三星 原装移动硬盘";
		productDetail.body = "三星 原装移动硬盘 S2 320G 带加密 三星S2 韩国原装 全国联保";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "发箍发带";
		productDetail.body = "【肉来来】超热卖 百变小领巾 兔耳朵布艺发箍发带";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "台版N97I";
		productDetail.body = "台版N97I 有迷你版 双卡双待手机 挂QQ JAVA 炒股 来电归属地 同款比价 ";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "苹果手机";
		productDetail.body = "山寨国产红苹果手机 Hiphone I9 JAVA QQ后台 飞信 炒股 UC";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "蝴蝶结";
		productDetail.body = "【饰品实物拍摄】满30包邮 三层绸缎粉色 蝴蝶结公主发箍多色入";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "韩版雪纺";
		productDetail.body = "饰品批发价 韩版雪纺纱圆点布花朵 山茶玫瑰花 发圈胸针两用 6002";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "五皇纸箱";
		productDetail.body = "加固纸箱 会员包快递拍好去运费冲纸箱首个五皇";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "MF唱片";
		productDetail.body = "【正版】MF唱片 HIFI毒药4 毒药涅磐再造 海洛 因新4号HD天碟1CD";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "学习机";
		productDetail.body = "六人行老友记friends全10季英语学习机版 MP3版子精读笔记";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "联华卡";
		productDetail.body = "联华OK卡，特价供应联华卡，联华OK卡，积点卡982折 卡密或代充";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "粽子批发";
		productDetail.body = "嘉兴粽子批发团购真真老老之大肉粽";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		// useful below...
		//
		// x < 50
		productDetail = new ProductDetail();
		productDetail.subject = "话费充值";
		productDetail.body = "【四钻信誉】北京移动30元 电脑全自动充值 1到10分钟内到账";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// 50 < x < 200
		productDetail = new ProductDetail();
		productDetail.subject = "短袖T恤";
		productDetail.body = "爱马仕男装短袖T恤2010新款时尚夏装韩版男士T恤正品原单圆领修身";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// 200 < x < 500
		productDetail = new ProductDetail();
		productDetail.subject = "田园沙发";
		productDetail.body = "环保 韩式田园沙发/布艺沙发/现代沙发/特价田园沙发<可定做>";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// 500 < x < 1000
		productDetail = new ProductDetail();
		productDetail.subject = "夏季登山鞋";
		productDetail.body = "8071男女士专柜正品夏季户外防滑鞋户外鞋登山鞋徒步鞋防水透气灰";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// 1000 < x < 2000
		productDetail = new ProductDetail();
		productDetail.subject = "精品娃娃";
		productDetail.body = "宜家宜精品娃娃，超柔短毛绒海豚抱枕 75厘米 全国包邮";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		//
		// 2000 < x < 5000
		productDetail = new ProductDetail();
		productDetail.subject = "HTC G5 谷歌G5";
		productDetail.body = "HTC G5 谷歌G5 Google G5 先验货后付款 支票刷卡 易人在线";
		productDetail.price = "一口价:0.01";
		productDetail.resId = 30;
		mProductlist.add(productDetail);

		return mProductlist;
	}
}
