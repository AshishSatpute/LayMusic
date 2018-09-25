package com.worldplanet.users.worldplanet.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.Toast;

import com.worldplanet.users.worldplanet.Adapter.ArtistAdapter;
import com.worldplanet.users.worldplanet.Adapter.ArtistAdapter2;
import com.worldplanet.users.worldplanet.Adapter.TopSongListAdapter;
import com.worldplanet.users.worldplanet.Api.DbSong;
import com.worldplanet.users.worldplanet.Api.HttpServiceClass;
import com.worldplanet.users.worldplanet.Api.RESTClient;
import com.worldplanet.users.worldplanet.MusicDetailsModel.Artist;
import com.worldplanet.users.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.users.worldplanet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListView listView;
    EditText editText;
    ArtistAdapter artistAdapter;
    ArtistAdapter2 artistAdapter2;
    private ProgressDialog progressDialog;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // listView = (ListView) findViewById(R.id.artistlist);
        recyclerView = findViewById(R.id.artistlist);
        editText = (EditText) findViewById(R.id.search2);

        setArtistSongs();
//        listView.setTextFilterEnabled(true);
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                artistAdapter2.filter(stringVar);


            }
        });
    }

    private void setArtistSongs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(ArtistActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getArtistDetails("0",new Callback<List<Artist>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<Artist> artistList, Response response) {
                if ((artistList.size()>0)) {

                    artistAdapter2 = new ArtistAdapter2(ArtistActivity.this,artistList );
                   // listView.setAdapter(artistAdapter2);
                   recyclerView.setAdapter(artistAdapter2);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(ArtistActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorV = "m";
                Log.d("Error", "Erroooor");
                Toast.makeText(ArtistActivity.this, "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
            }
        });
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

