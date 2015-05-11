package com.insthub.ecmobile.activity;

import android.content.res.Resources;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.F3_RegionPickAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class F3_RegionPickActivity extends Activity implements BusinessResponse {

	private TextView title;
	private ListView listView;
	private F3_RegionPickAdapter spinnerAdapter;
	private AddressModel addressModel;
	private int i = 0;
	
	private String country_id="";
	private String province_id="";
	private String city_id="";
	private String county_id="";
	
	private String country_name="";
	private String province_name="";
	private String city_name="";
	private String county_name="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f3_region_pick);
		
		title = (TextView) findViewById(R.id.address_title);
		listView = (ListView) findViewById(R.id.address_list);
        Resources resource = (Resources) getBaseContext().getResources();
        String scoun=resource.getString(R.string.addressb_country );
        title.setText(scoun);
		
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		addressModel.region(0);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (i == 1) {
                    country_id = addressModel.regionsList0.get(position).id;
                    country_name = addressModel.regionsList0.get(position).name;
                } else if (i == 2) {
                    province_id = addressModel.regionsList0.get(position).id;
                    province_name = addressModel.regionsList0.get(position).name;
                } else if (i == 3) {
                    city_id = addressModel.regionsList0.get(position).id;
                    city_name = addressModel.regionsList0.get(position).name;
                } else if (i == 4) {
                    county_id = addressModel.regionsList0.get(position).id;
                    county_name = addressModel.regionsList0.get(position).name;
                }
                addressModel.region(Integer.parseInt(addressModel.regionsList0.get(position).id));

            }
        });
	}
    public void setCountry() {
        Resources resource = (Resources) getBaseContext().getResources();
        String spro=resource.getString(R.string.select_province ) ;
        String scity=resource.getString(R.string.select_city );
        String sarea=resource.getString(R.string.select_area );

		if(addressModel.regionsList0.size() == 0) {
			Intent intent = new Intent();
			intent.putExtra("country_id", country_id);
			intent.putExtra("province_id", province_id);
			intent.putExtra("city_id", city_id);
			intent.putExtra("county_id", county_id);
			
			intent.putExtra("country_name", country_name);
			intent.putExtra("province_name", province_name);
			intent.putExtra("city_name", city_name);
			intent.putExtra("county_name", county_name);
			setResult(Activity.RESULT_OK, intent);  
            finish(); 
		}
		i++;
		if(i == 2) {
			title.setText(spro);
		} else if(i == 3) {
			title.setText(scity);
		} else if(i == 4) {
			title.setText(sarea);
		}
		
		spinnerAdapter = new F3_RegionPickAdapter(this, addressModel.regionsList0); 
		listView.setAdapter(spinnerAdapter);
		
	}
	
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub
		if(url.endsWith(ApiInterface.REGION)) {
			//Toast.makeText(this, jo+"", 0).show();
			
			setCountry();
			
		}
	}

}
