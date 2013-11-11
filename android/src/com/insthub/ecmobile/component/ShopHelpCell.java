package com.insthub.ecmobile.component;

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

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.HelpActivity;
import com.insthub.ecmobile.protocol.ARTICLE;
import com.insthub.ecmobile.protocol.SHOPHELP;


public class ShopHelpCell extends LinearLayout{

    Context mContext;
    TextView shophelp_content;
    LinearLayout shophelp_item;
    public List<ARTICLE> list;

    public ShopHelpCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void init()
    {
        if (null == shophelp_content)
        {
            shophelp_content = (TextView)findViewById(R.id.shophelp_content);
        }
        
        if (null == shophelp_item)
        {
        	shophelp_item = (LinearLayout)findViewById(R.id.shophelp_item);
        }
    }

    public void bindData(final SHOPHELP shophelp, final Context context,final String data,final int realPosition)
    {
        init();
        shophelp_content.setText(shophelp.name);
        shophelp_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, HelpActivity.class);
				intent.putExtra("position", realPosition);
				intent.putExtra("data", data);
				context.startActivity(intent);
			
			}
		});
       
    }
}
