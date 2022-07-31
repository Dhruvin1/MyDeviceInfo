package com.example.mydeviceinformation.ui.main;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.mydeviceinformation.R;

import java.lang.reflect.GenericDeclaration;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_1,
            R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // following three lines are the default code
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = Device.newInstance(null, null);
                break;
            case 1:
                fragment = Battery.newInstance(null, null);
                break;
            case 2:
                fragment = Network.newInstance(null, null);
                break;
            case 3:
                fragment = Locations.newInstance(null, null);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}