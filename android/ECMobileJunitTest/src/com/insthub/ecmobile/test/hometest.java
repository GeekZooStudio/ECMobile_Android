package com.insthub.ecmobile.test;

import java.util.List;

import javax.crypto.KeyAgreement;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.A0_SigninActivity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.C1_CheckOutActivity;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.activity.F1_NewAddressActivity;
import com.insthub.ecmobile.component.AdvanceSearchValueCell;
import com.insthub.ecmobile.protocol.BRAND;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.component.CategorySellingCell;
import com.insthub.ecmobile.component.HotSellingCell;
import com.insthub.ecmobile.component.ShopHelpCell;
import com.robotium.solo.Solo;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PointF;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class hometest extends ActivityInstrumentationTestCase2<EcmobileMainActivity> {
	Solo solo;
	public hometest(Class<EcmobileMainActivity> activityClass) {
		super(activityClass);
		
	}
	public hometest() {
		super(EcmobileMainActivity.class);
	}
	@Override
	protected void setUp() throws Exception {
		 
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	protected void tearDown() throws Exception {
		 
		super.tearDown();
	}
	
	public void testProductList() throws InterruptedException 
	{
        Thread.sleep(3000);
        LinearLayout title_right_button = (LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_right_button);
        solo.clickOnView(title_right_button);
        Thread.sleep(5000);
        solo.takeScreenshot();
        ImageView back = (ImageView) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
        solo.clickOnView(back);
        Thread.sleep(3000);


		AbsListView productListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.home_listview);
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        solo.drag(width/2, width/2,height/2,height,1);
        Thread.sleep(5000);

		View childView = productListView.getChildAt(1);
		solo.clickOnView(childView);
		Thread.sleep(5000);		
		ImageView backButtonImageView =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.nav_back_button);
		solo.clickOnView(backButtonImageView);				
		Thread.sleep(5000);		
		
		childView = productListView.getChildAt(2);
		ImageView categoryCellImageView = (ImageView)childView.findViewById(com.insthub.ecmobile.R.id.good_cell_photo_one);
		solo.clickOnView(categoryCellImageView);	
		Thread.sleep(3000);	
		solo.assertCurrentActivity("fail to product list", B2_ProductDetailActivity.class);

		backButtonImageView =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
		solo.clickOnView(backButtonImageView);				
		Thread.sleep(3000);					
		
		solo.scrollDown();
		Thread.sleep(3000);						
		childView = productListView.getChildAt(3);
		
		categoryCellImageView = (ImageView)childView.findViewById(com.insthub.ecmobile.R.id.good_cell_photo_one);
		solo.clickOnView(categoryCellImageView);		
		
		Thread.sleep(3000);
		solo.assertCurrentActivity("fail to product list", B1_ProductListActivity.class);
		backButtonImageView =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.nav_back_button);
		solo.clickOnView(backButtonImageView);				
		Thread.sleep(3000);
		
	}



	
	public void testSearchList() throws InterruptedException 
	{
		ImageView searchImageView=(ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabtwo);
		solo.clickOnView(searchImageView);
		Thread.sleep(3000);
		
		AbsListView categoryListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.parent_list);	
		View childView=categoryListView.getChildAt(1);
		Log.e("test", "the child class is "+ childView.getClass().toString());
		solo.clickOnView(childView);
		Thread.sleep(3000);
		
		AbsListView nullChildListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.child_list);	
		childView=nullChildListView.getChildAt(1);
		solo.clickOnView(childView);
		Thread.sleep(3000);
		
		ImageView backButtonImageView =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.nav_back_button);
		solo.clickOnView(backButtonImageView);				
		Thread.sleep(3000);
		
		AbsListView childListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.child_list);	
		childView=childListView.getChildAt(3);
		solo.clickOnView(childView);
		Thread.sleep(3000);
		
		solo.scrollDown();
		Thread.sleep(3000);
		
		solo.scrollToTop();
		Thread.sleep(3000);
		
		
		TextView filterTabone=(TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.filter_title_tabone);
		solo.clickOnView(filterTabone);
		Thread.sleep(3000);
		
		TextView filterTabtwo=(TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.filter_title_tabtwo);
		solo.clickOnView(filterTabtwo);
		Thread.sleep(3000);
		
		TextView filterTabthree=(TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.filter_title_tabthree);
		solo.clickOnView(filterTabthree);
		Thread.sleep(3000);
		
		AbsListView goodsListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.goods_listview);	
		childView=goodsListView.getChildAt(2).findViewById(com.insthub.ecmobile.R.id.gooditem_photo);
		solo.clickOnView(childView);
		Thread.sleep(3000);
		
		backButtonImageView =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
		solo.clickOnView(backButtonImageView);				
		Thread.sleep(3000);
		
		EditText searchInput =  (EditText)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.search_input);

		solo.enterText(searchInput, "诺基亚");
