package com.insthub.ecmobile.adapter;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import com.insthub.BeeFramework.activity.FullScreenPhotoActivity;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.FullScreenViewPagerActivity;
import com.insthub.ecmobile.protocol.PHOTO;

import java.util.ArrayList;

public class GoodDetailPhotoAdapter  extends BeeBaseAdapter{
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    protected class GoodDetailPhotoHolder extends BeeCellHolder
    {
        public WebImageView goodPhotoImageView;
    }

    public GoodDetailPhotoAdapter(Context c, ArrayList<PHOTO> dataList) {
        super(c, dataList);
    }

    @Override
    protected BeeBaseAdapter.BeeCellHolder createCellHolder(View cellView) {
        GoodDetailPhotoHolder holder = new GoodDetailPhotoHolder();
        holder.goodPhotoImageView = (WebImageView)cellView.findViewById(R.id.good_photo);
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
			holder.goodPhotoImageView.setImageWithURL(mContext,photo.thumb,R.drawable.default_image);
		} else if(imageType.equals("low")) {
			holder.goodPhotoImageView.setImageWithURL(mContext,photo.small,R.drawable.default_image);
		} else {
			String netType = shared.getString("netType", "wifi");
			if(netType.equals("wifi")) {
				holder.goodPhotoImageView.setImageWithURL(mContext,photo.thumb,R.drawable.default_image);
			} else {
				holder.goodPhotoImageView.setImageWithURL(mContext,photo.small,R.drawable.default_image);
			}
		}
        
        //holder.goodPhotoImageView.setImageWithURL(mContext,photo.thumb,R.drawable.default_image);
        holder.goodPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext,FullScreenViewPagerActivity.class);
                it.putExtra("position", position);
                mContext.startActivity(it);

            }
        });
        
        return cellView;
    }

    @Override
    public View createCellView() {
        // TODO Auto-generated method stub
        return mInflater.inflate(R.layout.good_detail_imageview, null);
    }

}
