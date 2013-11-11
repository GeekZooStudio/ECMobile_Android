package com.insthub.ecmobile.model;

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

import android.app.ProgressDialog;
import android.content.Context;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.protocol.SHOPHELP;
import com.insthub.ecmobile.protocol.STATUS;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


public class HelpModel extends BaseModel
{
    public ArrayList<SHOPHELP> shophelpsList = new ArrayList<SHOPHELP>();
    String pkName;

    public String rootpath;
    public HelpModel(Context context)
    {
        super(context);
        pkName = mContext.getPackageName();
        rootpath = context.getCacheDir()+"/ECMobile/cache" ;
        readHelpDataCache();
    }

    public void readHelpDataCache() {
        String path = rootpath+"/"+pkName+"/helpData.dat";
        File f1 = new File(path);
        try {
            InputStream is = new FileInputStream(f1);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader bf = new BufferedReader(input);

            helpDataCache(bf.readLine());

            bf.close();
            input.close();
            is.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void helpDataCache(String result) {

        try {
            if (result != null) {
                JSONObject jsonObject = new JSONObject(result);

                STATUS responseStatus = STATUS.fromJson(jsonObject.optJSONObject("status"));
                if (responseStatus.succeed == 1) {
                    fileSave(jsonObject.toString(), "helpData");
                    JSONArray shopHelpJsonArray = jsonObject.optJSONArray("data");
                    data = jsonObject.toString();
                    if (null != shopHelpJsonArray && shopHelpJsonArray.length() > 0) {
                        shophelpsList.clear();
                        for (int i = 0; i < shopHelpJsonArray.length(); i++) {
                            JSONObject shopHelpJsonObject = shopHelpJsonArray.getJSONObject(i);
                            SHOPHELP shopHelpItem = SHOPHELP.fromJson(shopHelpJsonObject);
                            shophelpsList.add(shopHelpItem);
                        }
                    }

                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 缓存数据
    private PrintStream ps = null;
    public void fileSave(String result, String name) {

        String path = rootpath+"/"+pkName;

        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        File file = new File(filePath+"/"+name+".dat");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos);
            ps.print(result);
            ps.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String data;
    public void fetchShopHelp()
    {
        String url = ProtocolConst.SHOPHELP;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try
                {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    if (responseStatus.succeed == 1)
                    {
                        fileSave(jo.toString(), "helpData");
                        JSONArray shopHelpJsonArray = jo.optJSONArray("data");
                        data = jo.toString();
                        if (null != shopHelpJsonArray && shopHelpJsonArray.length() > 0)
                        {
                            shophelpsList.clear();
                            for (int i = 0; i < shopHelpJsonArray.length(); i++)
                            {
                                JSONObject shopHelpJsonObject = shopHelpJsonArray.getJSONObject(i);
                                SHOPHELP shopHelpItem = SHOPHELP.fromJson(shopHelpJsonObject);
                                shophelpsList.add(shopHelpItem);
                            }
                            HelpModel.this.OnMessageResponse(url, jo, status);
                        }

                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        cb.url(url).type(JSONObject.class);
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("正在拉取...");
        aq.progress(pd).ajax(cb);

    }

}
