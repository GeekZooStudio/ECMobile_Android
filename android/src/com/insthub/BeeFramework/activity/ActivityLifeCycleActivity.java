package com.insthub.BeeFramework.activity;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.adapter.ActivitylifeCycleAdapter;
import com.insthub.BeeFramework.model.ActivityManagerModel;
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
public class ActivityLifeCycleActivity extends BaseActivity{
    XListView listView;
    ActivityManagerModel dataModel;
    ActivitylifeCycleAdapter listAdapter;
    TextView activityAll;
    TextView activityVisible;
    TextView activityForeground;
    TextView topview_title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_activity);

        topview_title = (TextView)findViewById(R.id.navigationbar_title);
        Resources resource = (Resources) getBaseContext().getResources();
        String lif=resource.getString(R.string.life_cycle);
        topview_title.setText(lif);


        listView = (XListView)findViewById(R.id.activitylist);
        dataModel = new ActivityManagerModel(this);
        listAdapter = new ActivitylifeCycleAdapter(this,dataModel.liveActivityList);

        listView.setAdapter(listAdapter);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);

        activityAll = (TextView)findViewById(R.id.activity_all);
        activityAll.setTextColor(ColorStateList.valueOf(Color.RED));
        activityAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activityAll.setTextColor(ColorStateList.valueOf(Color.RED));
                activityVisible.setTextColor(ColorStateList.valueOf(Color.BLACK));
                activityForeground.setTextColor(ColorStateList.valueOf(Color.BLACK));

                listAdapter.dataList = ActivityLifeCycleActivity.this.dataModel.liveActivityList;
                listAdapter.notifyDataSetChanged();
            }
        });
        activityVisible = (TextView)findViewById(R.id.activity_visible);
        activityVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activityAll.setTextColor(ColorStateList.valueOf(Color.BLACK));
                activityVisible.setTextColor(ColorStateList.valueOf(Color.RED));
                activityForeground.setTextColor(ColorStateList.valueOf(Color.BLACK));

                listAdapter.dataList = ActivityLifeCycleActivity.this.dataModel.visibleActivityList;
                listAdapter.notifyDataSetChanged();
            }
        });
        activityForeground = (TextView)findViewById(R.id.activity_foreground);
        activityForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activityAll.setTextColor(ColorStateList.valueOf(Color.BLACK));
                activityVisible.setTextColor(ColorStateList.valueOf(Color.BLACK));
                activityForeground.setTextColor(ColorStateList.valueOf(Color.RED));

                listAdapter.dataList = ActivityLifeCycleActivity.this.dataModel.foregroundActivityList;
                listAdapter.notifyDataSetChanged();
            }
        });

    }
}
