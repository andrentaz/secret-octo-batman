package com.myco.lcreporter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nakagaki on 20/02/2015.
 */
public class NucleoPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mScreen = new Fragment[MainActivity.NUM_PAGES];

    public NucleoPagerAdapter(FragmentManager fm) {
        super(fm);
        mScreen[0] = new MainFragment();
        mScreen[1] = new EqpFragment();
        mScreen[2] = new ContactListFragment();
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
