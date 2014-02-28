package com.tencent.weibo.sdk.android.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 解析JSON数据存储对象基类
 * */
public abstract class BaseVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8175948521471886407L;
	public static final int TYPE_BEAN = 0;
	public static final int TYPE_LIST = 1;
	public static final int TYPE_OBJECT = 2;
	public static final int TYPE_BEAN_LIST =3;	
	public static final int TYPE_JSON =4;	
	
	/**
	 * JSON集合解析,有个性需求则子类重写
	 * @param jsonObject
	 * @return
	 * @throws JSONException 
	 */ 
	public Map<String, Object> analyseHead(JSONObject result) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray array = result.getJSONArray("result_list");
		int total = result.getInt("total");
		int p = result.getInt("p");
		int ps = result.getInt("ps");
		boolean isLastPage = result.getBoolean("is_last_list");

		map.put("array", array);
		map.put("total", total);
		map.put("p", p);
		map.put("ps", ps);
		map.put("isLastPage", isLastPage);
		return map;
	}

	/**
	 * 解释JSON主体数据,子类自行实现
	 * */
	public Object analyseBody(JSONObject result) throws JSONException{
		return null;
	}
	/***
	 * 解析JSON主体,当其为Array时需调用HttpReqGis该方法才有效,
	 * @param result
	 * @return
	 * @throws JSONException
	 */
	public Object analyseBody(JSONArray result) throws JSONException{
		return null;
	}
}
