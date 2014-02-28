package com.external.activeandroid.util;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AnimationUtil {
	
	/**
	 * 弹出动画效果
	 */
	public static void showAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(400);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
	}
	
	/**
	 * 退出动画效果
	 */
	public static void backAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
		translateAnimation.setDuration(400);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
	}

	public static void showAnimation1(final View view,final View pview) {
		AnimationSet animationSet1 = new AnimationSet(true);
		TranslateAnimation translateAnimation1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -0.5f,
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation1.setDuration(300);
		translateAnimation1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				 
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				 
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				 
				view.clearAnimation(); 
	            //FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(view.getWidth(),view.getHeight());
	            lp.setMargins(-pview.getWidth() / 2, 0, 0, 0);
	            view.setLayoutParams(lp);
			}
		});
		animationSet1.setFillAfter(true); // 设置动画不返回
		animationSet1.addAnimation(translateAnimation1);
		view.startAnimation(animationSet1);
		
	}
	
	public static void showAnimation2(View view ,View pview) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1f,
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
		
		// 设置隐藏布局的宽带（父布局的一半）
		LayoutParams params = view.getLayoutParams();
		params.width = pview.getWidth() / 2;
		view.setLayoutParams(params);
	}
	
	public static void backAnimation1(View view) {
		AnimationSet animationSet1 = new AnimationSet(true);
		TranslateAnimation translateAnimation1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, -0.5f,
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation1.setDuration(300);
		animationSet1.setFillAfter(true); // 设置动画不返回
		animationSet1.addAnimation(translateAnimation1);
		view.startAnimation(animationSet1);
		
		//FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(view.getWidth(),view.getHeight());
        lp.setMargins(0, 0, 0, 0);
        view.setLayoutParams(lp);
	}
	
	public static void backAnimation2(View view) {
		
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 1f,
				Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f);
		
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
		
	}
	
}
