package com.insthub.ecmobile.test;

import com.insthub.BeeFramework.activity.StartActivity;
import com.insthub.ecmobile.activity.GalleryImageActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Scroller;

public class StartActivityTest extends ActivityInstrumentationTestCase2<StartActivity> {
	Solo solo;
	public StartActivityTest(Class<StartActivity> activityClass) {
		super(activityClass);
		 
	}
	
	public StartActivityTest() {
		super(StartActivity.class);
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
	
	public void testClick() throws InterruptedException 
	{
        if (solo.getCurrentActivity().getClass().equals(GalleryImageActivity.class))
        {
            Thread.sleep(5000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(3000);
            solo.scrollToSide(Solo.RIGHT);
            Thread.sleep(5000);
            solo.assertCurrentActivity("Failed to Change to Home","EcmobileMainActivity");
        }

	}

}
