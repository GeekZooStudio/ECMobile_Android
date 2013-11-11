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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.EXPRESS;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.PAGINATED;
import com.insthub.ecmobile.protocol.PAGINATION;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;

public class OrderModel extends BaseModel {

	private int page = 1;
	public PAGINATED paginated;
	
	public ArrayList<GOODORDER> order_list = new ArrayList<GOODORDER>();
	public ArrayList<EXPRESS> express_list = new ArrayList<EXPRESS>();
	
	public OrderModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void getOrder(String type) {
		page = 1;
		String url = ProtocolConst.ORDER_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				OrderModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						
						JSONArray dataJsonArray = jo.optJSONArray("data");
						
						order_list.clear();
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							order_list.clear();
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject orderJsonObject = dataJsonArray.getJSONObject(i);
                                GOODORDER order_list_Item = GOODORDER.fromJson(orderJsonObject);
                                order_list.add(order_list_Item);
                            }
                        }
						
						paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
						
					}
					
					OrderModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

		};

		SESSION session = SESSION.getInstance();
		PAGINATION pagination = new PAGINATION();
		pagination.page = 1;
		pagination.count = 10;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("pagination",pagination.toJson());
            requestJsonObject.put("type", type);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
		
	}
	
	public void getOrderMore(String type) {

		String url = ProtocolConst.ORDER_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				OrderModel.this.callback(url, jo, status);
				
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					
					if(responseStatus.succeed == 1) {
						
						JSONArray dataJsonArray = jo.optJSONArray("data");
						
						if (null != dataJsonArray && dataJsonArray.length() > 0) {

                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject orderJsonObject = dataJsonArray.getJSONObject(i);
                                GOODORDER order_list_Item = GOODORDER.fromJson(orderJsonObject);
                                order_list.add(order_list_Item);
                            }
                        }
						
						paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
						
					}
					
					OrderModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

		};

		SESSION session = SESSION.getInstance();
		PAGINATION pagination = new PAGINATION();
		pagination.page = order_list.size()/10+1;
		pagination.count = 10;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("pagination",pagination.toJson());
            requestJsonObject.put("type", type);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}

	public void orderPay(int order_id) {
		
		String url = ProtocolConst.ORDER_PAY;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				OrderModel.this.callback(url, jo, status);
				
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					
					OrderModel.this.OnMessageResponse(url, jo, status);
					
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
            requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	// 取消订单
	public void orderCancle(int order_id) {
		
		String url = ProtocolConst.ORDER_CANCLE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				OrderModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						
						OrderModel.this.OnMessageResponse(url, jo, status);
					}
					
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
            requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	
	// 确认收货
	public void affirmReceived(int order_id) {
		
		String url = ProtocolConst.AFFIRMRECEIVED;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				OrderModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						OrderModel.this.OnMessageResponse(url, jo, status);
					}
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
            requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
}
