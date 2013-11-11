package com.insthub.ecmobile.adapter;

/*
 *
 *       _/_/_/                      _/        _/_/_/_/_/
 *    _/          _/_/      _/_/    _/  _/          _/      _/_/      _/_/
 *   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/          _/      _/    _/  _/    _/
 *  _/    _/  _/        _/        _/  _/      _/        _/    _/  _/    _/
 *   _/_/_/    _/_/_/    _/_/_/  _/    _/  _/_/_/_/_/    _/_/      _/_/
 *
 *
 *  Copyright 2013-2014, Geek Zoo Studio
 *  http://www.ecmobile.cn/license.html
 *
 *  HQ China:
 *    2319 Est.Tower Van Palace
 *    No.2 Guandongdian South Street
 *    Beijing , China
 *
 *  U.S. Office:
 *    One Park Place, Elmira College, NY, 14901, USA
 *
 *  QQ Group:   329673575
 *  BBS:        bbs.ecmobile.cn
 *  Fax:        +86-10-6561-5510
 *  Mail:       info@geek-zoo.com
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.activeandroid.util.AnimationUtil;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobile.activity.ShoppingCartActivity;
import com.insthub.ecmobile.activity.TradeActivity;
import com.insthub.ecmobile.fragment.ShoppingCartFragment;
import com.insthub.ecmobile.protocol.GOODS_LIST;
import com.insthub.ecmobile.R;

public class ShoppingCartAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	public List<GOODS_LIST> list;
	private int i;
	public Handler parentHandler;

	public static int CART_CHANGE_CHANGE2 = 4;
	public static int CART_CHANGE_CHANGE1 = 3;
    public static int CART_CHANGE_MODIFY = 2;
    public static int CART_CHANGE_REMOVE = 1;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    
    public static Map<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();; 
    
    private MyDialog mDialog;
	
	public ShoppingCartAdapter(Context context, List<GOODS_LIST> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	
	}
	
	private boolean init(int position) {
		if(isSelected.containsKey(Integer.valueOf(position))) {
			if(isSelected.get(position) == true) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		int count = list != null ? list.size() : 1;
		return count;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		i = 0;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.shop_car_item, null);
			
			holder.totel = (TextView) convertView.findViewById(R.id.shop_car_item_total);
			holder.change = (Button) convertView.findViewById(R.id.shop_car_item_change);
			
			holder.view = (FrameLayout) convertView.findViewById(R.id.shop_car_item_view);
			holder.view1 = (LinearLayout) convertView.findViewById(R.id.shop_car_item_view1);
			holder.view2 = (FrameLayout) convertView.findViewById(R.id.shop_car_item_view2);
			
			holder.image = (WebImageView) convertView.findViewById(R.id.shop_car_item_image);
			holder.text = (TextView) convertView.findViewById(R.id.shop_car_item_text);
			holder.property = (TextView) convertView.findViewById(R.id.shop_car_item_property);
			
			holder.min = (ImageView) convertView.findViewById(R.id.shop_car_item_min);
			holder.editNum = (EditText) convertView.findViewById(R.id.shop_car_item_editNum);
			holder.sum = (ImageView) convertView.findViewById(R.id.shop_car_item_sum);
			holder.remove = (Button) convertView.findViewById(R.id.shop_car_item_remove);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final GOODS_LIST goods = list.get(position);
		
		isSelected.put(position, false);
		
		shared = context.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");
		
		if(imageType.equals("high")) {
			holder.image.setImageWithURL(context, goods.img.thumb, R.drawable.default_image);
		} else if(imageType.equals("low")) {
			holder.image.setImageWithURL(context, goods.img.small, R.drawable.default_image);
		} else {
			String netType = shared.getString("netType", "wifi");
			if(netType.equals("wifi")) {
				holder.image.setImageWithURL(context, goods.img.thumb, R.drawable.default_image);
			} else {
				holder.image.setImageWithURL(context, goods.img.small, R.drawable.default_image);
			}
		}
		
		holder.totel.setText(goods.subtotal);
		holder.text.setText(goods.goods_name);
		
		StringBuffer sbf = new StringBuffer();
		for(int i=0;i<goods.goods_attr.size();i++) {
//			if(i!=goods.goods_attr.size()-1) {
				sbf.append(goods.goods_attr.get(i).name+"：");
				sbf.append(goods.goods_attr.get(i).value+" | ");
//			} else {
//				sbf.append(goods.goods_attr.get(i).name+"：");
//				sbf.append(goods.goods_attr.get(i).value);
//			}
		}
		sbf.append("数量：");
		sbf.append(goods.goods_number);
		
		holder.property.setText(sbf.toString());
		
		holder.editNum.setText(goods.goods_number+"");
		
		holder.change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.view2.getVisibility() == View.GONE) {
					AnimationUtil.showAnimation1(holder.view1, holder.view);
					AnimationUtil.showAnimation2(holder.view2, holder.view);
					
					holder.view2.setVisibility(View.VISIBLE);
					holder.change.setText("完成");
					
					Message msg = new Message();
	                msg.what = CART_CHANGE_CHANGE1;
                    parentHandler.handleMessage(msg);
                    
                    isSelected.put(position, true);
					
				} else { // 收回隐藏布局
					AnimationUtil.backAnimation1(holder.view1);
					AnimationUtil.backAnimation2(holder.view2);
					
					holder.view2.setVisibility(View.GONE);
					holder.change.setText("修改");
					
					if(goods.goods_number != Integer.valueOf(holder.editNum.getText().toString())) {
						Message msg = new Message();
		                msg.what = CART_CHANGE_MODIFY;
		                msg.arg1 = Integer.valueOf(goods.rec_id);
		                msg.arg2 = Integer.valueOf(holder.editNum.getText().toString());
                        parentHandler.handleMessage(msg);
					}
					holder.editNum.setText(goods.goods_number+"");
					
					isSelected.put(position, false);
					boolean a = false;
					for(int j=0;j<list.size();j++) {
						if(init(j) == true) {
							a = true;
							break;
						} else {
							a = false;
						}
					}
					if(a == false) {
						Message msg1 = new Message();
		                msg1.what = CART_CHANGE_CHANGE2;
	                    parentHandler.handleMessage(msg1);
					}
				}
			}
		});
		
		holder.min.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = Integer.valueOf(holder.editNum.getText().toString());
				if(i>1) {
					holder.editNum.setText(--i+"");
				}
			}
		});
		
		holder.sum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = Integer.valueOf(holder.editNum.getText().toString());
                    ++i;
                    holder.editNum.setText(i+"");

			}
		});
		
		holder.remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mDialog = new MyDialog(context, "移除商品", "您确定要移除该商品？");
                mDialog.show();
                mDialog.positive.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog.dismiss();
						Message msg = new Message();
		                msg.what = CART_CHANGE_REMOVE;
		                msg.arg1 = Integer.valueOf(goods.rec_id);

		                parentHandler.handleMessage(msg);
						
					}
				});
                mDialog.negative.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog.dismiss();
						
					}
				});
			}
		});
		
		return convertView;
	}
	
	class ViewHolder {
		
		private TextView totel;
		private Button change;
		private FrameLayout view;
		private LinearLayout view1;
		private FrameLayout view2;
		
		private WebImageView image;
		private TextView text;
		private TextView property;
		
		private ImageView min;
		private EditText editNum;
		private ImageView sum;
		private Button remove;
		
	}

}
