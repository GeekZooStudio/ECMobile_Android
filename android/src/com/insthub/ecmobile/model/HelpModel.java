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
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class HelpModel extends BaseModel {
    public ArrayList<SHOPHELP> shophelpsList = new ArrayList<SHOPHELP>();
    String pkName;

    public String rootpath;

    public HelpModel(Context context) {
        super(context);
        pkName = mContext.getPackageName();
        rootpath = context.getCacheDir() + "/ECMobile/cache";
        readHelpDataCache();
    }

    public void readHelpDataCache() {
        String path = rootpath + "/" + pkName + "/helpData.dat";
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
                shopHelpResponse response = new shopHelpResponse();
                response.fromJson(jsonObject);
                if (response.status.succeed == 1) {
                    fileSave(jsonObject.toString(), "helpData");
                    ArrayList<SHOPHELP> shophelps = response.data;
                    data = jsonObject.toString();
                    if (null != shophelps && shophelps.size() > 0) {
                        shophelpsList.clear();
                        shophelpsList.addAll(shophelps);
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

        String path = rootpath + "/" + pkName;

        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        File file = new File(filePath + "/" + name + ".dat");
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

    public void fetchShopHelp() {
        shopHelpRequest request=new shopHelpRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    shopHelpResponse response = new shopHelpResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            fileSave(jo.toString(), "helpData");
                            ArrayList<SHOPHELP> shophelps = response.data;
                            data = jo.toString();
                            if (null != shophelps && shophelps.size() > 0) {
                                shophelpsList.clear();
                                shophelpsList.addAll(shophelps);
                                HelpModel.this.OnMessageResponse(url, jo, status);
                            }
                        }

                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        cb.url(ApiInterface.SHOPHELP).type(JSONObject.class);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

}
