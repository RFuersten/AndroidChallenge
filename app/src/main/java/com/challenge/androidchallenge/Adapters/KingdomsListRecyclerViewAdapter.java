package com.challenge.androidchallenge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.challenge.androidchallenge.KingdomActivity;
import com.challenge.androidchallenge.R;
import com.challenge.androidchallenge.Retrofit.POJO.Kingdom;
import com.challenge.androidchallenge.Utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ryan on 12/22/2015.
 */
public class KingdomsListRecyclerViewAdapter extends RecyclerView.Adapter<KingdomsListRecyclerViewAdapter.ViewHolder>{

    Context mContext;

    SessionManager session;

    static List<Kingdom> kingdoms;

    //Constructor for ArrayList of Kingdoms
    public KingdomsListRecyclerViewAdapter(Context context, List<Kingdom> kingdoms) {
        this.kingdoms = kingdoms;

        mContext = context;

        //Get the session
        session = new SessionManager(mContext);
    }

    @Override
    public KingdomsListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kingdom_card_view_item, parent, false);

        //Create and return a new ViewHolder
        return new ViewHolder(view);

    }

    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Kingdom kingdom = kingdoms.get(position);

        //Get the number of quests completed in this kingdom
        Integer questsCompleted = session.getNumberOfCompletedQuests(kingdom.getId().toString());

        //Load the kingdom image
        Picasso.with(mContext)
                .load(kingdom.getImage())
                .placeholder(R.drawable.image_placeholder_small)
                .error(R.drawable.no_image_small) //If there is an error in loading the image display the No Image, image
                .noFade()
                .resize(75, 75)
                .into(viewHolder.ivKingdomImage);

        //Set the kingdom name
        viewHolder.tvKingdomName.setText(kingdom.getName());
        viewHolder.tvQuestsCompleted.setText(questsCompleted.toString());


    }

    //Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return kingdoms.size();
    }


    //Internal ViewHolder class to help with the adapter
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvKingdomName, tvQuestsCompleted;
        public ImageView ivKingdomImage;

        public ViewHolder(View view) {
            super(view);
            tvKingdomName = (TextView) view.findViewById(R.id.tvKingdomName);
            tvQuestsCompleted = (TextView) view.findViewById(R.id.tvQuestsCompleted);
            ivKingdomImage = (ImageView) view.findViewById(R.id.ivKingdomImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int kingdomId = (kingdoms.get(getAdapterPosition()).getId());

            //Start a new KingdomActivity and put the kingdom_id as an extra on the intent
            //KingdomActivity will use this to get the right DetailedKingdom  from the internet
            Intent intent = new Intent(context, KingdomActivity.class);
            intent.putExtra("kingdom_id", kingdomId);
            context.startActivity(intent);
        }
    }


}
