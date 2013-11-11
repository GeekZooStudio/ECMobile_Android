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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insthub.ecmobile.protocol.CATEGORY;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.PLAYER;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.insthub.ecmobile.protocol.STATUS;

public class SearchModel extends BaseModel {

	public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<CATEGORY> categoryArrayList = new ArrayList<CATEGORY>();
    String pkName;
	
	public SearchModel(Context context) {
		super(context);
        pkName = mContext.getPackageName();
		readSearchDataCache();
	}
	
	// 读取缓存
    public void readSearchDataCache() {
		String path = ProtocolConst.FILEPATH+"/"+pkName+"/searchData.dat";
		File f1 = new File(path);
		try {
			InputStream is = new FileInputStream(f1);
			InputStreamReader input = new InputStreamReader(is, "UTF-8");
			BufferedReader bf = new BufferedReader(input);
			
			searchDataCache(bf.readLine());
			
			bf.close();
			input.close();
			is.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void searchDataCache(String result) {
    	try {
			if (result != null) {
				JSONObject jsonObject = new JSONObject(result);
				
				STATUS responseStatus = STATUS.fromJson(jsonObject.optJSONObject("status"));
				list.clear();
				if(responseStatus.succeed == 1) {
					JSONArray playerJSONArray = jsonObject.optJSONArray("data");
					if (null != playerJSONArray && playerJSONArray.length() > 0) {
						for (int i = 0; i < playerJSONArray.length(); i++) {
							list.add(playerJSONArray.getString(i));
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void searchCategory()
    {
        String url = ProtocolConst.CATEGORY;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                SearchModel.this.callback(url, jo, status);
                try {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    categoryArrayList.clear();
                    if(responseStatus.succeed == 1)
                    {
                        JSONArray categoryJSONArray = jo.optJSONArray("data");

                        if (null != categoryJSONArray && categoryJSONArray.length() > 0)
                        {
                            for (int i = 0; i < categoryJSONArray.length(); i++)
                            {
                                JSONObject categoryObject = categoryJSONArray.getJSONObject(i);
                                CATEGORY category = CATEGORY.fromJson(categoryObject);
                                categoryArrayList.add(category);
                            }
                        }

                        SearchModel.this.OnMessageResponse(url, jo, status);

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        cb.url(url).type(JSONObject.class);
        aq.ajax(cb);
    }
	
	// 获取搜索推荐关键字
	public void searchKeywords() {
		String url = ProtocolConst.SEARCHKEYWORDS;
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				
				SearchModel.this.callback(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					list.clear();
					if(responseStatus.succeed == 1) {
						fileSave(jo.toString(),"searchData");
						
						JSONArray playerJSONArray = jo.optJSONArray("data");
						
						if (null != playerJSONArray && playerJSONArray.length() > 0) {
							for (int i = 0; i < playerJSONArray.length(); i++) {
								list.add(playerJSONArray.getString(i));
							}
						}
						
                        SearchModel.this.OnMessageResponse(url, jo, status);
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		
		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);
		
	}
	
	// 缓存数据
	private PrintStream ps = null;
	public void fileSave(String result, String name) {
	
		String path = ProtocolConst.FILEPATH+"/"+pkName;
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		
		File file = new File(filePath+"/"+name+".dat");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			ps = new PrintStream(fos);
			ps.print(result);
			ps.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
