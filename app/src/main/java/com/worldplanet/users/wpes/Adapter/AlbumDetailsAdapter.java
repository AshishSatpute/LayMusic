package com.worldplanet.users.wpes.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.wpes.MusicDetailsModel.AlbumWise;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.AlbumsDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlbumDetailsAdapter extends BaseAdapter {
    private Dialog faredialog;
    private Context context;
    private List<AlbumWise> albums;
    private List<AlbumWise> filterAlbumList;
    private LayoutInflater inflater = null;


    public AlbumDetailsAdapter(AlbumsDetailsActivity context, List<AlbumWise> albums) {
        this.context = context;
        this.albums= albums;
        this.filterAlbumList = new ArrayList<>();       //Initialise filter List
        this.filterAlbumList.addAll(albums);
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return albums.size();
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

        alar_Name.setText(albums.get(i).getAlbumName());
        alar_details.setText(albums.get(i).getSongName());

        Picasso.with(context)
                .load(albums.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(alar_image);
      /*  ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                intent.putExtra("Song_Name",alar_Name.getText().toString());
                intent.putExtra("Song_Path",albums.get(i).getSongPath());
                context.startActivity(intent);
            }
        });*/

        return view;
    }

    //Filterclass to filter data

    public void filter(CharSequence stringVar) {

        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        albums.clear();
        if (stringVar.length() == 0) {
            albums.addAll(filterAlbumList);
        } else {
            for (AlbumWise wp : filterAlbumList) {
                if (wp.getAlbumName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    albums.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
