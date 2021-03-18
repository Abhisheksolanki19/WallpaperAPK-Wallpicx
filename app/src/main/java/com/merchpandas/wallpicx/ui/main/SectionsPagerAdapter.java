package com.merchpandas.wallpicx.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.merchpandas.wallpicx.Fragments.ExploreFragment;
import com.merchpandas.wallpicx.Fragments.FavouriteFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private final Context mContext;
    TabLayout tablayout;
    private ViewPager mViewpager;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ExploreFragment();
                break;
            case 1:
                fragment = new FavouriteFragment(mContext);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }


}