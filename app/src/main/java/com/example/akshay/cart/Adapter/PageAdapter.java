package com.example.akshay.cart.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.akshay.cart.Fragments.BlankFragment;
import com.example.akshay.cart.Fragments.BlankFragment2;
import com.example.akshay.cart.Fragments.ItemFragment;

/**
 * Created by Akshay on 11/29/2017.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    public PageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                BlankFragment tab1 = new BlankFragment();
                return tab1;
            case 1:
                ItemFragment tab2 = new ItemFragment();
                return tab2;
            case 2:
                BlankFragment2 tab3 = new BlankFragment2();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
