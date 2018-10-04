package com.worldplanet.users.wpes.bottomnavigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.AlbumsActivity;
import com.worldplanet.users.wpes.activity.AllSongsActivity;
import com.worldplanet.users.wpes.activity.ArtistActivity;
import com.worldplanet.users.wpes.activity.CategoryActivity;
import com.worldplanet.users.wpes.activity.MoviesActivity;
import com.worldplanet.users.wpes.activity.PlaylistActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicFragment extends Fragment {

    public static final String TAG = MyMusicFragment.class.getCanonicalName();

TextView txt1,txt2,txt3,txt4,txt5,txt6;
    public MyMusicFragment() {
        Log.i(TAG, "MyMusicFragment: ");
    }

    public static Fragment getInstance() {
        Log.i(TAG, "getInstance: ");
        return new MyMusicFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        final View view = inflater.inflate(R.layout.fragment_my_music, container, false);

        txt1 = view.findViewById(R.id.text_song);
        txt2 = view.findViewById(R.id.text_playlist);
        txt3 = view.findViewById(R.id.text_albums);
        txt4 = view.findViewById(R.id.text_movie);
        txt5 = view.findViewById(R.id.text_category);
        txt6 = view.findViewById(R.id.text_artists);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllSongsActivity.class);
                intent.putExtra("from","");
                intent.putExtra("PlaylistName","");
                startActivity(intent);


            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "PlaylistActivity: ");
                Intent intent = new Intent(getActivity(), PlaylistActivity.class);
                startActivity(intent);

            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "AlbumsActivity: ");
                Intent intent = new Intent(getActivity(), AlbumsActivity.class);
                startActivity(intent);

            }
        });

        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "MoviesActivity: ");
                Intent intent = new Intent(getActivity(), MoviesActivity.class);
                startActivity(intent);

            }
        });

        txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "CategoryActivity: ");
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);

            }
        });

        txt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "ArtistActivity: ");
                Intent intent = new Intent(getActivity(), ArtistActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }


}
