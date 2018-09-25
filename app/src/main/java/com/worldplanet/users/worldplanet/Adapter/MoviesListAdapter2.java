package com.worldplanet.users.worldplanet.Adapter;

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
import com.worldplanet.users.worldplanet.MusicDetailsModel.Movies;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.MovieDetailsActivity;
import com.worldplanet.users.worldplanet.activity.MoviesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoviesListAdapter2 extends RecyclerView.Adapter<MoviesListAdapter2.ViewHolder> {
    public static final String TAG = MoviesListAdapter2.class.getCanonicalName();
    private Dialog faredialog;
    private Context context;
    private List<Movies> movies;
    private List<Movies> filterMovieList;
    private LayoutInflater inflater = null;

    public MoviesListAdapter2(MoviesActivity context, List<Movies> movies) {
        this.movies = movies;
        this.context = context;
        this.filterMovieList = new ArrayList<>();       //Initialise filter List
        this.filterMovieList.addAll(movies);            //Add all items of array list to filter list
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.i(TAG, "MoviesListAdapter2: ");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.albums_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");

        holder.album_Name.setText(movies.get(position).getMovieName());
        //  categoryId.setText(data.getBalance());

        Picasso.with(context)
                .load(movies.get(position).getImage())
                .placeholder(R.drawable.aa)
                .error(R.drawable.aa)
                .fit()
                .into(holder.album_image);
        holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MovieDetailsActivity.class);
                intent.putExtra("Movies_Name",holder.album_Name.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView album_Name;
        ImageView album_image;
        LinearLayout ll_album_list;

        public ViewHolder(View view) {
            super(view);
            album_Name = (TextView) view.findViewById(R.id.album_Name);
            album_image = (ImageView) view.findViewById(R.id.album_image);
            ll_album_list = (LinearLayout) view.findViewById(R.id.ll_album_list);
        }
    }


    //Filterclass to filter data
    public void filter(CharSequence stringVar) {
        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        movies.clear();
        if (stringVar.length() == 0) {
            movies.addAll(filterMovieList);
        } else {
            for (Movies wp : filterMovieList) {
                if (wp.getMovieName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    movies.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
