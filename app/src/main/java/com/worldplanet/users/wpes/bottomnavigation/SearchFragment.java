package com.worldplanet.users.wpes.bottomnavigation;


import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.wpes.Adapter.CustomItemClickListener;
import com.worldplanet.users.wpes.Adapter.SongListAdapter2;
import com.worldplanet.users.wpes.Api.RESTClient;
import com.worldplanet.users.wpes.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.wpes.MusicDetailsModel.TopSongs;
import com.worldplanet.users.wpes.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final String TAG=SearchFragment.class.getCanonicalName();
    private ProgressDialog progressDialog;
    private SongListAdapter2 songListAdapter2;
    RecyclerView recyclerView;
    EditText searchFromAllResult;
    TextView playDurationrelease,songDurationrelease;
    SeekBar seekBarrelease;
    private LinearLayout player_footer_bg;
    String songPath;
    List<TopSongs> topSongs;
    private SeekBar seekbar;
    /*----Media Player variables----*/
    private MediaPlayer mediaPlayer;
    //Used to pause/resume MediaPlayer
    private int resumePosition;
    ImageButton media_pauserelease,media_playrelease,media_rewrelease,media_fwrelease;
    /*----Media Player variables----*/

    public static ArrayList<PlaylistSongs> songList = new ArrayList<>();


    public SearchFragment() {
        Log.i(TAG, "SearchFragment: ");
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

      //  ApiInterface apiInterface = ApiClient.getClient(BASE_URL).create(ApiInterface.class);

        searchFromAllResult = view.findViewById(R.id.searchFromAllResult);
        recyclerView = view.findViewById(R.id.categorylist);
        playDurationrelease = view.findViewById(R.id.playDurationrelease);
        songDurationrelease = view.findViewById(R.id.songDurationrelease);
        seekBarrelease = view.findViewById(R.id.seekBarrelease);
        media_rewrelease = view.findViewById(R.id.media_rewrelease);
        media_pauserelease = view.findViewById(R.id.media_pauserelease);
        media_playrelease = view.findViewById(R.id.media_playrelease);
        media_fwrelease = view.findViewById(R.id.media_ffrelease);
        player_footer_bg = view.findViewById(R.id.player_footer_bg);


        Log.i(TAG, "onCreateView: "+TAG);
        setSongList();

        songList =  songListAdapter2.songList;

        searchFromAllResult.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

              songListAdapter2.filter(stringVar);
            }
        });

        return view;
    }

    private void setSongList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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

                    songListAdapter2 = new SongListAdapter2(getActivity(),topSongs ,"","", new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            // Log.d("TAG", "clicked position:" + position);
                            Toast.makeText(getActivity(), "clicked position:", Toast.LENGTH_SHORT).show();
                            //    long postId = data.get(position).getID();
                            // do what ever you want to do with it
                        }
                    });
                    recyclerView.setAdapter(songListAdapter2);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Data to show", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "failure: ");
                Toast.makeText(getActivity(), "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
