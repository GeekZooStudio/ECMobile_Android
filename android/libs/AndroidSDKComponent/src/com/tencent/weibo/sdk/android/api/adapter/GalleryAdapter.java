package com.tencent.weibo.sdk.android.api.adapter;

import java.util.ArrayList;

import com.tencent.weibo.sdk.android.api.util.ImageLoaderAsync;
import com.tencent.weibo.sdk.android.api.util.ImageLoaderAsync.callBackImage;
import com.tencent.weibo.sdk.android.model.ImageInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class GalleryAdapter extends BaseAdapter {

			private ArrayList<ImageInfo> imageList ;
			private Context myContext;
			private ImageLoaderAsync imageLoader;
			private PopupWindow popView;
			
			public GalleryAdapter(Context context,PopupWindow loadingView,ArrayList<ImageInfo> images){
				this.myContext = context;
				this.imageList = images;
				imageLoader = new ImageLoaderAsync();
				popView = loadingView;
			}
			@Override
			public int getCount() {
				 
				return imageList.size();
			}
 
			@Override
			public Object getItem(int position) {
				 
				return imageList.get(position);
			}

			@Override
			public long getItemId(int position) {
				 
				return position;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				 
				final ImageView imageView = new ImageView(this.myContext);
				String imagePath = imageList.get(position).getImagePath();
				Drawable drawable = imageLoader.loadImage(imagePath, new callBackImage(){
					@Override
					public void callback(Drawable Drawable, String imagePath) {
						 
						if(Drawable != null){
							imageView.setImageDrawable(Drawable);
						}
					}
					
				});
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT,Gallery.LayoutParams.FILL_PARENT));
				if(popView!=null && popView.isShowing()){
					popView.dismiss();
				}
				return imageView;
			}

}
