package com.insthub.ecmobile.fragment;

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

import android.app.Activity;
import android.util.Log;
import android.widget.*;
import com.external.maxwin.view.XListView;
import com.iflytek.cloud.speech.*;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.insthub.BeeFramework.Utils.JsonParser;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.ShareConst;
import com.insthub.ecmobile.activity.D2_FilterActivity;
import com.insthub.ecmobile.activity.D1_CategoryActivity;
import com.insthub.ecmobile.adapter.D0_CategoryAdapter;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.FILTER;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import android.content.res.Resources;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView.OnEditorActionListener;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyViewGroup;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.model.ProtocolConst;
import com.insthub.ecmobile.model.SearchModel;

public class D0_CategoryFragment extends BaseFragment implements OnClickListener, BusinessResponse {
	
	private View view;
	private ImageView search;
	private EditText input;
	private ImageButton voice;
	
	private SearchModel searchModel;
	
	private Button btn[];
	private MyViewGroup layout;

    private XListView parentListView;

    D0_CategoryAdapter parentListAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
		view = inflater.inflate(R.layout.d0_category, null);
		
		input = (EditText) view.findViewById(R.id.search_input);
		search = (ImageView) view.findViewById(R.id.search_search);
		voice = (ImageButton) view.findViewById(R.id.search_voice);
		layout = (MyViewGroup) view.findViewById(R.id.search_layout);

        parentListView = (XListView) view.findViewById(R.id.parent_list);

		search.setOnClickListener(this);
		voice.setOnClickListener(this);

        input.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				 
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    try
                    {
                        Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                        FILTER filter = new FILTER();
                        filter.keywords = input.getText().toString().toString();
                        intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_right_in,
                                R.anim.push_right_out);
                        return true;

                    }
                    catch (JSONException e)
                    {

                    }
				}
				return false;
			}
		});


		if (null == searchModel)
        {
            searchModel = new SearchModel(getActivity());
            searchModel.searchCategory();
        }

		searchModel.addResponseListener(this);

        parentListAdapter = new D0_CategoryAdapter(getActivity(),searchModel.categoryArrayList);
        parentListView.setAdapter(parentListAdapter);
        parentListView.setPullLoadEnable(false);
        parentListView.setPullRefreshEnable(false);
        parentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position -1 < searchModel.categoryArrayList.size())
                {
                    CATEGORY category = searchModel.categoryArrayList.get(position -1);
                    try
                    {
                        if (category.children.size() > 0)
                        {
                            Intent it = new Intent(getActivity(),D1_CategoryActivity.class);
                            it.putExtra("category",category.toJson().toString());
                            it.putExtra("category_name", searchModel.categoryArrayList.get(position-1).name);
                            getActivity().startActivity(it);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                            FILTER filter = new FILTER();
                            filter.category_id = String.valueOf(category.id);
                            intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

		
		addKeywords();
		
		return view;
	} 

	@Override
	public void onClick(View v) {
		 
		String tag;
		Intent intent;
		switch(v.getId())
        {
            case R.id.search_search:

                break;
            case R.id.search_voice:
            {
                showRecognizerDialog(); //弹出语音搜索框

                break;
            }
		}
		
	}
	
	// 动态添加button，并设置监听
	public void addKeywords() {
		
		if(searchModel.list.size() > 0) {
			
			layout.removeAllViews();
			btn = new Button[searchModel.list.size()];
			for(int i=0; i<searchModel.list.size(); i++)
            {
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.button_view, null);
				btn[i] = (Button) view.findViewById(R.id.search_keyword);
				btn[i].setText(searchModel.list.get(i).toString());
				layout.addView(view);
			}
			
			for(int k=0;k<searchModel.list.size();k++) {
				btn[k].setTag(k);
				btn[k].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						 
						int a = (Integer) v.getTag();
                        try
                        {
                            Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                            FILTER filter = new FILTER();
                            filter.keywords = btn[a].getText().toString();
                            intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        catch (JSONException e)
                        {

                        }

					}
				});
			}
		}
		
	}
	
	public void showRecognizerDialog() {
        String appid=EcmobileManager.getXunFeiCode(getActivity());
        if(appid!=null&&!"".equals(appid)) {
            //用户登录
            SpeechUser.getUser().login(getActivity(), null, null
                    , "appid=" + EcmobileManager.getXunFeiCode(getActivity()), listener);
            final RecognizerDialog recognizerDialog = new RecognizerDialog(getActivity());
            //设置引擎为转写
            recognizerDialog.setParameter(SpeechConstant.DOMAIN, "iat");
            //设置识别语言为中文
            recognizerDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            //设置方言为普通话
            recognizerDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
            //设置录音采样率为
            recognizerDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
            //设置监听对象
            recognizerDialog.setListener(new RecognizerDialogListener() {
                @Override
                public void onResult(RecognizerResult results, boolean b) {
                    String text = JsonParser.parseIatResult(results.getResultString());
                    if (text.length() > 0) {
                        input.setText(text.toString());
                        input.setSelection(input.getText().length());
                        try {
                            recognizerDialog.setListener(null);
                            Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                            FILTER filter = new FILTER();
                            filter.keywords = input.getText().toString();
                            intent.putExtra(B1_ProductListActivity.FILTER, filter.toJson().toString());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        } catch (JSONException e) {
                        }
                    }
                }

                @Override
                public void onError(SpeechError speechError) {

                }
            });
            //开始识别
            recognizerDialog.show();
        }
	}

	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
		if (url.endsWith(ApiInterface.SEARCHKEYWORDS))
        {
			addKeywords();
		}
        else if (url.endsWith(ApiInterface.CATEGORY))
        {
            parentListAdapter.notifyDataSetChanged();
        }
	}


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        input.setText("");
        MobclickAgent.onPageStart("Search");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Search");
    }
    private SpeechListener listener = new SpeechListener()
    {

        @Override
        public void onData(byte[] arg0) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if(error != null) {
                Toast.makeText(getActivity(), "登陆失败"
                        , Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
        }
    };
}
