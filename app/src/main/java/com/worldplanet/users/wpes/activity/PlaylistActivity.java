package com.worldplanet.users.wpes.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ListView;

import com.worldplanet.users.wpes.Adapter.PlayListsAdapter2;
import com.worldplanet.users.wpes.MusicDetailsModel.PlayList;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.dataBase.MyDatabase;

import java.util.HashMap;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    ListView lst; // object     //data source
    RecyclerView recyclerView; // object     //data source
    ImageView iv_addNewPlayList,iv_eraseText;
    EditText ed_playlistName;
    Button btn_save;
    MyDatabase dbTools = new MyDatabase(this);
    String from , playlistName;
    Toolbar toolbar;

    public static final String TAG=PlaylistActivity.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onCreate: ");

        iv_addNewPlayList=(ImageView) findViewById(R.id.iv_addNewPlayList);
        recyclerView= findViewById(R.id.playlists);

        loadListView();
        iv_addNewPlayList.setOnClickListener(mSaveBtnListener);

        //from = getIntent().getStringExtra("from");
       // playlistName = getIntent().getStringExtra("PlaylistName");

    }

    private void loadListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        List<PlayList> parentDetailsList = dbTools.getAllPlaylistRecord();
        if (parentDetailsList != null && parentDetailsList.size() > 0) {
            PlayListsAdapter2 customListView=new PlayListsAdapter2(PlaylistActivity. this,parentDetailsList);
            recyclerView.setAdapter(customListView);
        }
    }

    private View.OnClickListener mSaveBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
           showAddPlaylistDialog();
        }
    };

    private void showAddPlaylistDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(PlaylistActivity.this);
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
                    boolean isAlreadyExist = new MyDatabase(PlaylistActivity.this).checkAlreadyExist(ed_playlistName.getText().toString());
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
