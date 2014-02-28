package com.tencent.weibo.sdk.android.network;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tencent.weibo.sdk.android.api.util.JsonUtil;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpReq.UTF8PostMethod;
/*
 * 
 * */
public class HttpReqWeiBo extends HttpReq {
	private Class<? extends BaseVO> mTargetClass;// 对象类型
	private Class<? extends BaseVO> mTargetClass2;// 对象类型
	private Integer mResultType = 0;// 结果类型
	private Context mContext;//上下文
/**
 * 构造函数
 * @param context 上下文
 * @param url     请求url
 * @param function  回调对象
 * @param targetClass  返回数据解析后存储对象，可为空
 * @param requestMethod 请求方式
 * @param resultType   返回数据类型BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2 BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
 * 
 * */
	public HttpReqWeiBo(Context context, String url, HttpCallback function,
			Class<? extends BaseVO> targetClass, String requestMethod,
			Integer resultType) {
		mContext = context;
		mHost = HttpConfig.CRM_SERVER_NAME;
		mPort = HttpConfig.CRM_SERVER_PORT;
		mUrl = url;
		mCallBack = function;
		mTargetClass = targetClass;
		mResultType = resultType;
		mMethod = requestMethod; 
	}

	public void setmTargetClass(Class<? extends BaseVO> mTargetClass) {
		this.mTargetClass = mTargetClass;
	}

	public void setmTargetClass2(Class<? extends BaseVO> mTargetClass2) {
		this.mTargetClass2 = mTargetClass2;
	}

	public void setmResultType(Integer mResultType) {
		this.mResultType = mResultType;
	}

	@Override
	protected Object processResponse(InputStream response) throws Exception {
		 
		ModelResult modelResult = new ModelResult();
		if (response != null) {
			
				InputStream is = response;
				InputStreamReader ireader = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(ireader);
				StringBuffer sb = new StringBuffer();
				String code;
				while ((code = breader.readLine()) != null) {
					sb.append(code);
				}
				breader.close();
				ireader.close();
				Log.d("relst", sb.toString());
				if(sb.toString().indexOf("errcode")==-1 && sb.toString().indexOf("access_token")!=-1){
					modelResult.setObj(sb.toString());
					return modelResult;
				}
				JSONObject json = new JSONObject(sb.toString());
				// 具体得json解析过程
				BaseVO baseVO = null;
				if (mTargetClass != null) {
					baseVO = mTargetClass.newInstance();
				}
				List<BaseVO> list = null;
				Map<String, Object> map = null;
				
				String errorCode = json.getString("errcode");
				String msg = json.getString("msg");
				if (errorCode != null && "0".equals(errorCode)) {
					modelResult.setSuccess(true);
					switch (mResultType) {
					case BaseVO.TYPE_BEAN:
						BaseVO vo = JsonUtil.jsonToObject(mTargetClass, json);
						list = new ArrayList<BaseVO>();
						list.add(vo);
						modelResult.setList(list);
						break;
					case BaseVO.TYPE_LIST:
						map = baseVO.analyseHead(json);
						JSONArray array = (JSONArray) map.get("array");
						list = JsonUtil.jsonToList(mTargetClass, array);
						Integer total = map.get("total") == null ? 0
								: (Integer) map.get("total");
						Integer p = map.get("p") == null ? 1 : (Integer) map
								.get("p");
						Integer ps = map.get("ps") == null ? 1 : (Integer) map
								.get("ps");
						boolean isLastPage = (Boolean) map.get("isLastPage");

						modelResult.setList(list);
						modelResult.setTotal(total);
						modelResult.setP(p);
						modelResult.setPs(ps);
						modelResult.setLastPage(isLastPage);

						break;
					case BaseVO.TYPE_OBJECT:
						// modelResult.setObj(baseVO.analyseBody(result));
						modelResult.setObj(JsonUtil
								.jsonToObject(mTargetClass, json));
						break;
					case BaseVO.TYPE_BEAN_LIST:
						BaseVO basebo = JsonUtil.jsonToObject(mTargetClass, json);
						JSONArray list_json = json.getJSONArray("result_list");
						list = JsonUtil.jsonToList(mTargetClass2, list_json);
						modelResult.setObj(basebo);
						modelResult.setList(list);
						break;
					case BaseVO.TYPE_JSON:
						modelResult.setObj(json);
						break;
					}
				} else {
					modelResult.setSuccess(false);
					modelResult.setError_message(msg);
				}
			}
		
		return modelResult;
	}

	@Override
	protected void setReq(HttpMethod method) throws Exception {
		 

		if ("POST".equals(mMethod)) {
			PostMethod post = (PostMethod) method;
			String mParamstr = mParam.toString();
			post.addParameter("Connection", "Keep-Alive");
			post.addParameter("Charset", "UTF-8");
			post.setRequestEntity(new ByteArrayRequestEntity(mParam.toString()
					.getBytes("utf-8")));
		}

	}
/**
 * 向服务器发送请求
 * @param url 请求url
 * @throws Exception
 * 
 * */
	public void setReq(String url) throws Exception {
		 

		if ("POST".equals(mMethod)) {
			PostMethod post = new UTF8PostMethod(mUrl);
			String mParamstr = mParam.toString();
			post.setRequestEntity(new ByteArrayRequestEntity(mParam.toString()
					.getBytes("utf-8")));
		}

	}

	
}
