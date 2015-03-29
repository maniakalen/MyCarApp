package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity
        implements ItemFragment.OnFragmentInteractionListener,
        AddNewFragment.OnFragmentInteractionListener {

    final int SHOW_PROFILES_LIST = 1;
    final int SELECT_PROFILE_LIST = 2;
    AddNewFragment addFragment;
    ItemFragment itemFragment;
    protected long profile_id;
    MenuItem selected_profile_icon;

    FragmentCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            itemFragment = new ItemFragment();
            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, itemFragment)
                    .commit();*/

            mDemoCollectionPagerAdapter =
                    new FragmentCollectionPagerAdapter(
                            getSupportFragmentManager());

            mDemoCollectionPagerAdapter.addItem("All", itemFragment);

            mDemoCollectionPagerAdapter.addItem("Fuel", new FuelItemFragment());
            //mDemoCollectionPagerAdapter.addItem("Add Maintenance", new AddNewMaintenanceEntry());
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mDemoCollectionPagerAdapter);



            final ActionBar actionBar = getSupportActionBar();

            // Specify that tabs should be displayed in the action bar.
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            // Create a tab listener that is called when the user changes tabs.
            ActionBar.TabListener tabListener = new ActionBar.TabListener() {
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }

                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                    // hide the given tab
                }

                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                    // probably ignore this event
                }
            };

            // Add 3 tabs, specifying the tab's text and TabListener
            int itemsCount = mDemoCollectionPagerAdapter.getCount();
            for (int i = 0; i < itemsCount; i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mDemoCollectionPagerAdapter.getPageTitle(i))
                                .setTabListener(tabListener));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        selected_profile_icon = menu.findItem(R.id.action_selected_profile);
        (new GetProfileWorker(this)).execute();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.pay_fuel) {
            addFragment = new AddNewFuelEntry();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder_add, addFragment)
                    .addToBackStack(null)
                    .commit();

            return true;
        }
        if (id == R.id.pay_maintenance) {
            addFragment = new AddNewMaintenanceEntry();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder_add, addFragment)
                    .addToBackStack(null)
                    .commit();

        }
        if (id == R.id.pay_ensurance) {
            addFragment = AddNewFragment.newInstance(MyDbHandler.ExpenseType.INSURANCE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder_add, addFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (id == R.id.pay_examination) {
            addFragment = AddNewFragment.newInstance(MyDbHandler.ExpenseType.EXAMINATION);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder_add, addFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (id == R.id.action_profiles) {
            Intent profiles = new Intent(this, ProfilesActivity.class);
            startActivityForResult(profiles, SELECT_PROFILE_LIST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PROFILE_LIST) {
                long id = data.getLongExtra("profile_id", 0);
                this.setProfileId(id);
            }
        }
    }

    private void setSelectedProfilePhoto(long id) {
        if (id > 0) {
            ContentResolver r = getContentResolver();
            Uri sel = ContentUris.withAppendedId(MyCarContentProvider.PROFILE_URI, id);
            String[] proj = {MyDbHandler.COLUMN_PROFILE_PHOTO};
            Cursor imgCursor = r.query(sel, proj, null, null, null);
            imgCursor.moveToFirst();
            String imgStr = imgCursor.getString(0);

            imgCursor.close();
            new BitmapWorkerTask<MenuItem>(selected_profile_icon, r, getResources()).execute(imgStr);
        }
    }

    public void onFragmentInteraction(String id) {

    }

    public void notifyDataChanged()
    {
        itemFragment.notifyDataChanged();
    }

    public void onAddNewItem(View view)
    {
        addFragment.onButtonAddPressed(view);
    }

    public void onRejectNewItem(View view)
    {
        getSupportFragmentManager().beginTransaction()
                .remove(addFragment)
                .commit();
    }

    public long getProfileId() {
        return profile_id;
    }
    public void setProfileId(long id) {
        this.profile_id = id;
        setSelectedProfilePhoto(id);
        notifyDataChanged();
    }
    private class GetProfileWorker extends AsyncTask<Void, Void, Integer> {
        protected ItemFragment.OnFragmentInteractionListener act;
        public GetProfileWorker(ItemFragment.OnFragmentInteractionListener act) {
            this.act = act;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            Activity act = (Activity)this.act;
            Cursor c = act.getContentResolver().query(MyCarContentProvider.PROFILE_URI, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                Integer id = c.getInt(c.getColumnIndex(MyDbHandler.COLUMN_ID));
                c.close();
                return id;
            } else {
                return 0;
            }
        }

        protected void onPostExecute(Integer result) {
            this.act.setProfileId(result);
        }
    }
}
