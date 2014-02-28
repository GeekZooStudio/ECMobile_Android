package com.tencent.weibo.sdk.android.api.util;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
public class ImageLoaderAsync {
	/**
	 * 异步加载网络图片
	 * @param imagePath 图片url
	 * @param  callback 回调对象
	 * */
	public Drawable loadImage(final String imagePath,final callBackImage callback){
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				 
				callback.callback((Drawable)msg.obj, imagePath);
			}		
		};
		new Thread(){
			@Override
			public void run() {
				Drawable drawable = Util.loadImageFromUrl(imagePath);
				handler.sendMessage(handler.obtainMessage(0, drawable));
			}
		
		}.start();
		return null;
	} 
	/**
	 * 异步加载图片回调接口
	 * */
	public interface callBackImage{
		/*
		 * 回调方法
		 * @param Drawable  网络返回图片
		 * @param imagePath  图片url
		 * */
		public void callback(Drawable Drawable,String imagePath);
	}
}
