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

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.protocol.flowcheckOrderResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.C4_InvoiceAdapter;
import com.insthub.ecmobile.protocol.INV_LIST;

public class C4_InvoiceActivity extends BaseActivity implements OnClickListener {

	private ImageView back;
	private Button save;
	
	private EditText taitou;
	
	private ListView listView_invoice_content;
	private ListView listView_invoice_type;
	
	private ArrayList<INV_LIST> list_content = new ArrayList<INV_LIST>();
	private ArrayList<INV_LIST> list_type = new ArrayList<INV_LIST>();
	
	private C4_InvoiceAdapter invoiceContentAdapter;
	private C4_InvoiceAdapter invoiceTypeAdapter;
	
	private int type_id =-1;
	private int content_id =-1;

	private String inv_payee = null; //发票抬头
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c4_invoice);
		
        listView_invoice_content = (ListView) findViewById(R.id.invoice_list_content);
        listView_invoice_type = (ListView) findViewById(R.id.invoice_list_type);

		Intent intent = getIntent();
		String s = intent.getStringExtra("payment");
		type_id = intent.getIntExtra("inv_type",-1);
		content_id = intent.getIntExtra("inv_content", -1);
		inv_payee = intent.getStringExtra("inv_payee");
		
		try{
            flowcheckOrderResponse response = new flowcheckOrderResponse();
            response.fromJson(new JSONObject(s));
		    ArrayList<INV_LIST> inv_lists = response.data.inv_content_list;

			if (null != inv_lists && inv_lists.size() > 0)
            {
			   list_content.clear();
			   list_content.addAll(inv_lists);

        	}
            else
            {
                listView_invoice_content.setVisibility(View.GONE);
            }

            ArrayList<INV_LIST> type_lists = response.data.inv_type_list;
			if (null != type_lists && type_lists.size()> 0)
            {
				list_type.addAll(type_lists);

        	}
            else
            {
                listView_invoice_type.setVisibility(View.GONE);
            }
		
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		back = (ImageView) findViewById(R.id.invoice_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
		
		save = (Button) findViewById(R.id.invoice_save);
		save.setOnClickListener(this);
		taitou = (EditText) findViewById(R.id.invoice_taitou);
		
		taitou.setText(inv_payee);
		
		invoiceContentAdapter = new C4_InvoiceAdapter(this, list_content, type_id);
		listView_invoice_content.setAdapter(invoiceContentAdapter);
		
		invoiceTypeAdapter = new C4_InvoiceAdapter(this, list_type, content_id);
		listView_invoice_type.setAdapter(invoiceTypeAdapter);
		
		listView_invoice_content.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                invoiceContentAdapter.flag = position;
                invoiceContentAdapter.notifyDataSetChanged();
                type_id = list_content.get(position).id;

            }
        });
		
		listView_invoice_type.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                invoiceTypeAdapter.flag = position;
                invoiceTypeAdapter.notifyDataSetChanged();
                content_id = list_type.get(position).id;
            }
        });
		
	}

	@Override
	public void onClick(View v) {		
		switch(v.getId()) {
		case R.id.invoice_save:
			Intent intent = new Intent();
			intent.putExtra("inv_type", type_id);
			intent.putExtra("inv_content", content_id);
			intent.putExtra("inv_payee", taitou.getText().toString());
			setResult(Activity.RESULT_OK, intent);  
            finish(); 
			break;
		}
		
	}
}
