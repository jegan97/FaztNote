package com.example.jegansbeast.fazt.timetable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jegansbeast.fazt.Database;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class DayPagerAdapter extends FragmentStatePagerAdapter {

    Database.DAYS[] days = Database.DAYS.values();

    public DayPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        DayFragment fragment = new DayFragment();
        fragment.setDay(days[position]);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return days[position].name();
    }

    @Override
    public int getCount() {
        return 6;
    }

}
