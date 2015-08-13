package com.example.maniakalen.mycarapp.tests;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.MenuItem;

import com.example.maniakalen.mycarapp.MainActivity;
import com.example.maniakalen.mycarapp.R;

/**
 * Created by maniakalen on 04-Jun-15.
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
        public MainActivityTest() {
            super(MainActivity.class);
        }

        protected Intent mLaunchIntent;
        protected MenuItem menu;
        @Override
        protected void setUp() throws Exception {
            super.setUp();
            mLaunchIntent = new Intent(getInstrumentation()
                    .getTargetContext(), MainActivity.class);
            startActivity(mLaunchIntent, null, null);
            final MenuItem menu =
                    (MenuItem) getActivity()
                            .findViewById(R.id.action_add);
        }
    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {

        menu.expandActionView();
    }
}
