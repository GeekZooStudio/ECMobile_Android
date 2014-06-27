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
import java.util.List;

import com.insthub.BeeFramework.activity.BaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.insthub.ecmobile.adapter.G1_HelpAdapter;
import com.insthub.ecmobile.protocol.ARTICLE;
import com.insthub.ecmobile.protocol.SHOPHELP;

public class G1_HelpActivity extends BaseActivity {
	private TextView title;
	private ImageView back;
	private ListView listView;
	private G1_HelpAdapter helpAdapter;
	private List<SHOPHELP> list_help = new ArrayList<SHOPHELP>();
	private List<ARTICLE> list_article = new ArrayList<ARTICLE>();
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g1_help);
		
		title = (TextView) findViewById(R.id.top_view_text);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		
		
		Intent intent = getIntent();
		String s = intent.getStringExtra("data");
		position = intent.getIntExtra("position", 0);

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
                        SHOPHELP help_Item = new SHOPHELP();
                        help_Item.fromJson(contentJsonObject);
                        list_help.add(help_Item);
                    }
                }

            } catch (JSONException e) {                
                e.printStackTrace();
            }
        }
		
		list_article = list_help.get(position).article;
		title.setText(list_help.get(position).name);
		
		listView = (ListView) findViewById(R.id.help_list);
		helpAdapter = new G1_HelpAdapter(this,list_article);
		listView.setAdapter(helpAdapter);
        if (list_article.size() == 0)
        {
            listView.setVisibility(View.GONE);
        }
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				Intent intent = new Intent(G1_HelpActivity.this, HelpWebActivity.class);
				intent.putExtra("id", Integer.parseInt(list_article.get(position).id));
				intent.putExtra("title", list_article.get(position).title);
				startActivity(intent);
				
			}
		});
		
	}
}
