package com.worldplanet.users.worldplanet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.worldplanet.users.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.users.worldplanet.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.AlbumsActivity;
import com.worldplanet.users.worldplanet.activity.AlbumsDetailsActivity;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity2;
import com.worldplanet.users.worldplanet.activity.PlaylistActivity;
import com.worldplanet.users.worldplanet.activity.PlaylistSongsActivity;
import com.worldplanet.users.worldplanet.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayListSongAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    String setView;
    CheckBox cb_select_play_songs;
    List<PlaylistSongs> songslist;      //Duplicate list for filtering
    private LayoutInflater inflater = null;
    public static ArrayList<String> songDataList = new ArrayList<>();
    public static ArrayList<PlaylistSongs> playSongList = new ArrayList<>();
    MyDatabase dbTools;
    String from, selectedData;
    
    public static final String TAG = PlayListSongAdapter.class.getCanonicalName();

    public PlayListSongAdapter(PlaylistSongsActivity context, List<PlaylistSongs> parentDetailsList, String from) {
        Log.i(TAG, "PlayListSongAdapter: ");
        this.context = context;
        this.songslist = parentDetailsList;
        this.from = from;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songslist.size();
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
        Log.i(TAG, "getView: ");
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.albums_layout, null);
        }
        final TextView album_Name = view.findViewById(R.id.album_Name);
        ImageView album_image = view.findViewById(R.id.album_image);
        LinearLayout ll_play_songs = view.findViewById(R.id.ll_play_songs);
        cb_select_play_songs = view.findViewById(R.id.cb_select_play_songs);

        album_image.setVisibility(View.GONE);
        album_Name.setText(songslist.get(position).getSongName());
        //  categoryId.setText(data.getBalance());
        if (from.equalsIgnoreCase("Playlist_Remove")){
            cb_select_play_songs.setVisibility(View.VISIBLE);
            album_image.setVisibility(View.GONE);
        }
        ll_play_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: card");
                if (from.equalsIgnoreCase("Playlist_Remove")) {
                    if (cb_select_play_songs.isChecked()) {
                        cb_select_play_songs.setChecked(false);
                    } else {
                        cb_select_play_songs.setChecked(true);
                    }
                }
                else {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("Song_Name", album_Name.getText().toString());
                intent.putExtra("Song_Path", songslist.get(position).getSongPath());
                context.startActivity(intent);
            }
            }
        });


        cb_select_play_songs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "onCheckedChanged: ");
                //CheckBox selectCheckbox = (CheckBox) v.findViewById(R.id.alertCheckbox);
                if (isChecked) {
                    String selectedData = songslist.get(position).getSongName();
                    setView = (selectedData);
                    songDataList.add(setView);
                    PlaylistSongs pItem = new PlaylistSongs();
                    pItem.setSongName(songslist.get(position).getSongName());
                    pItem.setSongPath(songslist.get(position).getSongPath());
                    playSongList.add(pItem);
                } else {
                    if (songDataList.contains(setView)) {
                        songDataList.remove(setView); }
                } }}
        );
        return view;
    }
}