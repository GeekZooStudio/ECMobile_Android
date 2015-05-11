package com.insthub.ecmobile.activity;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.content.res.Resources;
import android.widget.AdapterView;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.adapter.C6_RedEnvelopeAdapter;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.BONUS;
import com.insthub.ecmobile.protocol.flowcheckOrderResponse;
import com.umeng.analytics.MobclickAgent;
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

public class C6_RedEnvelopeActivity extends BaseActivity implements BusinessResponse {

	private ImageView back;
	private Button submit;
	
	private EditText input;
	
	private ShoppingCartModel shoppingCartModel;
    XListView redPacketsList;

    C6_RedEnvelopeAdapter redPacketsAdapter;
    ArrayList<BONUS> dataList = new ArrayList<BONUS>();

    BONUS selectedBONUS = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c6_red_envelope);

		shoppingCartModel = new ShoppingCartModel(this);
		shoppingCartModel.addResponseListener(this);
		
		back = (ImageView) findViewById(R.id.red_papaer_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
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


        redPacketsAdapter = new C6_RedEnvelopeAdapter(this,dataList);

        redPacketsList.setAdapter(redPacketsAdapter);
		
		submit = (Button) findViewById(R.id.red_papaer_submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				if(null == selectedBONUS) {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String red=resource.getString(R.string.redpaper);				
					ToastView toast = new ToastView(C6_RedEnvelopeActivity.this, red);
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.show();
				}
                else
                {					
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
                flowcheckOrderResponse response = new flowcheckOrderResponse();
                response.fromJson(jo);
                if (null != response.data.bonus && response.data.bonus.size() > 0)
                {
                    dataList.clear();
                    dataList.addAll(response.data.bonus);
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
		if(url.endsWith(ApiInterface.VALIDATE_BONUS))
        {

		}
		
	}
}
