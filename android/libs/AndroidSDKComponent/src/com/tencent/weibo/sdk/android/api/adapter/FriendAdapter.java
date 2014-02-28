package com.tencent.weibo.sdk.android.api.adapter;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.component.PublishActivity;
import com.tencent.weibo.sdk.android.model.Firend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendAdapter extends BaseExpandableListAdapter {
	private Context ctx;
	private List<String> group;
	private Map<String, List<Firend>> child;
	public FriendAdapter(Context ctx,List<String> group,Map<String, List<Firend>> child){
		this.ctx=ctx;
		this.group=group;
		this.child=child;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(getGroup(groupPosition)).get(childPosition);
	}
 
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public List<String> getGroup() {
		return group;
	}

	public void setGroup(List<String> group) {
		this.group = group;
	}

	public Map<String, List<Firend>> getChild() {
		return child;
	}

	public void setChild(Map<String, List<Firend>> child) {
		this.child = child;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
	            if (convertView==null) {
					LinearLayout layout=new LinearLayout(ctx);
					LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
					layout.setOrientation(LinearLayout.HORIZONTAL);
					layout.setGravity(Gravity.CENTER_VERTICAL);
					layout.setPadding(15, 0, 10,0 );
					layout.setBackgroundDrawable(BackGroudSeletor.getdrawble("childbg_",
					ctx));
					final ImageView imageView =new ImageView(ctx);
					imageView.setId(4502);
					imageView.setLayoutParams(new LinearLayout.LayoutParams(45, 45));		
					imageView.setImageDrawable(BackGroudSeletor.getdrawble("logo", ctx));
					LinearLayout layout_text=new LinearLayout(ctx);
					layout_text.setPadding(10, 0, 0, 0);
					layout_text.setGravity(Gravity.CENTER_VERTICAL);
					layout_text.setOrientation(LinearLayout.VERTICAL);
					TextView textView_name=new TextView(ctx);
					TextView textView_content=new TextView(ctx);
					textView_name.setTextSize(18f);
					textView_name.setId(4500);
					textView_name.setTextColor(Color.parseColor("#32769b"));
					textView_content.setTextSize(12);
					textView_content.setId(4501);
					textView_content.setTextColor(Color.parseColor("#a6c6d5"));
					textView_name.setText(((Firend)getChild(groupPosition, childPosition)).getNick());
					textView_content.setText(((Firend)getChild(groupPosition, childPosition)).getName());
					layout_text.addView(textView_name);
					layout_text.addView(textView_content);
					layout.setBackgroundDrawable(BackGroudSeletor.getdrawble("childbg_",
							ctx));
					layout.addView(imageView);
					layout.addView(layout_text);
					convertView=layout;
					convertView.setTag(layout);
					
				}else {
					
					LinearLayout layout=(LinearLayout) convertView.getTag();
					TextView textView_name=(TextView) layout.findViewById(4500);
					TextView textView_content=(TextView) layout.findViewById(4501);
					ImageView imageView_icon=(ImageView) ((LinearLayout) layout.getTag()).findViewById(4502);
					imageView_icon.setImageDrawable(BackGroudSeletor.getdrawble("logo", ctx));
					textView_name.setText(((Firend)getChild(groupPosition, childPosition)).getNick());
					textView_content.setText(((Firend)getChild(groupPosition, childPosition)).getName());
				}
	            final View t=convertView;
	            if (((Firend)getChild(groupPosition, childPosition)).getHeadurl()!=null) {
	            	  new AsyncTask<String, Integer, Bitmap>() {

	  					@Override
	  					protected Bitmap doInBackground(String... params) {
	  						Bitmap bitmap=null;
	  						try {
	  							Log.d("image urel", ((Firend)getChild(groupPosition, childPosition)).getHeadurl()+"");
								URL url = new URL(((Firend)getChild(groupPosition, childPosition)).getHeadurl());
								String responseCode = url.openConnection().getHeaderField(0);
								if (responseCode.indexOf("200") < 0){										 
									Log.d("图片文件不存在或路径错误，错误代码：" ,responseCode);
								}
                              bitmap=BitmapFactory.decodeStream(url.openStream());
							} catch (MalformedURLException e) {
								 
								e.printStackTrace();
								return null;
							} catch (IOException e) {
								 
								e.printStackTrace();
								return null;
							}
							
						
	  						return bitmap;
	  						
	  					}
	  				   @Override  
	  				   protected void onPreExecute() {  
	  				            	  				
	  				        super.onPreExecute();  
	  			    } 
	  				 @Override  
	  			    protected void onPostExecute(Bitmap result) {  	
	  					
	  					 ImageView imageView_icon=(ImageView) ((LinearLayout) t.getTag()).findViewById(4502);
	  					 if (result==null) {
							imageView_icon.setImageDrawable(BackGroudSeletor.getdrawble("logo", ctx));
						}else {
							
							 imageView_icon.setImageBitmap(result); 
						}					
	  		           
	  		             	  				     
	  				        super.onPostExecute(result);  
	  				   } 

	  				}.execute(((Firend)getChild(groupPosition, childPosition)).getHeadurl());
				}else {
					 ImageView imageView_icon=(ImageView) ((LinearLayout) convertView.getTag()).findViewById(4502);
					imageView_icon.setImageDrawable(BackGroudSeletor.getdrawble("logo", ctx));
				}
	           
	           
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return child.get(group.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		if (convertView==null) {
			LinearLayout layout=new LinearLayout(ctx);
			TextView textView=new TextView(ctx);
			layout.setPadding(30, 0, 0, 0);
			layout.setGravity(Gravity.CENTER_VERTICAL);
			textView.setTextSize(18);
			textView.setTextColor(Color.WHITE);
			textView.setWidth(40);
			textView.setText(getGroup(groupPosition).toString().toUpperCase());
			layout.addView(textView);
			//layout.setBackgroundColor(Color.parseColor("#b0bac3"));
			layout.setBackgroundDrawable(BackGroudSeletor.getdrawble("groupbg_",
					ctx));
			textView.setTag(groupPosition);
			convertView=layout;
			convertView.setTag(textView);
			
		}else {
			TextView textView=(TextView) convertView.getTag();
			textView.setText(getGroup(groupPosition).toString().toUpperCase());
		}
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
