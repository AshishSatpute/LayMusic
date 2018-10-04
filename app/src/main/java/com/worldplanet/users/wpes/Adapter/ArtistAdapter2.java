package com.worldplanet.users.wpes.Adapter;

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
import com.worldplanet.users.wpes.MusicDetailsModel.Artist;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.ArtistActivity;
import com.worldplanet.users.wpes.activity.ArtistsDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArtistAdapter2  extends RecyclerView.Adapter<ArtistAdapter2.ViewHolder> {
    public static final String TAG=ArtistAdapter2.class.getCanonicalName();

    private Dialog faredialog;
    private Context context;
    private List<Artist> artists;
    private List<Artist> filterArtistList;
    private LayoutInflater inflater = null;
    public ArtistAdapter2(ArtistActivity context, List<Artist> artists) {
        Log.i(TAG, "ArtistAdapter2: ");
        this.context = context;
        this.artists= artists;
        this.filterArtistList = new ArrayList<>();       //Initialise filter List
        this.filterArtistList.addAll(artists);            //Add all items of array list to filter list
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.artist_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArtistAdapter2.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.artist_Name.setText(artists.get(position).getArtistName());
        Picasso.with(context)
                .load(artists.get(position).getImage())
                .placeholder(R.drawable.aaa)
                .error(R.drawable.aaa)
                .fit()
                .into(holder.artist_image);

        holder.ll_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                Intent intent = new Intent(context,ArtistsDetailsActivity.class);
                intent.putExtra("Artist_Name",holder.artist_Name.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ArtistAdapter2");
        return artists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artist_Name;
        ImageView artist_image;
        LinearLayout ll_artist;

        public ViewHolder(View itemView) {
            super(itemView);
            artist_Name= (TextView) itemView.findViewById(R.id.artist_Name);
            artist_image= (ImageView) itemView.findViewById(R.id.artist_image);
            ll_artist= (LinearLayout) itemView.findViewById(R.id.ll_artist);

        }
    }

    public void filter(CharSequence stringVar) {

        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        artists.clear();
        if (stringVar.length() == 0) {
            artists.addAll(filterArtistList);
        } else {
            for (Artist wp : filterArtistList) {
                if (wp.getArtistName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    artists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
