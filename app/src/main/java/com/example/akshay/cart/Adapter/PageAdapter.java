package com.example.akshay.cart.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.akshay.cart.Fragments.ElectronicFragment;
import com.example.akshay.cart.Fragments.SportsFragment;
import com.example.akshay.cart.Fragments.GroceryFragment;

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
                ElectronicFragment tab1 = new ElectronicFragment();
                return tab1;
            case 1:
                GroceryFragment tab2 = new GroceryFragment();
                return tab2;
            case 2:
                SportsFragment tab3 = new SportsFragment();
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