//		solo.sendKey(KeyEvent.KEYCODE_SEARCH);
		Thread.sleep(3000);
		
		TextView filterTextView=(TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.search_filter);
		solo.clickOnView(filterTextView);
		Thread.sleep(3000);
		
		LinearLayout advanceSearchCellView=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.brand_value);
		ImageView valueImgOne=(ImageView)(advanceSearchCellView.getChildAt(1).findViewById(com.insthub.ecmobile.R.id.specification_value_img_one));
		solo.clickOnView(valueImgOne);				
		Thread.sleep(3000);
		
		solo.scrollToBottom();
		 
		LinearLayout priceCellView=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.price_value);
		if(priceCellView.getChildCount()>1){
			ImageView valueImgTwo=(ImageView)(priceCellView.getChildAt(1).findViewById(com.insthub.ecmobile.R.id.specification_value_img_one));
			solo.clickOnView(valueImgTwo);				
			Thread.sleep(3000);
		}
		
		LinearLayout advanceSearchDone=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_right_button);
		solo.clickOnView(advanceSearchDone);	
		Thread.sleep(3000);
	}
	public void testSignup() throws InterruptedException 
	{
		SharedPreferences shared =solo.getCurrentActivity(). getSharedPreferences("userInfo", 0); 
		Editor editor=shared.edit();
		editor.putString("uid", "");
        editor.putString("sid", "");
        editor.commit();
        SESSION.getInstance().uid = shared.getString("uid", "");
        SESSION.getInstance().sid = shared.getString("sid", "");
		ImageView profileImageView=(ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabfour);
		solo.clickOnView(profileImageView);
		Thread.sleep(3000);
		
	   TextView tvSignin=(TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_name);
	   if(solo.getCurrentActivity().getSharedPreferences("userInfo", 0).getString("uid", "").equals(""))
	  {
	    solo.clickOnView(tvSignin);
	    Thread.sleep(3000);
	    TextView registerButton=(TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.login_register);
	    solo.clickOnView(registerButton);
	    Thread.sleep(3000);
		
		EditText registerName =  (EditText)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.register_name);
		String userName=(int)((Math.random()*9+1)*100000)+"";
		solo.enterText(registerName, userName);
		Thread.sleep(3000);
		
		EditText registerEmail =  (EditText)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.register_email);
		solo.enterText(registerEmail, userName+"@126.com");
		Thread.sleep(3000);
		
		EditText registerPassword =  (EditText)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.register_password1);
		solo.enterText(registerPassword, "123456");
		Thread.sleep(3000);
		
		EditText registerConfirmPassword =  (EditText)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.register_password2);
		solo.enterText(registerConfirmPassword, "123456");
		Thread.sleep(3000);
		
		Button confirmButton=(Button)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.register_register);
		solo.clickOnView(confirmButton);
		Thread.sleep(5000);
	   }
	}
	 public void testProfilePayOrders() throws InterruptedException{
		 checkLogin();	
		 FrameLayout profileHeadPayment=(FrameLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_payment);
		 solo.clickOnView(profileHeadPayment);				
		 Thread.sleep(5000);
			 
		 AbsListView payOrdersListView=(AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.trade_list);
		  if(payOrdersListView.getCount()>2)
			{
				View childView=payOrdersListView.getChildAt(1);
				
				Button tradeItemCheck=(Button)childView.findViewById(com.insthub.ecmobile.R.id.trade_item_check);
				solo.clickOnView(tradeItemCheck);				
				if (solo.waitForDialogToOpen()) {
					solo.sendKey(KeyEvent.KEYCODE_BACK);
					Thread.sleep(3000);

				}
				childView=payOrdersListView.getChildAt(2);
				Button tradeItemOk=(Button)childView.findViewById(com.insthub.ecmobile.R.id.trade_item_ok);
				solo.clickOnView(tradeItemOk);				
				Thread.sleep(3000);
			}
			
			ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
			
			topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
	 }
	 public void testProfileShipOrders() throws InterruptedException{
		    checkLogin();	
		    FrameLayout profileHeadShip=(FrameLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_ship);
			solo.clickOnView(profileHeadShip);				
			Thread.sleep(5000);
			 
			solo.scrollDown();
			Thread.sleep(3000);
			
			ImageView mtopViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
			solo.clickOnView(mtopViewBack);				
			Thread.sleep(3000);
	 }
	 public void testProfileReceiptOrders() throws InterruptedException{
		    checkLogin();	
			FrameLayout profileHeadReceipt=(FrameLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_receipt);
			solo.clickOnView(profileHeadReceipt);				
			Thread.sleep(5000);
			 
			AbsListView receiptOrdersListView=(AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.trade_list);
			if(receiptOrdersListView.getChildCount()>0){
			     View childView=receiptOrdersListView.getChildAt(1);					
			     Button tradeItemCheck=(Button)childView.findViewById(com.insthub.ecmobile.R.id.trade_item_check);
				 solo.clickOnView(tradeItemCheck);				
				 Thread.sleep(5000);
				
				 if(solo.waitForDialogToOpen()){
					 	solo.sendKey(KeyEvent.KEYCODE_BACK);
						Thread.sleep(3000);					
					}	
				 Button tradeItemOk=(Button)childView.findViewById(com.insthub.ecmobile.R.id.trade_item_ok);
				 solo.clickOnView(tradeItemOk);				
				 Thread.sleep(3000);
				 
				 ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
				 solo.clickOnView(topViewBack);				
			     Thread.sleep(3000);
			}
			
			ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
	 }
	 public void testProfileHistoryOrders() throws InterruptedException{
		    checkLogin();	
		    FrameLayout profileHeadHistory=(FrameLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_history);
			solo.clickOnView(profileHeadHistory);				
			Thread.sleep(5000);			 
			solo.scrollDown();
			Thread.sleep(3000);
			
			AbsListView historyOrdersListView=(AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.trade_list);
			if(historyOrdersListView.getChildCount()>0){
			     View childView=historyOrdersListView.getChildAt(1);					
				 Button tradeItemCheck=(Button)childView.findViewById(com.insthub.ecmobile.R.id.trade_item_check);
				 solo.clickOnView(tradeItemCheck);				
				 Thread.sleep(3000);
				 
				 ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
				 solo.clickOnView(topViewBack);				
			     Thread.sleep(3000);
			}
			
			ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
	 }
	 public void testProfileCollect() throws InterruptedException{
		 checkLogin();
		 LinearLayout profileHeadCollect=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_collect);
		 solo.clickOnView(profileHeadCollect);				
		 Thread.sleep(3000);
			
			AbsListView collectListView=(AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.collect_list);
			if(collectListView.getChildCount()>0){
			     View childView=collectListView.getChildAt(1);					
			     LinearLayout collectItem=(LinearLayout)childView.findViewById(com.insthub.ecmobile.R.id.collect_item_layout1);
			 	 solo.clickOnView(collectItem);				
			 	 Thread.sleep(3000);
			 	 
			 	ImageView  topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
				solo.clickOnView(topViewBack);				
				Thread.sleep(3000);
				 
				Button  collectEdit=(Button)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.collect_edit);
				solo.clickOnView(collectEdit);				
				Thread.sleep(3000);
				 
				ImageView collectItemRemove=(ImageView)childView.findViewById(com.insthub.ecmobile.R.id.collect_item_remove1);
				solo.clickOnView(collectItemRemove);				
				Thread.sleep(3000);
				  
				solo.clickOnView(collectEdit);				
				Thread.sleep(3000);
			}
			
		    ImageView 	topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.collect_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
			
			solo.scrollToBottom();
			Thread.sleep(3000);
		
	 }
	 public void testProfileAddressManage() throws InterruptedException{
		    SharedPreferences shared =solo.getCurrentActivity(). getSharedPreferences("userInfo", 0); 
			Editor editor=shared.edit();
			editor.putString("uid", "");
	        editor.putString("sid", "");
	        editor.commit();
	        SESSION.getInstance().uid = shared.getString("uid", "");
	        SESSION.getInstance().sid = shared.getString("sid", ""); 
		    checkLogin();
		    solo.scrollToBottom();
			LinearLayout profileHeadAddressManage=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_address_manage);
			solo.clickOnView(profileHeadAddressManage);				
			Thread.sleep(3000);
			
			ImageView add=(ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.address_manage_add);
			solo.clickOnView(add);				
			Thread.sleep(3000);
			
			if (solo.getCurrentActivity().getClass().equals(F1_NewAddressActivity.class))
	        {
	            EditText name;
	            EditText tel;
	            EditText email;
	            EditText zipCode;

	            name = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_name);
	            solo.enterText(name,"howie");
	            Thread.sleep(1000);
	            tel = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_telNum);
	            solo.enterText(tel,"10086");
	            Thread.sleep(1000);
	            email = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_email);
	            solo.enterText(email,"ecmobile@qq.com");
	            Thread.sleep(1000);
	            zipCode = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_zipCode);
	            solo.enterText(zipCode,"10020");
	            Thread.sleep(1000);

	            LinearLayout area = (LinearLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_area);
	            solo.clickOnView(area);
	            Thread.sleep(3000);
	            solo.clickInList(1);
	            Thread.sleep(3000);
	            solo.clickInList(1);
	            Thread.sleep(3000);
	            solo.clickInList(1);
	            Thread.sleep(3000);
	            solo.clickInList(1);
	            Thread.sleep(3000);

	            EditText  detail = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_detail);
	            solo.enterText(detail,"长安东街甲2号");
	            Thread.sleep(3000);
	          
	            solo.scrollToBottom();
	            Thread.sleep(3000);
	            
	            FrameLayout addAddrss = (FrameLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_add);
	            solo.clickOnView(addAddrss);
	            Thread.sleep(3000);
	        }
			
			 ListView addressManageList=(ListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.address_manage_list);
			 if(addressManageList.getChildCount()>1){
				 View childView=addressManageList.getChildAt(1);					
			     LinearLayout addressManageItem=(LinearLayout)childView.findViewById(com.insthub.ecmobile.R.id.address_manage_item_layout);
			 	 solo.clickOnView(addressManageItem);				
			 	 Thread.sleep(3000);
			 	 solo.scrollToBottom();
			 	 Thread.sleep(3000);
			 	 
			 	 Button addressDefault=(Button)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.address_manage2_default);
			 	 solo.clickOnView(addressDefault);				
			 	 Thread.sleep(3000);
			 }
			
			ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.address_manage_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
	 }
	 public void testProfileHelp() throws InterruptedException{	
	        checkLogin();	 
			LinearLayout profileHelp=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_help);
			solo.clickOnView(profileHelp);				
			Thread.sleep(5000);
			
			ListView helpListView=(ListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.help_listview);
			if(helpListView.getChildCount()>0)
			{
			     ShopHelpCell cell =(ShopHelpCell)helpListView.getChildAt(1);					
			     LinearLayout ShopHelpCell=(LinearLayout)cell.findViewById(com.insthub.ecmobile.R.id.shophelp_item);
			 	 solo.clickOnView(ShopHelpCell);				
			 	 Thread.sleep(3000);
			 	 
			 	 ListView inforListView=(ListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.help_list);
			 	 if(inforListView.getChildCount()>0)
			 	 {
				     View childView2 =(View)helpListView.getChildAt(1);					
				 	 solo.clickOnView(childView2);				
				 	 Thread.sleep(3000);
				 	 
				 	 ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
					 solo.clickOnView(topViewBack);				
					 Thread.sleep(3000);			 	 
			 	}
			    ImageView	topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
				solo.clickOnView(topViewBack);				
				Thread.sleep(3000);
			}
			
		   ImageView	topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
			solo.clickOnView(topViewBack);				
			Thread.sleep(3000);
	 }
	public void testSetting() throws InterruptedException{
   		checkLogin();
		ImageView settingImg=(ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_setting);
		solo.clickOnView(settingImg);
		Thread.sleep(3000);
		
		LinearLayout settingType2=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_type2);
		solo.clickOnView(settingType2);
		Thread.sleep(3000);
		
		LinearLayout settingType3=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_type3);
		solo.clickOnView(settingType3);
		Thread.sleep(3000);
		
		LinearLayout settingType1=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_type1);
		solo.clickOnView(settingType1);
		Thread.sleep(3000);
		ImageView push=(ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_push);
		solo.clickOnView(push);	
		Thread.sleep(3000);
		
		solo.clickOnView(push);
		Thread.sleep(3000);
		solo.scrollDown();
		
		LinearLayout settingMobileLayout=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_mobile_layout);
		solo.clickOnView(settingMobileLayout);
		Thread.sleep(3000);
		
		if(solo.waitForDialogToOpen()){
			solo.sendKey(KeyEvent.KEYCODE_BACK);
			Thread.sleep(3000);
			
		}	
		
		LinearLayout settingOfficialWeb=(LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_official_web);
		solo.clickOnView(settingOfficialWeb);
		Thread.sleep(3000);
		
		ImageView backButtonImageView =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
		solo.clickOnView(backButtonImageView);				
		Thread.sleep(3000);
		
		Button settingExitLogin =  (Button)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.setting_exitLogin);
		solo.clickOnView(settingExitLogin);				
		Thread.sleep(3000);
		
		if(solo.waitForDialogToOpen()){
			solo.sendKey(KeyEvent.KEYCODE_BACK);
			Thread.sleep(3000);
			
		}	
		ImageView topViewBack =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.top_view_back);
		solo.clickOnView(topViewBack);				
		Thread.sleep(3000);
	}
	private void checkLogin() throws InterruptedException {
		ImageView profileImageView=(ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabfour);
   		solo.clickOnView(profileImageView);
   		Thread.sleep(3000);
		TextView profile_head_name =   (TextView) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.profile_head_name);
        if (profile_head_name.getText().equals("Click here to sign in") || profile_head_name.getText().equals("点击此处登录"))
        {
            solo.clickOnView(profile_head_name);
            Thread.sleep(5000);
            solo.assertCurrentActivity("fail to sign in", A0_SigninActivity.class);
            Thread.sleep(5000);

            EditText userName = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.login_name);
            solo.enterText(userName,"howie");
            Thread.sleep(3000);
            EditText password = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.login_password);
            solo.enterText(password,"198546");
            Thread.sleep(3000);
            Button login = (Button) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.login_login);
            solo.clickOnView(login);
            Thread.sleep(5000);
        }
	}

}
