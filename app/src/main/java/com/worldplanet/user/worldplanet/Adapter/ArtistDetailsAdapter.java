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
import com.worldplanet.user.worldplanet.MusicDetailsModel.ArtistWise;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.AlbumsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.ArtistsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArtistDetailsAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    private List<ArtistWise> artists;
    private List<ArtistWise> filterArtistList;
    private LayoutInflater inflater = null;
    public ArtistDetailsAdapter(ArtistsDetailsActivity context, List<ArtistWise> artists) {
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
    public View getView(final int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView==null){
            view = inflater.inflate(R.layout.details_alar_layout, null);
        }
        final TextView alar_Name= (TextView) view.findViewById(R.id.alar_Name);
        final TextView alar_details= (TextView) view.findViewById(R.id.alar_details);
        ImageView alar_image= (ImageView) view.findViewById(R.id.alar_image);
        LinearLayout ll_album_list= (LinearLayout) view.findViewById(R.id.ll_album_list);

        alar_Name.setText(artists.get(i).getArtistName());
        alar_details.setText(artists.get(i).getSongName());

        Picasso.with(context)
                .load(artists.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(alar_image);
       /* ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                intent.putExtra("Song_Name",alar_Name.getText().toString());
                intent.putExtra("Song_Path",artists.get(i).getSongPath());
                context.startActivity(intent);
            }
        });*/

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
            for (ArtistWise wp : filterArtistList) {
                if (wp.getArtistName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    artists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
    }
}