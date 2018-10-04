package com.worldplanet.users.wpes.Adapter;

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
import com.worldplanet.users.wpes.MusicDetailsModel.Album;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.AlbumsActivity;
import com.worldplanet.users.wpes.activity.AlbumsDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllbumsAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    private List<Album> albums;
    private List<Album> filterAlbumList;        //Duplicate list for filtering
    private LayoutInflater inflater = null;

    public AllbumsAdapter(AlbumsActivity context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
        this.filterAlbumList = new ArrayList<>();       //Initialise filter List
        this.filterAlbumList.addAll(albums);            //Add all items of array list to filter list
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (convertView == null) {
            view = inflater.inflate(R.layout.albums_layout, null);
        }
        final TextView album_Name = (TextView) view.findViewById(R.id.album_Name);
        ImageView album_image = (ImageView) view.findViewById(R.id.album_image);
        LinearLayout ll_album_list = (LinearLayout) view.findViewById(R.id.ll_album_list);

        album_Name.setText(albums.get(i).getAlbumName());
        //  categoryId.setText(data.getBalance());
/**/
        Picasso.with(context)
                .load(albums.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(album_image);
        ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumsDetailsActivity.class);
                intent.putExtra("Album_Name", album_Name.getText().toString());
                context.startActivity(intent);
            }
        });

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
                for (Album wp : filterAlbumList) {
                    if (wp.getAlbumName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                        albums.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
}

