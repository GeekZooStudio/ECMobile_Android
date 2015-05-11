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
import com.insthub.BeeFramework.model.BeeQuery;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;

import android.content.Context;
import android.os.Environment;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.external.androidquery.callback.AjaxStatus;


public class HomeModel extends BaseModel {
    public ArrayList<SIMPLEGOODS> simplegoodsList = new ArrayList<SIMPLEGOODS>();
    public ArrayList<CATEGORYGOODS> categorygoodsList = new ArrayList<CATEGORYGOODS>();
    public ArrayList<PLAYER> playersList = new ArrayList<PLAYER>();

    String pkName;

    public String rootpath;

    public HomeModel(Context context) {
        super(context);
        pkName = mContext.getPackageName();

        rootpath = context.getCacheDir() + "/ECMobile/cache";

        readHomeDataCache();
        readGoodsDataCache();
    }

    public void readHomeDataCache() {

        String path = rootpath + "/" + pkName + "/homeData.dat";
        File f1 = new File(path);
        if (f1.exists()) {
            try {
                InputStream is = new FileInputStream(f1);
                InputStreamReader input = new InputStreamReader(is, "UTF-8");
                BufferedReader bf = new BufferedReader(input);

                homeDataCache(bf.readLine());

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

    }

    public String homeDataCache() {
        String path = rootpath + "/" + pkName + "/homeData.dat";
        File f1 = new File(path);
        String s = null;
        if (f1.exists()) {
            try {
                InputStream is = new FileInputStream(f1);
                InputStreamReader input = new InputStreamReader(is, "UTF-8");
                BufferedReader bf = new BufferedReader(input);

                s = bf.readLine();

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

        return s;
    }

    public void readGoodsDataCache() {
        String path = rootpath + "/" + pkName + "/goodsData.dat";
        File f1 = new File(path);
        if (f1.exists()) {
            try {
                InputStream is = new FileInputStream(f1);
                InputStreamReader input = new InputStreamReader(is, "UTF-8");
                BufferedReader bf = new BufferedReader(input);

                goodsDataCache(bf.readLine());

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

    }


    public void homeDataCache(String result) {

        try {
            if (result != null) {
                JSONObject jsonObject = new JSONObject(result);

                homedataResponse response = new homedataResponse();
                response.fromJson(jsonObject);
                if (response.status.succeed == 1) {
                    HOME_DATA data = response.data;
                    if (null != data) {
                        ArrayList<PLAYER> players = data.player;
                        if (null != players && players.size() > 0) {
                            playersList.clear();

                            playersList.addAll(players);

                        }

                        ArrayList<SIMPLEGOODS> promote_goods = data.promote_goods;

                        if (null != promote_goods && promote_goods.size() > 0) {
                            simplegoodsList.clear();

                            simplegoodsList.addAll(promote_goods);

                        }

                    }

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void goodsDataCache(String result) {

        try {
            if (result != null) {
                JSONObject jsonObject = new JSONObject(result);
                homecategoryResponse response = new homecategoryResponse();
                response.fromJson(jsonObject);
                if (response.status.succeed == 1) {
                    ArrayList<CATEGORYGOODS> simplegoodses = response.data;
                    if (null != simplegoodses && simplegoodses.size() > 0) {
                        categorygoodsList.clear();
                        categorygoodsList.addAll(simplegoodses);


                    }
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void fetchHotSelling() {
        homedataRequest request = new homedataRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                try {
                    homedataResponse response = new homedataResponse();
                    response.fromJson(jo);
                    if (response.status.succeed == 1) {
                        fileSave(jo.toString(), "homeData");

                        HOME_DATA home_data = response.data;
                        if (null != home_data) {
                            ArrayList<PLAYER> players = home_data.player;
                            if (null != players && players.size() > 0) {
                                playersList.clear();

                                playersList.addAll(players);

                            }

                            ArrayList<SIMPLEGOODS> promote_goods = home_data.promote_goods;

                            if (null != promote_goods && promote_goods.size() > 0) {
                                simplegoodsList.clear();

                                simplegoodsList.addAll(promote_goods);

                            } else {
                                simplegoodsList.clear();
                            }

                            HomeModel.this.OnMessageResponse(url, jo, status);

                        }
                    }

                } catch (JSONException e) {
                }

            }

        };

        cb.url(ApiInterface.HOME_DATA).type(JSONObject.class);

        aq.ajax(cb);

    }

    public void fetchCategoryGoods() {
        homecategoryRequest request = new homecategoryRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                done(url, jo, status);
                try {
                    homecategoryResponse response = new homecategoryResponse();
                    response.fromJson(jo);
                    if (jo != null) {


                        if (response.status.succeed == 1) {
                            fileSave(jo.toString(), "goodsData");
                            ArrayList<CATEGORYGOODS> simplegoodses = response.data;
                            if (null != simplegoodses && simplegoodses.size() > 0) {
                                categorygoodsList.clear();
                                categorygoodsList.addAll(simplegoodses);

                                HomeModel.this.OnMessageResponse(url, jo, status);
                            }
                        } else {
                            categorygoodsList.clear();
                        }


                    }

                } catch (JSONException e) {
                }

            }

        };

        cb.url(ApiInterface.HOME_CATEGORY).type(JSONObject.class);
        aq.ajax(cb);

    }


    protected void done(String url, JSONObject jo, AjaxStatus status) {
        String localUrl = url;
        JSONObject result = jo;
    }

    public String web;

    public void helpArticle(int article_id) {

        articleRequest request = new articleRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                HomeModel.this.callback(url, jo, status);

                try {
                    articleResponse response = new articleResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        web = response.data;

                        HomeModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        request.session = SESSION.getInstance();
        request.article_id = article_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ARTICLE).type(JSONObject.class).params(params);
        aq.ajax(cb);

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

}
