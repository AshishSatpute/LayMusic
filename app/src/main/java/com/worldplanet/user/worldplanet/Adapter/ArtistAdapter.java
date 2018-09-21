package com.worldplanet.user.worldplanet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.user.worldplanet.Api.DbSong;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Artist;
import com.worldplanet.user.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.AlbumsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.ArtistActivity;
import com.worldplanet.user.worldplanet.activity.ArtistsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.TopSongsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArtistAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    private List<Artist> artists;
    private List<Artist> filterArtistList;
    private LayoutInflater inflater = null;
    public ArtistAdapter(ArtistActivity context, List<Artist> artists) {
        this.context = context;
        this.artists= artists;
        this.filterArtistList = new ArrayList<>();       //Initialise filter List
        this.filterArtistList.addAll(artists);            //Add all items of array list to filter list

        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return artists.size();
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
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView==null){
            view = inflater.inflate(R.layout.artist_layout, null);
        }
             final TextView artist_Name= (TextView) view.findViewById(R.id.artist_Name);
        ImageView artist_image= (ImageView) view.findViewById(R.id.artist_image);
        LinearLayout ll_artist= (LinearLayout) view.findViewById(R.id.ll_artist);

        artist_Name.setText(artists.get(i).getArtistName());
        //  categoryId.setText(data.getBalance());

        Picasso.with(context)
                .load(artists.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(artist_image);

        ll_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ArtistsDetailsActivity.class);
                intent.putExtra("Artist_Name",artist_Name.getText().toString());
                context.startActivity(intent);
            }
        });
        return view;
    }
    //Filterclass to filter data

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

