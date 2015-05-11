package com.insthub.ecmobile;

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
