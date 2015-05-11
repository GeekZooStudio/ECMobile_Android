package com.insthub.ecmobile.fragment;

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

import com.insthub.ecmobile.activity.AlixPayActivity;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.ORDER_INFO;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListViewCart;
import com.external.maxwin.view.XListViewCart.IXListViewListenerCart;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.F1_NewAddressActivity;
import com.insthub.ecmobile.activity.C1_CheckOutActivity;
import com.insthub.ecmobile.activity.PayWebActivity;
import com.insthub.ecmobile.adapter.C0_ShoppingCartAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.ShoppingCartModel;
/**
 * 购物车
 */
public class C0_ShoppingCartFragment extends BaseFragment implements BusinessResponse, IXListViewListenerCart{
	
	private View view;
	private View footerView;
	
	private TextView footer_total;
	private FrameLayout footer_balance;
	private FrameLayout shop_car_null;
	private FrameLayout shop_car_isnot;
	private ImageView cart_icon;
	
	private XListViewCart xlistView;
	private C0_ShoppingCartAdapter shopCarAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	
	private ShoppingCartModel shoppingCartModel;
    public  Handler messageHandler;
    private ImageView back;
    
    private AddressModel addressModel;
    private OrderModel orderModel;

    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
        final Resources resource = this.getResources();

		view = inflater.inflate(R.layout.c0_shopping_cart, null);
		
		shared = getActivity().getSharedPreferences("userInfo", 0); 
		editor = shared.edit();

		shop_car_null = (FrameLayout) view.findViewById(R.id.shop_car_null);
		shop_car_isnot = (FrameLayout) view.findViewById(R.id.shop_car_isnot);
		
		xlistView = (XListViewCart) view.findViewById(R.id.shop_car_list);
		xlistView.setPullLoadEnable(false);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		
		footerView = LayoutInflater.from(getActivity()).inflate(R.layout.c0_shopping_car_footer, null);
		footer_total = (TextView) footerView.findViewById(R.id.shop_car_footer_total);
		footer_balance = (FrameLayout) footerView.findViewById(R.id.shop_car_footer_balance);
		cart_icon = (ImageView) footerView.findViewById(R.id.shop_car_footer_balance_cart_icon);
		
		xlistView.addFooterView(footerView);
		
		addressModel = new AddressModel(getActivity());
		addressModel.addResponseListener(this);

		footer_balance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				addressModel.getAddressList();
				
				
			}
		});

        if (null == shoppingCartModel)
        {
            shoppingCartModel = new ShoppingCartModel(getActivity());
        }
		
        String uid = shared.getString("uid", "");
		if (uid.equals("")) {
			shop_car_null.setVisibility(View.VISIBLE);
			shop_car_isnot.setVisibility(View.GONE);
		} else {
			shoppingCartModel.addResponseListener(this);
			shoppingCartModel.cartList();
		}
        
        messageHandler = new Handler(){

            public void handleMessage(Message msg) {

                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_REMOVE) {
                    Integer rec_id = (Integer) msg.arg1;
                    shoppingCartModel.deleteGoods(rec_id);
                }
                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_MODIFY) {
                    int rec_id =  msg.arg1;
                    int new_number = msg.arg2;
                    shoppingCartModel.updateGoods(rec_id, new_number);
                } 
                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_CHANGE1) {
                	
                	footer_balance.setEnabled(false);
                	footer_balance.setBackgroundResource(R.drawable.item_info_add_cart_desabled_btn_red_b);
                	cart_icon.setImageResource(R.drawable.shopping_cart_acc_cart_icon);
                }
                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_CHANGE2) {
                	footer_balance.setEnabled(true);
                	footer_balance.setBackgroundResource(R.drawable.button_red);
                	cart_icon.setImageResource(R.drawable.shopping_cart_acc_cart_icon);
                }
                
            }
        };

        back = (ImageView) view.findViewById(R.id.top_view_back);
        back.setVisibility(View.INVISIBLE);
        
        
        orderModel = new OrderModel(getActivity());
		orderModel.addResponseListener(this);
		
		return view;
	}

	@SuppressLint("NewApi")
	public void setShopCart() {
		
		if(shoppingCartModel.goods_list.size() == 0) {
			shop_car_null.setVisibility(View.VISIBLE);
			shop_car_isnot.setVisibility(View.GONE);
		} else {
			footer_total.setText(shoppingCartModel.total.goods_price);
			
			shop_car_isnot.setVisibility(View.VISIBLE);
			shop_car_null.setVisibility(View.GONE);
			if (null == shopCarAdapter)
            {
                shopCarAdapter = new C0_ShoppingCartAdapter(getActivity(), shoppingCartModel.goods_list);
            }

			xlistView.setAdapter(shopCarAdapter);
            shopCarAdapter.notifyDataSetChanged();
            
            footer_balance.setEnabled(true);
        	footer_balance.setBackgroundResource(R.drawable.button_red);
        	cart_icon.setImageResource(R.drawable.shopping_cart_acc_cart_icon);
			
			shopCarAdapter.parentHandler = messageHandler;
		}
		
	}
	
	public void updataCar() {
		shoppingCartModel.goods_list.clear();
		shoppingCartModel.cartList();
	}

	@SuppressLint("NewApi")
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
		if (url.endsWith(ApiInterface.CART_LIST)) {
			xlistView.stopRefresh();
			xlistView.setRefreshTime();
			setShopCart();
			TabsFragment.setShoppingcartNum();
		} else if(url.endsWith(ApiInterface.CART_DELETE)) {
			updataCar();
		} else if(url.endsWith(ApiInterface.CART_UPDATE)) {
			updataCar();
		} else if(url.endsWith(ApiInterface.ADDRESS_LIST)) {
			if(addressModel.addressList.size() == 0) {
				Intent intent = new Intent(getActivity(), F1_NewAddressActivity.class);
				startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
			} else {
				Intent intent = new Intent(getActivity(), C1_CheckOutActivity.class);
				startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
			}
			
		}
        else if(url.endsWith(ApiInterface.ORDER_PAY))
        {
			Intent intent = new Intent(getActivity(), PayWebActivity.class);

            String data = null;
            try
            {
                data = jo.getString("data").toString();
                intent.putExtra("html", data);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
			startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
		}
	}


	@Override
	public void onRefresh(int id) {
		 

		shoppingCartModel.cartList();
	}

	@Override
	public void onLoadMore(int id) {
		 
		
	}
	

	@SuppressLint("NewApi")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1)
        {
		    shoppingCartModel.cartList();
		} 
	}

    @Override
    public void onResume() {
         
        super.onResume();
        MobclickAgent.onPageStart("ShopCart");
    }

    @Override
    public void onPause() {
         
        super.onPause();
        MobclickAgent.onPageEnd("ShopCart");
    }



}
