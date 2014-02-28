package com.tencent.weibo.sdk.android.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.sdk.android.api.PublishWeiBoAPI;
import com.tencent.weibo.sdk.android.api.adapter.FriendAdapter;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.FirendCompare;
import com.tencent.weibo.sdk.android.api.util.HypyUtil;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.Firend;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * 好友列表组件
 * 
 * */
public class FriendActivity extends Activity implements
		LetterListView.OnTouchingLetterChangedListener, HttpCallback {
	private ExpandableListView listView;
	private LetterListView letterListView;
	private List<String> groups = new ArrayList<String>();
	private int[] nums;
	private List<String> group = new ArrayList<String>();
	private Map<String, List<Firend>> child = new HashMap<String, List<Firend>>();
	private List<String> newgourp = new ArrayList<String>();
	private Map<String, List<Firend>> newchild = new HashMap<String, List<Firend>>();
	private TextView textView;
	private FriendAdapter adapter;
	private EditText editText;
    private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout layout = (LinearLayout) initview();
		getdate();
		setContentView(layout);

	} 
   /**
    * 初始化界面中使用的控件
    * */
	private View initview() {
		LinearLayout viewroot = new LinearLayout(FriendActivity.this);
		LinearLayout.LayoutParams params_viewroot = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		viewroot.setLayoutParams(params_viewroot);
		viewroot.setOrientation(LinearLayout.VERTICAL);
		
		
		RelativeLayout layout_title = new RelativeLayout(FriendActivity.this);
		RelativeLayout.LayoutParams params_title = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		//params_title.addRule(RelativeLayout.CENTER_VERTICAL);
		layout_title.setLayoutParams(params_title);
		layout_title.setGravity(LinearLayout.HORIZONTAL);
		layout_title.setBackgroundDrawable(BackGroudSeletor.getdrawble(
				"up_bg2x", FriendActivity.this));
		
		RelativeLayout.LayoutParams params_button_back = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params_button_back.addRule(RelativeLayout.CENTER_VERTICAL);
		params_button_back.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params_button_back.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		params_button_back.topMargin=10;
		params_button_back.leftMargin=10;
		params_button_back.bottomMargin=10;
		Button button_back = new Button(FriendActivity.this);
		String back_string[] = { "return_btn2x", "return_btn_hover" };
		button_back.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(
				back_string, FriendActivity.this));
		button_back.setText("  返回");
		button_back.setLayoutParams(params_button_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		TextView text_title = new TextView(FriendActivity.this);
		text_title.setText("好友列表");
		text_title.setTextColor(Color.WHITE);
		text_title.setTextSize(24f);
		RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		text_title.setLayoutParams(titleParams);
		layout_title.addView(text_title);
		layout_title.addView(button_back);
		viewroot.addView(layout_title);
		LinearLayout layout_find = new LinearLayout(FriendActivity.this);
		layout_find.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		//layout_find.setPadding(10, 0, 10, 0);
		layout_find.setOrientation(LinearLayout.HORIZONTAL);
		layout_find.setGravity(Gravity.CENTER);
//		layout_find.setBackgroundDrawable(BackGroudSeletor.getdrawble(
//				"icon_bg2x", FriendActivity.this));
		editText = new EditText(FriendActivity.this);
		editText.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		editText.setPadding(20, 0, 10, 0);
		editText.setBackgroundDrawable(BackGroudSeletor.getdrawble(
				"searchbg_", FriendActivity.this));
		editText.setCompoundDrawablesWithIntrinsicBounds(BackGroudSeletor
				.getdrawble("search_", FriendActivity.this), null, null,
				null);
		editText.setHint("搜索");
		editText.setTextSize(18f);
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				search(s.toString());
			}
		});

		layout_find.addView(editText);
		viewroot.addView(layout_find);
		// FrameLayout layout_friend=new FrameLayout(FriendActivity.this);
		listView = new ExpandableListView(FriendActivity.this);
		FrameLayout.LayoutParams params_friend = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT);
		// layout_friend.setLayoutParams(params_friend);

		listView.setLayoutParams(params_viewroot);
		listView.setGroupIndicator(null);

		// layout_friend.addView(listView);
		LinearLayout layout_scroll = new LinearLayout(FriendActivity.this);
		layout_scroll.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		textView = new TextView(FriendActivity.this);
		layout_scroll.setPadding(30, 0, 0, 0);
		layout_scroll.setGravity(Gravity.CENTER_VERTICAL);
		textView.setTextSize(18);
		textView.setTextColor(Color.WHITE);
		textView.setText("常用联系人");
		layout_scroll.addView(textView);
		layout_scroll.setBackgroundColor(Color.parseColor("#b0bac3"));
		// layout_friend.addView(layout_scroll);
		letterListView = new LetterListView(FriendActivity.this, group);
		letterListView.setOnTouchingLetterChangedListener(FriendActivity.this);
		RelativeLayout.LayoutParams params_letter = new RelativeLayout.LayoutParams(
				50, LinearLayout.LayoutParams.FILL_PARENT);

		RelativeLayout relativeLayout = new RelativeLayout(FriendActivity.this);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params_letter.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		letterListView.setLayoutParams(params_letter);

		relativeLayout.setLayoutParams(layoutParams);

		relativeLayout.addView(listView);
		relativeLayout.addView(layout_scroll);
		relativeLayout.addView(letterListView);
		// layout_friend.addView(letterListView);
		viewroot.addView(relativeLayout);
		return viewroot;

	}
   /**
    * 搜索获取满足输入条件的结果
    * @param 输入框输入的内容
    * 
    * */
	public void search(String text) {
		newgourp.clear();
		newchild.clear();
		for (int i = 0; i < group.size(); i++) {
			for (int j = 0; j < child.get(group.get(i)).size(); j++) {
				if (child.get(group.get(i)).get(j).getNick().contains(text)) {
					if (newchild.get(group.get(i)) == null) {
						List<Firend> list = new ArrayList<Firend>();
						list.add(child.get(group.get(i)).get(j));
						newchild.put(group.get(i), list);
						newgourp.add(group.get(i));
					} else {
						newchild.get(group.get(i)).add(
								child.get(group.get(i)).get(j));
					}
				}
			}

		}
		Log.d("size", newgourp.size() + "---" + newchild.size());
		nums = new int[newgourp.size()];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = totle(i);
		}
		letterListView.setB(newgourp);
		adapter.setChild(newchild);
		adapter.setGroup(newgourp);
		adapter.notifyDataSetChanged();
		ex(newgourp, newchild);
	}
