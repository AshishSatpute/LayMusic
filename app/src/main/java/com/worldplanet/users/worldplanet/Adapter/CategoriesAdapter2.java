package com.worldplanet.users.worldplanet.Adapter;

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
import com.worldplanet.users.worldplanet.MusicDetailsModel.ArtistWise;
import com.worldplanet.users.worldplanet.MusicDetailsModel.Categories;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.CategoryActivity;
import com.worldplanet.users.worldplanet.activity.CategoryDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoriesAdapter2 extends RecyclerView.Adapter<CategoriesAdapter2.ViewHolder> {
    public static final String TAG=CategoriesAdapter2.class.getCanonicalName();
    private Dialog faredialog;
    private Context context;
    private List<Categories> categories;
    private List<Categories> filterCategorytList;
    private LayoutInflater inflater = null;
    public CategoriesAdapter2(CategoryActivity context, List<Categories> categories) {
        Log.i(TAG, "CategoriesAdapter: ");
        this.context = context;
        this.categories= categories;
        this.filterCategorytList = new ArrayList<>();       //Initialise filter List
        this.filterCategorytList.addAll(categories);            //Add all items of array list to filter list
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void onBindViewHolder(@NonNull final CategoriesAdapter2.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.album_Name.setText(categories.get(position).getCategoryName());
        //  categoryId.setText(data.getBalance());

        Picasso.with(context)
                .load(categories.get(position).getImage())
                .placeholder(R.drawable.aa)
                .error(R.drawable.aa)
                .fit()
                .into(holder.album_image);
        holder.ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CategoryDetailsActivity.class);
                intent.putExtra("Category_Name",holder.album_Name.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView album_Name;
        ImageView album_image;
        LinearLayout ll_album_list;
        public ViewHolder(View view) {
            super(view);
            album_Name= view.findViewById(R.id.album_Name);
            album_image= view.findViewById(R.id.album_image);
            ll_album_list= view.findViewById(R.id.ll_album_list);
        }
    }

    public void filter(CharSequence stringVar) {

        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        categories.clear();
        if (stringVar.length() == 0) {
            categories.addAll(filterCategorytList);
        } else {
            for (Categories wp : filterCategorytList) {
                if (wp.getCategoryName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    categories.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
