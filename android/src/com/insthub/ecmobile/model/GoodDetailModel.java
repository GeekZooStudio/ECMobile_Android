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
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Toast;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoodDetailModel extends BaseModel
{
    public ArrayList<PHOTO> photoList = new ArrayList<PHOTO>();
    public int goodId;
    public GOODS goodDetail = new GOODS();
    public GoodDetailModel(Context context) {
        super(context);
    }

    public void fetchGoodDetail(int goodId)
    {
        String url = ProtocolConst.GOODSDETAIL;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

            	GoodDetailModel.this.callback(url, jo, status);
                try
                {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    PAGINATION pagination = PAGINATION.fromJson(jo.optJSONObject("pagination"));

                    if (responseStatus.succeed == 1)
                    {
                        JSONObject dataJSONObject = jo.optJSONObject("data");

                        if (null != dataJSONObject )
                        {
                            GoodDetailModel.this.goodDetail = GOODS.fromJson(dataJSONObject);
                            GoodDetailModel.this.OnMessageResponse(url, jo, status);
                        }

                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        SESSION session = SESSION.getInstance();
        JSONObject requestJsonObject = new JSONObject();
        Map<String, String> params = new HashMap<String, String>();

        try
        {
            requestJsonObject.put("goods_id",goodId);
            requestJsonObject.put("session",session.toJson());
        }
        catch (JSONException e)
        {

        }

        params.put("json", requestJsonObject.toString());

        cb.url(url).type(JSONObject.class).params(params);
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
    }

    public void cartCreate(int goodId, ArrayList<Integer> specIdList,int goodQuantity)
    {
        String url = ProtocolConst.CARTCREATE;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

            	GoodDetailModel.this.callback(url, jo, status);
                try
                {
                    STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                    PAGINATION pagination = PAGINATION.fromJson(jo.optJSONObject("pagination"));

                    GoodDetailModel.this.callback(url, jo, status);

                    if (responseStatus.succeed == 1)
                    {

                        GoodDetailModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        JSONArray spec = new JSONArray();
        JSONObject requestJsonObject = new JSONObject();
        Map<String, String> params = new HashMap<String, String>();

        try
        {

            requestJsonObject.put("goods_id",goodId);
            requestJsonObject.put("session",SESSION.getInstance().toJson());
            requestJsonObject.put("number",goodQuantity);
            for (int i = 0; i< specIdList.size(); i++)
            {
               Integer specId =  specIdList.get(i);
               spec.put(specId.intValue());
            }

            requestJsonObject.put("spec",spec);

        }
        catch (JSONException e)
        {

        }

        params.put("json", requestJsonObject.toString());

        cb.url(url).type(JSONObject.class).params(params);
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);
    }

   public void collectCreate(int goodId)
   {
       String url = ProtocolConst.COLLECTION_CREATE;

       BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

           @Override
           public void callback(String url, JSONObject jo, AjaxStatus status) {
        	   GoodDetailModel.this.callback(url, jo, status);
               try
               {
                   STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
                   PAGINATION pagination = PAGINATION.fromJson(jo.optJSONObject("pagination"));

                   if (responseStatus.succeed == ErrorCodeConst.ResponseSucceed)
                   {
                       GoodDetailModel.this.OnMessageResponse(url, jo, status);
                       
                   }
                   else if (responseStatus.error_code == ErrorCodeConst.UnexistInformation)
                   {
                       Resources resource = mContext.getResources();
                       String registration_closed = resource.getString(R.string.unexist_information);
                       ToastView toast = new ToastView(mContext, registration_closed);
                       toast.setGravity(Gravity.CENTER, 0, 0);
                       toast.show();
                   }

               } catch (JSONException e) {
                   // TODO: handle exception
               }



           }

       };

       JSONObject requestJsonObject = new JSONObject();
       SESSION session = SESSION.getInstance();
       Map<String, String> params = new HashMap<String, String>();

       try
       {
    	   requestJsonObject.put("session",session.toJson());
           requestJsonObject.put("goods_id",goodId);
       }
       catch (JSONException e)
       {

       }

       params.put("json", requestJsonObject.toString());

       cb.url(url).type(JSONObject.class).params(params);
       ProgressDialog pd = new ProgressDialog(mContext);
       pd.setMessage("请稍后...");
       aq.progress(pd).ajax(cb);
   }
   
   public String goodsWeb;
   public void goodsDesc(int goodId)
   {
       String url = ProtocolConst.GOODSDESC;

       BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

           @Override
           public void callback(String url, JSONObject jo, AjaxStatus status) {

           	GoodDetailModel.this.callback(url, jo, status);
               try
               {
                   STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

                   if (responseStatus.succeed == 1)
                   {
                       goodsWeb = jo.getString("data").toString();

                       
                       GoodDetailModel.this.OnMessageResponse(url, jo, status);
                       

                   }

               } catch (JSONException e) {
                   // TODO: handle exception
               }

           }

       };

       JSONObject requestJsonObject = new JSONObject();
       Map<String, String> params = new HashMap<String, String>();

       try
       {
           requestJsonObject.put("goods_id",goodId);
       }
       catch (JSONException e)
       {

       }

       params.put("json", requestJsonObject.toString());

       cb.url(url).type(JSONObject.class).params(params);
       ProgressDialog pd = new ProgressDialog(mContext);
       pd.setMessage("请稍后...");
       aq.progress(pd).ajax(cb);
   }

}
