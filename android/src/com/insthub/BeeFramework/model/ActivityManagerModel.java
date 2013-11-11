package com.insthub.BeeFramework.model;

import android.content.Context;
import com.insthub.BeeFramework.activity.BaseActivity;

import java.util.ArrayList;

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
public class ActivityManagerModel extends BaseModel
{
    public static ArrayList <BaseActivity> liveActivityList = new ArrayList<BaseActivity>();
    public static ArrayList <BaseActivity> visibleActivityList = new ArrayList<BaseActivity>();
    public static ArrayList <BaseActivity> foregroundActivityList = new ArrayList<BaseActivity>();


    public ActivityManagerModel(Context context)
    {
        super(context);
    }

    public static void addLiveActivity(BaseActivity baseActivity)
    {
       if (!liveActivityList.contains(baseActivity))
       {
           liveActivityList.add(baseActivity);
       }
    }

    public static void removeLiveActivity(BaseActivity baseActivity)
    {
        liveActivityList.remove(baseActivity);
        visibleActivityList.remove(baseActivity);
        foregroundActivityList.remove(baseActivity);
    }


    public static void addVisibleActivity(BaseActivity baseActivity)
    {
        if (!visibleActivityList.contains(baseActivity))
        {
            visibleActivityList.add(baseActivity);
        }
    }

    public static void removeVisibleActivity(BaseActivity baseActivity)
    {
        visibleActivityList.remove(baseActivity);
    }

    public static void addForegroundActivity(BaseActivity baseActivity)
    {
        if (!foregroundActivityList.contains(baseActivity))
        {
            foregroundActivityList.add(baseActivity);
        }
    }

    public static void removeForegroundActivity(BaseActivity baseActivity)
    {
        foregroundActivityList.remove(baseActivity);
    }

}
