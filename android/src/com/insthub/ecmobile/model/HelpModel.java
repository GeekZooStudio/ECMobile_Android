package com.insthub.ecmobile.model;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//


import android.app.ProgressDialog;
import android.content.Context;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobile.R;
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
             
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
             
            e.printStackTrace();
        } catch (IOException e) {
             
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
        pd.setMessage(mContext.getString(R.string.hold_on));
        aq.progress(pd).ajax(cb);

    }

}
