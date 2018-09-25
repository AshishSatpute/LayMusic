package com.worldplanet.users.worldplanet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.worldplanet.users.worldplanet.Adapter.CategoriesAdapter;
import com.worldplanet.users.worldplanet.Adapter.CategoriesAdapter2;
import com.worldplanet.users.worldplanet.Api.RESTClient;
import com.worldplanet.users.worldplanet.MusicDetailsModel.Categories;
import com.worldplanet.users.worldplanet.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CategoryActivity extends AppCompatActivity {
    ListView listView;
    RecyclerView recyclerView;
    EditText editText;
    CategoriesAdapter categoriesAdapter;
    CategoriesAdapter2 categoriesAdapter2;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    public static final String TAG = CategoryActivity.class.getCanonicalName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_albums);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.i(TAG, "onCreate: CategoryActivity ");
        //listView = (ListView) findViewById(R.id.albumslist);
        recyclerView =  findViewById(R.id.albumslist);
        editText = (EditText) findViewById(R.id.search1);

        setCategories();

        //listView.setTextFilterEnabled(true);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                categoriesAdapter2.filter(stringVar);
            }
        });

    }

    private void setCategories() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Log.i(TAG, "setCategories: ");
        progressDialog = new ProgressDialog(CategoryActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RESTClient.getRestAdapter().getCategories("0",new Callback<List<Categories>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<Categories> categories, Response response) {
                if ((categories.size()>0)) {
                    categoriesAdapter2 = new CategoriesAdapter2(CategoryActivity.this,categories );
                    //listView.setAdapter(categoriesAdapter);
                    recyclerView.setAdapter(categoriesAdapter2);
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(CategoryActivity.this, "No Data to show)", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                String errorV = "m";
                Log.d("Error", "Erroooor");
                Toast.makeText(CategoryActivity.this, "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
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
