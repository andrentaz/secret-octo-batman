package com.myco.lcreporter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nakagaki on 20/02/2015.
 */
public class NucleoPagerAdapter extends FragmentPagerAdapter {


    private static final int LAYOUT_NUCLEO = 0;
    private static final int LAYOUT_EQP = 1;
    private static final int LAYOUT_SHEEP = 2;

    public NucleoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ScreenFragment.init(position);
    }

    @Override
    public int getCount() {
        return MainActivity.NUM_PAGES;
    }

}
