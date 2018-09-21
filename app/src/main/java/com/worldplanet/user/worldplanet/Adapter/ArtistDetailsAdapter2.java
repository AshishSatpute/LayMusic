package com.worldplanet.user.worldplanet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.user.worldplanet.MusicDetailsModel.ArtistWise;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.ArtistsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArtistDetailsAdapter2 extends RecyclerView.Adapter<ArtistDetailsAdapter2.ViewHolder> {
    public static final String TAG = ArtistDetailsAdapter2.class.getCanonicalName();

    private Dialog faredialog;
    private Context context;
    private List<ArtistWise> artists;
    private List<ArtistWise> filterArtistList;
    private LayoutInflater inflater = null;

    public ArtistDetailsAdapter2(ArtistsDetailsActivity context, List<ArtistWise> artists) {
        Log.i(TAG, "ArtistDetailsAdapter2: ");
        this.context = context;
        this.artists = artists;
        this.filterArtistList = new ArrayList<>();       //Initialise filter List
        this.filterArtistList.addAll(artists);            //Add all items of array list to filter list
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.details_alar_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArtistDetailsAdapter2.ViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");

        holder.alar_Name.setText(artists.get(position).getArtistName());
        holder.alar_details.setText(artists.get(position).getSongName());

        Picasso.with(context)
                .load(artists.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(holder.alar_image);
        holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMusicActivity2.class);
                intent.putExtra("Song_Name", holder.alar_Name.getText().toString());
                intent.putExtra("Song_Path", artists.get(position).getSongPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return artists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alar_Name;
        TextView alar_details;
        ImageView alar_image;
        LinearLayout ll_album_list;

        public ViewHolder(View view) {
            super(view);
            alar_Name = view.findViewById(R.id.alar_Name);
            alar_details = view.findViewById(R.id.alar_details);
            alar_image = view.findViewById(R.id.alar_image);
            ll_album_list = view.findViewById(R.id.ll_album_list);

        }
    }


    public void filter(CharSequence stringVar) {
        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        artists.clear();
        if (stringVar.length() == 0) {
            artists.addAll(filterArtistList);
        } else {
            for (ArtistWise wp : filterArtistList) {
                if (wp.getArtistName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    artists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
