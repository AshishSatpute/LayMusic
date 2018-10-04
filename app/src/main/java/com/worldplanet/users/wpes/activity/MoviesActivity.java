package com.worldplanet.users.wpes.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.worldplanet.users.wpes.Adapter.MoviesListAdapter;
import com.worldplanet.users.wpes.Adapter.MoviesListAdapter2;
import com.worldplanet.users.wpes.Api.RESTClient;
import com.worldplanet.users.wpes.MusicDetailsModel.Movies;
import com.worldplanet.users.wpes.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MoviesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListView listView;
    EditText editText;
    MoviesListAdapter moviesListAdapter;
    MoviesListAdapter2 moviesListAdapter2;
    private ProgressDialog progressDialog;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listView = (ListView) findViewById(R.id.albumslist);
        recyclerView = findViewById(R.id.albumslist);
        editText = (EditText) findViewById(R.id.search1);

        setAlbums();

        //listView.setTextFilterEnabled(true);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                moviesListAdapter2.filter(stringVar);
            }
        });

    }

    private void setAlbums() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(MoviesActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getMovieList("0", new Callback<List<Movies>>() {
            @Override
            public void success(List<Movies> movies, Response response) {
                if ((movies.size() > 0)) {

                    moviesListAdapter2 = new MoviesListAdapter2(MoviesActivity.this, movies);
                    //  listView.setAdapter(moviesListAdapter);
                    recyclerView.setAdapter(moviesListAdapter2);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MoviesActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MoviesActivity.this, "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
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
