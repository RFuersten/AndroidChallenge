package com.challenge.androidchallenge;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.challenge.androidchallenge.Retrofit.POJO.DetailedKingdom;
import com.squareup.picasso.Picasso;


public class DetailedKingdomFragmentPage extends Fragment{

    Activity mActivity;

    ImageView ivKingdomImage;
    TextView tvKingdomName, tvClimate, tvPopulation, tvQuestsAmount;

    DetailedKingdom kingdom;

    public DetailedKingdomFragmentPage() {
        // Required empty public constructor
    }

    public static DetailedKingdomFragmentPage newInstance(DetailedKingdom kingdom) {
        DetailedKingdomFragmentPage fragment = new DetailedKingdomFragmentPage();
        fragment.setKingdom(kingdom);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed_kingdom_page, container, false);

        //Set up variables and look needed for this fragment
        init(view);

        return view;
    }


    //Helper method to initialize variables and look needed for this fragment
    private void init(View view){
        //Get the parent activity of the fragment
        mActivity = getActivity();

        ivKingdomImage = (ImageView) view.findViewById(R.id.ivKingdomImage);
        tvKingdomName = (TextView) view.findViewById(R.id.tvKingdomName);
        tvClimate = (TextView) view.findViewById(R.id.tvClimate);
        tvPopulation = (TextView) view.findViewById(R.id.tvPopulation);
        tvQuestsAmount = (TextView) view.findViewById(R.id.tvQuestsAmount);

        //Load the kingdom image
        Picasso.with(mActivity)
                .load(kingdom.getImage())
                .placeholder(R.drawable.image_placeholder_large)
                .error(R.drawable.no_image_large) //If there is an error in loading the image display the No Image, image
                .noFade()
                .fit()
                .centerCrop()
                .into(ivKingdomImage);

        tvKingdomName.setText(kingdom.getName());
        tvClimate.setText(kingdom.getClimate());
        tvPopulation.setText(kingdom.getPopulation().toString());
        tvQuestsAmount.setText(new Integer(kingdom.getQuests().size()).toString());
    }

    //Helper method to set the local DetailedKingdom variable from in newInstance()
    private void setKingdom(DetailedKingdom kingdom){
        this.kingdom = kingdom;
    }


}
