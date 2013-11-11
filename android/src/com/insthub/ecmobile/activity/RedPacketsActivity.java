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

import android.content.res.Resources;
import android.widget.AdapterView;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.adapter.RedPacketsAdapter;
import com.insthub.ecmobile.protocol.BONUS;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.ShoppingCartModel;

import java.util.ArrayList;

public class RedPacketsActivity extends BaseActivity implements BusinessResponse {

	private ImageView back;
	private Button submit;
	
	private EditText input;
	
	private ShoppingCartModel shoppingCartModel;
    XListView redPacketsList;

    RedPacketsAdapter redPacketsAdapter;
    ArrayList<BONUS> dataList = new ArrayList<BONUS>();

    BONUS selectedBONUS = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.red_paper);

		shoppingCartModel = new ShoppingCartModel(this);
		shoppingCartModel.addResponseListener(this);
		
		back = (ImageView) findViewById(R.id.red_papaer_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		input = (EditText) findViewById(R.id.red_paper_input);

        redPacketsList = (XListView)findViewById(R.id.red_packet_list);
        redPacketsList.setPullLoadEnable(false);
        redPacketsList.setPullRefreshEnable(false);

        redPacketsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                {
                    selectedBONUS = dataList.get(position -1);
                    redPacketsAdapter.setSelectedPosition(position);
                    redPacketsAdapter.notifyDataSetInvalidated();
                }

            }
        });


        redPacketsAdapter = new RedPacketsAdapter(this,dataList);

        redPacketsList.setAdapter(redPacketsAdapter);
		
		submit = (Button) findViewById(R.id.red_papaer_submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(null == selectedBONUS) {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String red=resource.getString(R.string.redpaper);
					//Toast toast = Toast.makeText(RedPacketsActivity.this, red, 0);
					ToastView toast = new ToastView(RedPacketsActivity.this, red);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				}
                else
                {
					//shoppingCartModel.bonus(selectedBONUS.bonus_id);
                    try
                    {
                        Intent intent = new Intent();
                        if (null != selectedBONUS)
                        {
                            intent.putExtra("bonus",selectedBONUS.toJson().toString());
                        }

                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                    catch (JSONException e)
                    {

                    }

				}
				
			}
		});

        Intent intent = getIntent();
        String payment = intent.getStringExtra("payment");

        if (null != payment)
        {
            try
            {
                JSONObject jo = new JSONObject(payment);
                JSONArray dataJsonArray = jo.optJSONArray("bonus");
                if (null != dataJsonArray && dataJsonArray.length() > 0)
                {
                    dataList.clear();
                    for (int i = 0; i < dataJsonArray.length(); i++)
                    {
                        JSONObject bonusJsonObject = dataJsonArray.getJSONObject(i);
                        BONUS bonus_list_Item = BONUS.fromJson(bonusJsonObject);
                        dataList.add(bonus_list_Item);
                    }
                }
                else
                {
                    redPacketsList.setVisibility(View.GONE);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(url.endsWith(ProtocolConst.VALIDATE_BONUS))
        {

		}
		
	}
	
}
