package com.worldplanet.users.worldplanet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.worldplanet.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity;
import com.worldplanet.users.worldplanet.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongListAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    String from, playlist_Name, setView;
    private List<TopSongs> topSongs;
    private List<TopSongs> filterTopSongsList;        //Duplicate list for filtering
    private LayoutInflater inflater = null;
    public static ArrayList<String> songDataList = new ArrayList<>();
    public static ArrayList<PlaylistSongs> songList = new ArrayList<>();
    MyDatabase dbTools;
    CustomItemClickListener listener;

    public static final String TAG=SongListAdapter.class.getCanonicalName();

    public SongListAdapter(Context context, List<TopSongs> topSongs, String from, String playlist_Name, CustomItemClickListener listener) {
        super();
        Log.i(TAG, "SongListAdapter: ");
        this.context = context;
        this.topSongs = topSongs;
        this.from = from;
        this.playlist_Name = playlist_Name;
        this.listener = listener;
        this.filterTopSongsList = new ArrayList<>();       //Initialise filter List
        this.filterTopSongsList.addAll(topSongs);            //Add all items of array list to filter list
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dbTools = new MyDatabase(context);
    }

    /*@Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.topsongs_layout, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.top_songs_Name.setText(topSongs.get(position).getSongName());
        viewHolder.top_songs_details.setText(topSongs.get(position).getAlbumName());

        //  categoryId.setText(data.getBalance());
        if (from.equalsIgnoreCase("Playlist")) {
            viewHolder.cb_select_songs.setVisibility(View.VISIBLE);
            viewHolder.top_songs_image.setVisibility(View.GONE);
        }
        viewHolder.ll_topSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (from.equalsIgnoreCase("Playlist")) {
                    if (viewHolder.cb_select_songs.isChecked()) {
                        viewHolder.cb_select_songs.setChecked(false);
                    } else {
                        viewHolder.cb_select_songs.setChecked(true);
                    }
                }
                *//*else {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("Song_Name", top_songs_Name.getText().toString());
                    intent.putExtra("Song_Path", topSongs.get(i).getSongPath());
                    context.startActivity(intent);
                }*//*
            }
        });

        Picasso.with(context)
                .load(topSongs.get(position).getImage())
                .placeholder(R.drawable.aa)
                .error(R.drawable.aa)
                .fit()
                .into(viewHolder.top_songs_image);

        viewHolder.cb_select_songs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //CheckBox selectCheckbox = (CheckBox) v.findViewById(R.id.alertCheckbox);
                if (isChecked) {
                    String selectedData = topSongs.get(position).getSongName();
                    setView = (selectedData);
                    songDataList.add(setView);
                    PlaylistSongs pItem = new PlaylistSongs();
                    pItem.setSongName(topSongs.get(position).getSongName());
                    pItem.setSongPath(topSongs.get(position).getSongPath());
                    pItem.setPlyalistName(playlist_Name);
                    songList.add(pItem);
                    } else {
                    if (songDataList.contains(setView)) {
                        songDataList.remove(setView); }
                        } }}
        );
    }*/


    @Override
    public int getCount() {
        return topSongs.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Log.i(TAG, "getView: ");
        if (convertView == null) {
            view = inflater.inflate(R.layout.search_songs, null);
        }

        final TextView top_songs_Name = (TextView) view.findViewById(R.id.top_songs_Name);
        TextView top_songs_details = (TextView) view.findViewById(R.id.top_songs_details);
        ImageView top_songs_image = (ImageView) view.findViewById(R.id.top_songs_image);
        ConstraintLayout ll_topSongs = view.findViewById(R.id.ll_topSongs);
        final CheckBox cb_select_songs = (CheckBox) view.findViewById(R.id.cb_select_songs);

        top_songs_Name.setText(topSongs.get(position).getSongName());
        top_songs_details.setText(topSongs.get(position).getAlbumName());

        //  categoryId.setText(data.getBalance());
        if (from.equalsIgnoreCase("Playlist_Add")) {
            cb_select_songs.setVisibility(View.VISIBLE);
            top_songs_image.setVisibility(View.GONE);
        }
        ll_topSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                if (from.equalsIgnoreCase("Playlist_Add")) {
                    if (cb_select_songs.isChecked()) {
                        cb_select_songs.setChecked(false);
                    } else {
                        cb_select_songs.setChecked(true);
                    }
                } else {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("Song_Name", top_songs_Name.getText().toString());
                    intent.putExtra("Song_Path", topSongs.get(position).getSongPath());
                    context.startActivity(intent);
                }
            }
        });
        Picasso.with(context)
                .load(topSongs.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(top_songs_image);
        cb_select_songs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           //CheckBox selectCheckbox = (CheckBox) v.findViewById(R.id.alertCheckbox);
                                                           if (isChecked) {
                                                               Log.i(TAG, "onCheckedChanged: ");
                                                               String selectedData = topSongs.get(position).getSongName();
                                                               setView = (selectedData);
                                                               songDataList.add(setView);
                                                               PlaylistSongs pItem = new PlaylistSongs();
                                                               pItem.setSongName(topSongs.get(position).getSongName());
                                                               pItem.setSongPath(topSongs.get(position).getSongPath());
                                                               pItem.setPlyalistName(playlist_Name);
                                                               songList.add(pItem);
                                                           } else {
                                                               if (songDataList.contains(setView)) {
                                                                   songDataList.remove(setView);
                                                               }
                                                           }
                                                       }
                                                   }
        );
        return view;
    }

/*    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView top_songs_image;
        public TextView top_songs_Name, top_songs_details;
        public LinearLayout ll_topSongs;
        public final CheckBox cb_select_songs;


    }*/
    //Filterclass to filter data

    public void filter(CharSequence stringVar) {
        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        topSongs.clear();
        if (stringVar.length() == 0) {
            topSongs.addAll(filterTopSongsList);
        } else {
            for (TopSongs wp : filterTopSongsList) {
                if (wp.getSongName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    topSongs.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

