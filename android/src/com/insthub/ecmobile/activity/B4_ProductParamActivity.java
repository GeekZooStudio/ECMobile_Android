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

import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.B4_ProductParamAdapter;
import com.insthub.ecmobile.model.GoodDetailDraft;

public class B4_ProductParamActivity extends BaseActivity implements BusinessResponse
{
    private ListView propertyListView;
    private B4_ProductParamAdapter listAdapter;

    private TextView title;
    private ImageView back;
    private View null_paView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b4_product_param);

        Resources resource = (Resources) getBaseContext().getResources();
        String gooddetail_parameter = resource.getString(R.string.gooddetail_parameter);
        
        title = (TextView) findViewById(R.id.top_view_text);
        title.setText(gooddetail_parameter);
        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {                
                finish();
            }
        });
        null_paView = findViewById(R.id.null_pager);
        propertyListView = (ListView)findViewById(R.id.property_list);

        if(GoodDetailDraft.getInstance().goodDetail.properties.size() > 0) {
        	propertyListView.setVisibility(View.VISIBLE);
        	listAdapter = new B4_ProductParamAdapter(this, GoodDetailDraft.getInstance().goodDetail.properties);
            propertyListView.setAdapter(listAdapter);
        } else {
        	propertyListView.setVisibility(View.GONE);
            null_paView.setVisibility(View.VISIBLE);
        }
        
        
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }
}
