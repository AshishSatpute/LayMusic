package com.worldplanet.users.wpes.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.worldplanet.users.wpes.Adapter.PlayListSongAdapter;
import com.worldplanet.users.wpes.Adapter.PlayListSongAdapter2;
import com.worldplanet.users.wpes.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaylistSongsActivity extends AppCompatActivity {
    public static final String TAG=PlaylistSongsActivity.class.getCanonicalName();
    //ListView lst; // object     //data source
    RecyclerView lst; // object     //data source
    ImageView iv_addNewPlayList,iv_eraseText,iv_removePlayListSongs;
    EditText ed_playlistName;
    Button btn_save;
    MyDatabase dbTools = new MyDatabase(this);
    ArrayList<PlaylistSongs> songDataList = new ArrayList<>();
    ArrayList<String> songData = new ArrayList<>();
    String from , playlistName;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onCreate: ");
        from = getIntent().getStringExtra("from");
        playlistName = getIntent().getStringExtra("PlaylistName");
        iv_addNewPlayList=(ImageView) findViewById(R.id.iv_addNewPlayList);
        iv_removePlayListSongs=(ImageView) findViewById(R.id.iv_removePlayListSongs);
        iv_addNewPlayList.setVisibility(View.GONE);
        //lst=(ListView) findViewById(R.id.playlists);
        lst=findViewById(R.id.playlists);

        if (!from.isEmpty()&& !from.equals("")&&from != null) {
            if (from.equalsIgnoreCase("Playlist_Remove")) {

                iv_removePlayListSongs.setVisibility(View.VISIBLE);
            }
        }
       /* if (songData.size()>0 && from.equalsIgnoreCase("Playlist_Remove")) {

        }*/
        loadListView();
        iv_addNewPlayList.setOnClickListener(mSaveBtnListener);
        iv_removePlayListSongs.setOnClickListener(mRemoveBtnListener);

    }

    private void loadListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lst.setLayoutManager(layoutManager);
        lst.setHasFixedSize(true);
        Log.i(TAG, "loadListView: ");
        List<PlaylistSongs> parentDetailsList = dbTools.getPlaylistSongRecord(playlistName);
        if (parentDetailsList != null && parentDetailsList.size() > 0) {
            PlayListSongAdapter2 customListView=new PlayListSongAdapter2(PlaylistSongsActivity. this,parentDetailsList, from);
            lst.setAdapter(customListView);
        }
    }

    private View.OnClickListener mSaveBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
           showAddPlaylistDialog();
        }
    };
    private View.OnClickListener mRemoveBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
            removePlayListSongs();
        }
    };
    private void showAddPlaylistDialog() {
        Log.i(TAG, "showAddPlaylistDialog: ");
        // Create custom dialog object
        final Dialog dialog = new Dialog(PlaylistSongsActivity.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.view_add_playlist);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
        ed_playlistName = (EditText) dialog.findViewById(R.id.ed_playlistName);
        iv_eraseText = (ImageView) dialog.findViewById(R.id.iv_eraseText);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_save = (Button) dialog.findViewById(R.id.btn_save);

        dialog.show();
        iv_eraseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_playlistName.setText("");
            }
        });

        // if decline button is clicked, close the custom dialog
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBeforeSave()) {
                    boolean isAlreadyExist = new MyDatabase(PlaylistSongsActivity.this).checkAlreadyExist(ed_playlistName.getText().toString());
                    if(isAlreadyExist) {
                        try {
                            HashMap<String, String> queryValuesMap = new HashMap<String, String>();
                            queryValuesMap.put("playlist_name", ed_playlistName.getText().toString());
                            dbTools.insertPlaylistName(queryValuesMap);
                            loadListView();
                            // Close dialog
                            dialog.dismiss();
                            ed_playlistName.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("RegEError", e.getMessage() + "");
                        }
                    }
                    else{

                            hideSoftKeyboard();
                        ed_playlistName.setError("Playlist Name Already Exist!");
                         //   Toast.makeText(PlaylistActivity.this, "Playlist Name Already Exist!", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }

    private void removePlayListSongs() {

        songDataList = PlayListSongAdapter.playSongList;
            try {
                for (int i = 0; i < songDataList.size(); i++) {
                    new MyDatabase(PlaylistSongsActivity.this)
                            .deleteSongs(playlistName, songDataList.get(i).getSongName());
                    }
                songData = null;
                songDataList = null;
                Toast.makeText(PlaylistSongsActivity.this, "Removed from Playlist", Toast.LENGTH_LONG).show();
                Intent home = new Intent(PlaylistSongsActivity.this, PlaylistActivity.class);
                startActivity(home);
                finish();

            } catch (Exception ex) {
                Log.e("Error in insertion ", ex.toString());
            }
        }


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private boolean checkBeforeSave() {
        String playlistName = ed_playlistName.getText().toString();
        if (playlistName != null && !playlistName.equals("") &&
                !playlistName.equalsIgnoreCase("Not Specified") &&
                !playlistName.equalsIgnoreCase("null")) {

            btn_save.setEnabled(true);
            return true;
        }
        else {
            btn_save.setEnabled(false);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
