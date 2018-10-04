package com.worldplanet.users.wpes.bottomnavigation;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.worldplanet.users.wpes.Adapter.DataModel;
import com.worldplanet.users.wpes.Adapter.NewReleaseRecyclerViewAdapter;
import com.worldplanet.users.wpes.Adapter.TopSongsRecyclerViewAdapter;
import com.worldplanet.users.wpes.Api.DbSong;
import com.worldplanet.users.wpes.Api.RESTClient;
import com.worldplanet.users.wpes.MusicDetailsModel.Categories;
import com.worldplanet.users.wpes.MusicDetailsModel.NewRelease;
import com.worldplanet.users.wpes.MusicDetailsModel.TopSongs;
import com.worldplanet.users.wpes.activity.CategoryDetailsActivity;
import com.worldplanet.users.wpes.activity.NewReleaseActivity;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.TopSongsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeFragment extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView, recyclerView1, recyclerView2, rv_category;
    private static ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;
    private List<DbSong> mRecipiesList;
    TextView tv_newRelease, tv_topSongs, tv_categoryBased, textView1, textView2, tv_category_seeAll;
    ProgressDialog progressDialog;

    public static final String TAG = HomeFragment.class.getSimpleName();

    public static Fragment getInstance() {
        Log.i(TAG, "getInstance: ");
        return new HomeFragment();
    }

    public HomeFragment() {
        Log.i(TAG, "HomeFragment: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: ");
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView1 = view.findViewById(R.id.recyclerview2);
        recyclerView2 = view.findViewById(R.id.recyclerview3);
        rv_category = view.findViewById(R.id.rv_category);
        tv_newRelease = view.findViewById(R.id.tv_newRelease);
        tv_topSongs = view.findViewById(R.id.tv_topSongs);
        tv_categoryBased = view.findViewById(R.id.tv_categoryBased);
        textView1 = view.findViewById(R.id.tv_newRelease_seeAll);
        textView2 = view.findViewById(R.id.tv_topSong_seeAll);
        tv_category_seeAll = view.findViewById(R.id.tv_category_seeAll);


        setNewReleaseSongs();
        TopSongs();
        setCategories();

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: seeAll new release");
                Intent intent = new Intent(getActivity(), NewReleaseActivity.class);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: see all top song ");
                Intent intent = new Intent(getActivity(), TopSongsActivity.class);
                startActivity(intent);

            }
        });

        tv_category_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: see all category");
                Intent intent = new Intent(getActivity(), CategoryDetailsActivity.class);
                startActivity(intent);

            }
        });

        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        recyclerView2.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        rv_category.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_category.setLayoutManager(layoutManager);
        rv_category.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    private void setNewReleaseSongs() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Log.i(TAG, "setNewReleaseSongs: ");
        RESTClient.getRestAdapter().getNewReleaseSongs("0", new Callback<List<NewRelease>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<NewRelease> newReleases, Response response) {
                if ((newReleases.size() > 0)) {
                    for (int i = 0; i < newReleases.size(); i++) { //6 ==> newReleases.size()
                        NewReleaseRecyclerViewAdapter customListView =
                                new NewReleaseRecyclerViewAdapter(getActivity(), newReleases);
                        recyclerView1.setAdapter(customListView);
                        tv_newRelease.setVisibility(View.VISIBLE);
                        textView1.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Data to show)", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "something went wrong (New Release)", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void TopSongs() {
        Log.i(TAG, "TopSongs: ");

        RESTClient.getRestAdapter().getTopSongs("0", new Callback<List<TopSongs>>() {
            @Override
            public void success(List<TopSongs> topSongs, Response response) {
                if ((topSongs.size() > 0)) {
                    Log.i(TAG, "success: ");
                    for (int i = 0; i < topSongs.size(); i++) {
                        TopSongsRecyclerViewAdapter customListView =
                                new TopSongsRecyclerViewAdapter(getActivity(), topSongs, "topSongs");
                        recyclerView2.setAdapter(customListView);
                        tv_topSongs.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Data to show)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorV = "m";
                Log.d("Error", "Erroooor");
                Toast.makeText(getActivity(), "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setCategories() {
        Log.i(TAG, "setCategories: chicked");
        RESTClient.getRestAdapter().getCategories("0", new Callback<List<Categories>>() {
            //RESTClient.getRestAdapter().getCategoryNews(vidarbhaCatId, new Callback<MainModel>() {
            @Override
            public void success(List<Categories> categories, Response response) {
                if ((categories.size() > 0)) {
                    TopSongsRecyclerViewAdapter customListView =
                            new TopSongsRecyclerViewAdapter(getActivity(), categories, "categories");
                    rv_category.setAdapter(customListView);
                    tv_categoryBased.setVisibility(View.VISIBLE);
                    tv_category_seeAll.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "No Data to show)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "failure: "+error);
                Toast.makeText(getActivity(), "something went wrong (Top songs)", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* private static class MyOnClickListener implements View.OnClickListener {

        private final HomeFragment context;
        private ImageView imageViewIcon;

        public MyOnClickListener(HomeFragment context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder;
            viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id[i];
                }
            }
            adapter.notifyItemRemoved(selectedItemPosition);

            int selectedItemPosition1 = recyclerView1.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder1;
            viewHolder1 = recyclerView1.findViewHolderForPosition(selectedItemPosition1);
            textViewName = (TextView) viewHolder1.itemView.findViewById(R.id.textViewName);
            String selectedName1 = (String) textViewName.getText();

            int selectedItemId1 = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName1.equals(MyData.nameArray[i])) {
                    selectedItemId1 = MyData.id[i];
                }
            }
            adapter.notifyItemRemoved(selectedItemPosition1);

            int selectedItemPosition2 = recyclerView2.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder2;
            viewHolder2 = recyclerView2.findViewHolderForPosition(selectedItemPosition2);
            textViewName = (TextView) viewHolder2.itemView.findViewById(R.id.textViewName);
            String selectedName2 = (String) textViewName.getText();

            int selectedItemId2 = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName2.equals(MyData.nameArray[i])) {
                    selectedItemId2 = MyData.id[i];
                }
            }
            adapter.notifyItemRemoved(selectedItemPosition2);


        }
    }*/

}


