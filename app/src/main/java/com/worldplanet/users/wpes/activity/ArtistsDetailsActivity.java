package com.worldplanet.users.wpes.activity;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.wpes.Adapter.ArtistDetailsAdapter2;
import com.worldplanet.users.wpes.Api.RESTClient;
import com.worldplanet.users.wpes.MusicDetailsModel.ArtistWise;
import com.worldplanet.users.wpes.R;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistsDetailsActivity extends AppCompatActivity {
    //private ListView listView;
    private RecyclerView recyclerView;
    private EditText editText;
    private String Artist_Name;
    private List<ArtistWise> artistData;
    private ArtistDetailsAdapter2 artistDetailsAdapter;
    private ProgressDialog progressDialog;

    private Toolbar toolbar;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listView = (ListView) findViewById(R.id.artistlist);
        recyclerView =  findViewById(R.id.artistlist);
        editText = (EditText) findViewById(R.id.search2);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        playDurationrelease = (TextView) findViewById(R.id.playDurationrelease);
        songDurationrelease = (TextView) findViewById(R.id.songDurationrelease);
        media_shufflerelease = (ImageButton) findViewById(R.id.media_shufflerelease);
        media_rewrelease = (ImageButton) findViewById(R.id.media_rewrelease);
        media_pauserelease = (ImageButton) findViewById(R.id.media_pauserelease);
        media_playrelease = (ImageButton) findViewById(R.id.media_playrelease);
        media_fwrelease = (ImageButton) findViewById(R.id.media_fwrelease);
        player_footer_bg = (LinearLayout) findViewById(R.id.player_footer_bg);

        Artist_Name = getIntent().getStringExtra("Artist_Name");
        getArtistList(Artist_Name);

        // Mediaplayer
        mediaPlayer = new MediaPlayer();

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                artistDetailsAdapter.filter(stringVar);
            }
        });

        //listView.setTextFilterEnabled(true);
   /*     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (artistData.size() >= position || artistData.size() <= position) {
                    songPath = artistData.get(position).getSongPath();
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
                            if (current_position < (artistData.size() - 1)) {
                                songPath = artistData.get(current_position + 1).getSongPath();
                                current_position = current_position + 1;
                                startPlaying(songPath);
                            } else {
                                // play first song
                                songPath = artistData.get(0).getSongPath();
                                startPlaying(songPath);
                            }
                        }else {
                            current_position = position;
                            // check if next song is there or not
                            if (current_position < (artistData.size() - 1)) {
                                songPath = artistData.get(current_position + 1).getSongPath();
                                current_position = current_position + 1;
                                startPlaying(songPath);
                            } else {
                                // play first song
                                songPath = artistData.get(0).getSongPath();
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
                            songPath = artistData.get(position - 1).getSongPath();
                            startPlaying(songPath);
                        }else{
                            // play last song
                            //startPlaying(artistData.size() - 1);
                            Integer lastPosition  = artistData.size() - 1 ;
                            songPath = artistData.get(lastPosition).getSongPath();
                            startPlaying(songPath);
                        }
                        media_pauserelease.setVisibility(View.VISIBLE);
                        media_playrelease.setVisibility(View.GONE);
                    }
                });
            }
        });*/

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
    private void getArtistList(String artist_name) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(ArtistsDetailsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getArtistWiseData(artist_name,new Callback<List<ArtistWise>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<ArtistWise> artistList, Response response) {
                if ((artistList.size()>0)) {
                    artistData = artistList;
                    artistDetailsAdapter = new ArtistDetailsAdapter2(ArtistsDetailsActivity.this,artistList );
                    //listView.setAdapter(artistDetailsAdapter);
                    recyclerView.setAdapter(artistDetailsAdapter);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(ArtistsDetailsActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void failure(RetrofitError error) {
                String errorV = "m";
                Log.d("Error", "Erroooor");
                Toast.makeText(ArtistsDetailsActivity.this, "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
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

