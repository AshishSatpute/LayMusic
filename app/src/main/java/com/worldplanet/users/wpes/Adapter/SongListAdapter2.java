package com.worldplanet.users.wpes.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.wpes.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.wpes.MusicDetailsModel.TopSongs;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.PlayMusicActivity2;
import com.worldplanet.users.wpes.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongListAdapter2 extends RecyclerView.Adapter<SongListAdapter2.ViewHolder> {
    public static final String TAG = SongListAdapter2.class.getCanonicalName();
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

    public SongListAdapter2(Context context, List<TopSongs> topSongs, String from, String playlist_Name, CustomItemClickListener listener) {
        super();
        Log.i(TAG, "SongListAdapter2: ");
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


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_songs, parent, false);
        Log.i(TAG, "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongListAdapter2.ViewHolder holder, final int position) {
        holder.top_songs_Name.setText(topSongs.get(position).getSongName());
        holder.top_songs_details.setText(topSongs.get(position).getAlbumName());
        Log.i(TAG, "onBindViewHolder: ");
        //  categoryId.setText(data.getBalance());


        if (from.equalsIgnoreCase("Playlist_Add")) {
            Log.i(TAG, "onBindViewHolder: equalsIgnoreCase");
            holder.cb_select_songs.setVisibility(View.VISIBLE);
            holder.top_songs_image.setVisibility(View.GONE);
        }
        holder.ll_topSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: holder.ll_topSongs ");
                if (from.equalsIgnoreCase("Playlist_Add")) {
                    if (holder.cb_select_songs.isChecked()) {
                        holder.cb_select_songs.setChecked(false);
                    } else {
                        holder.cb_select_songs.setChecked(true);
                    }
                } else {
                    Intent intent = new Intent(context, PlayMusicActivity2.class);
                    intent.putExtra("Song_Name", topSongs.get(position).getSongName());
                    intent.putExtra("Song_Path", topSongs.get(position).getSongPath());
                    Log.i(TAG, "onClick:top song "+topSongs.get(position));
                    context.startActivity(intent);
                }
            }
        });


        Picasso.with(context)
                .load(topSongs.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(holder.top_songs_image);

        holder.cb_select_songs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                              @Override
                                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                  //CheckBox selectCheckbox = (CheckBox) v.findViewById(R.id.alertCheckbox);
                                                                  if (isChecked) {
                                                                      Log.i(TAG, "onCheckedChanged: ");
                                                                      String selectedData = topSongs.get(position).getSongName();
                                                                      Log.i(TAG, "onCheckedChanged:selectedData ");
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

    }

    @Override
    public int getItemCount() {
        return topSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView top_songs_Name;
        TextView top_songs_details;
        ImageView top_songs_image;
        ConstraintLayout ll_topSongs;
        CheckBox cb_select_songs;

        public ViewHolder(View view) {
            super(view);
            top_songs_Name = view.findViewById(R.id.top_songs_Name);
            top_songs_details = view.findViewById(R.id.top_songs_details);
            top_songs_image = view.findViewById(R.id.top_songs_image);
            ll_topSongs = view.findViewById(R.id.ll_topSongs);
            cb_select_songs = view.findViewById(R.id.cb_select_songs);
        }
    }

    public void filter(CharSequence stringVar) {

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
