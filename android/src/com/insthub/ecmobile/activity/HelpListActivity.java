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

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.HelpListAdapter;
import com.insthub.ecmobile.model.HelpModel;
import com.insthub.ecmobile.model.ProtocolConst;



public class HelpListActivity extends BaseActivity
{
    HelpModel dataModel;
    ListView helpListView;
    HelpListAdapter listAdapter;
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_list);

        helpListView = (ListView)findViewById(R.id.help_listview);
        dataModel = new HelpModel(this);
        dataModel.fetchShopHelp();
        listAdapter = new HelpListAdapter(this,dataModel);
        helpListView.setAdapter(listAdapter);

        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        title = (TextView) findViewById(R.id.top_view_text);
        title.setText(R.string.help);

    }
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
    {
        if(url.endsWith(ProtocolConst.SHOPHELP))
        {
            listAdapter.notifyDataSetChanged();
        }
    }
   
}
