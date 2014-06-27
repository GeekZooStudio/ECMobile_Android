package com.insthub.BeeFramework.model;

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

import java.util.ArrayList;

import android.content.res.Resources;
import com.insthub.ecmobile.ErrorCodeConst;
import com.insthub.ecmobile.fragment.E0_ProfileFragment;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.A0_SigninActivity;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.STATUS;

public class BaseModel implements BusinessResponse{

    protected com.insthub.BeeFramework.model.BeeQuery aq ;
    protected ArrayList<BusinessResponse > businessResponseArrayList = new ArrayList<BusinessResponse>();
    protected Context mContext;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;

    public BaseModel()
    {

    }

    public BaseModel(Context context)
    {
        aq = new BeeQuery(context);
        mContext = context;
    }
    protected void saveCache()
    {
        return ;
    }

    protected void cleanCache()
    {
        return ;
    }

    public void addResponseListener(BusinessResponse listener)
    {
        if (!businessResponseArrayList.contains(listener))
        {
            businessResponseArrayList.add(listener);
        }
    }

    public void removeResponseListener(BusinessResponse listener)
    {
        businessResponseArrayList.remove(listener);
    }

    //公共的错误处理
    public void callback(String url, JSONObject jo, AjaxStatus status)
    {
        if (null == jo)
        {
            ToastView toast = new ToastView(mContext,"网络错误，请检查网络设置");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        try
        {
            STATUS responseStatus = new STATUS();
            responseStatus.fromJson(jo.optJSONObject("status"));
            if (responseStatus.succeed != ErrorCodeConst.ResponseSucceed)
            {
                if (responseStatus.error_code == ErrorCodeConst.InvalidSession)
                {
                    ToastView toast = new ToastView(mContext, mContext.getString(R.string.session_expires_tips));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    E0_ProfileFragment.isRefresh=true;
                    Intent intent = new Intent(mContext, A0_SigninActivity.class);
                    mContext.startActivity(intent);
                    
                    shared = mContext.getSharedPreferences("userInfo", 0); 
            		editor = shared.edit();
                    
                    editor.putString("uid", "");
		            editor.putString("sid", "");
		            editor.commit();
		            SESSION.getInstance().uid = shared.getString("uid", "");
		            SESSION.getInstance().sid = shared.getString("sid", "");
                    
                }
                
            }

            
            Resources resource = mContext.getResources();
            String way=resource.getString(R.string.delivery);
            String col=resource.getString(R.string.collected);
            String und=resource.getString(R.string.understock);
            String been=resource.getString(R.string.been_used);
            String sub=resource.getString(R.string.submit_the_parameter_error);
            String fai=resource.getString(R.string.failed);
            String pur=resource.getString(R.string.error_10008);
            String noi=resource.getString(R.string.no_shipping_information);
            String error=resource.getString(R.string.username_or_password_error);
            if(responseStatus.error_code == ErrorCodeConst.SelectedDeliverMethod) {
            	ToastView toast = new ToastView(mContext, way);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.AlreadyCollected) {
            	//Toast.makeText(mContext, "您已收藏过此商品", Toast.LENGTH_SHORT).show();
            	ToastView toast = new ToastView(mContext,col);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.InventoryShortage) {
            	ToastView toast = new ToastView(mContext, und);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.UserOrEmailExist) {
            	ToastView toast = new ToastView(mContext, been);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.InvalidParameter) {
            	ToastView toast = new ToastView(mContext, sub);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.ProcessFailed) {
            	ToastView toast = new ToastView(mContext, fai);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.BuyFailed) {
            	ToastView toast = new ToastView(mContext, pur);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            
            if(responseStatus.error_code == ErrorCodeConst.NoShippingInformation) {
            	ToastView toast = new ToastView(mContext, noi);
            	toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            if(responseStatus.error_code==ErrorCodeConst.InvalidUsernameOrPassword){
                ToastView toast = new ToastView(mContext, error);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            if(responseStatus.error_code==ErrorCodeConst.UserOrEmailExist){
                ToastView toast = new ToastView(mContext, resource.getString(R.string.been_used));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        catch (JSONException e)
        {

        }

    }

    public  void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
        for (BusinessResponse iterable_element : businessResponseArrayList)
        {
            iterable_element.OnMessageResponse(url,jo,status);
        }
    }
}
