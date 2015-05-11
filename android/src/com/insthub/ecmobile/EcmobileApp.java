package com.insthub.ecmobile;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import com.insthub.BeeFramework.BeeFrameworkApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class EcmobileApp extends BeeFrameworkApp
{
    public static DisplayImageOptions options;		// DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_head;		// DisplayImageOptions是用于设置图片显示的类
    @Override
    public void onCreate() {
        super.onCreate();


        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.default_image)			// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image)	// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image)		// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                        //.displayer(new RoundedBitmapDisplayer(20))	// 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.profile_no_avarta_icon)			// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.profile_no_avarta_icon)	// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.profile_no_avarta_icon)		// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                        //.displayer(new RoundedBitmapDisplayer(30))	// 设置成圆角图片
                .build();
    }
}