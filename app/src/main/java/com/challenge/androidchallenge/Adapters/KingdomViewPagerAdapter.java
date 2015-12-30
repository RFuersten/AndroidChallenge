package com.challenge.androidchallenge.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.challenge.androidchallenge.DetailedKingdomFragmentPage;
import com.challenge.androidchallenge.QuestFragmentPage;
import com.challenge.androidchallenge.Retrofit.POJO.DetailedKingdom;

/**
 * Created by Ryan on 12/24/2015.
 */
public class KingdomViewPagerAdapter extends FragmentStatePagerAdapter {

    DetailedKingdom kingdom;

    int pageCount;

    public KingdomViewPagerAdapter(FragmentManager fm, DetailedKingdom kingdom) {
        super(fm);
        this.kingdom = kingdom;

        //Number of pages (fragments) to create is the number of quests for the kingdom + the fragment
        //for the detailed kingdom fragment
        this.pageCount = this.kingdom.getQuests().size() + 1;

    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            //If the position is the first position in the view pager, display the detailed kingdom fragment
            case 0: return DetailedKingdomFragmentPage.newInstance(kingdom);
            //Else the rest are quest fragments
            default : return QuestFragmentPage.newInstance(kingdom, position - 1);
        }
    }


}
