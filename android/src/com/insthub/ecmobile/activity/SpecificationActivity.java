package com.insthub.ecmobile.activity;

import android.content.res.Resources;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.SpecificationAdapter;
import com.insthub.ecmobile.model.GoodDetailDraft;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */
public class SpecificationActivity extends BaseActivity implements BusinessResponse
{
    private ListView specificationListView;
    private SpecificationAdapter listAdapter;
    private TextView title;
    private ImageView back;

    private ImageView minusImageView;
    private ImageView addImageView;
    private Button ok;
    private EditText quantityEditText;

    private TextView goodTotalPriceTextView;

    private View addItemComponent;
    private int num;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specification_activity);

        //this.setFinishOnTouchOutside(false);
        
        Intent intent = getIntent();
        num = intent.getIntExtra("num", 0);
        
//        title = (TextView) findViewById(R.id.top_view_text);
//        title.setText(GoodDetailDraft.getInstance().goodDetail.goods_name);
//        back = (ImageView) findViewById(R.id.top_view_back);
//        back.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                finish();
//            }
//        });
        specificationListView = (ListView)findViewById(R.id.specification_list);

        listAdapter = new SpecificationAdapter(this, GoodDetailDraft.getInstance().goodDetail.specification);
        
        addItemComponent = (View)LayoutInflater.from(this).inflate(R.layout.add_item_component,null);
        specificationListView.addFooterView(addItemComponent);
        
        specificationListView.setAdapter(listAdapter);
        //specificationListView.setPullLoadEnable(false);
        //specificationListView.setPullRefreshEnable(false);

        
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
            		//Toast.makeText(SpecificationActivity.this, und, 0).show();
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
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //To change body of implemented methods use File | Settings | File Templates.
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
				// TODO Auto-generated method stub
				
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
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.my_alpha_action,R.anim.my_scale_finish);
		}
		return true;
	}
	
	
	
}
