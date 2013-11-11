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

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.COMMENTS;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.PAGINATED;
import com.insthub.ecmobile.protocol.PAGINATION;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;

public class CommentModel extends BaseModel {

	public PAGINATED paginated;
	
	public ArrayList<COMMENTS> comment_list = new ArrayList<COMMENTS>();
	
	public CommentModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void getCommentList(int goods_id) {
		String url = ProtocolConst.COMMENTS;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CommentModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						
						JSONArray dataJsonArray = jo.optJSONArray("data");
						
						comment_list.clear();
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							comment_list.clear();
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject commentJsonObject = dataJsonArray.getJSONObject(i);
                                COMMENTS comment_list_Item = COMMENTS.fromJson(commentJsonObject);
                                comment_list.add(comment_list_Item);
                            }
                        }
					
						paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
						
					}
					
					CommentModel.this.OnMessageResponse(url, jo, status);
					
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
            requestJsonObject.put("goods_id", goods_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	public void getCommentsMore(int goods_id) {
		String url = ProtocolConst.COMMENTS;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CommentModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if(responseStatus.succeed == 1) {
						
						JSONArray dataJsonArray = jo.optJSONArray("data");
						
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
                            for (int i = 0; i < dataJsonArray.length(); i++) {
                                JSONObject commentJsonObject = dataJsonArray.getJSONObject(i);
                                COMMENTS comment_list_Item = COMMENTS.fromJson(commentJsonObject);
                                comment_list.add(comment_list_Item);
                            }
                        }
					
						paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
					}
					
					CommentModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

		};

		SESSION session = SESSION.getInstance();
		PAGINATION pagination = new PAGINATION();
		pagination.page = comment_list.size()/10+1;
		pagination.count = 10;
		 
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("session",session.toJson());
            requestJsonObject.put("pagination",pagination.toJson());
            requestJsonObject.put("goods_id", goods_id);
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}

}
