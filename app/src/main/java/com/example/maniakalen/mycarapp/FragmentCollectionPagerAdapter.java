package com.example.maniakalen.mycarapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by maniakalen on 29-Mar-15.
 */
public class FragmentCollectionPagerAdapter extends FragmentStatePagerAdapter {
    protected List<CharSequence> titles;
    protected List<Fragment> fragments;

    public FragmentCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        if (fragments.size() <= i) {
            return null;
        }
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addItem(CharSequence title, Fragment fragment) {
        titles.add(title);
        fragments.add(fragment);
    }
}
