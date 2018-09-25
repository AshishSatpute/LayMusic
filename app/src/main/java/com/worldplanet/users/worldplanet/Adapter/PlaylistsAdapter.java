package com.worldplanet.users.worldplanet.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.worldplanet.MusicDetailsModel.PlayList;
import com.worldplanet.users.worldplanet.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.AllSongsActivity;
import com.worldplanet.users.worldplanet.activity.PlaylistActivity;
import com.worldplanet.users.worldplanet.activity.PlaylistSongsActivity;
import com.worldplanet.users.worldplanet.dataBase.MyDatabase;

import java.util.List;

public class PlaylistsAdapter extends BaseAdapter {

    Context context;
    List<PlayList> playlist;
    LayoutInflater layoutInflater;
    int playlistPosition;
    String playlistName;
    public static final String TAG = PlaylistsAdapter.class.getCanonicalName();
    public PlaylistsAdapter(Context context, List<PlayList> playlist) {
        Log.i(TAG, "PlaylistsAdapter: ");
        this.context = context;
        this.playlist = playlist;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return playlist != null ? playlist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View child, ViewGroup parent) {
        Log.i(TAG, "getView: ");
        final PlayList playlistData = playlist.get(position);
        final Holder holder;
        if (child == null) {
            child = layoutInflater.inflate(R.layout.playlist_layout, null);

            holder = new Holder();

            holder.PlaylistName = child.findViewById(R.id.PlaylistName);
            holder.overflow = child.findViewById(R.id.overflow);
            holder.Card_Playlist = (CardView) child.findViewById(R.id.Card_Playlist);

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
            child.setTag(holder);

        }

        return child;
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
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public  final String TAG=MyMenuItemClickListener.class.getCanonicalName();
        public MyMenuItemClickListener() {
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    Log.i(TAG, "onMenuItemClick:action_delete ");
                    removePlaylist(playlistPosition,playlistName);
                    Toast.makeText(context, "Playlist Deleted", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_songs:
                    Log.i(TAG, "onMenuItemClick: add_songs");
                    Intent intent = new Intent(context, AllSongsActivity.class);
                    intent.putExtra("from", "Playlist_Add");
                    intent.putExtra("PlaylistName", playlistName);
                    context.startActivity(intent);
                    ((Activity)context).finish();
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
                ((Activity)context).finish();
            }

        }
    }
    public class Holder {
        TextView PlaylistName;
        ImageView overflow;
        CardView Card_Playlist;
    }
}