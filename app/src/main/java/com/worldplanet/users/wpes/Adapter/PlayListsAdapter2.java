package com.worldplanet.users.wpes.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.wpes.MusicDetailsModel.PlayList;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.AllSongsActivity;
import com.worldplanet.users.wpes.activity.PlaylistActivity;
import com.worldplanet.users.wpes.activity.PlaylistSongsActivity;
import com.worldplanet.users.wpes.dataBase.MyDatabase;

import java.util.List;

public class PlayListsAdapter2 extends RecyclerView.Adapter<PlayListsAdapter2.ViewHolder> {

    Context context;
    List<PlayList> playlist;
    LayoutInflater layoutInflater;
    int playlistPosition;
    String playlistName;
    public static final String TAG = PlayListsAdapter2.class.getCanonicalName();

    public PlayListsAdapter2(Context context, List<PlayList> playlist) {
        Log.i(TAG, "PlaylistsAdapter2: ");
        this.context = context;
        this.playlist = playlist;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.playlist_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");
        PlayList playlistData = playlist.get(position);
        holder.PlaylistName.setText(playlistData.getPlayList_name());

        holder.PlaylistName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlaylistSongsActivity.class);
                intent.putExtra("from", "songlist");
                intent.putExtra("PlaylistName", playlist.get(position).getPlayList_name());
                context.startActivity(intent);
            }
        });


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ");
                playlistName = playlist.get(position).getPlayList_name();
                //  playlistName = playlistData.getPlayList_name();
                playlistPosition = position;
                showPopupMenu(holder.overflow);
            }
        });

        //child.setTag(holder);

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        Log.i(TAG, "showPopupMenu: ");
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_playlist, popup.getMenu());
        popup.setOnMenuItemClickListener(new PlayListsAdapter2.MyMenuItemClickListener());
        popup.show();
    }


    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public final String TAG = PlaylistsAdapter.MyMenuItemClickListener.class.getCanonicalName();

        public MyMenuItemClickListener() {
            Log.i(TAG, "MyMenuItemClickListener: ");
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Log.i(TAG, "onMenuItemClick: ");
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    Log.i(TAG, "onMenuItemClick:action_delete ");
                    removePlaylist(playlistPosition, playlistName);
                    Toast.makeText(context, "Playlist Deleted", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_songs:
                    Log.i(TAG, "onMenuItemClick: add_songs");
                    Intent intent = new Intent(context, AllSongsActivity.class);
                    intent.putExtra("from", "Playlist_Add");
                    intent.putExtra("PlaylistName", playlistName);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                    return true;
                case R.id.action_remove_songs:
                    Log.i(TAG, "onMenuItemClick:action_remove_songs");
                    removePlaylistSongs(playlistName);
                    return true;

                default:
            }
            return false;
        }
    }


    private void removePlaylistSongs(String playlistName) {
        Log.i(TAG, "removePlaylistSongs: ");
        Intent intent = new Intent(context, PlaylistSongsActivity.class);
        intent.putExtra("from", "Playlist_Remove");
        intent.putExtra("PlaylistName", playlistName);
        context.startActivity(intent);
    }

    private void removePlaylist(int playlistPosition, String playlistName) {
        Log.i(TAG, "removePlaylist: ");
        boolean isDeleted = new MyDatabase(context).deletePlaylist(playlistName);
        if (isDeleted) {
            boolean isSongDeleted = new MyDatabase(context).deletePlaylistSongs(playlistName);
            if (isSongDeleted) {
                playlist.remove(playlistName);
                notifyDataSetChanged();
                Intent intent = new Intent(context, PlaylistActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }

        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return playlist != null ? playlist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView PlaylistName;
        ImageView overflow;
        CardView Card_Playlist;

        public ViewHolder(View itemView) {
            super(itemView);
            PlaylistName = itemView.findViewById(R.id.PlaylistName);
            overflow = itemView.findViewById(R.id.overflow);
            Card_Playlist = itemView.findViewById(R.id.Card_Playlist);
        }
    }
}