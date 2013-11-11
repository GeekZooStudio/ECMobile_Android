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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import com.external.activeandroid.query.Select;
import com.external.androidquery.AQuery;
import com.insthub.BeeFramework.AppConst;
import com.insthub.BeeFramework.Utils.Utils;
import com.insthub.ecmobile.protocol.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class MsgModel extends BaseModel {

	public PAGINATED paginated;
	
	public ArrayList<MESSAGE> msg_list = new ArrayList<MESSAGE>();
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public int unreadCount = 0;

    public static MsgModel Instance;

    public static MsgModel getInstance()
    {
        return Instance;
    }
	public MsgModel(Context context)
    {
		super(context);
        Instance = this;
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
	}

    public void loadCache()
    {
    }

    public int getLatestMessageId()
    {
        return 0;
    }

    public void getUnreadMessageCount()
    {

    }

	public void fetchPre()
    {

    }

    public void fetchNext()
    {

    }
	
}
