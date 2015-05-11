package com.insthub.BeeFramework.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import org.json.JSONException;
import org.json.JSONTokener;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */
public class Utils
{
    public static Object parseResponse(String responseBody) throws JSONException {
        Object result = null;
        //trim the string to prevent start with blank, and test if the string is valid JSON, because the parser don't do this :(. If Json is not valid this will return null
        responseBody = responseBody.trim();
        if(responseBody.startsWith("{") || responseBody.startsWith("[")) {
            result = new JSONTokener(responseBody).nextValue();
        }
        if (result == null) {
            result = responseBody;
        }
        return result;
    }

    public static Bitmap scaleBitmap(Bitmap bm,int pixel){
        int srcHeight = bm.getHeight();
        int srcWidth = bm.getWidth();


        if(srcHeight>pixel || srcWidth>pixel)
        {
            float scale_y = 0;
            float scale_x = 0;
            if (srcHeight > srcWidth)
            {
                scale_y = ((float)pixel)/srcHeight;
                scale_x = scale_y;
            }
            else
            {
                scale_x = ((float)pixel)/srcWidth;
                scale_y = scale_x;
            }

            Matrix  matrix = new Matrix();
            matrix.postScale(scale_x, scale_y);
            Bitmap dstbmp = Bitmap.createBitmap(bm, 0, 0, srcWidth, srcHeight, matrix, true);
            return dstbmp;
        }
        else{
            return Bitmap.createBitmap(bm);
        }
    }

    public static Bitmap scaleBitmap(Bitmap bm,int dstHeight,int dstWidth){
        if(bm == null) return null;//java.lang.NullPointerException
        int srcHeight = bm.getHeight();
        int srcWidth = bm.getWidth();
        if(srcHeight>dstHeight || srcWidth>dstWidth){
            float scale_y = ((float)dstHeight)/srcHeight;
            float scale_x = ((float)dstWidth)/srcWidth;
            float scale = scale_y<scale_x?scale_y:scale_x;
            Matrix  matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap dstbmp = Bitmap.createBitmap(bm, 0, 0, srcWidth, srcHeight, matrix, true);
            return dstbmp;
        }
        else{
            return Bitmap.createBitmap(bm);
        }
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try
        {
            // ---get the package info---
            PackageManager pm  = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }


}
