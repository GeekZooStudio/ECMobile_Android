package com.insthub.BeeFramework.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.insthub.ecmobile.R;

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

public class DebugTabActivity extends TabActivity {

	private TabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_home_tab);
		
		tabHost = getTabHost();
		
		
		TabSpec spec_tab1 = tabHost.newTabSpec("spec_tab1").setIndicator("spec_tab1")
				.setContent(new Intent(DebugTabActivity.this,DebugMessageListActivity.class));
		tabHost.addTab(spec_tab1);
		
		TabSpec spec_tab2 = tabHost.newTabSpec("spec_tab2").setIndicator("spec_tab2")
				.setContent(new Intent(DebugTabActivity.this,ActivityLifeCycleActivity.class));
		tabHost.addTab(spec_tab2);
		
		TabSpec spec_tab3 = tabHost.newTabSpec("spec_tab3").setIndicator("spec_tab3")
				.setContent(new Intent(DebugTabActivity.this,CrashLogActivity.class));
		tabHost.addTab(spec_tab3);
		
		TabSpec spec_tab4 = tabHost.newTabSpec("spec_tab4").setIndicator("spec_tab4")
				.setContent(new Intent(DebugTabActivity.this,FloatViewSettingActivity.class));
		tabHost.addTab(spec_tab4);
		
		
		RadioGroup group = (RadioGroup) this.findViewById(R.id.tab_group);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 
				switch(checkedId) {
				case R.id.tab_one:
					tabHost.setCurrentTabByTag("spec_tab1");
					break;
				case R.id.tab_two:
					tabHost.setCurrentTabByTag("spec_tab2");
					break;
				case R.id.tab_three:
					tabHost.setCurrentTabByTag("spec_tab3");
					break;
				case R.id.tab_four:
					tabHost.setCurrentTabByTag("spec_tab4");
					break;
				
				}
			}
		});
	}
}
