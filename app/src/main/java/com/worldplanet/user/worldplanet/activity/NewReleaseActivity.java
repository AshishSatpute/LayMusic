package com.worldplanet.user.worldplanet.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.user.worldplanet.Adapter.NewReleaseListAdapter;
import com.worldplanet.user.worldplanet.Api.RESTClient;
import com.worldplanet.user.worldplanet.MusicDetailsModel.NewRelease;
import com.worldplanet.user.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.user.worldplanet.R;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewReleaseActivity extends AppCompatActivity {
    ListView list_newRelease;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private List<NewRelease> newReleasesSongs;
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

    public static final String TAG = NewReleaseActivity.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_release);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onCreate: ");
        list_newRelease=(ListView) findViewById(R.id.list_newRelease);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        playDurationrelease = (TextView) findViewById(R.id.playDurationrelease);
        songDurationrelease = (TextView) findViewById(R.id.songDurationrelease);
        media_shufflerelease = (ImageButton) findViewById(R.id.media_shufflerelease);
        media_rewrelease = (ImageButton) findViewById(R.id.media_rewrelease);
        media_pauserelease = (ImageButton) findViewById(R.id.media_pauserelease);
        media_playrelease = (ImageButton) findViewById(R.id.media_playrelease);
        media_fwrelease = (ImageButton) findViewById(R.id.media_fwrelease);
        player_footer_bg = (LinearLayout) findViewById(R.id.player_footer_bg);

        setNewReleaseSongs();
        // Mediaplayer
        mediaPlayer = new MediaPlayer();
        list_newRelease.setTextFilterEnabled(true);
        list_newRelease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (newReleasesSongs.size() >= position || newReleasesSongs.size() <= position) {
                    songPath = newReleasesSongs.get(position).getSongPath();
                    startPlaying(songPath);

                     /*   Uri uri = Uri.parse(songPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setDataAndType(uri, "audio/*");
                        startActivity(intent);*/

                }

                media_fwrelease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            // check if next song is there or not
                            if (current_position < (newReleasesSongs.size() - 1)) {
                                songPath = newReleasesSongs.get(current_position + 1).getSongPath();
                                current_position = current_position + 1;
                                startPlaying(songPath);
                            } else {
                                // play first song
                                songPath = newReleasesSongs.get(0).getSongPath();
                                startPlaying(songPath);
                            }
                        }else {
                            current_position = position;
                            // check if next song is there or not
                            if (current_position < (newReleasesSongs.size() - 1)) {
                                songPath = newReleasesSongs.get(current_position + 1).getSongPath();
                                current_position = current_position + 1;
                                startPlaying(songPath);
                            } else {
                                // play first song
                                songPath = newReleasesSongs.get(0).getSongPath();
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
                            songPath = newReleasesSongs.get(position - 1).getSongPath();
                            startPlaying(songPath);
                        }else{
                            // play last song
                            //startPlaying(newReleasesSongs.size() - 1);
                            Integer lastPosition  = newReleasesSongs.size() - 1 ;
                            songPath = newReleasesSongs.get(lastPosition).getSongPath();
                            startPlaying(songPath);
                        }
                        media_pauserelease.setVisibility(View.VISIBLE);
                        media_playrelease.setVisibility(View.GONE);
                    }
                });
            }
        });media_playrelease.setOnClickListener(new View.OnClickListener() {

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

    private void setNewReleaseSongs() {
        Log.i(TAG, "setNewReleaseSongs: ");
        progressDialog = new ProgressDialog(NewReleaseActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getNewReleaseSongs("0",new Callback<List<NewRelease>>() {
            @Override
            public void success(List<NewRelease> newReleases, Response response) {
                Log.i(TAG, "success: ");
                if ((newReleases.size()>0)) {
                    newReleasesSongs = newReleases;
                    NewReleaseListAdapter customListView=new NewReleaseListAdapter(NewReleaseActivity.this,newReleases);
                    list_newRelease.setAdapter(customListView);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(NewReleaseActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                String errorV = "m";
                Log.d("Error", "Erroooor");
                progressDialog.dismiss();
                Toast.makeText(NewReleaseActivity.this, "something went wrong (New Release)", Toast.LENGTH_SHORT).show();
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
