package com.worldplanet.user.worldplanet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Movies;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.AlbumsActivity;
import com.worldplanet.user.worldplanet.activity.AlbumsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.MovieDetailsActivity;
import com.worldplanet.user.worldplanet.activity.MoviesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoviesListAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    private List<Movies> movies;
    private List<Movies> filterMovieList;
    private LayoutInflater inflater = null;
    public MoviesListAdapter(MoviesActivity context, List<Movies> movies) {
        this.movies= movies;
        this.context = context;
        this.filterMovieList = new ArrayList<>();       //Initialise filter List
        this.filterMovieList.addAll(movies);            //Add all items of array list to filter list

        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView==null){
            view = inflater.inflate(R.layout.albums_layout, null);
        }
        final TextView album_Name= (TextView) view.findViewById(R.id.album_Name);
        ImageView album_image= (ImageView) view.findViewById(R.id.album_image);
        LinearLayout ll_album_list= (LinearLayout) view.findViewById(R.id.ll_album_list);

        album_Name.setText(movies.get(i).getMovieName());
        //  categoryId.setText(data.getBalance());

        Picasso.with(context)
                .load(movies.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(album_image);
        ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MovieDetailsActivity.class);
                intent.putExtra("Movies_Name",album_Name.getText().toString());
                context.startActivity(intent);
            }
        });

        return view;
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