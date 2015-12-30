package com.challenge.androidchallenge;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.challenge.androidchallenge.Adapters.KingdomsListRecyclerViewAdapter;
import com.challenge.androidchallenge.Retrofit.POJO.Kingdom;
import com.challenge.androidchallenge.Retrofit.ServiceClient;
import com.challenge.androidchallenge.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class KingdomsListFragment extends Fragment {

    Toolbar toolbar;

    SessionManager session;

    Activity mActivity;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Kingdom> kingdoms;

    public KingdomsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kingdoms_list, container, false);

        //Tell fragment it has a options menu so it can inflate the menu layout
        setHasOptionsMenu(true);

        //Get the session
        session = new SessionManager(getActivity());

        //Set up variables and look needed for this fragment
        init(view);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //Notify the adapter of a data set change to update the number of quests completed if any
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_kingdoms, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            //User logged out so update the preference file to isSignedUp = false
            session.setSignedUp(false);

            //Clear the registered users information from the preference file
            session.clearPreferenceFile();

            //Bring the user back to the SignupActivity
            startActivity(new Intent(getActivity(), SignupActivity.class));

            //Call finish() on KingdomsActivity to prevent phone back button going back
            //to the KingdomsActivity once the SignupActivity intent has started
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Helper method to initialize variables and look needed for this fragment
    private void init(View view){
        //Get the parent activity of the fragment
        mActivity = getActivity();

        toolbar =  (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);

        //Set the title to the users email stored in preferences
        if(((AppCompatActivity) mActivity).getSupportActionBar() != null){
            ((AppCompatActivity) mActivity).getSupportActionBar().setTitle("Email: " + session.getEmail());
        }

        //Create a new blank ArrayList object for the list of kingdoms
        kingdoms = new ArrayList<>();
        //Get the list of kingdoms from the internet and then set the kingdoms list with the retrieved data
        getAndSetKingdomsList();

        //Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());

        //Create an adapter for the RecyclerView
        mAdapter = new KingdomsListRecyclerViewAdapter(mActivity, kingdoms);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvKingdomsList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    //Method used to retrieve the list of kingdoms from the internet and then set the data
    //to a local variable
    private void getAndSetKingdomsList(){
        //Attempt to get the list of kingdoms
        ServiceClient.get().getKingdoms(new Callback<List<Kingdom>>() {
            @Override
            public void success(List<Kingdom> kingdomsList, Response response) {
                //Successfully retrieved the list of kingdoms from the internet.
                //Now add all kingdoms retrieved to a local variable to be able to use the data
                kingdoms.addAll(kingdomsList);

                //Notify the adapter of a data set change
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void failure(RetrofitError error) {
                //Failed to retrieve the list of kingdoms from the internet
            }
        });
    }

}
