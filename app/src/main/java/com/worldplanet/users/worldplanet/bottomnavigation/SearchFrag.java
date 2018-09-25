package com.worldplanet.users.worldplanet.bottomnavigation;


import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.worldplanet.Adapter.CustomItemClickListener;
import com.worldplanet.users.worldplanet.Adapter.SongListAdapter;
import com.worldplanet.users.worldplanet.Api.RESTClient;
import com.worldplanet.users.worldplanet.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.users.worldplanet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFrag extends Fragment {


    public SearchFrag() {
        // Required empty public constructor
    }

    public static final String TAG=SearchFragment.class.getCanonicalName();
    private ProgressDialog progressDialog;
    private SongListAdapter songListAdapter;
    ListView iteList;
    EditText searchFromAllResult;
    TextView playDurationrelease,songDurationrelease;
    SeekBar seekBarrelease;
    private LinearLayout player_footer_bg;
    String songPath;
    private List<TopSongs> topSongsData;
    private SeekBar seekbar;
    /*----Media Player variables----*/
    private MediaPlayer mediaPlayer;
    //Used to pause/resume MediaPlayer
    private int resumePosition;
    ImageButton media_pauserelease,media_playrelease,media_rewrelease,media_fwrelease;
    /*----Media Player variables----*/

    public static ArrayList<PlaylistSongs> songList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search2, container, false);

        //  ApiInterface apiInterface = ApiClient.getClient(BASE_URL).create(ApiInterface.class);

        searchFromAllResult = (EditText) view.findViewById(R.id.searchFromAllResult);
        iteList = (ListView) view.findViewById(R.id.categorylist);
        playDurationrelease = (TextView) view.findViewById(R.id.playDurationrelease);
        songDurationrelease = (TextView) view.findViewById(R.id.songDurationrelease);
        seekBarrelease = (SeekBar) view.findViewById(R.id.seekBarrelease);
        media_rewrelease = (ImageButton) view.findViewById(R.id.media_rewrelease);
        media_pauserelease = (ImageButton) view.findViewById(R.id.media_pauserelease);
        media_playrelease = (ImageButton) view.findViewById(R.id.media_playrelease);
        media_fwrelease = (ImageButton) view.findViewById(R.id.media_ffrelease);
        player_footer_bg = (LinearLayout) view.findViewById(R.id.player_footer_bg);


        setSongList();          //Set Songs List

        songList =  songListAdapter.songList;

        searchFromAllResult.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                songListAdapter.filter(stringVar);
            }
        });

        return view;
    }

    private void setSongList() {
        Log.i(TAG, "setSongList: ");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getAllSongs("0",new Callback<List<TopSongs>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<TopSongs> topSongs, Response response) {
                if ((topSongs.size()>0)) {
                    // The number of Columns
                    topSongsData = topSongs;
                    songListAdapter = new SongListAdapter(getActivity(),topSongs ,"","", new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            // Log.d("TAG", "clicked position:" + position);
                            Toast.makeText(getActivity(), "clicked position:", Toast.LENGTH_SHORT).show();
                            //    long postId = data.get(position).getID();
                            // do what ever you want to do with it
                        }
                    });
                    iteList.setAdapter(songListAdapter);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Data to show", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "failure: ");
                String errorV = "m";
                Toast.makeText(getActivity(), "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
