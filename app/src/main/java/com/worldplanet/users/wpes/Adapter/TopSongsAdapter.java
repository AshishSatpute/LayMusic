package com.worldplanet.users.wpes.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.TopSongsDetailsActivity;
import com.worldplanet.users.wpes.bottomnavigation.HomeFragment;

import java.util.ArrayList;

public class TopSongsAdapter extends RecyclerView.Adapter<TopSongsAdapter.MyViewHolder> {
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

    public TopSongsAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public TopSongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        view.setOnClickListener(HomeFragment.myOnClickListener);

        TopSongsAdapter.MyViewHolder myViewHolder = new TopSongsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final TopSongsAdapter.MyViewHolder holder, final int listPosition) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),TopSongsDetailsActivity.class);
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
