package com.insthub.ecmobile;

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
public class ErrorCodeConst
{
    public final static int ResponseSucceed = 1;//请求成功
    public final static int InvalidUsernameOrPassword = 6;//用户名或密码错误
    public final static int ProcessFailed = 8;//处理失败
    public final static int UserOrEmailExist = 11; //用户名或email已使用
    public final static int UnexistInformation = 13;//不存在的信息
    public final static int BuyFailed = 14;//购买失败

    public final static int InvalidSession = 100;//session 不合理
    public final static int InvalidParameter = 101;//错误的参数提交

    public final static int InvalidPagination = 501;//没有pagination结构
    public final static int InvalidCode = 502;//code错误
    public final static int CodeExpire  = 503;//合同期终止

    public final static int SelectedDeliverMethod = 10001;//您必须选定一个配送方式
    public final static int NoGoodInCart          = 10002;//购物车中没有商品
    public final static int InsufficientBalance   = 10003;//您的余额不足以支付整个订单，请选择其他支付方式
    public final static int InsufficientNumberOfPackage = 10005;//您选择的超值礼包数量已经超出库存。请您减少购买量或联系商家
    public final static int CashOnDeliveryDisable       = 10006;//如果是团购，且保证金大于0，不能使用货到付款
    public final static int AlreadyCollected       = 10007;//您已收藏过此商品
    public final static int InventoryShortage       = 10008;//库存不足
    public final static int NoShippingInformation       = 10009;//订单无发货信息


}
