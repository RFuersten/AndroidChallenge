package com.challenge.androidchallenge;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.challenge.androidchallenge.Adapters.KingdomViewPagerAdapter;
import com.challenge.androidchallenge.Retrofit.POJO.DetailedKingdom;
import com.challenge.androidchallenge.Retrofit.ServiceClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KingdomFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener{

    Toolbar toolbar;

    Activity mActivity;

    ViewPager vpKingdom;
    KingdomViewPagerAdapter mAdapter;

    //Id of the kingdom clicked on in the KingdomsListFragment
    int kingdomID;
    DetailedKingdom kingdom;

    Button bPreviousPage, bNextPage;

    public KingdomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kingdom, container, false);

        //Set up variables and look needed for this fragment
        init(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPreviousPage:
                //If the current page is != the first page go back a page
                if(vpKingdom.getCurrentItem() > 0) {
                    vpKingdom.setCurrentItem(vpKingdom.getCurrentItem() - 1, true);
                }
                break;
            case R.id.bNextPage:
                //If the current page is != the last page go forward a page
                if(vpKingdom.getCurrentItem() < mAdapter.getCount()) {
                    vpKingdom.setCurrentItem(vpKingdom.getCurrentItem() + 1, true);
                }
                break;
        }
    }


    //Helper method to initialize variables and look needed for this fragment
    private void init(View view){
        //Get the parent activity of the fragment
        mActivity = getActivity();

        //Get the intent passed in from KingdomsListFragment based on what kingdom was clicked on
        //in the RecyclerView. Default is id = 1 if no int extra was passed
        kingdomID =  mActivity.getIntent().getIntExtra("kingdom_id", 1);

        toolbar =  (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);

        //Set the title to nothing very temporary until the fragments in the viewpager can load
        if(((AppCompatActivity) mActivity).getSupportActionBar() != null){
            ((AppCompatActivity) mActivity).getSupportActionBar().setTitle("");
        }

        vpKingdom = (ViewPager) view.findViewById(R.id.vpKingdom);

        bPreviousPage = (Button) view.findViewById(R.id.bPreviousPage);
        bPreviousPage.setClickable(false);
        bPreviousPage.setText("");
        bPreviousPage.setOnClickListener(this);
        bNextPage = (Button) view.findViewById(R.id.bNextPage);
        bNextPage.setOnClickListener(this);

        //Get the kingdom clicked on in KingdomsListFragment from the internet and then set the kingdom with the retrieved data
        //Once kingdom info is gathered getAndSetKingdom() calls finishInit() to finish initializing variables that need
        //information from the kingdom before they can be initialized
        getAndSetKingdom();

    }


    //Method used to retrieve the kingdom clicked on in the KingdomsListFragment from the internet and then set the data
    //to a local variable. Once kingdom is retrieved it finishes initializing variables needed for this fragment
    private void getAndSetKingdom(){
        //Attempt to get the kingdom clicked on in KingdomsListFragment
        Call<DetailedKingdom> call = ServiceClient.get().getKingdom(kingdomID);
        call.enqueue(new Callback<DetailedKingdom>() {
            @Override
            public void onResponse(Call<DetailedKingdom> call, Response<DetailedKingdom> response) {
                //Successfully retrieved the kingdom from the internet.
                kingdom = response.body();

                //Finish initializing variables and items needed for fragment
                finishInit();
            }

            @Override
            public void onFailure(Call<DetailedKingdom> call, Throwable t) {
                //Failed to retrieve the list of kingdoms from the internet
            }
        });

    }

    //Once kingdom is retrieved in getAndSetKingdom() this method is called to finish initializing
    //variables needed for this fragment
    private void finishInit(){
        //Finish setting up the adapter and viewpager after the kingdom data has been retrieved
        mAdapter =  new KingdomViewPagerAdapter(((AppCompatActivity) mActivity).getSupportFragmentManager(),
                kingdom);
        vpKingdom.setAdapter(mAdapter);
        vpKingdom.addOnPageChangeListener(this);

        //Set the title to the kingdom name because the detailed kingdom fragment will always be first
        ((AppCompatActivity) mActivity).getSupportActionBar().setTitle(kingdom.getName());
    }

    //Helper method for onPageSelected to help turn buttons on or off based on which
    //page the user is on in the viewpager
    private void switchButtonsOnOff(int position){
        //If the page position is the first page, remove the Previous button
        if(position == 0){
            bPreviousPage.setClickable(false);
            bPreviousPage.setText("");
        } else {
            bPreviousPage.setClickable(true);
            bPreviousPage.setText(R.string.bPreviousPage);
        }
        //If the page position is the last page, remove the Next button
        if(position == mAdapter.getCount() - 1){
            bNextPage.setClickable(false);
            bNextPage.setText("");
        } else {
            bNextPage.setClickable(true);
            bNextPage.setText(R.string.bNextPage);
        }
    }


    @Override
    public void onPageSelected(int position) {
        //When a new page is selected, switch the previous or next page buttons on or off if it's needed
        switchButtonsOnOff(position);

        switch(position) {
            //If the position is the first position in the view pager, then its a detailed kingdom fragment
            case 0:
                ((KingdomActivity)mActivity).getSupportActionBar().setTitle(kingdom.getName());
                break;
            //Else the rest are quest fragments
            default:
                ((KingdomActivity) mActivity).getSupportActionBar().setTitle(kingdom.getQuests().get(position - 1).getName());
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageScrollStateChanged(int state) { }

}
