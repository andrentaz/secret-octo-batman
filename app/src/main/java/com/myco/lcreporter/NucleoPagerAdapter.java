package com.myco.lcreporter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by nakagaki on 20/02/2015.
 */
public class NucleoPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mScreen = new Fragment[2];

    public NucleoPagerAdapter(FragmentManager fm) {
        super(fm);
        mScreen[0] = new MainFragment();
        mScreen[1] = new EqpFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return mScreen[position];
    }

    @Override
    public int getCount() {
        return MainActivity.NUM_PAGES;
    }

    public void setInfo() {
        ((MainFragment) this.mScreen[0]).setSettInfo();
        ((EqpFragment) this.mScreen[1]).setSettInfo();
    }

}
