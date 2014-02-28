package com.tencent.weibo.sdk.android.network;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.zip.GZIPInputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import android.util.Log;
/**
 * 处理网络请求的抽象类，异步发出网络请求，返回网络数据后并响应回调函数
 */
public abstract class HttpReq extends AsyncTask<Void, Integer, Object> {
	private final String GET = "GET";//Get请求方式
	private final String POST = "POST";//Post请求方式
	protected String mHost = null;//请求主机地址
	protected int mPort = 8088;//请求端口
	protected String mUrl = null;//网络请求地址
	protected String mMethod = null;//定义网络请求方式
	protected ReqParam mParam = new ReqParam();//参数封装类
	protected HttpCallback mCallBack = null;//回调函数
	private int mServiceTag = -1; 
	public void setServiceTag(int nTag) {
		mServiceTag = nTag;
	}

	public int getServiceTag() {
		return mServiceTag;
	}

	/**
	 * 返回回调方法
	 */
	protected HttpCallback getCallBack() {
		return mCallBack;
	}

	/**
	 * 设置网络请求方式的抽象函数
	 * @param method 网络请求方式 post或者get
	 * @throws Exception
	 */
	protected abstract void setReq(HttpMethod method) throws Exception;

	/**
	 * 响应网络请求得抽象函数
	 * @param response 网络请求返回的数据
	 * @return Object 返回的数据对象
	 * @throws Exception
	 */
	protected abstract Object processResponse(InputStream response)
			throws Exception;

	/**
	 * 设置网络请求参数封装类的函数
	 * @param param 网络请求参数封装类
	 */
	public void setParam(ReqParam param) {
		mParam = param;
	}

	/**
	 * 添加网络请求参数
	 * @param key 请求参数的名称
	 * @param value 请求参数的值，字符串类型
	 */
	public void addParam(String key, String value) {
		mParam.addParam(key, value);
	}
	
	/**
	 * 添加网络请求参数
	 * @param key 请求参数的名称
	 * @param value 请求参数的值，对象类型
	 */
	public void addParam(String key, Object value) {
		mParam.addParam(key, value);
	}

	/**
	 * 网络请求函数，该函数中通过判断网络请求的方式（post 或者 get）
	 * 发出网络请求，并返回响应的数据结果
	 * @return Object 返回的数据对象
	 * @throws Exception
	 */
	public Object runReq() throws Exception {

		HttpClient client = new HttpClient();
		HttpMethod method = null;
		int statusCode = -1;

		// 区分POST,GET方法
		if (mMethod.equals(GET)) {
			mUrl += "?"
					+ mParam.toString().substring(0,
							mParam.toString().length() - 1);
			method = new GetMethod(mUrl);
		} else if (mMethod.equals(POST)) {
			if (mParam.getmParams().get("pic") != null) {
				return processResponse(picMethod());
			}

			method = new UTF8PostMethod(mUrl);
		} else {
			throw new Exception("unrecognized http method");
		}
		client.getHostConfiguration().setHost(mHost, mPort, "https");

		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");

		// 设置请求body部分
		setReq(method);

		statusCode = client.executeMethod(method);
		Log.d("result", statusCode + "");
		if (statusCode != HttpStatus.SC_OK) {
			return null;
		}

		Object result = null;
		result = processResponse(method.getResponseBodyAsStream());

		return result;
	}

	/**
	 * 处理发送图片的函数
	 * @return InputStream 发送图片后，网络返回的结果
	 */
	private InputStream picMethod() {
		DefaultHttpClient client = new DefaultHttpClient();
		InputStream result = null;
		// 数据分隔线
		final String BOUNDARY = "---------------------------7da2137580612";
		// 回车
		final String RETURN = "\r\n";
		// 前缀
		final String PREFIX = "--";
		String MULTIPART_FORM_DATA = "multipart/form-data";
		HttpPost post = new HttpPost(mUrl);
		HttpUriRequest request = null;
		request = post;
		byte[] data = null;
		ByteArrayOutputStream bos = null;
		bos = new ByteArrayOutputStream();
		String strparams = mParam.toString();
		post.setHeader("Content-Type", MULTIPART_FORM_DATA + "; boundary="
				+ BOUNDARY);
		try {

			if (strparams != null && !strparams.equals("")) {
				// 分割文本类型参数字符串
				String[] params = strparams.split("&");
				for (String str : params) {
					if (str != null && !str.equals("")) {
						if (str.indexOf("=") > -1) {
							String[] p = str.split("=");
							// 获取参数值
							String value = (p.length == 2 ? decode(p[1]) : "");
							// 添加数据分隔线
							StringBuilder temp = new StringBuilder();
							temp.append(PREFIX + BOUNDARY + RETURN);
							temp.append("Content-Disposition:form-data; name=\""
									+ p[0] + "\"" + RETURN);
							temp.append(RETURN);
							temp.append(value);
							temp.append(RETURN);
							bos.write(temp.toString().getBytes("utf-8"));
						}
					}
				}
				StringBuilder temp = new StringBuilder();
				temp.append(PREFIX + BOUNDARY + RETURN);
				temp.append("Content-Disposition:form-data; name=\"pic\"; filename=\""
						+ "123456.jpg" + "\"" + RETURN);
				temp.append("Content-Type:image/jpeg" + RETURN + RETURN);

				bos.write(temp.toString().getBytes("utf-8"));
				char[] pics = mParam.getmParams().get("pic").toCharArray();
				byte pic[] = new byte[pics.length];
				for (int i = 0; i < pics.length; i++) {
					pic[i] = (byte) pics[i];
				}
				bos.write(pic);
				bos.write((BOUNDARY + RETURN).getBytes("utf-8"));

			}
			bos.write((PREFIX + BOUNDARY + PREFIX + RETURN).getBytes("utf-8"));
			data = bos.toByteArray();
			ByteArrayEntity formEntity = new ByteArrayEntity(data);
			post.setEntity(formEntity);
			bos.close();
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();

			if (statusCode != 200) {
				result = readHttpResponse(response);
				return null;
			}
			result = readHttpResponse(response);

		} catch (IOException e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * 转换编码格式
	 * @param s 需要转换的字符串
	 * @return 转换编码后的字符串
	 */
	public static String decode(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取HTTP响应函数
	 * @param response 发出网络请求后返回的HttpResponse
	 * @return 返回的数据流
	 */
	private static InputStream readHttpResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		InputStream inputStream = null;
		try {
			inputStream = entity.getContent();
			Header header = response.getFirstHeader("Content-Encoding");
			if (header != null
					&& header.getValue().toLowerCase().indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}

			return inputStream;
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		}
		return inputStream;
	}

	/**
	 * 继承PostMethod类，重写返回RequestCharSet为UTF-8
	 */
	public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			return "UTF-8";
		}
	}

	/**
	 *  AsyncTask方法重载
	 *  预执行
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	/**
	 *  AsyncTask方法重载
	 *  后台异步执行
	 */
	@Override
	protected Object doInBackground(Void... params) {
		try {
			Object result = this.runReq();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  AsyncTask方法重载
	 *  在任务执行成功时回调
	 */
	@Override
	protected void onPostExecute(Object result) {
		if (mCallBack != null) {
			mCallBack.onResult(result);
		}
		HttpService.getInstance().onReqFinish(this);
	}

	/**
	 *  AsyncTask方法重载
	 *  在任务成功取消时回调
	 */
	@Override
	protected void onCancelled() {
		if (mCallBack != null) {
			mCallBack.onResult(null);
		}
		HttpService.getInstance().onReqFinish(this);
	}
}
