package com.insthub.ecmobile.adapter;
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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.insthub.BeeFramework.activity.FullScreenPhotoActivity;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B3_ProductPhotoActivity;
import com.insthub.ecmobile.protocol.PHOTO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class B3_ProductPhotoAdapter  extends BeeBaseAdapter{
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected class GoodDetailPhotoHolder extends BeeCellHolder
    {
        public ImageView goodPhotoImageView;
    }

    public B3_ProductPhotoAdapter(Context c, ArrayList<PHOTO> dataList) {
        super(c, dataList);
    }

    @Override
    protected BeeBaseAdapter.BeeCellHolder createCellHolder(View cellView) {
        GoodDetailPhotoHolder holder = new GoodDetailPhotoHolder();
        holder.goodPhotoImageView = (ImageView)cellView.findViewById(R.id.good_photo);
        return holder;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }

    public int getItemVIewType(int position)
    {
        return 0;
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent,
                            BeeBaseAdapter.BeeCellHolder h) {
        PHOTO photo = (PHOTO)dataList.get(position);
        GoodDetailPhotoHolder holder = (GoodDetailPhotoHolder)h;
        
        shared = mContext.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");
		
		if(imageType.equals("high")) {
            imageLoader.displayImage(photo.thumb,holder.goodPhotoImageView, EcmobileApp.options);
		} else if(imageType.equals("low")) {
            imageLoader.displayImage(photo.small,holder.goodPhotoImageView, EcmobileApp.options);
		} else {
			String netType = shared.getString("netType", "wifi");
			if(netType.equals("wifi")) {
                imageLoader.displayImage(photo.thumb,holder.goodPhotoImageView, EcmobileApp.options);
			} else {
                imageLoader.displayImage(photo.small,holder.goodPhotoImageView, EcmobileApp.options);
			}
		}
                
        holder.goodPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext,B3_ProductPhotoActivity.class);
                it.putExtra("position", position);
                mContext.startActivity(it);

            }
        });
        
        return cellView;
    }

    @Override
    public View createCellView() {        
        return mInflater.inflate(R.layout.b2_product_photo_cell, null);
    }

}
