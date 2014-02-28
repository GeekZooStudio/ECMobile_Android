package com.insthub.BeeFramework.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.BeeFramework.model.BeeCallback;

import java.util.ArrayList;
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
public class MemoryMessageActivity extends BaseActivity {

	private TextView memory_total;
	private TextView memory_avail;
	private TextView memory_low;
	
	private TextView pid;
	private TextView uid;
	private TextView memSize;
	private TextView processName;
    private TextView networkUsage;
	
	private LinearLayout view;
	
	private Timer timer;
	
	private ActivityManager mActivityManager = null ; 
	
	private List<Integer> list = new ArrayList<Integer>();
	
	private int _memSize;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.memory_message);
        
        memory_total = (TextView) findViewById(R.id.memory_total);
        memory_avail = (TextView) findViewById(R.id.memory_avail);
        memory_low = (TextView) findViewById(R.id.memory_low);
        
        pid = (TextView) findViewById(R.id.memory_pid);
        uid = (TextView) findViewById(R.id.memory_uid);
        memSize = (TextView) findViewById(R.id.memory_memSize);
        processName = (TextView) findViewById(R.id.memory_processName);
        networkUsage = (TextView)findViewById(R.id.network_usage);
        
        view = (LinearLayout) findViewById(R.id.memory_view);
        
        mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        
        setContent();
        
        for(int i=0;i<9;i++) {
        	list.add(_memSize);
        }
        
        timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				 
				Message msg = new Message(); 
                msg.what = 1;
                handler.sendMessage(msg);
			} 
        };
        
        timer.schedule(timerTask, 1000, 1000);
        
        MyView myView = new MyView(this);
        view.addView(myView);
        
    }
    
    Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			 
			super.handleMessage(msg);
			switch(msg.what) {
			case 1:
				setContent();
				view.removeAllViews();
				MyView myView = new MyView(MemoryMessageActivity.this);
		        view.addView(myView);
				break;
			}
		}
    	
    };
    
	public void setContent() {
    	
		MemoryInfo memoryInfo = new MemoryInfo() ;  
        mActivityManager.getMemoryInfo(memoryInfo) ;

        Resources resource = (Resources) getBaseContext().getResources();
        String mot=resource.getString(R.string.memorymessagemctivity_motalram );
        String fre=resource.getString(R.string.memorymessageactivity_freeram);
        String low=resource.getString(R.string.memorymessageactivity_lowram);
        String rat=resource.getString(R.string.memorymessageactivity_rate);
        memory_total.setText(mot +formateFileSize(memoryInfo.totalMem));
		memory_avail.setText(fre+formateFileSize(memoryInfo.availMem));
		memory_low.setText(low+memoryInfo.lowMemory);
        networkUsage.setText(rat+ BeeCallback.averageBandwidthUsedPerSecond);
		
		getRunningAppProcessInfo();
		
		
    }
    
	private void getRunningAppProcessInfo() {

		// 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
		
		for(int i=0;i < appProcessList.size();i++) {
			
			if(appProcessList.get(i).processName.equals("com.BeeFramework.example")) {
				int _pid = appProcessList.get(i).pid;
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

                Resources resource = (Resources) getBaseContext().getResources();
                String proid=resource.getString(R.string.memorymessageactivity_id );
                String puid=resource.getString(R.string.memorymessageactivity_uid );
                String mem=resource.getString(R.string.memorymessageactivity_mem );
                String pro=resource.getString(R.string.memorymessageactivity_process );
                String rat=resource.getString(R.string.memorymessageactivity_rate);
				pid.setText(proid+_pid);
			    uid.setText(puid+_uid);
			    memSize.setText(mem+_memSize+"k");
			    processName.setText(pro+_processName);
                networkUsage.setText(rat+ BeeCallback.averageBandwidthUsedPerSecond);
			    
			    list.add(_memSize);
			    if(list.size() >= 10) {
					list.remove(0);
				}
				
				break;
			}
		}

	}
    
    private String formateFileSize(long size){  
        return Formatter.formatFileSize(this, size);   
    }
    
    @Override
	public void onDestroy() {
		 
		super.onDestroy();
		timer.cancel();
	}
    
    public class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);// 重写onDraw方法

			canvas.drawColor(Color.WHITE);// 设置背景颜色
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);// 去锯齿
			paint.setColor(Color.BLUE);// 颜色

			Paint paint1 = new Paint();
			paint1.setStyle(Paint.Style.STROKE);
			paint1.setAntiAlias(true);// 去锯齿
			paint1.setColor(Color.parseColor("#c4c4c4c4"));

			// 数据 线
			int x = 0;
			int[] y = new int[100];
			y[0] = 650;

			for (int i = 0; i < list.size(); i++) {
				y[i+1] = 650 - list.get(i) * 50 / 1024;
				x = x + 50;
				int x2 = x + 50;
				if(i!=0) {
					canvas.drawLine(x, y[i], x2, y[i+1], paint);
				}
				canvas.drawCircle(x2, y[i+1], 2, paint);
				
			}

			// 设置Y轴
			canvas.drawLine(100, 50, 100, 650, paint);
			canvas.drawLine(100, 100, 105, 100, paint);
			canvas.drawLine(100, 150, 105, 150, paint);
			canvas.drawLine(100, 200, 105, 200, paint);
			canvas.drawLine(100, 250, 105, 250, paint);
			canvas.drawLine(100, 300, 105, 300, paint);
			canvas.drawLine(100, 350, 105, 350, paint);
			canvas.drawLine(100, 400, 105, 400, paint);
			canvas.drawLine(100, 450, 105, 450, paint);
			canvas.drawLine(100, 500, 105, 500, paint);
			canvas.drawLine(100, 550, 105, 550, paint);
			canvas.drawLine(100, 600, 105, 600, paint);
			
			// 设置X轴
			canvas.drawLine(100, 650, 550, 650, paint);
			canvas.drawLine(150, 650, 150, 645, paint);
			canvas.drawLine(200, 650, 200, 645, paint);
			canvas.drawLine(250, 650, 250, 645, paint);
			canvas.drawLine(300, 650, 300, 645, paint);
			canvas.drawLine(350, 650, 350, 645, paint);
			canvas.drawLine(400, 650, 400, 645, paint);
			canvas.drawLine(450, 650, 450, 645, paint);
			canvas.drawLine(500, 650, 500, 645, paint); 
			
			// 绘制表格
			// y轴平行线
			canvas.drawLine(150, 50, 150, 650, paint1);
			canvas.drawLine(200, 50, 200, 650, paint1);
			canvas.drawLine(250, 50, 250, 650, paint1);
			canvas.drawLine(300, 50, 300, 650, paint1);
			canvas.drawLine(350, 50, 350, 650, paint1);
			canvas.drawLine(400, 50, 400, 650, paint1);
			canvas.drawLine(450, 50, 450, 650, paint1);
			canvas.drawLine(500, 50, 500, 650, paint1);
			canvas.drawLine(550, 50, 550, 650, paint1);
			
			// x轴平行线
			canvas.drawLine(100, 600, 550, 600, paint1);
			canvas.drawLine(100, 550, 550, 550, paint1);
			canvas.drawLine(100, 500, 550, 500, paint1);
			canvas.drawLine(100, 450, 550, 450, paint1);
			canvas.drawLine(100, 400, 550, 400, paint1);
			canvas.drawLine(100, 350, 550, 350, paint1);
			canvas.drawLine(100, 300, 550, 300, paint1);
			canvas.drawLine(100, 250, 550, 250, paint1);
			canvas.drawLine(100, 200, 550, 200, paint1);
			canvas.drawLine(100, 150, 550, 150, paint1);
			canvas.drawLine(100, 100, 550, 100, paint1);
			canvas.drawLine(100, 50, 550, 50, paint1);
			
			// 设置Y轴文字
			paint.setTextSize(20);
			canvas.drawText("0", 80, 665, paint);
			canvas.drawText("1", 80, 605, paint);
			canvas.drawText("2", 80, 555, paint);
			canvas.drawText("3", 80, 505, paint);
			canvas.drawText("4", 80, 455, paint);
			canvas.drawText("5", 80, 405, paint);
			canvas.drawText("6", 80, 355, paint);
			canvas.drawText("7", 80, 305, paint);
			canvas.drawText("8", 80, 255, paint);
			canvas.drawText("9", 80, 205, paint);
			canvas.drawText("10", 70, 155, paint);
			canvas.drawText("11", 70, 105, paint);
			canvas.drawText("千k", 70, 55, paint);

//			// 设置X轴文字
//			canvas.drawText("1", 145, 670, paint);
//			canvas.drawText("2", 195, 670, paint);
//			canvas.drawText("3", 245, 670, paint);
//			canvas.drawText("4", 295, 670, paint);
//			canvas.drawText("5", 345, 670, paint);
//			canvas.drawText("6", 395, 670, paint);
//			canvas.drawText("7", 445, 670, paint);
//			canvas.drawText("8", 495, 670, paint);
//			canvas.drawText("千k", 555, 670, paint);

		}
	}
	
}
