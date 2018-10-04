package com.worldplanet.users.wpes.Adapter;

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
import com.worldplanet.users.wpes.MusicDetailsModel.CategoryWise;
import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.activity.CategoryDetailsActivity;
import com.worldplanet.users.wpes.activity.PlayMusicActivity2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CategoryDetailsAdapter2 extends RecyclerView.Adapter<CategoryDetailsAdapter2.ViewHolder> {
    private Context context;
    private List<CategoryWise> categories;
    private List<CategoryWise> filterCategoriesList;
    private LayoutInflater inflater = null;
    public static final String TAG = CategoryDetailsAdapter2.class.getCanonicalName();

    public CategoryDetailsAdapter2(CategoryDetailsActivity context, List<CategoryWise> categories) {
        Log.i(TAG, "CategoryDetailsAdapter2: ");
        this.context = context;
        this.categories= categories;
        this.filterCategoriesList = new ArrayList<>();       //Initialise filter List
        this.filterCategoriesList.addAll(categories);
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void onBindViewHolder(@NonNull final CategoryDetailsAdapter2.ViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.alar_Name.setText(categories.get(position).getCategoryName());
        holder.alar_details.setText(categories.get(position).getSongName());

        Picasso.with(context)
                .load(categories.get(position).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(holder.alar_image);

        holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                Intent intent = new Intent(context, PlayMusicActivity2.class);
                intent.putExtra("Song_Name",categories.get(position).getSongName());
                intent.putExtra("Song_Path",categories.get(position).getSongPath());
                intent.putExtra("Song_Id",categories.get(position).getSongId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView alar_Name;
        TextView alar_details;
        ImageView alar_image;
        LinearLayout ll_album_list;

        public ViewHolder(View view) {
            super(view);
            alar_Name = view.findViewById(R.id.alar_Name);
            alar_details= view.findViewById(R.id.alar_details);
            alar_image= view.findViewById(R.id.alar_image);
            ll_album_list= view.findViewById(R.id.ll_album_list);
        }
    }



    public void filter(CharSequence stringVar) {
        categories.clear();
        if (stringVar.length() == 0) {
            categories.addAll(filterCategoriesList);
        } else {
            for (CategoryWise wp : filterCategoriesList) {
                if (wp.getSongName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    categories.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
