package com.worldplanet.user.worldplanet.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.worldplanet.user.worldplanet.Adapter.AllbumsAdapter;
import com.worldplanet.user.worldplanet.Adapter.AllbumsAdapter2;
import com.worldplanet.user.worldplanet.Api.RESTClient;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.user.worldplanet.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AlbumsActivity extends AppCompatActivity {
    ListView listView;
    RecyclerView recyclerView;
    EditText editText;
    AllbumsAdapter albumsAdapter;
    AllbumsAdapter2 allbumsAdapter2;
    public static final String TAG=AlbumsActivity.class.getCanonicalName();

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.i(TAG, "onCreate: ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listView = (ListView) findViewById(R.id.albumslist);
        recyclerView =  findViewById(R.id.albumslist);
        editText = findViewById(R.id.search1);
        setAlbums();

        //listView.setTextFilterEnabled(true);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                allbumsAdapter2.filter(stringVar);
            }
        });

    }

    private void setAlbums() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(AlbumsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getAlbums("0",new Callback<List<Album>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<Album> albumList, Response response) {
                if ((albumList.size()>0)) {
                    allbumsAdapter2 = new AllbumsAdapter2(AlbumsActivity.this,albumList );
                  //  listView.setAdapter(albumsAdapter);
                    recyclerView.setAdapter(allbumsAdapter2);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(AlbumsActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorV = "m";
                Log.d("Error", "Erroooor");
                Toast.makeText(AlbumsActivity.this, "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
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