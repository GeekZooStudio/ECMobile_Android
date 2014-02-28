package com.tencent.weibo.sdk.android.component;


import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
/**
 * 自定义控件显示好友昵称首字母列表
 * 
 * 
 * */
public class LetterListView extends View {
	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	List<String> b ;
	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;
   
	public LetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LetterListView(Context context,List<String> b) {
		super(context);
		this.b=b;
	}

	 
/**
 * 设置好友昵称首字母列表数据
 * */
	public void setB(List<String> b) {
		this.b = b;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#00000000"));
		}

		int height = getHeight();
		int width = getWidth()-30;
		if (b.size()>0) {
			int singleHeight = height / b.size();
			for (int i = 0; i < b.size(); i++) {
				paint.setColor(Color.parseColor("#2796c4"));
				paint.setTextSize(17);
				paint.setTypeface(Typeface.DEFAULT_BOLD);
				paint.setAntiAlias(true);
				if (i == choose) {
					paint.setColor(Color.GRAY);
					paint.setFakeBoldText(true);
				}
				float xPos = width / 2 - paint.measureText(b.get(i)) / 2;
				float yPos = singleHeight * i + singleHeight;
				canvas.drawText(b.get(i).toUpperCase(), xPos, yPos, paint);
				paint.reset();
			}
		}
	

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.size());

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < b.size()) {
					listener.onTouchingLetterChanged(c);					
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >=0 && c < b.size()) {
					listener.onTouchingLetterChanged(c);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
   /**
    * 点击好友昵称首字母列表监听接口
    * */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(int c);
	}

}
