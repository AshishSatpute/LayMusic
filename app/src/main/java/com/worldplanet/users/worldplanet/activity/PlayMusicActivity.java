package com.worldplanet.users.worldplanet.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.worldplanet.users.worldplanet.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayMusicActivity extends AppCompatActivity {

    private ImageButton im_btn_media_ffrelease, im_btn_media_pauserelease, media_playrelease,
            media_rewrelease, media_repeatrelease, media_shufflerelease;
    private ImageView iv;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1, tx2, tx3;
    private boolean isRepeat;
    private boolean isShuffle;
    private String songName, songPath, image;
    private Toolbar toolbar;

    public static final String TAG = PlayMusicActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_release_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onCreate: ");

        im_btn_media_ffrelease = findViewById(R.id.media_ffrelease);
        im_btn_media_pauserelease = findViewById(R.id.media_pauserelease);
        media_playrelease = findViewById(R.id.media_playrelease);
        media_rewrelease = findViewById(R.id.media_rewrelease);
        media_repeatrelease = findViewById(R.id.media_repeatrelease);
        media_shufflerelease = findViewById(R.id.media_shufflerelease);

        iv = (ImageView) findViewById(R.id.mp3Imagerelease);
        tx1 = (TextView) findViewById(R.id.playDurationrelease);
        tx2 = (TextView) findViewById(R.id.songNamerelease);
        tx3 = (TextView) findViewById(R.id.songDurationrelease);

        songName = getIntent().getStringExtra("Song_Name");
        songPath = getIntent().getStringExtra("Song_Path");

        tx2.setText(songName);

        seekbar = (SeekBar) findViewById(R.id.seekBarrelease);
        seekbar.setClickable(true);

        startPlaying(songPath);
        //   mediaPlayer = MediaPlayer.create(this, R.raw.tuzhyavina);
        //  mediaPlayer = MediaPlayer.create(this, Uri.parse("http://laymusic.worldplanetesolutions.com/Audio/01 - Aashiq Banaya Aapne.mp3"));

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "onResume: ");

        tx3.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        tx1.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        seekbar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);

        media_playrelease.setOnClickListener(new View.OnClickListener() {

            boolean mStartPlaying = true;

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                startPlaying(songPath);
                if (mStartPlaying) {
                    Log.i(TAG, "onClick:");
                    if (media_playrelease.getVisibility() == View.VISIBLE) {
                        media_playrelease.setVisibility(View.GONE);
                        im_btn_media_pauserelease.setVisibility(View.VISIBLE);
                    } else {
                        media_playrelease.setVisibility(View.VISIBLE);
                    }
                }
                //  mediaPlayer = ! mediaPlayer;
                //   im_btn_media_pauserelease.setEnabled(true);
            }
        });

        im_btn_media_pauserelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                //  Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                if (im_btn_media_pauserelease.getVisibility() == View.VISIBLE) {
                    im_btn_media_pauserelease.setVisibility(View.GONE);
                    media_playrelease.setVisibility(View.VISIBLE);
                } else {
                    im_btn_media_pauserelease.setVisibility(View.VISIBLE);
                }
            }
        });

        im_btn_media_ffrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    //    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                } else {
                    //     Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        media_rewrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                int temp = (int) startTime;

                if ((temp - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    //   Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                } else {
                    //     Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void startPlaying(String songPath) {
        Log.i(TAG, "startPlaying: ");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else if (mediaPlayer != null) {
            mediaPlayer.start();
        } else {
            mediaPlayer = new MediaPlayer();
            try {
              /*  mediaPlayer = MediaPlayer.create(this, Uri.parse(songPath));
                mediaPlayer.setLooping(true);
                mediaPlayer.setVolume(100,100);
                mediaPlayer.start();*/

                mediaPlayer.setDataSource(songPath);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                Log.e("Tag", "prepare() failed");
            }
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

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
