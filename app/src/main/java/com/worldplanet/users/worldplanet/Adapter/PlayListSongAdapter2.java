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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.worldplanet.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity2;
import com.worldplanet.users.worldplanet.activity.PlaylistSongsActivity;
import com.worldplanet.users.worldplanet.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class PlayListSongAdapter2 extends RecyclerView.Adapter<PlayListSongAdapter2.ViewHolder> {

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

    public static final String TAG = PlayListSongAdapter2.class.getCanonicalName();

    public PlayListSongAdapter2(PlaylistSongsActivity context, List<PlaylistSongs> parentDetailsList, String from) {
        Log.i(TAG, "PlayListSongAdapter: ");
        this.context = context;
        this.songslist = parentDetailsList;
        this.from = from;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void onBindViewHolder(@NonNull PlayListSongAdapter2.ViewHolder holder, final int position) {

        Log.i(TAG, "onBindViewHolder: ");
        holder.album_Name.setText(songslist.get(position).getSongName());

        holder.ll_play_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "onClick: card");
                if (from.equalsIgnoreCase("Playlist_Remove")) {
                    if (cb_select_play_songs.isChecked()) {
                        cb_select_play_songs.setChecked(false);
                    } else {
                        cb_select_play_songs.setChecked(true);
                    }
                } else {
                    Log.i(TAG, "onClick: ");
                    Intent intent = new Intent(context, PlayMusicActivity2.class);
                    intent.putExtra("Song_Name", songslist.get(position).getSongName());
                    intent.putExtra("Song_Path", songslist.get(position).getSongPath());
                    intent.putExtra("Song_Id", songslist.get(position).getSongId());
                    context.startActivity(intent);
                }

            }
        });



        /*cb_select_play_songs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        );*/


    }

    @Override
    public int getItemCount() {
        return songslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView album_Name;
        ImageView album_image;
        LinearLayout ll_play_songs;
        CheckBox cb_select_play_songs;

        public ViewHolder(View view) {
            super(view);
            album_Name = view.findViewById(R.id.album_Name);
            album_image = view.findViewById(R.id.album_image);
            ll_play_songs = view.findViewById(R.id.ll_play_songs);
            cb_select_play_songs = view.findViewById(R.id.cb_select_play_songs);
        }
    }
}
