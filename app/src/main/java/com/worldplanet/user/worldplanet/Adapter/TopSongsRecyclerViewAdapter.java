package com.worldplanet.user.worldplanet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Categories;
import com.worldplanet.user.worldplanet.MusicDetailsModel.NewRelease;
import com.worldplanet.user.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.CategoryDetailsActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity2;

import java.util.List;

public class TopSongsRecyclerViewAdapter extends RecyclerView.Adapter<TopSongsRecyclerViewAdapter.ViewHolder> {

    private transient Context context;
    private List<TopSongs> topSongs;
    private List<Categories> categories;
    String stringType;
    public static final String TAG=TopSongsRecyclerViewAdapter.class.getCanonicalName();

    public TopSongsRecyclerViewAdapter(FragmentActivity context, List<TopSongs> topSongs, String stringType) {
        Log.i(TAG, "TopSongsRecyclerViewAdapter: topSongs");
        this.context = context;
        this.topSongs = topSongs;
        this.stringType = stringType;
    }

    public TopSongsRecyclerViewAdapter(Context context, List<Categories> categories, String stringType) {
        Log.i(TAG, "TopSongsRecyclerViewAdapter: categories ");
        this.context = context;
        this.categories = categories;
        this.stringType = stringType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.i(TAG, "onCreateViewHolder: ");
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;


    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Log.i(TAG, "onBindViewHolder: ");
        if (stringType.equals("topSongs")) {
            if (topSongs.size() > 0) {

                viewHolder.textViewName.setText(topSongs.get(i).getSongName());

                Picasso.with(context)
                        .load(topSongs.get(i).getImage())
                        .placeholder(R.drawable.ic_album_black_24dp)
                        .error(R.drawable.ic_album_black_24dp)
                        .fit()
                        .into(viewHolder.iv_songsImage);

                viewHolder.ll_songs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, PlayMusicActivity2.class);
                        intent.putExtra("Song_Name", topSongs.get(i).getSongName());
                        intent.putExtra("Song_Path", topSongs.get(i).getSongPath());
                        context.startActivity(intent);
                    }
                });
            }
        }else {
            if (stringType.equals("categories")) {
                if (categories.size() > 0) {
                    Log.i(TAG, "onBindViewHolder: ");
                    viewHolder.textViewName.setText(categories.get(i).getCategoryName());
                    Picasso.with(context)
                            .load(categories.get(i).getImage())
                            .placeholder(R.drawable.ic_album_black_24dp)
                            .error(R.drawable.ic_album_black_24dp)
                            .fit()
                            .into(viewHolder.iv_songsImage);

                    viewHolder.ll_songs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                    Intent intent = new Intent(context,CategoryDetailsActivity.class);
                                    intent.putExtra("Category_Name",viewHolder.textViewName.getText().toString());
                                    context.startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {

        if (stringType.equals("topSongs")) {
            return topSongs.size();
        }else
            return categories.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView iv_songsImage;
        public LinearLayout ll_songs;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            iv_songsImage = itemView.findViewById(R.id.iv_songsImage);
            ll_songs = itemView.findViewById(R.id.ll_songs);

        }
    }

}
