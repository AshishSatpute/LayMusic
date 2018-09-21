package com.worldplanet.user.worldplanet.Adapter;

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
import com.worldplanet.user.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.AlbumsActivity;
import com.worldplanet.user.worldplanet.activity.AlbumsDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllbumsAdapter2  extends RecyclerView.Adapter<AllbumsAdapter2.ViewHolder>{

    public static final String TAG=AllbumsAdapter2.class.getCanonicalName();

    private Dialog faredialog;
    private Context context;
    private List<Album> albums;
    private List<Album> filterAlbumList;        //Duplicate list for filtering
    private LayoutInflater inflater = null;

    public AllbumsAdapter2(AlbumsActivity context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
        this.filterAlbumList = new ArrayList<>();       //Initialise filter List
        this.filterAlbumList.addAll(albums);            //Add all items of array list to filter list
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.albums_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllbumsAdapter2.ViewHolder holder, int position) {
        holder.album_Name.setText(albums.get(position).getAlbumName());
        //  categoryId.setText(data.getBalance());


        /**/
        Picasso.with(context)
                .load(albums.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(holder.album_image);
        holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumsDetailsActivity.class);
                intent.putExtra("Album_Name",holder.album_Name.getText().toString());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: AllbumsAdapter2");
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView album_Name;
        ImageView album_image;
        LinearLayout ll_album_list;
        public ViewHolder(View view) {
            super(view);
            album_Name = view.findViewById(R.id.album_Name);
            album_image = view.findViewById(R.id.album_image);
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
            for (Album wp : filterAlbumList) {
                if (wp.getAlbumName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    albums.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
