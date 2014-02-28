package com.tencent.weibo.sdk.android.api.adapter;

import java.util.List;

import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.component.PublishActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConversationAdapter extends BaseAdapter {
	private Context ct;
	private List<String> cvlist;

	public ConversationAdapter(Context ct, List<String> cvlist) {
		this.ct = ct;
		this.cvlist = cvlist;
	}

	@Override
	public int getCount() {
		return cvlist.size();
	}
 
	@Override
	public Object getItem(int position) {
		return cvlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<String> getCvlist() {
		return cvlist;
	}

	public void setCvlist(List<String> cvlist) {
		this.cvlist = cvlist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LinearLayout layout = new LinearLayout(ct);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			// layout.setLayoutParams(params);
			layout.setBackgroundDrawable(BackGroudSeletor.getdrawble("topic_",
					ct));
			layout.setGravity(Gravity.CENTER_VERTICAL);
			layout.setPadding(10, 0, 10, 0);
			TextView textView = new TextView(ct);
			textView.setTextColor(Color.parseColor("#108ece"));
			textView.setText(getItem(position).toString());
			// textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_VERTICAL);
		//	textView.setHeight(55);
			textView.setTextSize(18f);
			layout.addView(textView);
			convertView = layout;
			convertView.setTag(textView);

		} else {
			TextView textView = (TextView) convertView.getTag();
			textView.setText(getItem(position).toString());
		}
		return convertView;
	}

}
