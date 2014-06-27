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

import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class SearchModel extends BaseModel {

	public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<CATEGORY> categoryArrayList = new ArrayList<CATEGORY>();
    String pkName;
    // 缓存数据
    private PrintStream ps = null;
	
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
			 
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
    }
    
    public void searchDataCache(String result) {
    	try {
			if (result != null) {
				JSONObject jsonObject = new JSONObject(result);
				homedataResponse data=new homedataResponse();
                data.fromJson(jsonObject);
				list.clear();
				if(data.status.succeed == 1) {
					ArrayList<PLAYER> players = data.data.player;
					if (null != players && players.size() > 0) {
						for (int i = 0; i < players.size(); i++) {
							list.add(players.get(i).toJson().toString());
						}
					}
				}
			}

		} catch (Exception e) {
			 
			e.printStackTrace();
		}
    }

    public void searchCategory()
    {
        categoryRequest request=new categoryRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                SearchModel.this.callback(url, jo, status);
                try {
                    categoryResponse response = new categoryResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        categoryArrayList.clear();
                        if (response.status.succeed == 1) {
                            ArrayList<CATEGORY> data = response.data;

                            if (null != data && data.size() > 0) {
                                categoryArrayList.addAll(data);
                            }

                            SearchModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        cb.url(ApiInterface.CATEGORY).type(JSONObject.class);
        aq.ajax(cb);
    }
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
