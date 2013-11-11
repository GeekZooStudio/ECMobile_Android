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

import android.app.Activity;
import android.content.res.Resources;
import com.insthub.ecmobile.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.PAGINATED;
import com.insthub.ecmobile.protocol.PAGINATION;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.insthub.ecmobile.protocol.STATUS;

public class GoodsListModel extends BaseModel {

	public ArrayList<SIMPLEGOODS> simplegoodsList = new ArrayList<SIMPLEGOODS>();

    public static String PRICE_DESC = "price_desc";
    public static String PRICE_ASC  = "price_asc";
    public static String IS_HOT  = "is_hot";

    public static final int PAGE_COUNT = 6;

	public GoodsListModel(Context context) 
	{
		super(context);	
	}
		
    public void fetchPreSearch(FILTER filter)
    {
		String url = ProtocolConst.SEARCH;
        
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
			
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				GoodsListModel.this.callback(url, jo, status);
				try 
				{
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    PAGINATED paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
					
					if (responseStatus.succeed == 1) 
					{												
						JSONArray simpleGoodsJsonArray = jo.optJSONArray("data");
							
						simplegoodsList.clear();
						if (null != simpleGoodsJsonArray && simpleGoodsJsonArray.length() > 0) 
						{
							simplegoodsList.clear();
							for (int i = 0; i < simpleGoodsJsonArray.length(); i++) 
							{
								JSONObject simpleGoodsJsonObject = simpleGoodsJsonArray.getJSONObject(i);
								SIMPLEGOODS simplegoods = SIMPLEGOODS.fromJson(simpleGoodsJsonObject);
								simplegoodsList.add(simplegoods);
							}
						}
						
						GoodsListModel.this.OnMessageResponse(url, jo, status);
						
					}
					
				} catch (JSONException e) {
					// TODO: handle exception
				}
				
			}
			
		};

		PAGINATION pagination = new PAGINATION();
		pagination.page = 1;
		pagination.count = PAGE_COUNT;
		
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("filter",filter.toJson());
            requestJsonObject.put("pagination",pagination.toJson());
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);

        Resources resource = mContext.getResources();
        String wait=resource.getString(R.string.hold_on);
        pd.setMessage(wait);
	    aq.progress(pd).ajax(cb);

    }
    
    public void fetchPreSearchMore(FILTER filter)
    {
		String url = ProtocolConst.SEARCH;
        
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
			
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				GoodsListModel.this.callback(url, jo, status);
				try 
				{
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    PAGINATED paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));
					
					if (responseStatus.succeed == 1) 
					{												
						JSONArray simpleGoodsJsonArray = jo.optJSONArray("data");
							
						if (null != simpleGoodsJsonArray && simpleGoodsJsonArray.length() > 0) 
						{
							for (int i = 0; i < simpleGoodsJsonArray.length(); i++) 
							{
								JSONObject simpleGoodsJsonObject = simpleGoodsJsonArray.getJSONObject(i);
								SIMPLEGOODS simplegoods = SIMPLEGOODS.fromJson(simpleGoodsJsonObject);
								simplegoodsList.add(simplegoods);
							}
						}
						
						GoodsListModel.this.OnMessageResponse(url, jo, status);
						
					}
					
				} catch (JSONException e) {
					// TODO: handle exception
				}
				
			}
			
		};

		PAGINATION pagination = new PAGINATION();
		pagination.page = (int)Math.ceil((double)simplegoodsList.size()*1.0/PAGE_COUNT)+1;
		pagination.count = PAGE_COUNT;
		
		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try 
		{
            requestJsonObject.put("filter",filter.toJson());
            requestJsonObject.put("pagination",pagination.toJson());
		} catch (JSONException e) {
			// TODO: handle exception
		}

        params.put("json",requestJsonObject.toString());
		
		cb.url(url).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

}
