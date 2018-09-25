package com.worldplanet.users.worldplanet.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.worldplanet.MusicDetailsModel.NewRelease;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.AndroidBuildingMusicPlayerActivity;
import com.worldplanet.users.worldplanet.activity.NewReleaseActivity;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity;

import java.util.List;

public class NewReleaseListAdapter extends BaseAdapter {
    private Dialog faredialog;
    private Context context;
    private List<NewRelease> newReleases;
    private LayoutInflater inflater = null;
    
    public static final String TAG = NewReleaseListAdapter.class.getCanonicalName();

    public NewReleaseListAdapter(NewReleaseActivity context, List<NewRelease> newReleases) {
        Log.i(TAG, "NewReleaseListAdapter: ");
        this.newReleases= newReleases;
        this.context = context;
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

@Override
public int getCount() {
    return newReleases.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Log.i(TAG, "getView: ");
        if(convertView==null){
            view = inflater.inflate(R.layout.newrelease_layout, null);
        }
        final TextView new_relase_Name= (TextView) view.findViewById(R.id.new_relase_Name);
        TextView new_relase_details= (TextView) view.findViewById(R.id.new_relase_details);
        ImageView new_relase_image= (ImageView) view.findViewById(R.id.new_relase_image);
        LinearLayout ll_song_name= (LinearLayout) view.findViewById(R.id.ll_song_name);

            new_relase_Name.setText(newReleases.get(position).getSongName());
            new_relase_details.setText(newReleases.get(position).getAlbumName());
            //  categoryId.setText(data.getBalance());

            Picasso.with(context)
                    .load(newReleases.get(position).getImage())
                    .placeholder(R.drawable.ic_album_black_24dp)
                    .error(R.drawable.ic_album_black_24dp)
                    .fit()
                    .into(new_relase_image);

   /*     ll_song_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                intent.putExtra("Song_Name",new_relase_Name.getText().toString());
                intent.putExtra("Song_Path",newReleases.get(position).getSongPath());
                context.startActivity(intent);
            }
        });*/

        return view;
    }
}
