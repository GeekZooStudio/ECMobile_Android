package com.insthub.BeeFramework.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.insthub.BeeFramework.BeeFrameworkConst;
import com.insthub.BeeFramework.adapter.CrashLogAdapter;
import com.insthub.BeeFramework.protocol.CrashMessage;
import com.external.maxwin.view.XListView;
import com.insthub.ecmobile.R;
import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
public class CrashLogActivity extends BaseActivity
{
    ArrayList<File> logFilesList = new ArrayList<File>();
    ArrayList<CrashMessage> crashMessageArrayList = new ArrayList<CrashMessage>();

    XListView logListView;
    TextView titleTextView;
    CrashLogAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_log_activity);
        logListView = (XListView)findViewById(R.id.log_list_view);
        titleTextView = (TextView)findViewById(R.id.navigationbar_title);

        Resources resource = (Resources) getBaseContext().getResources();
        String log_str =  resource.getString(R.string.crash_log_analysis);
        titleTextView.setText(log_str);

        new Thread() {
            @Override
            public void run() {
                 initLog();
            }
        }.start();

        logListView.setPullLoadEnable(false);
        logListView.setPullRefreshEnable(false);
        logListView.setRefreshTime();

        listAdapter = new CrashLogAdapter(this,crashMessageArrayList);
        logListView.setAdapter(listAdapter);

        logListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id)
             {
             	int size =crashMessageArrayList.size();
                 CrashMessage crashMessage = crashMessageArrayList.get(size-position);
                 Intent it = new Intent(CrashLogActivity.this, CrashLogDetailActivity.class);
                 it.putExtra("crash_time",crashMessage.crashTime);
                 it.putExtra("crash_content",crashMessage.crashContent);
                 startActivity(it);
             }
         });
         
    }


    public void initLog()
    {
        try
        {

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + BeeFrameworkConst.LOG_DIR_PATH;

            getFiles(logFilesList, path);
            for (int i = 0; i < logFilesList.size(); i++)
            {
                File file = logFilesList.get(i);
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    int length = fin.available();
                    byte[] buffer = new byte[length];
                    fin.read(buffer);
                    String content = EncodingUtils.getString(buffer,"UTF-8");
                    fin.close();
                    String fileName = file.getName();
                    String[] nameArray = fileName.split("\\.");
                    if (nameArray.length > 0)
                    {
                        String intStr = nameArray[0];

                        long timestamp =  Long.parseLong(intStr);
                        Date currentTime = new Date(timestamp);

                        CrashMessage crashMessage = new CrashMessage();

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        String dateString = formatter.format(currentTime);
                        crashMessage.crashTime = dateString;
                        crashMessage.crashContent = content;
                        crashMessageArrayList.add(crashMessage);
                    }

                }
                catch (FileNotFoundException e)
                {

                }
                catch (IOException e2)
                {
                    e2.printStackTrace();
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                listAdapter.notifyDataSetChanged();
                break;
            }

        }
    };

    private void getFiles(ArrayList<File> logFilesList, String path)
    {
        File[] allFiles = new File(path).listFiles();
        for (int i = 0; i < allFiles.length; i++)
        {
            File file = allFiles[i];
            if (file.isFile()&&file.getName().contains("txt"))
            {
                logFilesList.add(file);
            }
        }
    }
}
