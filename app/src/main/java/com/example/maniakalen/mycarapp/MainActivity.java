package com.example.maniakalen.mycarapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity
        implements ItemFragment.OnFragmentInteractionListener,
        AddNewFragment.OnFragmentInteractionListener {

    final int SHOW_PROFILES_LIST = 1;

    AddNewFragment addFragment;
    ItemFragment itemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            addFragment = new AddNewFragment();
            itemFragment = new ItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, itemFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_add) {
            //fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder_add, addFragment)
                    .commit();

            return true;
        }
        if (id == R.id.action_profiles) {
            Intent profiles = new Intent(this, ProfilesActivity.class);
            startActivity(profiles);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
