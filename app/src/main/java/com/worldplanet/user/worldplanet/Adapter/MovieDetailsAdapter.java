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
import com.worldplanet.user.worldplanet.MusicDetailsModel.Artist;
import com.worldplanet.user.worldplanet.MusicDetailsModel.MovieWise;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Movies;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.ArtistsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.MovieDetailsActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieDetailsAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    private List<MovieWise> movies;
    private List<MovieWise> filterMoviesList;
    private LayoutInflater inflater = null;
    public MovieDetailsAdapter(MovieDetailsActivity context, List<MovieWise> movies) {
        this.context = context;
        this.movies= movies;
        this.filterMoviesList = new ArrayList<>();       //Initialise filter List
        this.filterMoviesList.addAll(movies);            //Add all items of array list to filter list
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
            view = inflater.inflate(R.layout.details_alar_layout, null);
        }
        final TextView alar_Name= (TextView) view.findViewById(R.id.alar_Name);
        final TextView alar_details= (TextView) view.findViewById(R.id.alar_details);
        ImageView alar_image= (ImageView) view.findViewById(R.id.alar_image);
        LinearLayout ll_album_list= (LinearLayout) view.findViewById(R.id.ll_album_list);

        alar_Name.setText(movies.get(i).getMovieName());
        alar_details.setText(movies.get(i).getSongName());

        Picasso.with(context)
                .load(movies.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(alar_image);
       /* ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                intent.putExtra("Song_Name",alar_Name.getText().toString());
                intent.putExtra("Song_Path",movies.get(i).getSongPath());
                context.startActivity(intent);
            }
        });*/

        return view;
    }
    //Filterclass to filter data

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