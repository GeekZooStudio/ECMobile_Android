package com.insthub.ecmobile.fragment;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.LoginActivity;
import com.insthub.ecmobile.model.ShoppingCartModel;

public class TabsFragment extends Fragment
{

    ImageView tab_one;
    ImageView tab_two;
    ImageView tab_three;
    ImageView tab_four;

    ImageView tab_onebg;
    ImageView tab_twobg;
    ImageView tab_threebg;
    ImageView tab_fourbg;
    
    private static TextView shopping_cart_num;
    private static LinearLayout shopping_cart_num_bg;
    
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    ShoppingCartFragment shoppingCartFragment;
    ProfileFragment profileFragment;

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
    	
    	this.shopping_cart_num = (TextView) mainView.findViewById(R.id.shopping_cart_num);
    	this.shopping_cart_num_bg = (LinearLayout) mainView.findViewById(R.id.shopping_cart_num_bg);

        this.tab_one = (ImageView) mainView.findViewById(R.id.toolbar_tabone);
        this.tab_onebg = (ImageView) mainView.findViewById(R.id.toolbar_tabonebg);
        this.tab_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_one");
            }
        });

        this.tab_two = (ImageView) mainView.findViewById(R.id.toolbar_tabtwo);
        this.tab_twobg = (ImageView) mainView.findViewById(R.id.toolbar_tabtwobg);
        this.tab_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_two");
            }
        });

        this.tab_three = (ImageView) mainView.findViewById(R.id.toolbar_tabthree);
        this.tab_threebg = (ImageView) mainView.findViewById(R.id.toolbar_tabthreebg);
        this.tab_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_three");
            }
        });

        this.tab_four = (ImageView) mainView.findViewById(R.id.toolbar_tabfour);
        this.tab_fourbg = (ImageView) mainView.findViewById(R.id.toolbar_tabfourbg);
        this.tab_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTabSelected("tab_four");
            }
        });

        OnTabSelected("tab_one");
    }

	void OnTabSelected(String tabName) {
        if (tabName == "tab_one") {

            if (null == homeFragment)
            {
                homeFragment = new HomeFragment();
            }

            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
            localFragmentTransaction.replace(R.id.fragment_container, homeFragment, "tab_one");
            localFragmentTransaction.commit();

            this.tab_one.setImageResource(R.drawable.footer_home_active_icon);
            this.tab_two.setImageResource(R.drawable.footer_search_icon);
            this.tab_three.setImageResource(R.drawable.footer_shopping_cart_icon);
            this.tab_four.setImageResource(R.drawable.footer_user_icon);

            this.tab_onebg.setVisibility(View.VISIBLE);
            this.tab_twobg.setVisibility(View.INVISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);

        }
        else if (tabName == "tab_two")
        {
            
                searchFragment = new SearchFragment();
            

            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
            localFragmentTransaction.replace(R.id.fragment_container, searchFragment, "tab_two");
            localFragmentTransaction.commit();
        	

            this.tab_one.setImageResource(R.drawable.footer_home_icon);
            this.tab_two.setImageResource(R.drawable.footer_search_active_icon);
            this.tab_three.setImageResource(R.drawable.footer_shopping_cart_icon);
            this.tab_four.setImageResource(R.drawable.footer_user_icon);

            this.tab_onebg.setVisibility(View.INVISIBLE);
            this.tab_twobg.setVisibility(View.VISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);
        }
        else if (tabName == "tab_three")
        {
//            String uid = shared.getString("uid", "");
//            if(uid.equals(""))
//            {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivityForResult(intent, 2);
//                getActivity().overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
//            }
//            else
//            {

                
                    shoppingCartFragment = new ShoppingCartFragment();
                

                FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
                localFragmentTransaction.replace(R.id.fragment_container, shoppingCartFragment, "tab_three");
                localFragmentTransaction.commit();

                this.tab_one.setImageResource(R.drawable.footer_home_icon);
                this.tab_two.setImageResource(R.drawable.footer_search_icon);
                this.tab_three.setImageResource(R.drawable.footer_shoppingcart_active_icon);
                this.tab_four.setImageResource(R.drawable.footer_user_icon);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.VISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);
//           }
        }
        else if (tabName == "tab_four")
        {
//        	//判断是否已登录，如果登录跳到个人主页，否则跳到登录页面，
//        	String uid = shared.getString("uid", "");
//        	if(uid.equals("")) {
//        		Intent intent = new Intent(getActivity(), LoginActivity.class);
//            	startActivityForResult(intent, 1);
//            	getActivity().overridePendingTransition(R.anim.push_buttom_in,R.anim.push_buttom_out);
//        	} else {

                
                    profileFragment = new ProfileFragment();
                


            	FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
            	localFragmentTransaction.replace(R.id.fragment_container, profileFragment, "tab_four");
            	localFragmentTransaction.commit();
          	
            	this.tab_one.setImageResource(R.drawable.footer_home_icon);
            	this.tab_two.setImageResource(R.drawable.footer_search_icon);
            	this.tab_three.setImageResource(R.drawable.footer_shopping_cart_icon);
            	this.tab_four.setImageResource(R.drawable.footer_user_active_icon);
  
            	this.tab_onebg.setVisibility(View.INVISIBLE);
            	this.tab_twobg.setVisibility(View.INVISIBLE);
            	this.tab_threebg.setVisibility(View.INVISIBLE);
            	this.tab_fourbg.setVisibility(View.VISIBLE);
//        	}
        	
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	//登录成功返回到个人主页
		if (requestCode == 1) {
			if (data != null) {
                if (null == profileFragment)
                {
                    profileFragment = new ProfileFragment();
                }

				FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
				localFragmentTransaction.replace(R.id.fragment_container,profileFragment, "tab_four");
				localFragmentTransaction.commit();

				this.tab_one.setImageResource(R.drawable.footer_home_icon);
				this.tab_two.setImageResource(R.drawable.footer_search_icon);
				this.tab_three.setImageResource(R.drawable.footer_shopping_cart_icon);
				this.tab_four.setImageResource(R.drawable.footer_user_active_icon);

				this.tab_onebg.setVisibility(View.INVISIBLE);
				this.tab_twobg.setVisibility(View.INVISIBLE);
				this.tab_threebg.setVisibility(View.INVISIBLE);
				this.tab_fourbg.setVisibility(View.VISIBLE);
			}
		}
        else if (requestCode == 2)
        {
            if (null != data)
            {
                if (null == shoppingCartFragment)
                {
                    shoppingCartFragment = new ShoppingCartFragment();
                }

                FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
                localFragmentTransaction.replace(R.id.fragment_container, shoppingCartFragment, "tab_three");
                localFragmentTransaction.commit();

                this.tab_one.setImageResource(R.drawable.footer_home_icon);
                this.tab_two.setImageResource(R.drawable.footer_search_icon);
                this.tab_three.setImageResource(R.drawable.footer_shoppingcart_active_icon);
                this.tab_four.setImageResource(R.drawable.footer_user_icon);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.VISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);
            }

        }
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	setShoppingcartNum();
    }
    
    public static void setShoppingcartNum() {
    	//shopping_cart_num.setText("12");
    	if(ShoppingCartModel.getInstance().goods_num == 0) {
    		shopping_cart_num_bg.setVisibility(View.GONE);
        } else {
        	shopping_cart_num_bg.setVisibility(View.VISIBLE);
        	shopping_cart_num.setText(ShoppingCartModel.getInstance().goods_num+"");
        }
    }
    
    
}