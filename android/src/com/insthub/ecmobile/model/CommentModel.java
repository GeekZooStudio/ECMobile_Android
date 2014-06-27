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

import com.insthub.ecmobile.protocol.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import org.w3c.dom.Comment;

public class CommentModel extends BaseModel {

    public PAGINATED paginated;

    public ArrayList<COMMENTS> comment_list = new ArrayList<COMMENTS>();

    public CommentModel(Context context) {
        super(context);

    }

    public void getCommentList(int goods_id) {
        commentsRequest request = new commentsRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                CommentModel.this.callback(url, jo, status);
                try {
                    commentsResponse response = new commentsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<COMMENTS> data = response.data;
                            comment_list.clear();
                            if (null != data && data.size() > 0) {
                                comment_list.clear();
                                for (int i = 0; i < data.size(); i++) {
                                    comment_list.addAll(data);
                                }
                            }

                            paginated = response.paginated;

                        }

                        CommentModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = 1;
        pagination.count = 10;
        request.session = SESSION.getInstance();
        request.pagination = pagination;
        request.goods_id = goods_id;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.COMMENTS).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

    public void getCommentsMore(int goods_id) {
        commentsRequest request = new commentsRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                CommentModel.this.callback(url, jo, status);
                try {
                    commentsResponse response = new commentsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<COMMENTS> data = response.data;
                            if (null != data && data.size() > 0) {
                                comment_list.addAll(data);
                            }
                            paginated = response.paginated;
                        }
                        CommentModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };


        PAGINATION pagination = new PAGINATION();
        pagination.page = comment_list.size() / 10 + 1;
        pagination.count = 10;
        request.session = SESSION.getInstance();
        request.pagination = pagination;
        request.goods_id = goods_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.COMMENTS).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

}
