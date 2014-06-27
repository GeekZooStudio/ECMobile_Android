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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class CollectListModel extends BaseModel {

	public ArrayList<COLLECT_LIST> collectList = new ArrayList<COLLECT_LIST>();
	
	public PAGINATED paginated;
	public CollectListModel(Context context) {
		super(context);
		 
	}
	
	public void getCollectList() {
        usercollectlistRequest request = new usercollectlistRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                CollectListModel.this.callback(url, jo, status);

                try {
                    usercollectlistResponse response = new usercollectlistResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<COLLECT_LIST> data = response.data;
                            collectList.clear();
                            if (null != data && data.size() > 0) {
                                collectList.clear();
                                collectList.addAll(data);

                            }
                            paginated = response.paginated;
                            CollectListModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();
        PAGINATION pagination = new PAGINATION();
        pagination.page = 1;
        pagination.count = 10;

        request.session = session;
        request.pagination = pagination;
        request.rec_id = 0;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.USER_COLLECT_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
	
	
	public void getCollectListMore() {
        usercollectlistRequest request = new usercollectlistRequest();
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				CollectListModel.this.callback(url, jo, status);
				
				try {
                    usercollectlistResponse response = new usercollectlistResponse();
                    response.fromJson(jo);
					if(response.status.succeed == 1) {
                        ArrayList<COLLECT_LIST> data = response.data;
                        if (null != data && data.size() > 0) {
                                collectList.addAll(data);

                        }
                        paginated = response.paginated;
                        CollectListModel.this.OnMessageResponse(url, jo, status);
					}
					
				} catch (JSONException e) {
					 
					e.printStackTrace();
				}
				
			}

		};

		SESSION session = SESSION.getInstance();
		PAGINATION pagination = new PAGINATION();
		pagination.page = 1;
		pagination.count = 10;
        request.session=session;
        request.pagination=pagination;
        request.rec_id=Integer.parseInt(collectList.get(collectList.size()-1).rec_id);

		Map<String, String> params = new HashMap<String, String>();
		try
		{
            params.put("json",request.toJson().toString());
		} catch (JSONException e) {
			// TODO: handle exception
		}
		cb.url(ApiInterface.USER_COLLECT_LIST).type(JSONObject.class).params(params);
		aq.ajax(cb);
		
	}
	
	
	// 删除收藏商品
	public void collectDelete(String rec_id) {
        usercollectdeleteRequest request=new usercollectdeleteRequest();
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

                CollectListModel.this.callback(url, jo, status);

                try {
                    usercollectdeleteResponse response = new usercollectdeleteResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            CollectListModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        };
        request.rec_id=rec_id;
        request.session=SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            params.put("json",request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
	    cb.url(ApiInterface.USER_COLLECT_DELETE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
			
	}

}
