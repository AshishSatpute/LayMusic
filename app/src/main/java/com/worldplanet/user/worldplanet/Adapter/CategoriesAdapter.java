package com.worldplanet.user.worldplanet.Adapter;

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
import com.worldplanet.user.worldplanet.Api.DbSong;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Artist;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Categories;
import com.worldplanet.user.worldplanet.R;
import com.worldplanet.user.worldplanet.activity.AlbumsActivity;
import com.worldplanet.user.worldplanet.activity.AlbumsDetailsActivity;
import com.worldplanet.user.worldplanet.activity.CategoryActivity;
import com.worldplanet.user.worldplanet.activity.CategoryDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoriesAdapter extends BaseAdapter {
    private Dialog faredialog;
    private Context context;
    private List<Categories> categories;
    private List<Categories> filterCategorytList;
    private LayoutInflater inflater = null;
    public static final String TAG = CategoriesAdapter.class.getCanonicalName();
    public CategoriesAdapter(CategoryActivity context, List<Categories> categories) {
        Log.i(TAG, "CategoriesAdapter: ");
        this.context = context;
        this.categories= categories;
        this.filterCategorytList = new ArrayList<>();       //Initialise filter List
        this.filterCategorytList.addAll(categories);            //Add all items of array list to filter list
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        Log.i(TAG, "getCount:CategoriesAdapter ");
        return categories.size();
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
        Log.i(TAG, "getView: ");
        if(convertView==null){
            view = inflater.inflate(R.layout.albums_layout, null);
        }
        final TextView album_Name= (TextView) view.findViewById(R.id.album_Name);
        ImageView album_image= (ImageView) view.findViewById(R.id.album_image);
        LinearLayout ll_album_list= (LinearLayout) view.findViewById(R.id.ll_album_list);

        album_Name.setText(categories.get(i).getCategoryName());
        //  categoryId.setText(data.getBalance());

        Picasso.with(context)
                .load(categories.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(album_image);
        ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CategoryDetailsActivity.class);
                intent.putExtra("Category_Name",album_Name.getText().toString());
                context.startActivity(intent);
            }
        });

        return view;
    }

    //Filterclass to filter data

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

