package com.worldplanet.users.worldplanet.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.worldplanet.R;

import java.util.concurrent.TimeUnit;

public class NewReleaseDetailsActivity extends AppCompatActivity {

    private ImageButton im_btn_media_ffrelease,im_btn_media_pauserelease,media_playrelease,
            media_rewrelease, media_repeatrelease,media_shufflerelease;
    private ImageView iv;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    public static int oneTimeOnly = 0;
    private boolean isRepeat;
    private boolean isShuffle;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_release_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        im_btn_media_ffrelease =  findViewById(R.id.media_ffrelease);
        im_btn_media_pauserelease =  findViewById(R.id.media_pauserelease);
        media_playrelease =  findViewById(R.id.media_playrelease);
        media_rewrelease =  findViewById(R.id.media_rewrelease);
        media_repeatrelease =  findViewById(R.id.media_repeatrelease);
        media_shufflerelease =  findViewById(R.id.media_shufflerelease);

        iv = (ImageView)findViewById(R.id.mp3Imagerelease);

        tx1 = (TextView)findViewById(R.id.playDurationrelease);
        tx2 = (TextView)findViewById(R.id.songNamerelease);
        tx3 = (TextView)findViewById(R.id.songDurationrelease);
        tx2.setText("tuzhyavina.mp3");

     //   mediaPlayer = MediaPlayer.create(this, R.raw.tuzhyavina);
        mediaPlayer = MediaPlayer.create(this, Uri.parse("http://laymusic.worldplanetesolutions.com/Audio/01 - Aashiq Banaya Aapne.mp3"));
       /* try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource("http://laymusic.worldplanetesolutions.com/Audio/01 - Aashiq Banaya Aapne.mp3");
            player.prepare();
            player.start();
        } catch (Exception e) {
            // TODO: handle exception
        }*/
        seekbar = (SeekBar)findViewById(R.id.seekBarrelease);
        seekbar.setClickable(true);
        im_btn_media_pauserelease.setEnabled(false);


        media_playrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                tx3.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekbar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
                im_btn_media_pauserelease.setEnabled(true);
                media_playrelease.setEnabled(false);
            }
        });


        im_btn_media_pauserelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                im_btn_media_pauserelease.setEnabled(false);
                media_playrelease.setEnabled(true);
            }
        });

        im_btn_media_ffrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                //    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
               //     Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        media_rewrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                 //   Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
               //     Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }

        });

        /*b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*if(isRepeat){
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                  //  btnRepeat.setImageResource(R.drawable.btn_repeat);
                }else{
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    showDialog(); // call popup
                    //songRepeatCount.setText(String.valueOf(initialRepeatCount));
                    // make shuffle to false
                    isShuffle = false;
                   *//**//* btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);*//**//*
                }*//*
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* if(isShuffle){
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }*//*
            }
        });*/
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
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


