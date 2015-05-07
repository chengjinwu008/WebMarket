package com.lanhaijiye.WebMarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

import java.util.ArrayList;

/**
 * Created by CJQ on 2015/5/7.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;

    public PagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
