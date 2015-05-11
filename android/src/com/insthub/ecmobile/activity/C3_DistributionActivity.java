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

import java.util.ArrayList;

import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.C3_DistributionAdapter;
import com.insthub.ecmobile.protocol.PAYMENT;
import com.insthub.ecmobile.protocol.SHIPPING;

public class C3_DistributionActivity extends BaseActivity {

	private TextView title;
	private ImageView back;
	
	private ListView listView;
	
	private ArrayList<SHIPPING> list = new ArrayList<SHIPPING>();
	private C3_DistributionAdapter shippingAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c2_payment);
		
		Intent intent = getIntent();
		String s = intent.getStringExtra("payment");
		
		try{
            flowcheckOrderResponse response = new flowcheckOrderResponse();
            response.fromJson(new JSONObject(s));
			ArrayList<SHIPPING> shippings = response.data.shipping_list;
			if (null != shippings && shippings.size() > 0) {
				list.clear();
			    list.addAll(shippings);
        	}
		
		} catch (JSONException e) {			
			e.printStackTrace();
		}

        Resources resource = (Resources) getBaseContext().getResources();
        String way=resource.getString(R.string.balance_shipping);
		title = (TextView) findViewById(R.id.top_view_text);
		title.setText(way);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		
		listView = (ListView) findViewById(R.id.payment_list);

		if(list.size() > 0) {
			listView.setVisibility(View.VISIBLE);
			shippingAdapter = new C3_DistributionAdapter(this, list);
			listView.setAdapter(shippingAdapter);
		} else {
			listView.setVisibility(View.GONE);
            String noway=resource.getString(R.string.no_mode_of_distribution);			
			ToastView toast = new ToastView(this, noway);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
		}
		
		
		listView.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
            {				
				Intent intent = new Intent();
                SHIPPING shipping = list.get(position);
                try
                {
                    intent.putExtra("shipping",shipping.toJson().toString());
                }
                catch (JSONException e)
                {

                }

				setResult(Activity.RESULT_OK, intent);  
	            finish(); 
			}
		});
		
		
	}
}
