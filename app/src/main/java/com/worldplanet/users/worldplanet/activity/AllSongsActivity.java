package com.worldplanet.users.worldplanet.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.worldplanet.Adapter.CustomItemClickListener;
import com.worldplanet.users.worldplanet.Adapter.SongListAdapter;
import com.worldplanet.users.worldplanet.Adapter.SongListAdapter2;
import com.worldplanet.users.worldplanet.Api.RESTClient;
import com.worldplanet.users.worldplanet.MusicDetailsModel.ArtistWise;
import com.worldplanet.users.worldplanet.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.dataBase.MyDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllSongsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
       // ListView listView;
    EditText editText;
    ImageView iv_PlayList_action;
    TextView tv_PlayList_action;
    String from, playlistName;
    Toolbar toolbar,tb_playlist_action;
    private ProgressDialog progressDialog;
    private SongListAdapter2 songListAdapter2;
   // private SongListAdapter songListAdapter;
    private List<TopSongs> allSongsDetail;
    ArrayList<PlaylistSongs> songDataList = new ArrayList<>();
    MyDatabase dbTools = new MyDatabase(this);
    

    private SeekBar songProgressBar;
    private TextView playDurationrelease, songDurationrelease;
    private ImageButton media_shufflerelease, media_rewrelease, media_pauserelease, media_playrelease, media_fwrelease;
    private String songPath;
    private int current_position = 0;
    private MediaPlayer mediaPlayer;
    private LinearLayout player_footer_bg;
    private Handler myHandler = new Handler();
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;

    public static final String TAG = AllSongsActivity.class.getCanonicalName();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onCreate: ");
        from = getIntent().getStringExtra("from");
        playlistName = getIntent().getStringExtra("PlaylistName");

        //listView = findViewById(R.id.AllSongslist);
        recyclerView = findViewById(R.id.AllSongslist);
        editText = findViewById(R.id.edittext1);
        tb_playlist_action = findViewById(R.id.tb_playlist_action);
        songProgressBar = findViewById(R.id.songProgressBar);
        iv_PlayList_action = findViewById(R.id.iv_PlayList_action);
        tv_PlayList_action = findViewById(R.id.tv_PlayList_action);
        playDurationrelease = findViewById(R.id.playDurationrelease);
        songDurationrelease = findViewById(R.id.songDurationrelease);
        media_shufflerelease = findViewById(R.id.media_shufflerelease);
        media_rewrelease = findViewById(R.id.media_rewrelease);
        media_pauserelease = findViewById(R.id.media_pauserelease);
        media_playrelease = findViewById(R.id.media_playrelease);
        media_fwrelease = findViewById(R.id.media_fwrelease);
        player_footer_bg = findViewById(R.id.player_footer_bg);


        if (!from.isEmpty()&& !from.equals("")&&from != null) {
            Log.i(TAG, "!from.isEmpty()&& !from.equals(\"\")&&from != null: ");

            if (from.equalsIgnoreCase("Playlist_Add")) {
                Log.i(TAG, "from.equalsIgnoreCase(\"Playlist_Add\"): ");
                tb_playlist_action.setVisibility(View.VISIBLE);
                Picasso.with(AllSongsActivity.this)
                        .load(R.drawable.ic_file_download_black_24dp)
                        .fit()
                        .into(iv_PlayList_action);
                tv_PlayList_action.setText("Add to Playlist");
            }
        }/*else if (!from.isEmpty()&& !from.equals("")&&from != null) {
            if (from.equalsIgnoreCase("Playlist_Remove")) {
                tb_playlist_action.setVisibility(View.VISIBLE);
                Picasso.with(AllSongsActivity.this)
                        .load(R.drawable.plus_sign)
                        .fit()
                        .into(iv_PlayList_action);
                tv_PlayList_action.setText("Remove from Playlist");
            }
        }*/


        setSongList();


        // Mediaplayer
        mediaPlayer = new MediaPlayer();
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                songListAdapter2.filter(stringVar);
            }
        });

       /* listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (allSongsDetail.size() >= position || allSongsDetail.size() <= position) {
                    songPath = allSongsDetail.get(position).getSongPath();
                    startPlaying(songPath);

                     *//*   Uri uri = Uri.parse(songPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setDataAndType(uri, "audio/*");
                        startActivity(intent);*//*

                }

                media_fwrelease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            // check if next song is there or not
                            if (current_position < (allSongsDetail.size() - 1)) {
                                songPath = allSongsDetail.get(current_position + 1).getSongPath();
                                current_position = current_position + 1;
                                startPlaying(songPath);
                            } else {
                                // play first song
                                songPath = allSongsDetail.get(0).getSongPath();
                                startPlaying(songPath);
                            }
                        }else {
                            current_position = position;
                            // check if next song is there or not
                            if (current_position < (allSongsDetail.size() - 1)) {
                                songPath = allSongsDetail.get(current_position + 1).getSongPath();
                                current_position = current_position + 1;
                                startPlaying(songPath);
                            } else {
                                // play first song
                                songPath = allSongsDetail.get(0).getSongPath();
                                startPlaying(songPath);
                            }
                        }
                        media_pauserelease.setVisibility(View.VISIBLE);
                        media_playrelease.setVisibility(View.GONE);

                    }
                });

                media_rewrelease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if(position > 0){
                            songPath = allSongsDetail.get(position - 1).getSongPath();
                            startPlaying(songPath);
                        }else{
                            // play last song
                            //startPlaying(allSongsDetail.size() - 1);
                            Integer lastPosition  = allSongsDetail.size() - 1 ;
                            songPath = allSongsDetail.get(lastPosition).getSongPath();
                            startPlaying(songPath);
                        }
                        media_pauserelease.setVisibility(View.VISIBLE);
                        media_playrelease.setVisibility(View.GONE);
                    }
                });
            }
        });
        */
        media_playrelease.setOnClickListener(new View.OnClickListener() {

            boolean mStartPlaying = true;

            @Override
            public void onClick(View v) {
                startPlaying(songPath);
                if (mStartPlaying) {
                    if (media_playrelease.getVisibility() == View.VISIBLE) {
                        media_playrelease.setVisibility(View.GONE);
                        media_pauserelease.setVisibility(View.VISIBLE);
                    } else {
                        media_playrelease.setVisibility(View.VISIBLE);
                    }
                }
                //  mediaPlayer = ! mediaPlayer;
                //   im_btn_media_pauserelease.setEnabled(true);
            }
        });
        media_pauserelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();

                //   mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);

                if (media_pauserelease.getVisibility() == View.VISIBLE) {
                    media_pauserelease.setVisibility(View.GONE);
                    media_playrelease.setVisibility(View.VISIBLE);
                } else {
                    media_pauserelease.setVisibility(View.VISIBLE);
                }

            }
        });
        // perform seek bar change listener event used for getting the progress value
        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
               /* Toast.makeText(AlbumsDetailsActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();*/
                int temp = (int)startTime;
                //   startTime = startTime + progressChangedValue;
                mediaPlayer.seekTo((int) progressChangedValue);
            }
        });

        tb_playlist_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songDataList = songListAdapter2.songList;
                try {
                    MyDatabase myDb = new MyDatabase(getApplicationContext());
                    //SQLiteDatabase db = myDb.openDatabase();
                    SQLiteDatabase DB = myDb.getWritableDatabase();
                    for (int j = 0; j < songDataList.size(); j++) {
                            boolean isAlreadyExist = new MyDatabase(AllSongsActivity.this).
                                    checkSongAlreadyExist(songDataList.get(j).getSongName(), playlistName);
                            if (isAlreadyExist) {
                                ContentValues cv = new ContentValues();
                                cv.put("song_name", songDataList.get(j).getSongName());
                                cv.put("song_path", songDataList.get(j).getSongPath());
                                cv.put("playlist_name", songDataList.get(j).getPlyalistName());
                                // cv.put("downloadStatus", "Yes");
                                DB.insert("SongsDetail", null, cv);
                                //cv.clear();
                            }

                    }// cv.close();
                    DB.close();
                    songDataList = null;
                    Toast.makeText(AllSongsActivity.this, "All Songs Are Added to Playlist", Toast.LENGTH_LONG).show();
                    Intent home = new Intent(AllSongsActivity.this, PlaylistActivity.class);
                    startActivity(home);
                    finish();

                } catch (Exception ex) {
                    Log.e("Error in insertion ", ex.toString());
                }
            }
        });

    }
    private void startPlaying(String songPath) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        } /*else if(mediaPlayer != null){
            mediaPlayer.start();
        }*/
        mediaPlayer = new MediaPlayer();

       /*// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer = MediaPlayer.create(this, Uri.parse(songPath));
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);

        mediaPlayer.start();*/
        /*-----The Below code work slowly in context to load music----*/

        try {
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        player_footer_bg.setVisibility(View.VISIBLE);
        seekUpdation();

    }
    private void seekUpdation() {
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            songProgressBar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        songProgressBar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);

        songDurationrelease.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        playDurationrelease.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            playDurationrelease.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    startTime)))
            );
            songProgressBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    private void setSongList() {
        Log.i(TAG, "setSongList: ");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(AllSongsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getAllSongs("0",new Callback<List<TopSongs>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<TopSongs> topSongs, Response response) {
                if ((topSongs.size()>0)) {
                    Log.i(TAG, "success: ");
                    /*for (int i= 0;i<topSongs.size();i++) {
                        listItemsValue.;
                    }*/
                    allSongsDetail = topSongs;
                    songListAdapter2 = new SongListAdapter2(AllSongsActivity.this,topSongs, from , playlistName, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Toast.makeText(AllSongsActivity.this, "Clicked Item: " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(songListAdapter2);
                    //listView.setAdapter(songListAdapter);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(AllSongsActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AllSongsActivity.this, "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
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
