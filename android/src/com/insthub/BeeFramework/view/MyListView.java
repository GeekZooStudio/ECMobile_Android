package com.insthub.BeeFramework.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.external.maxwin.view.XListView;

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
public class MyListView extends XListView {

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
    public FrameLayout bannerView;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	
    	mGestureDetector = new GestureDetector(new YScrollDetector());

        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if(distanceY!=0&&distanceX!=0)
            {

            }

            if (null != bannerView)
            {
                Rect rect = new Rect();
                bannerView.getHitRect(rect);

                if (null != e1)
                {
                    if(rect.contains((int)e1.getX(),(int)e1.getY()))
                    {
                        return false;
                    }
                }

                if (null != e2)
                {
                    if(rect.contains((int)e2.getX(),(int)e2.getY()))
                    {
                        return false;
                    }
                }

            }
//            if(Math.abs(distanceY) >= Math.abs(distanceX))
//            {
//                Log.e("listview", "********************** distanceX :" + distanceX + "  distanceY" + distanceY + "\n");
//                return true;
//            }
//            Log.e("listview", "-------------------------- distanceX :" + distanceX + "  distanceY" + distanceY + "\n");
            return true;
        }
    }

}
