package com.worldplanet.users.wpes.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.wpes.MusicDetailsModel.AlbumWise;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.AlbumsDetailsActivity;
import com.worldplanet.users.wpes.activity.PlayMusicActivity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlbumDetailsAdapter2 extends RecyclerView.Adapter<AlbumDetailsAdapter2.ViewHolder> {

    private Dialog faredialog;
    private Context context;
    private List<AlbumWise> albums;
    private List<AlbumWise> filterAlbumList;
    private LayoutInflater inflater = null;
    public static final String TAG = AlbumDetailsAdapter2.class.getCanonicalName();


    public AlbumDetailsAdapter2(AlbumsDetailsActivity context, List<AlbumWise> albums) {
        Log.i(TAG, "AlbumDetailsAdapter2: ");
        this.context = context;
        this.albums = albums;
        this.filterAlbumList = new ArrayList<>();       //Initialise filter List
        this.filterAlbumList.addAll(albums);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.details_alar_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumDetailsAdapter2.ViewHolder holder, final int position) {

        holder.alar_Name.setText(albums.get(position).getAlbumName());
        holder.alar_details.setText(albums.get(position).getSongName());

        Picasso.with(context)
                .load(albums.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(holder.alar_image);
        holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMusicActivity2.class);
                intent.putExtra("Song_Name", holder.alar_Name.getText().toString());
                intent.putExtra("Song_Path", albums.get(position).getSongPath());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alar_Name;
        TextView alar_details;
        ImageView alar_image;
        LinearLayout ll_album_list;

        public ViewHolder(View view) {
            super(view);
            alar_Name = view.findViewById(R.id.alar_Name);
            alar_details = view.findViewById(R.id.alar_details);
            alar_image = view.findViewById(R.id.alar_image);
            ll_album_list = view.findViewById(R.id.ll_album_list);


        }

    }

    public void filter(CharSequence stringVar) {

        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        albums.clear();
        if (stringVar.length() == 0) {
            albums.addAll(filterAlbumList);
        } else {
            for (AlbumWise wp : filterAlbumList) {
                if (wp.getAlbumName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    albums.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
