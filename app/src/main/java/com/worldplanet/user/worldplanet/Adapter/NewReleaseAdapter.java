package com.worldplanet.user.worldplanet.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldplanet.user.worldplanet.activity.NewReleaseDetailsActivity;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.bottomnavigation.HomeFragment;

import java.util.ArrayList;

public class NewReleaseAdapter extends RecyclerView.Adapter<NewReleaseAdapter.MyViewHolder> {
    private ArrayList<DataModel> dataSet;
    private static final String TAG = "CustomAdapter";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        // TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.iv_songsImage);
        }
    }

    public NewReleaseAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public NewReleaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        view.setOnClickListener(HomeFragment.myOnClickListener);

        NewReleaseAdapter.MyViewHolder myViewHolder = new NewReleaseAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewReleaseAdapter.MyViewHolder holder, final int listPosition) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),NewReleaseDetailsActivity.class);
                v.getContext().startActivity(i);
            }
        });

        TextView textViewName = holder.textViewName;

        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getName());
        Log.e(TAG, "onBindViewHolder: "+(dataSet.get(listPosition).getImage()));

        imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }
}
