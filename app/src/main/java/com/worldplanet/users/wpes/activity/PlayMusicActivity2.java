package com.worldplanet.users.wpes.activity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.worldplanet.users.wpes.MusicDetailsModel.CategoryWise;
import com.worldplanet.users.wpes.MusicDetailsModel.NewRelease;
import com.worldplanet.users.wpes.R;

import java.io.IOException;
import java.util.List;

public class PlayMusicActivity2 extends AppCompatActivity {

    Button playBtn, playPreviousSongs, playNextSongs;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel, song_Name;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    private String songName, songPath, songType;
    int totalTime;
    private int current_position = 0;
    public static final String TAG = PlayMusicActivity2.class.getCanonicalName();
    private MediaPlayer mediaPlayer;
    private LinearLayout player_footer_bg;
    private Handler myHandler = new Handler();
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    private List<CategoryWise> categoryWiseData;
    private List<NewRelease> newReleases;
    RecyclerView recyclerView;
    Integer songId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music2);
        Log.i(TAG, "onCreate: ");
        playBtn = (Button) findViewById(R.id.playBtn);
        playNextSongs = findViewById(R.id.nextSongs);
        playPreviousSongs = findViewById(R.id.btnPrevious);
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);
        song_Name = findViewById(R.id.song_Name);

        songName = getIntent().getStringExtra("Song_Name");
        songPath = getIntent().getStringExtra("Song_Path");
        songType = getIntent().getStringExtra("type");
        //   songId = getIntent().getIntExtra("Song_id", Integer.parseInt(null));



        song_Name.setText(songName);
        Log.i(TAG, "onCreate: songsPath:"+songPath);
        startPlaying(songPath);


        playNextSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: next");
                mp.stop();
                playnext();
                // songPath =  newReleases.get(current_position + 1).getSongPath();
                //  startPlaying(songPath+1);
            }
        });


        // Position Bar
        positionBar = findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            Log.i(TAG, "onProgressChanged: ");
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );


        // Volume Bar
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Log.i(TAG, "onProgressChanged: ");
                        float volumeNum = progress / 100f;
                        mp.setVolume(volumeNum, volumeNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        // Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ");
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart: ");
        super.onRestart();
    }

    private void playnext() {
        Log.i(TAG, "playnext: ");
    }

    private void startPlaying(String songPath) {
        Log.i(TAG, "startPlaying: ");
        mp = new MediaPlayer();
        try {
            mp.setDataSource(songPath.replaceAll(" ","%20"));
            mp.prepare();
            mp.setLooping(true);
            mp.seekTo(0);
            mp.setVolume(0.5f, 0.5f);
            totalTime = mp.getDuration();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: ");
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        Log.i(TAG, "createTimeLabel: ");
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public void playBtnClick(View view) {
        Log.i(TAG, "playBtnClick: ");
        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            Log.i(TAG, "playBtnClick: start");
            /**/
            playBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);

        } else {
            // Playing
            mp.pause();
            /*  */
            Log.i(TAG, "playBtnClick: pause");
            playBtn.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed: ");
        mp.stop();
    }

    public void playPrevious(View view) throws IOException {
        Log.i(TAG, "playPrevious: ");
    }

    public void PlayNext(View view) throws IOException {
        Log.i(TAG, "PlayNext: ");
    }

}
