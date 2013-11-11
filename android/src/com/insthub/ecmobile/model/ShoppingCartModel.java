package com.insthub.ecmobile.model;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.view.Gravity;
import android.widget.Toast;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.ToastView;

public class ShoppingCartModel extends BaseModel {

	public ArrayList<GOODS_LIST> goods_list = new ArrayList<GOODS_LIST>();
	public TOTAL total;
	public int goods_num;

    // 结算（提交订单前的订单预览）
    public ADDRESS address;
    public ArrayList<GOODS_LIST> balance_goods_list = new ArrayList<GOODS_LIST>();
    public ArrayList<PAYMENT> payment_list = new ArrayList<PAYMENT>();
    public ArrayList<SHIPPING> shipping_list = new ArrayList<SHIPPING>();

    public String orderInfoJsonString;

	private static ShoppingCartModel instance;
    public static ShoppingCartModel getInstance()
    {
        return instance;
    }

    public ShoppingCartModel()
    {
       super();
    }

    public ShoppingCartModel(Context context)
    {
        super(context);
        instance = this;
    }
	
	// 获取购物车列表
	public void cartList() {
		String url = ProtocolConst.CARTLIST;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

					System.out.println("jo--"+jo);
					if(responseStatus.succeed == 1)
                    {
						JSONObject dataJsonObject = jo.optJSONObject("data");
						
						total = TOTAL.fromJson(dataJsonObject.optJSONObject("total"));
						JSONArray dataJsonArray = dataJsonObject.optJSONArray("goods_list");
						
						goods_list.clear();
						ShoppingCartModel.this.goods_num = 0;
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							goods_list.clear();
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject goodsJsonObject = dataJsonArray.getJSONObject(i);
                                GOODS_LIST goods_list_Item = GOODS_LIST.fromJson(goodsJsonObject);
                                goods_list.add(goods_list_Item);
                                
                                ShoppingCartModel.this.goods_num += Integer.valueOf(goods_list_Item.goods_number);
                                
                            }
                        }
						
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
		
	}
	
	// 在首页获取购物车列表，存成单件
	public void homeCartList() {
		String url = ProtocolConst.CARTLIST;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

					if(responseStatus.succeed == 1)
                    {
						JSONObject dataJsonObject = jo.optJSONObject("data");
						
						total = TOTAL.fromJson(dataJsonObject.optJSONObject("total"));
						JSONArray dataJsonArray = dataJsonObject.optJSONArray("goods_list");
						
						goods_list.clear();
						ShoppingCartModel.this.goods_num = 0;
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							goods_list.clear();
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject goodsJsonObject = dataJsonArray.getJSONObject(i);
                                GOODS_LIST goods_list_Item = GOODS_LIST.fromJson(goodsJsonObject);
                                goods_list.add(goods_list_Item);
                                
                                ShoppingCartModel.this.goods_num += Integer.valueOf(goods_list_Item.goods_number);
                                
                            }
                        }
					}
					ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	
	public void deleteGoods(int rec_id) {
		String url = ProtocolConst.CARTDELETE;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("rec_id",rec_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	public void updateGoods(int rec_id, int new_number) {
		String url = ProtocolConst.CARTUPDATA;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("rec_id",rec_id);
            requestJsonObject.put("new_number",new_number);
		} catch (JSONException e) {
			// TODO: handle exception
		}
		
        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}

	public void checkOrder() {
		String url = ProtocolConst.CHECKORDER;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS res_status = STATUS.fromJson(jo.optJSONObject("status"));
					if(res_status.succeed == 1) {
						
						JSONObject dataJsonObject = jo.getJSONObject("data");
						address = ADDRESS.fromJson(dataJsonObject.optJSONObject("consignee"));
						JSONArray goodsArray = dataJsonObject.optJSONArray("goods_list");

						if (null != goodsArray && goodsArray.length() > 0) {

							balance_goods_list.clear();
                            for (int i = 0; i < goodsArray.length(); i++)
                            {
                                JSONObject goodsJsonObject = goodsArray.getJSONObject(i);
                                GOODS_LIST goods_list_Item = GOODS_LIST.fromJson(goodsJsonObject);
                                balance_goods_list.add(goods_list_Item);
                            }
                        }

                        orderInfoJsonString = dataJsonObject.toString();

                        JSONArray shippingArray = dataJsonObject.optJSONArray("shipping_list");
                        if (null != shippingArray && shippingArray.length() > 0)
                        {
                            shipping_list.clear();
                            for (int i = 0; i < shippingArray.length(); i++)
                            {
                                JSONObject shippingJSONObject =  shippingArray.getJSONObject(i);
                                SHIPPING shipping = SHIPPING.fromJson(shippingJSONObject);
                                shipping_list.add(shipping);
                            }
                        }
						
						JSONArray paymentArray = dataJsonObject.optJSONArray("payment_list");
						
						if (null != paymentArray && paymentArray.length() > 0) {
							payment_list.clear();
                            for (int i = 0; i < paymentArray.length(); i++) {
                                JSONObject paymentJsonObject = paymentArray.getJSONObject(i);
                                PAYMENT payment_Item = PAYMENT.fromJson(paymentJsonObject);
                                payment_list.add(payment_Item);
                            }
                        }
						
					}
					
					ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
		
	}
	
	// 订单生成
	public String order_id;
	public void flowDone(String pay_id, String shipping_id,String bonus, String integral, String inv_type,String inv_payee, String inv_content) {
		String url = ProtocolConst.FLOW_DOWN;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1)
                    {
						JSONObject json = jo.getJSONObject("data");
						order_id = json.getString("order_id");
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("pay_id",pay_id);
            requestJsonObject.put("shipping_id",shipping_id);
            if (null != bonus)
            {
                requestJsonObject.put("bonus",bonus);
            }

            if (null != integral)
            {
                requestJsonObject.put("integral",integral);
            }

            if (null != inv_type)
            {
                requestJsonObject.put("inv_type",inv_type);
            }

            if (null != inv_payee)
            {
                requestJsonObject.put("inv_payee",inv_payee);
            }

            if (null != inv_content)
            {
                requestJsonObject.put("inv_content",inv_content);
            }

            
		} catch (JSONException e) {
			// TODO: handle exception
		}
		
        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
		
	}

	public void integral(String integral) {
		String url = ProtocolConst.VALIDATE_INTEGRAL;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1)
                    {
						JSONObject data = jo.getJSONObject("data");
						String bonus = data.getString("bonus").toString();
						String bonus_formated = data.getString("bonus_formated").toString();
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("integral",integral);
		} catch (JSONException e) {
			// TODO: handle exception
		}
		
        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	// 验证红包
	public void bonus(String bonus_sn) {
		String url = ProtocolConst.VALIDATE_BONUS;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				//ShoppingCartModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						JSONObject data = jo.getJSONObject("data");
						String bonus = data.getString("bonus").toString();
						String bonus_formated = data.getString("bonus_formated").toString();
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
						
					}
					
					if(responseStatus.error_code == 101) {
						//Toast toast = Toast.makeText(mContext, "红包输入错误", 0);
						ToastView toast = new ToastView(mContext, "红包输入错误");
				        toast.setGravity(Gravity.CENTER, 0, 0);
				        toast.show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		SESSION session = SESSION.getInstance();;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("bonus_sn",bonus_sn);
		} catch (JSONException e) {
			// TODO: handle exception
		}
		
        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}

    public void getRedPackets()
    {
        String url = ProtocolConst.VALIDATE_BONUS;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>()
        {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {

                //ShoppingCartModel.this.callback(url, jo, status);
                try
                {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    if(responseStatus.succeed == 1)
                    {
                        JSONObject data = jo.getJSONObject("data");
                        String bonus = data.getString("bonus").toString();
                        String bonus_formated = data.getString("bonus_formated").toString();
                        ShoppingCartModel.this.OnMessageResponse(url, jo, status);

                    }

                    if(responseStatus.error_code == 101)
                    {
                        ToastView toast = new ToastView(mContext, "红包输入错误");
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }
                catch (JSONException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        SESSION session = SESSION.getInstance();

        JSONObject requestJsonObject = new JSONObject();

        Map<String, Object> params = new HashMap<String, Object>();
        try
        {
            requestJsonObject.put("session",session.toJson());
        }
        catch (JSONException e)
        {
            // TODO: handle exception
        }

        params.put("json",requestJsonObject.toString());

        cb.url(url).type(JSONObject.class).params(params);
        aq.ajax(cb);
    }
	
	

}
