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
import com.challenge.androidchallenge.Utility.BlurTransformation;
import com.challenge.androidchallenge.Utility.SessionManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class QuestFragmentPage extends Fragment implements View.OnClickListener{

    Activity mActivity;

    SessionManager session;

    ImageView ivGiverImage, ivGiverImageBackground, ivQuestCompleted;
    TextView tvQuestName, tvQuestCompleted, tvQuestDescription, tvQuestGiverName;
    //TextView Button to mark a quest completed
    TextView tbCompleteQuest;

    DetailedKingdom kingdom;
    int questNumber;

    public QuestFragmentPage() {
        // Required empty public constructor
    }

    public static QuestFragmentPage newInstance(DetailedKingdom kingdom, int questNumber) {
        QuestFragmentPage fragment = new QuestFragmentPage();
        fragment.setKingdom(kingdom);
        fragment.setQuestNumber(questNumber);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quest_page, container, false);

        //Get the session
        session = new SessionManager(getActivity());

        //Set up variables and look needed for this fragment
        init(view);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbCompleteQuest:
                //When the button is clicked, check if the quest has been completed or not yet
                //If it has not been completed set the quest as completed and then alter the tvQuestCompleted view
                //to say that it is now completed. Finally increment the number of quests completed for the kingdom
                if(!session.isQuestCompleted(kingdom.getQuests().get(questNumber).getId().toString())) {
                    session.setQuestCompleted(kingdom.getQuests().get(questNumber).getId().toString(), true);
                    session.incrementCompletedQuestsAmount(kingdom.getId().toString());
                    tbCompleteQuest.setVisibility(View.GONE);
                    tvQuestCompleted.setVisibility(View.VISIBLE);
                    ivQuestCompleted.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    //Helper method to initialize variables and look needed for this fragment
    private void init(View view){
        //Get the parent activity of the fragment
        mActivity = getActivity();

        tvQuestName = (TextView) view.findViewById(R.id.tvQuestName);
        tvQuestDescription = (TextView) view.findViewById(R.id.tvQuestDescription);
        tvQuestCompleted = (TextView) view.findViewById(R.id.tvQuestCompleted);

        ivGiverImage = (ImageView) view.findViewById(R.id.ivGiverImage);
        ivGiverImageBackground = (ImageView) view.findViewById(R.id.ivGiverImageBackground);
        tvQuestGiverName = (TextView) view.findViewById(R.id.tvQuestGiverName);

        ivQuestCompleted = (ImageView) view.findViewById(R.id.ivQuestCompleted);
        tbCompleteQuest = (TextView) view.findViewById(R.id.tbCompleteQuest);
        tbCompleteQuest.setOnClickListener(this);

        //If the quest is completed set the text and images to show that is is completed
        if(session.isQuestCompleted(kingdom.getQuests().get(questNumber).getId().toString())) {
            tbCompleteQuest.setVisibility(View.GONE);

        } else {
            ivQuestCompleted.setVisibility(View.GONE);
            tvQuestCompleted.setVisibility(View.GONE);
        }

        tvQuestName.setText(kingdom.getQuests().get(questNumber).getName());

        //If the quest description length is not 0, set the quest description
        //Else there is no description for the quest so set the description as noQuestDescription
        if(kingdom.getQuests().get(questNumber).getDescription().length() != 0) {
            tvQuestDescription.setText(kingdom.getQuests().get(questNumber).getDescription());
        } else {
            tvQuestDescription.setText(mActivity.getString(R.string.noQuestDescription));
        }

        tvQuestGiverName.setText(kingdom.getQuests().get(questNumber).getGiver().getName());

        //Load the header background image
        Picasso.with(mActivity)
                .load(kingdom.getQuests().get(questNumber).getGiver().getImage())
                .error(R.drawable.no_image_large) //If there is an error in loading the image display the No Image, image
                .noFade()
                .transform(new BlurTransformation(mActivity))
                .into(ivGiverImageBackground, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        //Need to hide the background image if it fails. Otherwise we will have overlapping on error images
                        ivGiverImageBackground.setVisibility(View.GONE);
                    }
                });

        //Load the giver image
        Picasso.with(mActivity)
                .load(kingdom.getQuests().get(questNumber).getGiver().getImage())
                .placeholder(R.drawable.image_placeholder_small_centered)
                .error(R.drawable.no_image_small) //If there is an error in loading the image display the No Image, image
                .noFade()
                .into(ivGiverImage);

    }

    //Helper method to set the local DetailedKingdom variable from in newInstance()
    private void setKingdom(DetailedKingdom kingdom){
        this.kingdom = kingdom;
    }

    //Helper method to set the questNumber variable from in newInstance()
    private void setQuestNumber(int questNumber){
        this.questNumber = questNumber;
    }


}
