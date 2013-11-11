package com.insthub.ecmobile.activity;

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

import java.util.ArrayList;
import java.util.List;

import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.HelpAdapter;
import com.insthub.ecmobile.protocol.ARTICLE;
import com.insthub.ecmobile.protocol.SHOPHELP;

public class HelpActivity extends BaseActivity {
	private TextView title;
	private ImageView back;
	private ListView listView;
	private HelpAdapter helpAdapter;
	private List<SHOPHELP> list_help = new ArrayList<SHOPHELP>();
	private List<ARTICLE> list_article = new ArrayList<ARTICLE>();
	private int p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		title = (TextView) findViewById(R.id.top_view_text);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		Intent intent = getIntent();
		String s = intent.getStringExtra("data");
		p = intent.getIntExtra("position", 0);

        if (null != s && s.length() > 0)
        {
            try{
                JSONObject jo = new JSONObject(s);
                JSONArray contentArray = jo.optJSONArray("data");

                if (null != contentArray && contentArray.length() > 0)
                {
                    list_help.clear();
                    for (int i = 0; i < contentArray.length(); i++)
                    {
                        JSONObject contentJsonObject = contentArray.getJSONObject(i);
                        SHOPHELP help_Item = SHOPHELP.fromJson(contentJsonObject);
                        list_help.add(help_Item);
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		list_article = list_help.get(p).article;
		title.setText(list_help.get(p).name);
		
		listView = (ListView) findViewById(R.id.help_list);
		helpAdapter = new HelpAdapter(this,list_article);
		listView.setAdapter(helpAdapter);
        if (list_article.size() == 0)
        {
            listView.setVisibility(View.GONE);
        }

		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HelpActivity.this, HelpWebActivity.class);
				intent.putExtra("id", list_article.get(position).id);
				intent.putExtra("title", list_article.get(position).title);
				startActivity(intent);
				
			}
		});
		
	}

}
