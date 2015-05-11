package com.insthub.BeeFramework.view;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

	private HashMap<String, SoftReference<Bitmap>> imageCache;

	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * 载入图片资源，如果缓存中不存在图片资源，则从本地加载，本地没有则从网络下载
	 * 
	 * @param imageUrl
	 * @param imageCallback
	 * @param localPath 缓存到本地的图片
	 * @return
	 */
	public Bitmap loadDrawable(final String imageUrl,final String localPath,final ImageCallback imageCallback) {
		final String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			Bitmap drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		File file = new File(localPath+fileName);
//		System.out.println("文件"+(localPath+fileName)+file.exists());
		if (file.exists()) {
			System.out.println("SD卡存在图片文件："+file.getAbsolutePath());
			Bitmap drawable = BitmapFactory.decodeFile(localPath+fileName);
			imageCache.put(imageUrl, new SoftReference<Bitmap>(drawable));
			return drawable;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (imageCallback != null) {
					imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				if (imageUrl != null) {
					String localFilePath = saveUrlAsFile(imageUrl,localPath);
					System.out.println("异步下载AsyncImageLoader*******"+imageUrl);
					System.out.println("异步下载AsyncImageLoader*******"+localPath);
					if(localFilePath!=null){
						Bitmap drawable = BitmapFactory.decodeFile(localPath+fileName);
						imageCache.put(imageUrl, new SoftReference<Bitmap>(drawable));
						Message msg = handler.obtainMessage(0, drawable);
						handler.sendMessage(msg);
					}else{
//						System.out.println("异步下载的图片为空!");
					}
//					Bitmap drawable = loadImageFromUrl(imageUrl);
//					imageCache.put(imageUrl,new SoftReference<Bitmap>(drawable));
//					Message msg = handler.obtainMessage(0, drawable);
//					handler.sendMessage(msg);
				}
			}
		}.start();
		return null;
	}

	public Bitmap loadImageFromUrl(String url) {
		URL m = null;
		InputStream i = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream out = null;
		Bitmap bitmap = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			bis = new BufferedInputStream(i, 1024 * 8);
			out = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			byte[] data = out.toByteArray();
			BitmapFactory.Options opts = new BitmapFactory.Options();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
			out.close();
			bis.close();
			i.close();
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把网络上的图片下载并存储到本地磁盘
	 * @param fileUrl
	 * @param savePath
	 * @return
	 */
	public static String saveUrlAsFile(String fileUrl, String savePath)/*fileUrl网络资源地址*/
    {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
		File dir = new File(savePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(savePath+fileName);
		if(!file.exists()){
			try {
				file.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
         try
         {
            URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/
            /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            /*此处也可用BufferedInputStream与BufferedOutputStream*/
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
            byte[] buffer = new byte[4096];
            int count = 0;
            int num = 0;
            while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/
            {
            	num += count;
                out.write(buffer, 0, count);
            }
            out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/
            in.close();
            connection.disconnect();
            if(num==0){
            	file.delete();
            	return null;
            }
            return savePath+fileName;/*网络资源截取并存储本地成功返回true*/
        }
        catch (Exception e)
        {
             System.out.println(e + fileUrl + savePath);
             return null;
        }
    }

	public Bitmap loadImageFromUrl2(String url) {
		URL m;
		InputStream i = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream out = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			bis = new BufferedInputStream(i, 1024 * 8);
			out = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			byte[] data = out.toByteArray();
			BitmapFactory.Options opts = new BitmapFactory.Options();

			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			out.close();
			bis.close();
			i.close();
			// Drawable d = Drawable.createFromStream(i, "src");
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 从SD卡读取Android文件
	 * 
	 * @param url
	 */
	public void loadImageFromSDCard(String url) {

	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}
}