package com.insthub.BeeFramework.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Formatter;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.insthub.BeeFramework.BeeFrameworkApp;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.Utils.LinuxUtils;
import com.insthub.BeeFramework.model.BeeCallback;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

@SuppressLint("NewApi")
public class MemoryService extends Service {

	private float downX;// 触发MotionEvent.ACTION_DOWN时的x坐标，mWindowManagerLayoutParams中的x的值
	private float downY;// 触发MotionEvent.ACTION_DOWN时的y坐标，mWindowManagerLayoutParams中的y的值
	private LayoutParams mLayoutParams;// 用于记录修改后的悬浮图标的位置(xy坐标)
	
	private float upX;// 触发MotionEvent.ACTION_UP时的x坐标，mWindowManagerLayoutParams中的x的值
	private float upY;// 触发MotionEvent.ACTION_UP时的y坐标，mWindowManagerLayoutParams中的y的值
	
	private float moveX;// 触发MotionEvent.ACTION_MOVE时的x坐标,MotionEvent.getRawX()
	private float moveY;// 触发MotionEvent.ACTION_MOVE时的y坐标,MotionEvent.getRawY()
	
	private float actionDownX;// 触发MotionEvent.ACTION_DOWN时通过MotionEvent.getX()函数得到的x坐标
	private float actionDownY;// 触发MotionEvent.ACTION_DOWN时通过MotionEvent.getY()函数得到的y坐标
	
	private WindowManager wManager ;
	
	private View view;
	
	private Timer timer;
	
	private ActivityManager mActivityManager = null ; 
	
	private SharedPreferences mPref;// 用于保存关闭悬浮窗口时悬浮窗口的坐标
	private Editor mEditor;
	
	private long _memSize;
	
	private TextView total; //总内存
	private TextView avail; //剩余内存
	private TextView low; //是否处于低内存状态
	private TextView memSize; //当前程序所占内存
	private TextView pid; //进程ID
	private TextView cpuUsage; //cpu使用量
	private TextView processName; //进程名

    private TextView networkUsage;
    private TextView networkWakeupTime;
    private TextView networkLastSecondUsage;
    private TextView networkLimitBandwidth;
	
	private ImageView logo;

    public static String MEMORYSERVICENAME = "com.insthub.BeeFramework.service.MemoryService";
	
	@Override
	public IBinder onBind(Intent intent) {
		 
		return null;
	}
	
	@Override
	public void onCreate() {
		 
		super.onCreate();
		
		mPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
		
		BeeFrameworkApp.getInstance().currContext = this;
        
        wManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.TOP|Gravity.LEFT ;
        
        wmParams.x = mPref.getInt("x", 0);
        wmParams.y = mPref.getInt("y", 0);

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mLayoutParams = wmParams;// 创建对象时需要传入对象的布局参数，本类负责更新布局参数
        
        LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.memory, null);

		total = (TextView) view.findViewById(R.id.f_memory_total);
		avail = (TextView) view.findViewById(R.id.f_memory_avail);
		low = (TextView) view.findViewById(R.id.f_memory_low);
		memSize = (TextView) view.findViewById(R.id.f_memory_memSize);
		pid = (TextView) view.findViewById(R.id.f_memory_pid);
		cpuUsage = (TextView) view.findViewById(R.id.f_memory_cpuUsage);
		processName = (TextView) view.findViewById(R.id.f_memory_processName);
        networkUsage = (TextView)view.findViewById(R.id.network_usage);
        networkWakeupTime = (TextView)view.findViewById(R.id.network_wakeuptime);
        networkLastSecondUsage = (TextView)view.findViewById(R.id.network_lastSecondUsage);
        networkLimitBandwidth = (TextView)view.findViewById(R.id.network_limit_bandwidth);
		
        logo = (ImageView) view.findViewById(R.id.f_logo);
        
        logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Intent intent = new Intent();
				intent.setAction(MemoryService.MEMORYSERVICENAME);
				stopService(intent);
			}
		});
		
        wManager.addView(view, wmParams);
        
        view.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 
				int ea=event.getAction();

				switch(ea){
				case MotionEvent.ACTION_DOWN:
					downX = mLayoutParams.x;
					downY = mLayoutParams.y;
					
					actionDownX = event.getX();
					actionDownY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					moveX = event.getRawX();
					moveY = event.getRawY() - 25; 
					updateViewPosition(false);
					break;
				}
				return true;
			}
			
        });
        
        
        timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				 
				Message msg = new Message(); 
                msg.what = 1;
                handler.sendMessage(msg);
			} 
        };
        
        timer.schedule(timerTask, 0, 250);
		
	}
	
	private void updateViewPosition(boolean isActionUp) {
		if (!isActionUp) {// MotionEvent.ACTION_MOVE
			// 更新浮动窗口位置参数
			mLayoutParams.x = (int) (moveX - actionDownX);
			mLayoutParams.y = (int) (moveY - actionDownY);
		} else {// MotionEvent.ACTION_UP
			if (!(Math.abs(upX - downX) > 50 || Math.abs(upY - downY) > 50)) {// 移动范围太小,返回初始位置
				// 更新浮动窗口位置参数，返回触摸前的位置
				mLayoutParams.x = (int) (downX);
				mLayoutParams.y = (int) (downY);
			}
		}
		wManager.updateViewLayout(view, mLayoutParams); // 更新悬浮窗口
	}
	
    Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			 
			super.handleMessage(msg);
			switch(msg.what) {
			case 1:
				setContent();
				break;
			case 2:
				DecimalFormat df = new DecimalFormat("#.##");
				cpuUsage.setText("当前进程所占cpu："+df.format(f)+"%");
				break;
			}
		}
    };
    
    public void setContent() {
    	
		MemoryInfo memoryInfo = new MemoryInfo() ;  
		mActivityManager.getMemoryInfo(memoryInfo) ;  
        
		total.setText("总内存:"+formateFileSize(memoryInfo.totalMem));
		avail.setText("空闲内存:"+formateFileSize(memoryInfo.availMem));
		low.setText("是否处于低内存状态："+memoryInfo.lowMemory);
        networkUsage.setText("传输速率:"+ BeeCallback.averageBandwidthUsedPerSecond+"bytes");

        if (null != BeeCallback.throttleWakeUpTime  && BeeCallback.throttleWakeUpTime.after(new Date()))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            networkWakeupTime.setText("唤醒时间:"+format.format(BeeCallback.throttleWakeUpTime));

        }
        else
        {
            networkWakeupTime.setText("");
        }
        networkLastSecondUsage.setText("上一秒传输速率:"+BeeCallback.bandwidthUsedInLastSecond+"bytes");
        networkLimitBandwidth.setText("限定速率:"+BeeCallback.maxBandwidthPerSecond+"bytes");

		
		getRunningAppProcessInfo();
		
    }
    
    private String formateFileSize(long size){  
        return Formatter.formatFileSize(this, size);   
    }
    
    private float f; //进程所占CPU
	private void getRunningAppProcessInfo() {

		// 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
		
		for(int i=0;i < appProcessList.size();i++) {
			
			if(appProcessList.get(i).processName.equals(this.getPackageName())) {
				final int _pid = appProcessList.get(i).pid;
				// 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等
				int _uid = appProcessList.get(i).uid;
				// 进程名，默认是包名或者由属性android：process=""指定
				String _processName = appProcessList.get(i).processName;
				// 获得该进程占用的内存
				int[] myMempid = new int[] { _pid };
				// 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
				Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(myMempid);
				// 获取进程占内存用信息 kb单位
				_memSize = memoryInfo[0].dalvikPrivateDirty;
				
				pid.setText("进程ID："+_pid);
				
			    //uid.setText("进程所在的UID："+_uid);
			    //memSize.setText("进程占用内存："+_memSize+"k");
			    processName.setText("进程名："+_processName);
			    memSize.setText("进程占用内存："+formateFileSize(_memSize*1024));
			    
			    new Thread() {
					@Override
					public void run() {
						 
						super.run();
						LinuxUtils linux = new LinuxUtils();
						f = linux.syncGetProcessCpuUsage(_pid);
						Message msg = new Message(); 
		                msg.what = 2;
		                handler.sendMessage(msg);
					}
					
				}.start();
			    
				break;
			}
		}
	}
	
	@Override
	public void onDestroy() {
		 
		super.onDestroy();
		timer.cancel();
		wManager.removeView(view);
		
		mEditor = mPref.edit();
		mEditor.putInt("x", mLayoutParams.x);
		mEditor.putInt("y", mLayoutParams.y);
		mEditor.commit();
	}

}
