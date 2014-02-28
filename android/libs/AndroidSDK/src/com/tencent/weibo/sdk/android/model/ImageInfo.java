package com.tencent.weibo.sdk.android.model;
import android.graphics.drawable.Drawable;
/**
 * 图片相关 ，包括视频缩略图信息
 * 
 */
public class ImageInfo extends BaseVO{
	private static final long serialVersionUID = 2647179822312867756L;
	
	private String imagePath;//图片路径
	
	private String imageName;//图片名称

	private Drawable drawable;//图片 drawable对象
	
	private String playPath;
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	} 
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	public String getPlayPath() {
		return playPath;
	}
	public void setPlayPath(String playPath) {
		this.playPath = playPath;
	}
	
}
