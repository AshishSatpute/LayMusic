package com.worldplanet.users.wpes.Adapter;

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
import com.worldplanet.users.wpes.MusicDetailsModel.NewRelease;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.PlayMusicActivity2;

import java.util.List;

public class NewReleaseRecyclerViewAdapter extends RecyclerView.Adapter<NewReleaseRecyclerViewAdapter.ViewHolder> {


    public static final String TAG = NewReleaseRecyclerViewAdapter.class.getCanonicalName();
    private transient Context context;
    private List<NewRelease> newReleases;

    public NewReleaseRecyclerViewAdapter(FragmentActivity context, List<NewRelease> newReleases) {
        Log.i(TAG, "NewReleaseRecyclerViewAdapter: ");
        this.context = context;
        this.newReleases= newReleases;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view  = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_layout, viewGroup, false);
            return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        if (newReleases.size() > 0) {
            viewHolder.textViewName.setText(newReleases.get(i).getSongName());
            Picasso.with(context)
                    .load(newReleases.get(i).getImage())
                    .placeholder(R.drawable.ic_album_black_24dp)
                    .error(R.drawable.ic_album_black_24dp)
                    .fit()
                    .into(viewHolder.iv_songsImage);

            viewHolder.ll_songs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayMusicActivity2.class);
                    intent.putExtra("Song_Name",newReleases.get(i).getSongName());
                    intent.putExtra("Song_Path",newReleases.get(i).getSongPath());
                    intent.putExtra("type","newReleases");
                    Log.i(TAG, "onClick: id"+newReleases.get(i).getRealiesId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newReleases.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView iv_songsImage;
        public LinearLayout ll_songs;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            iv_songsImage = itemView.findViewById(R.id.iv_songsImage);
            ll_songs = (LinearLayout) itemView.findViewById(R.id.ll_songs);

           /* iv_songsImage.buildDrawingCache();
            Bitmap bitmap= iv_songsImage.getDrawingCache();*/

        }
    }

}
