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
import com.insthub.ecmobile.protocol.COLLECT_LIST;
import com.insthub.ecmobile.protocol.PAGINATED;
import com.insthub.ecmobile.protocol.PAGINATION;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;

public class CollectListModel extends BaseModel {

	public ArrayList<COLLECT_LIST> collectList = new ArrayList<COLLECT_LIST>();
	
	public PAGINATED paginated;
	public CollectListModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void getCollectList() {
		String url = ProtocolConst.COLLECT_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CollectListModel.this.callback(url, jo, status);
				
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					
					if(responseStatus.succeed == 1) {
						JSONArray dataJsonArray = jo.optJSONArray("data");
						collectList.clear();
                        if (null != dataJsonArray && dataJsonArray.length() > 0) {
                        	collectList.clear();
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject collectJsonObject = dataJsonArray.getJSONObject(i);
                                COLLECT_LIST collectItem = COLLECT_LIST.fromJson(collectJsonObject);
                                collectList.add(collectItem);
                            }
                        }
                        paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
                        CollectListModel.this.OnMessageResponse(url, jo, status);
					}
					
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
            requestJsonObject.put("rec_id", 0);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
		
	}
	
	
	public void getCollectListMore() {
		String url = ProtocolConst.COLLECT_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CollectListModel.this.callback(url, jo, status);
				
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					
					if(responseStatus.succeed == 1) {
						JSONArray dataJsonArray = jo.optJSONArray("data");
                        if (null != dataJsonArray && dataJsonArray.length() > 0) {
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject collectJsonObject = dataJsonArray.getJSONObject(i);
                                COLLECT_LIST collectItem = COLLECT_LIST.fromJson(collectJsonObject);
                                collectList.add(collectItem);
                            }
                        }
                        paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
                        CollectListModel.this.OnMessageResponse(url, jo, status);
					}
					
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
            requestJsonObject.put("rec_id", collectList.get(collectList.size()-1).rec_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	
	// 删除收藏商品
	public void collectDelete(String rec_id) {
		String url = ProtocolConst.COLLECT_DELETE;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CollectListModel.this.callback(url, jo, status);
					
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						CollectListModel.this.OnMessageResponse(url, jo, status);
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
	        requestJsonObject.put("rec_id",rec_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

	    params.put("json",requestJsonObject.toString());
	    cb.url(url).type(JSONObject.class).params(params);
	    ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
			
	}

}
