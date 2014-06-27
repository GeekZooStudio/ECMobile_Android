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

import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.protocol.ApiInterface;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.G2_InfoAdapter;
import com.insthub.ecmobile.model.HelpModel;
import com.insthub.ecmobile.model.ProtocolConst;

public class G2_InfoActivity extends BaseActivity implements BusinessResponse
{
    HelpModel dataModel;
    ListView helpListView;
    G2_InfoAdapter listAdapter;
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g2_info);

        helpListView = (ListView)findViewById(R.id.help_listview);
        dataModel = new HelpModel(this);
        dataModel.addResponseListener(this);
        dataModel.fetchShopHelp();
        listAdapter = new G2_InfoAdapter(this,dataModel);
        helpListView.setAdapter(listAdapter);

        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                
                finish();
            }
        });

        title = (TextView) findViewById(R.id.top_view_text);
        title.setText(R.string.help);

    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
    {
        if(url.endsWith(ApiInterface.SHOPHELP))
        {
            listAdapter.notifyDataSetChanged();
        }
    }
}
