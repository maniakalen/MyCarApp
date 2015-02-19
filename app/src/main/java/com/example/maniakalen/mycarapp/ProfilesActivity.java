package com.example.maniakalen.mycarapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;


public class ProfilesActivity extends ActionBarActivity
        implements ProfilesFragment.OnProfilesFragmentInteractionListener,
        AddProfileFragment.OnAddProfileFragmentListener {

    ProfilesFragment listFragment;
    AddProfileFragment addProfileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        listFragment = ProfilesFragment.newInstance();
        transaction.replace(R.id.fragment_profiles_placeholder, listFragment );
        transaction.addToBackStack(null);
        transaction.commit();

        addProfileFragment = new AddProfileFragment();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiles, menu);
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
        if (id == R.id.action_expenses) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.action_profiles_add) {
            getFragmentManager()
                    .beginTransaction()
                    //.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.fragment_profiles_placeholder_add, addProfileFragment)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProfilesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAddProfileFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSelectImageClick(View view) {
        addProfileFragment.onPhotoSelection(view);
    }
    public void addNewProfileEntry(View view) {
        addProfileFragment.addNewProfile(view);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == AddProfileFragment.TAKE_PHOTO_CODE) {
                savePhotoFile(data);
            }
        }
    }

    public void savePhotoFile(Intent data) {
        addProfileFragment.savePhotoFile(data);
    }
    public void notifyDataChanged() {
        listFragment.notifyDataChanged();
    }
}
