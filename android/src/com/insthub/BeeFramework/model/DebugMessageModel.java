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
package com.insthub.BeeFramework.model;

import android.content.Context;
import com.external.androidquery.callback.AjaxCallback;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author mc374
 *
 */
public class DebugMessageModel extends BaseModel {

	public static Stack<BeeCallback> messageList = new Stack<BeeCallback>();
    public static ArrayList<BeeCallback> sendingmessageList = new ArrayList<BeeCallback>();
	
    
	public DebugMessageModel(Context context) {
		super(context);
		 
	}
	
	public static void addMessage(BeeCallback msg)
	{
		
		messageList.push(msg);
        sendingmessageList.add(msg);
        
	}

    public static void finishMessage(BeeCallback msg)
    {
        long currentTimestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH时mm分ss秒");
        msg.endTimeStamp = sdf.format(new Date(currentTimestamp));

        sendingmessageList.remove(msg);
    }

    public static boolean isSendingMessage(String url)
    {
        for (int i = 0; i < sendingmessageList.size(); i++)
        {
        	AjaxCallback msg = sendingmessageList.get(i);
            if(msg.getUrl().endsWith(url) )
            {
                return true;
            }
        }

        return false;
    }

}
