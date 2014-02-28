package com.tencent.weibo.sdk.android.component;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.sdk.android.api.PublishWeiBoAPI;
import com.tencent.weibo.sdk.android.api.adapter.ConversationAdapter;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 话题类表组件
 * 
 * 
 * 
 * */
public class ConversationActivity extends Activity implements HttpCallback{
	private ListView listView;
	private List<String> list;
	private EditText editText;
	private ConversationAdapter adapter;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout layout=(LinearLayout) initview();
		setContentView(layout);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(ConversationActivity.this, arg2+"", 100).show();
			}
		});
		if (dialog==null) {
			dialog=new ProgressDialog(ConversationActivity.this);
			dialog.setMessage("请稍后...");
			dialog.setCancelable(false);
		}
		dialog.show();
		new PublishWeiBoAPI(new AccountModel(Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN"))).recent_used(ConversationActivity.this, ConversationActivity.this, null, 15, 1, 0);
	} 
	/**
	 * 初始化界面使用控件，并设置相应监听
	 * 
	 * */
	private View initview(){
		LinearLayout viewroot=new LinearLayout(ConversationActivity.this);
		LinearLayout.LayoutParams params_viewroot=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		viewroot.setLayoutParams(params_viewroot);
		viewroot.setOrientation(LinearLayout.VERTICAL);
		LinearLayout layout_title=new LinearLayout(ConversationActivity.this);
		LinearLayout.LayoutParams params_title=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layout_title.setLayoutParams(params_title);
		layout_title.setOrientation(LinearLayout.HORIZONTAL);
		layout_title.setBackgroundDrawable(BackGroudSeletor.getdrawble("up_bg2x", ConversationActivity.this));
		layout_title.setPadding(20, 0, 20, 0);
		layout_title.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout layout_editext=new LinearLayout(ConversationActivity.this);
		LinearLayout.LayoutParams params_editext=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		layout_editext.setLayoutParams(params_editext);
		layout_editext.setPadding(0, 0, 12, 0);
		editText=new EditText(ConversationActivity.this);
		editText.setSingleLine(true);
		editText.setLines(1);
		editText.setTextSize(18f);
		editText.setHint("搜索话题");
		editText.setPadding(20, 0, 10, 0);
		editText.setBackgroundDrawable(BackGroudSeletor.getdrawble("huati_input2x", ConversationActivity.this));
		editText.setCompoundDrawablesWithIntrinsicBounds(BackGroudSeletor.getdrawble("huati_icon_hover2x", ConversationActivity.this), null, null, null);
		editText.setLayoutParams(params_viewroot);
		Button button_esc=new Button(ConversationActivity.this);
		String string_esc[] = { "sent_btn_22x", "sent_btn_hover" };
		button_esc.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(string_esc, ConversationActivity.this));
		button_esc.setTextColor(Color.WHITE);
		button_esc.setText("取消");
		layout_editext.addView(editText);
		layout_title.addView(layout_editext);
		layout_title.addView(button_esc);
		viewroot.addView(layout_title);
		LinearLayout layout_list=new LinearLayout(ConversationActivity.this);
		LinearLayout.LayoutParams params_list=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
		layout_list.setLayoutParams(params_list);
		listView=new ListView(ConversationActivity.this);
		listView.setDivider(BackGroudSeletor.getdrawble("table_lie_",
				ConversationActivity.this));
	    listView.setLayoutParams(params_viewroot);
	   
	 
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				List<String> lists=new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).contains(s.toString())) {
						lists.add(list.get(i));
					}
				}
				adapter.setCvlist(lists);
				adapter.notifyDataSetChanged();
				click(lists);
			}
		});
	    
	    LinearLayout layout_foot=new LinearLayout(ConversationActivity.this);
	    Button button=new Button(ConversationActivity.this);
	    layout_foot.setGravity(Gravity.CENTER);
	    button.setLayoutParams(params_title);
	    button.setText("abc");
	    button.setTextColor(Color.BLACK);
	    button_esc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    layout_foot.addView(button);
	    //listView.addFooterView(layout_foot);
	    
	    layout_list.addView(listView);
		viewroot.addView(layout_list);
		
		return viewroot;
	}
	/**
	 * 解析获取的话题数据
	 * */
 private List<String> initData(JSONObject jsonObject){
	 List<String> list=new ArrayList<String>();
     try {
		JSONArray array=jsonObject.getJSONObject("data").getJSONArray("info");
		   for (int i = 0; i < array.length(); i++) {
			list.add("#"+array.getJSONObject(i).getString("text")+"#");
		}
	} catch (JSONException e) {
		 
		e.printStackTrace();
	}
	 
	 return list;
 }
 /**
  * 
  * 设置话题列表每条的点击事件
  * */
 private void click(final List<String> li){
	 listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent =new Intent();
			intent.setClass(ConversationActivity.this, PublishActivity.class);
			intent.putExtra("conversation", li.get(arg2));
			setResult(RESULT_OK, intent);
			finish();
		}
	});

 }
 
@Override
protected void onStop() {
	super.onStop();
	if (dialog!=null&& dialog.isShowing()) {
		dialog.dismiss();
	}
}
@Override
public void onResult(Object object) {
	if (dialog!=null&& dialog.isShowing()) {
		dialog.dismiss();
	}
	if (object!=null&&((ModelResult)object).isSuccess()) {
		Log.d("发送成功", ((ModelResult)object).getObj().toString());
		JSONObject jsonObject=(JSONObject) ((ModelResult) object).getObj();
		list=initData(jsonObject);
		adapter=new ConversationAdapter(ConversationActivity.this, list);
		listView.setAdapter(adapter);
		click(list);
	}
	
}
}
