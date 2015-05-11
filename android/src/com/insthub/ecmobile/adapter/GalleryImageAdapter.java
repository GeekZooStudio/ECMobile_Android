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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.insthub.ecmobile.R;

public class GalleryImageAdapter extends PagerAdapter {

	private LayoutInflater mInflater;
    private Context context;
    public Handler parentHandler;
    
	LayoutInflater inflater;

    public GalleryImageAdapter(Context context)
    {
        mInflater =  LayoutInflater.from(context);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
	
	@Override
	public int getCount() {
		 
		return 5;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
    	
    	final ViewHolder holder;
		
		holder = new ViewHolder();
		View imageLayout = mInflater.inflate(R.layout.gallery_image_item, null);
			
		holder.image = (LinearLayout) imageLayout.findViewById(R.id.gallery_image_item_view);
		
		if(position == 4) {
			holder.image.setEnabled(true);
		} else {
			holder.image.setEnabled(false);
		}
		if(position == 0) {
			holder.image.removeAllViews();
			View view0 = inflater.inflate(R.layout.lead_a, null);
			holder.image.addView(view0);
		} else if (position == 1) {
			holder.image.removeAllViews();
			View view1 = inflater.inflate(R.layout.lead_b, null);
			holder.image.addView(view1);
		} else if (position == 2) {
			holder.image.removeAllViews();
			View view2 = inflater.inflate(R.layout.lead_c, null);
			holder.image.addView(view2);
		} else if (position == 3) {
			holder.image.removeAllViews();
			View view3 = inflater.inflate(R.layout.lead_d, null);
			holder.image.addView(view3);
		} else if (position == 4) {
			holder.image.removeAllViews();
			View view4 = inflater.inflate(R.layout.lead_e, null);
			holder.image.addView(view4);
		} 
		
    	((ViewPager) view).addView(imageLayout, 0);
    	
        return imageLayout;
    }
    
    class ViewHolder {
		private LinearLayout image;
	}

}
