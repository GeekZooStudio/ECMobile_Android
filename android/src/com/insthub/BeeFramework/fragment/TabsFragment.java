package com.insthub.BeeFramework.fragment;

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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.insthub.ecmobile.R;


@SuppressLint("NewApi")
public class TabsFragment extends Fragment
{

    ImageButton tab_one;
    ImageButton tab_two;
    ImageButton tab_three;
    ImageButton tab_four;

    ImageView tab_onebg;
    ImageView tab_twobg;
    ImageView tab_threebg;
    ImageView tab_fourbg;
    
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    public TabsFragment() {
    }

    /*
     * (non-Javadoc)
     *
     * 
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.toolbar, container, false);
        init(mainView);
        
        shared = getActivity().getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
        
        return mainView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        setRetainInstance(true);
    }

    void init(View mainView) {

        this.tab_one = (ImageButton) mainView.findViewById(R.id.toolbar_tabone);
        this.tab_onebg = (ImageView) mainView.findViewById(R.id.toolbar_tabonebg);
        this.tab_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_one");
            }
        });

        this.tab_two = (ImageButton) mainView.findViewById(R.id.toolbar_tabtwo);
        this.tab_twobg = (ImageView) mainView.findViewById(R.id.toolbar_tabtwobg);
        this.tab_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_two");
            }
        });

        this.tab_three = (ImageButton) mainView.findViewById(R.id.toolbar_tabthree);
        this.tab_threebg = (ImageView) mainView.findViewById(R.id.toolbar_tabthreebg);
        this.tab_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_three");
            }
        });

        this.tab_four = (ImageButton) mainView.findViewById(R.id.toolbar_tabfour);
        this.tab_fourbg = (ImageView) mainView.findViewById(R.id.toolbar_tabfourbg);
        this.tab_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_four");
            }
        });

        OnTabSelected("tab_one");
    }

    @SuppressLint("NewApi")
	void OnTabSelected(String tabName) {
        if (tabName == "tab_one") {

            this.tab_onebg.setVisibility(View.VISIBLE);
            this.tab_twobg.setVisibility(View.INVISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);

        }
        else if (tabName == "tab_two")
        {

            this.tab_onebg.setVisibility(View.INVISIBLE);
            this.tab_twobg.setVisibility(View.VISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);
        }
        else if (tabName == "tab_three")
        {
            this.tab_onebg.setVisibility(View.INVISIBLE);
            this.tab_twobg.setVisibility(View.INVISIBLE);
            this.tab_threebg.setVisibility(View.VISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);
        }
        else if (tabName == "tab_four")
        {

            this.tab_onebg.setVisibility(View.INVISIBLE);
            this.tab_twobg.setVisibility(View.INVISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.VISIBLE);
        	
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);

    }
    
}