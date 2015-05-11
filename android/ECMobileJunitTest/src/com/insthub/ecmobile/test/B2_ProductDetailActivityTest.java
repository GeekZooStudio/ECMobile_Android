package com.insthub.ecmobile.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.external.maxwin.view.XListViewCart;
import com.insthub.ecmobile.*;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.*;
import com.robotium.solo.Solo;

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
public class B2_ProductDetailActivityTest extends ActivityInstrumentationTestCase2<EcmobileMainActivity> {

    Solo solo;
    public B2_ProductDetailActivityTest(Class<EcmobileMainActivity> activityClass) {
        super(activityClass);
    }
    public B2_ProductDetailActivityTest()
    {
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

    private void checkSplashAndLoginIn() throws InterruptedException {
        if (solo.getCurrentActivity().getClass().equals(GalleryImageActivity.class))
        {
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
        }

        //登录
        ImageView profileButton =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabfour);
        Thread.sleep(5000);
        solo.clickOnView(profileButton);
        Thread.sleep(5000);

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

    private void handleShoppingCart() throws InterruptedException {


        XListViewCart xlistView = (XListViewCart) solo.getCurrentActivity().findViewById(R.id.shop_car_list);
        View childView = (View)xlistView.getChildAt(1);
        Button change =   (Button) childView.findViewById(R.id.shop_car_item_change);
        solo.clickOnView(change);
        Thread.sleep(3000);
        ImageView sum = (ImageView) childView.findViewById(R.id.shop_car_item_sum);
        solo.clickOnView(sum);
        Thread.sleep(3000);
        solo.clickOnView(change);
        Thread.sleep(3000);


        FrameLayout footer_balance = (FrameLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.shop_car_footer_balance);
        solo.clickOnView(footer_balance);
        Thread.sleep(3000);

        if (solo.getCurrentActivity().getClass().equals(F1_NewAddressActivity.class))
        {
            EditText name;
            EditText tel;
            EditText email;
            EditText phoneNum;
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
            phoneNum = (EditText) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_telNum);
            solo.enterText(phoneNum,"10086");
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

            FrameLayout add = (FrameLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_address_add);
            solo.clickOnView(add);

//            assertTrue(solo.waitForActivity(C1_CheckOutActivity.class));

            Thread.sleep(3000);
            assertTrue(solo.waitForActivity(EcmobileMainActivity.class));
            Thread.sleep(3000);

            footer_balance = (FrameLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.shop_car_footer_balance);
            solo.clickOnView(footer_balance);
            Thread.sleep(3000);

        }

        if(solo.getCurrentActivity().getClass().equals(C1_CheckOutActivity.class))
        {
            Thread.sleep(10000);
            if (solo.waitForDialogToOpen(10000))
            {
                solo.sendKey(KeyEvent.KEYCODE_BACK);
            }

            LinearLayout pay = (LinearLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.balance_pay);
            solo.clickOnView(pay);
            Thread.sleep(3000);
            solo.assertCurrentActivity("fail to C2_PaymentActivity",C2_PaymentActivity.class);
            solo.clickInList(1);
            Thread.sleep(3000);
            solo.assertCurrentActivity("fail to select payment",C1_CheckOutActivity.class);

            LinearLayout dis = (LinearLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.balance_dis);
            solo.clickOnView(dis);
            Thread.sleep(3000);
            solo.assertCurrentActivity("fail to C2_PaymentActivity",C3_DistributionActivity.class);
            solo.clickInList(1);
            Thread.sleep(3000);
            solo.assertCurrentActivity("fail to select payment",C1_CheckOutActivity.class);
            solo.scrollToBottom();
            Thread.sleep(3000);

            FrameLayout submit = (FrameLayout) solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.balance_submit);
            solo.clickOnView(submit);
            assertTrue(solo.waitForDialogToOpen(20000));
            solo.takeScreenshot();
        }
    }

    public void testBuyProduct() throws InterruptedException
    {
        checkSplashAndLoginIn();

        ImageView indexButton =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabone);
        Thread.sleep(5000);
        solo.clickOnView(indexButton);
        Thread.sleep(5000);

        AbsListView productListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.home_listview);
        View childView = productListView.getChildAt(2);
        ImageView categoryCellImageView = (ImageView)childView.findViewById(com.insthub.ecmobile.R.id.good_cell_photo_one);
        solo.clickOnView(categoryCellImageView);
        Thread.sleep(5000);
        solo.assertCurrentActivity("fail to product list", B2_ProductDetailActivity.class);

        solo.scrollToBottom();
        Thread.sleep(3000);
        TextView goodCategoryTextView = (TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.good_category);
        solo.clickOnView(goodCategoryTextView);
        Thread.sleep(5000);
        
        solo.assertCurrentActivity("fail to product list", SpecificationActivity.class);

        ImageView addImageView = (ImageView)solo.getCurrentActivity().findViewById(R.id.shop_car_item_sum);
        solo.clickOnView(addImageView);
        Thread.sleep(1000);
        solo.clickOnView(addImageView);
        Thread.sleep(1000);
        ImageView minusImageView = (ImageView)solo.getCurrentActivity().findViewById(R.id.shop_car_item_min);
        solo.clickOnView(minusImageView);
        Thread.sleep(1000);
        solo.clickOnView(minusImageView);
        Thread.sleep(1000);


        solo.sendKey(KeyEvent.KEYCODE_BACK);
        Thread.sleep(5000);
        solo.scrollToBottom();
        Thread.sleep(5000);
        LinearLayout goodBasicParameterLayout = (LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.good_basic_parameter);
        solo.clickOnView(goodBasicParameterLayout);
        Thread.sleep(5000);
        solo.assertCurrentActivity("fail to product list", B4_ProductParamActivity.class);
        solo.sendKey(KeyEvent.KEYCODE_BACK);
        Thread.sleep(5000);

        TextView addToCartTextView = (TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.add_to_cart);
        solo.clickOnView(addToCartTextView);
        assertTrue(solo.waitForDialogToOpen(5000));

        Thread.sleep(5000);
        solo.sendKey(KeyEvent.KEYCODE_BACK);

        Thread.sleep(3000);
        ImageView cardButton =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabthree);
        solo.clickOnView(cardButton);
        assertTrue(solo.waitForFragmentByTag("tab_three"));
        Thread.sleep(3000);

        handleShoppingCart();

    }


    public void testBuyProductimmediately() throws InterruptedException
    {
        checkSplashAndLoginIn();

        ImageView indexButton =  (ImageView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.toolbar_tabone);
        Thread.sleep(5000);
        solo.clickOnView(indexButton);
        Thread.sleep(5000);

        AbsListView productListView =  (AbsListView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.home_listview);
        View childView = productListView.getChildAt(2);
        ImageView categoryCellImageView = (ImageView)childView.findViewById(com.insthub.ecmobile.R.id.good_cell_photo_one);
        solo.clickOnView(categoryCellImageView);
        Thread.sleep(5000);
        solo.assertCurrentActivity("fail to product list", B2_ProductDetailActivity.class);


        solo.scrollToBottom();
        Thread.sleep(3000);
        TextView goodCategoryTextView = (TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.good_category);
        solo.clickOnView(goodCategoryTextView);
        Thread.sleep(5000);
        solo.assertCurrentActivity("fail to product list", SpecificationActivity.class);

        ImageView addImageView = (ImageView)solo.getCurrentActivity().findViewById(R.id.shop_car_item_sum);
        solo.clickOnView(addImageView);
        Thread.sleep(1000);
        solo.clickOnView(addImageView);
        Thread.sleep(1000);
        ImageView minusImageView = (ImageView)solo.getCurrentActivity().findViewById(R.id.shop_car_item_min);
        solo.clickOnView(minusImageView);
        Thread.sleep(1000);
        solo.clickOnView(minusImageView);
        Thread.sleep(1000);


        solo.sendKey(KeyEvent.KEYCODE_BACK);
        Thread.sleep(5000);
        solo.scrollToBottom();
        Thread.sleep(5000);
        LinearLayout goodBasicParameterLayout = (LinearLayout)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.good_basic_parameter);
        solo.clickOnView(goodBasicParameterLayout);
        Thread.sleep(5000);
        solo.assertCurrentActivity("fail to product list", B4_ProductParamActivity.class);
        solo.sendKey(KeyEvent.KEYCODE_BACK);
        Thread.sleep(5000);

        TextView buyNowTextView = (TextView)solo.getCurrentActivity().findViewById(com.insthub.ecmobile.R.id.buy_now);
        solo.clickOnView(buyNowTextView);
        Thread.sleep(5000);

        handleShoppingCart();

    }



}
