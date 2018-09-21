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
import com.worldplanet.user.worldplanet.MusicDetailsModel.MovieWise;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.MovieDetailsActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieDetailsAdapter2 extends RecyclerView.Adapter<MovieDetailsAdapter2.ViewHolder> {

    public static final String TAG=MovieDetailsAdapter2.class.getCanonicalName();
    private Dialog faredialog;
    private Context context;
    private List<MovieWise> movies;
    private List<MovieWise> filterMoviesList;
    private LayoutInflater inflater = null;
    public MovieDetailsAdapter2(MovieDetailsActivity context, List<MovieWise> movies) {
        Log.i(TAG, "MovieDetailsAdapter2: ");
        this.context = context;
        this.movies= movies;
        this.filterMoviesList = new ArrayList<>();       //Initialise filter List
        this.filterMoviesList.addAll(movies);            //Add all items of array list to filter list
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void onBindViewHolder(@NonNull final MovieDetailsAdapter2.ViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.alar_Name.setText(movies.get(position).getMovieName());
        holder.alar_details.setText(movies.get(position).getSongName());

        Picasso.with(context)
                .load(movies.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(holder.alar_image);
       holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                Intent intent = new Intent(context,PlayMusicActivity2.class);
                intent.putExtra("Song_Name",holder.alar_Name.getText().toString());
                intent.putExtra("Song_Path",movies.get(position).getSongPath());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alar_Name;
        TextView alar_details;
        ImageView alar_image;
        LinearLayout ll_album_list;
        public ViewHolder(View view) {
            super(view);
             alar_Name= view.findViewById(R.id.alar_Name);
            alar_details= view.findViewById(R.id.alar_details);
            alar_image= view.findViewById(R.id.alar_image);
            ll_album_list= view.findViewById(R.id.ll_album_list);
        }
    }


    public void filter(CharSequence stringVar) {

        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        movies.clear();
        if (stringVar.length() == 0) {
            movies.addAll(filterMoviesList);
        } else {
            for (MovieWise wp : filterMoviesList) {
                if (wp.getArtistName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    movies.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
