package com.insthub.ecmobile.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.insthub.BeeFramework.Utils.Utils;
import com.insthub.ecmobile.R;

import java.util.Timer;
import java.util.TimerTask;

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
public class MoveBackgroundLayout extends FrameLayout
{
    Bitmap backgroundBitmap;
    int    backgoundWidth;
    int    backgoundHeight;
    int    screenWidth;
    int    screenHeight;
    private int startX = 0 ;
    public MoveBackgroundLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tuitional_carousel);

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        screenWidth = dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

       //backgroundBitmap = Utils.scaleBitmap(backgroundBitmap,screenHeight,screenWidth*5);

        backgoundWidth = screenWidth*5;
        backgoundHeight = screenHeight;

        startX =  0;
    }

    public void setOffsetX(float offset)
    {
        startX = -(int)(offset*1.0*(backgoundWidth - screenWidth));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //Bitmap b2 = Bitmap.createBitmap(backgroundBitmap, startX, 0, screenWidth, backgoundHeight);
        //canvas.drawBitmap(backgroundBitmap, startX, 0, null);
        super.onDraw(canvas);
        //b2.recycle();
    }
}
