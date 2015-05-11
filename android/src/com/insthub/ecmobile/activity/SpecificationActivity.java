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

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.SpecificationAdapter;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;

public class SpecificationActivity extends Activity implements BusinessResponse
{
    private ListView specificationListView;
    private SpecificationAdapter listAdapter;
    private ImageView minusImageView;
    private ImageView addImageView;
    private Button ok;
    private EditText quantityEditText;
    private TextView goodTotalPriceTextView;
    private View addItemComponent;
    private int num;
    private boolean creat_cart;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specification_activity);
        
        Intent intent = getIntent();
        num = intent.getIntExtra("num", 0);
        creat_cart = intent.getBooleanExtra("creat_cart", false);

        specificationListView = (ListView)findViewById(R.id.specification_list);

        listAdapter = new SpecificationAdapter(this, GoodDetailDraft.getInstance().goodDetail.specification);
        
        addItemComponent = (View)LayoutInflater.from(this).inflate(R.layout.add_item_component,null);
        specificationListView.addFooterView(addItemComponent);
        
        specificationListView.setAdapter(listAdapter);
        
        goodTotalPriceTextView = (TextView)addItemComponent.findViewById(R.id.good_total_price);

        minusImageView = (ImageView)addItemComponent.findViewById(R.id.shop_car_item_min);
        minusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GoodDetailDraft.getInstance().goodQuantity - 1 > 0)
                {
                    GoodDetailDraft.getInstance().goodQuantity --;
                    quantityEditText.setText(String.valueOf(GoodDetailDraft.getInstance().goodQuantity));
                }
            }
        });

        addImageView = (ImageView)addItemComponent.findViewById(R.id.shop_car_item_sum);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(GoodDetailDraft.getInstance().goodQuantity>num-1) {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String und=resource.getString(R.string.understock);            		
            		ToastView toast = new ToastView(SpecificationActivity.this, und);
            		toast.setGravity(Gravity.CENTER, 0, 0);
            		toast.show();
            	} else {
            		GoodDetailDraft.getInstance().goodQuantity ++;
                    quantityEditText.setText(String.valueOf(GoodDetailDraft.getInstance().goodQuantity));
            	}
                
            }
        });

        quantityEditText = (EditText)addItemComponent.findViewById(R.id.shop_car_item_editNum);
        quantityEditText.setText(String.valueOf(GoodDetailDraft.getInstance().goodQuantity));
        quantityEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               
            }

            @Override
            public void afterTextChanged(Editable s) {
                String count = s.toString();
                if (count.length() > 0)
                {
                    GoodDetailDraft.getInstance().goodQuantity = Integer.valueOf(count).intValue();
                    refreshTotalPrice();
                }

            }
        });
        
        ok = (Button) findViewById(R.id.shop_car_item_ok);
        ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				if(creat_cart) {
					Intent intent = new Intent();
					setResult(Activity.RESULT_OK, intent);  
		            finish(); 
				}
				finish();
				overridePendingTransition(R.anim.my_alpha_action,R.anim.my_scale_finish);
				
			}
		});

        EventBus.getDefault().register(this);
        refreshTotalPrice();

    }
    
    void refreshTotalPrice()
    {
        Resources resource = (Resources) getBaseContext().getResources();
        String tot=resource.getString(R.string.total_price);
        String totolPrice =   tot+GoodDetailDraft.getInstance().getTotalPrice();
        goodTotalPriceTextView.setText(totolPrice);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    public void onEvent(Object event)
    {
        if (event.getClass() == SPECIFICATION_VALUE.class)
        {
            refreshTotalPrice();
        }
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.my_alpha_action,R.anim.my_scale_finish);
		}
		return true;
	}
}
