package com.tencent.weibo.sdk.android.network;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
/**
 * 网络请求参数存储类
 * 
 * */
public class ReqParam {
	private Map<String, String> mParams = new HashMap<String, String>();//参数存储容器map
	public Bitmap mBitmap = null;//上传本地图片bitmap对象
	
	public void setBitmap(Bitmap bm){
		mBitmap = bm;
	}
		public Map<String, String> getmParams() {
		return mParams;
	}
 
	public void setmParams(Map<String, String> mParams) {
		this.mParams = mParams;
	}
       
		public void addParam(String key, String val)
		{
			mParams.put(key, val);
		}
		public void addParam(String key, byte[] val){
			double size = val.length / 1024;// KB
			if (size > 400) {// 进行压缩
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				double i = size / 400;
				Bitmap bitmap = zoomImage(mBitmap,
						mBitmap.getWidth() / Math.sqrt(i),
						mBitmap.getHeight() / Math.sqrt(i));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				val = baos.toByteArray();
			}
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < val.length; i++) {
				buffer.append((char)val[i]);
			}
			
			Log.d("buffer=======", buffer.toString());
			mParams.put(key, buffer.toString());
		}
		public void addParam(String key, Object val)
		{
			mParams.put(key, val.toString());
		}
		public String toString()
		{
			
			List<String> keyList = new ArrayList<String>();
			Iterator<String> it = mParams.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				keyList.add(key);
			}

			Collections.sort(keyList, new Comparator<String>() {
				public int compare(String str1, String str2) {
					if(str1.compareTo(str2)>0) {
						return 1;
					}
					if(str1.compareTo(str2)<0) {
						return -1;
					}
					return 0;
				}
			});
			StringBuffer strbuf = new StringBuffer();
			for(String key:keyList) {
				if (!key.equals("pic")) {
					strbuf.append(key);
					strbuf.append("=");
					strbuf.append(mParams.get(key));
					strbuf.append("&");
				}
				
			}
			Log.d("p-----", strbuf.toString());
			String p = strbuf.toString().replaceAll("\n", "").replaceAll("\r", "");
			
			return p;
		}
		/**
		 * 压缩图片
		 * */
		public Bitmap zoomImage(Bitmap bm, double newWidth, double newHeight) {

			// 获取这个图片的宽和高
			float width = bm.getWidth();
			float height = bm.getHeight();
			// 创建操作图片用的matrix对象
			Matrix matrix = new Matrix();
			// 计算宽高缩放率
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			// 缩放图片动作
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width,
					(int) height, matrix, true);
			return bitmap;
		}
		
}