/**
 * 请求网络，获取数据
 * */
	private void getdate() {
		if (dialog==null) {
			dialog=new ProgressDialog(FriendActivity.this);
			dialog.setMessage("请稍后...");
			dialog.show();
		}
		new PublishWeiBoAPI(new AccountModel(Util.getSharePersistent(
				getApplicationContext(), "ACCESS_TOKEN"))).mutual_list(
				FriendActivity.this, FriendActivity.this, null, 0, 0, 0, 10);
	}
   /**
    * 初始化控件效果，并设置监听
    * */
	private void ex(final List<String> groupt,
			final Map<String, List<Firend>> childs) {
		for (int i = 0; i < groupt.size(); i++) {
			listView.expandGroup(i);
		}
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
			}
		});
		listView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				return true;
			}
		});
		listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				intent.setClass(FriendActivity.this, PublishActivity.class);

				intent.putExtra("firend", childs.get(groupt.get(groupPosition))
						.get(childPosition).getNick());
				setResult(RESULT_OK, intent);
				finish();
				return true;
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Log.d("first", firstVisibleItem + "");
				for (int i = 0; i < groupt.size(); i++) {
					if (i == 0) {
						if (firstVisibleItem >= 0 && firstVisibleItem < nums[i]) {
							textView.setText(groupt.get(i).toUpperCase());
							return;
						}
					}
					if (firstVisibleItem < nums[i]
							&& firstVisibleItem >= nums[i - 1]) {
						textView.setText(groupt.get(i).toUpperCase());
						return;
					}
				}

			}
		});

	}

	@Override
	public void onTouchingLetterChanged(int c) {
		listView.setSelectedGroup(c);
	}

	@Override
	public void onResult(Object object) {
		if (dialog!=null&&dialog.isShowing()) {
			dialog.dismiss();
		}
		if (object != null) {
			if (((ModelResult) object).isSuccess()) {
				JSONObject jsonObject = (JSONObject) ((ModelResult) object)
						.getObj();
				getJsonData(jsonObject);
				nums = new int[group.size()];
				for (int i = 0; i < nums.length; i++) {
					nums[i] = totle(i);
				}
				letterListView.setB(group);
				adapter = new FriendAdapter(FriendActivity.this, group, child);
				listView.setAdapter(adapter);
				//int hight=listView.geth
				ex(group, child);
				Log.d("发送成功", object.toString());
			}
		}

	}
/**
 * 解析网络获取的json 数据，并对数据排序
 * */
	private void getJsonData(JSONObject json) {
		List<Firend> firends = new ArrayList<Firend>();
		try {
			JSONArray array = json.getJSONObject("data").getJSONArray("info");
			for (int i = 0; i < array.length(); i++) {
				Firend firend = new Firend();
				firend.setNick(((JSONObject) array.get(i)).getString("nick"));
				firend.setName(((JSONObject) array.get(i)).getString("name"));
				firend.setHeadurl(((JSONObject) array.get(i)).getString(
						"headurl").replaceAll("\\/", "/")
						+ "/180");

				firends.add(firend);
			}
			Collections.sort(firends, new FirendCompare());
		} catch (JSONException e) {
			 
			e.printStackTrace();
		}

		for (int i = 0; i < firends.size(); i++) {
			if (child.get(HypyUtil.cn2py(firends.get(i).getNick())
					.substring(0, 1).toUpperCase()) != null) {
				child.get(
						HypyUtil.cn2py(firends.get(i).getNick())
								.substring(0, 1).toUpperCase()).add(
						firends.get(i));
			} else {
				Log.d("group", HypyUtil.cn2py(firends.get(i).getNick())
						.substring(0, 1));
				group.add(HypyUtil.cn2py(firends.get(i).getNick())
						.substring(0, 1).toUpperCase());
				List<Firend> list = new ArrayList<Firend>();
				list.add(firends.get(i));
				child.put(
						HypyUtil.cn2py(firends.get(i).getNick())
								.substring(0, 1).toUpperCase(), list);
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (dialog!=null&&dialog.isShowing()) {
			dialog.dismiss();
		}
	}
  /**
   * 获取每组数据的显示position范围，
   * */
	private int totle(int position) {
		if (position == 0) {
			return child.get(group.get(position)).size() + 1;
		} else {
			return child.get(group.get(position)).size() + 1
					+ totle(position - 1);
		}
	}
}
