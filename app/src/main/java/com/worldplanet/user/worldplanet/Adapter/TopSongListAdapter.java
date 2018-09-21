package com.worldplanet.user.worldplanet.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.user.worldplanet.MusicDetailsModel.TopSongs;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.PlayMusicActivity;
import com.worldplanet.user.worldplanet.activity.TopSongsActivity;

import java.util.List;

import retrofit.Callback;

public class TopSongListAdapter extends BaseAdapter {

    private Dialog faredialog;
    private Context context;
    private List<TopSongs> topSongs;
    private LayoutInflater inflater = null;

    public TopSongListAdapter(TopSongsActivity context, List<TopSongs> topSongs) {
        this.topSongs = topSongs;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return topSongs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.topsongs_layout, null);
        }
        final TextView new_relase_Name = (TextView) view.findViewById(R.id.new_relase_Name);
        TextView new_relase_details = (TextView) view.findViewById(R.id.new_relase_details);
        ImageView new_relase_image = (ImageView) view.findViewById(R.id.new_relase_image);
        LinearLayout ll_topSongs = (LinearLayout) view.findViewById(R.id.ll_topSongs);

        new_relase_Name.setText(topSongs.get(i).getSongName());
        new_relase_details.setText(topSongs.get(i).getAlbumName());
        //  categoryId.setText(data.getBalance());

        Picasso.with(context)
                .load(topSongs.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(new_relase_image);

       /* ll_topSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                intent.putExtra("Song_Name",top_songs_Name.getText().toString());
                intent.putExtra("Song_Path",topSongs.get(i).getSongPath());
                context.startActivity(intent);
            }
        });*/

        return view;
    }
}

