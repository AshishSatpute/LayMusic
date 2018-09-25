package com.worldplanet.users.worldplanet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldplanet.users.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.users.worldplanet.MusicDetailsModel.Categories;
import com.worldplanet.users.worldplanet.MusicDetailsModel.CategoryWise;
import com.worldplanet.users.worldplanet.R;
import com.worldplanet.users.worldplanet.activity.AlbumsDetailsActivity;
import com.worldplanet.users.worldplanet.activity.CategoryDetailsActivity;
import com.worldplanet.users.worldplanet.activity.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryDetailsAdapter extends BaseAdapter {
    private Dialog faredialog;
    private Context context;
    private List<CategoryWise> categories;
    private List<CategoryWise> filterCategoriesList;
    private LayoutInflater inflater = null;


    public CategoryDetailsAdapter(CategoryDetailsActivity context, List<CategoryWise> categories) {
        this.context = context;
        this.categories= categories;
        this.filterCategoriesList = new ArrayList<>();       //Initialise filter List
        this.filterCategoriesList.addAll(categories);
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
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
        if(convertView==null){
            view = inflater.inflate(R.layout.details_alar_layout, null);
        }
        final TextView alar_Name= (TextView) view.findViewById(R.id.alar_Name);
        final TextView alar_details= (TextView) view.findViewById(R.id.alar_details);
        ImageView alar_image= (ImageView) view.findViewById(R.id.alar_image);
        LinearLayout ll_album_list= (LinearLayout) view.findViewById(R.id.ll_album_list);

        alar_Name.setText(categories.get(i).getCategoryName());
        alar_details.setText(categories.get(i).getSongName());

        Picasso.with(context)
                .load(categories.get(i).getImage())
                .placeholder(R.drawable.ic_album_black_24dp)
                .error(R.drawable.ic_album_black_24dp)
                .fit()
                .into(alar_image);
        /*ll_album_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                intent.putExtra("Song_Name",alar_Name.getText().toString());
                intent.putExtra("Song_Path",categories.get(i).getSongPath());
                context.startActivity(intent);
            }
        });*/

        return view;
    }

    //Filterclass to filter data

    public void filter(CharSequence stringVar) {

        // Filter Class
        //    stringVar = stringVar.toLowerCase(Locale.getDefault());
        categories.clear();
        if (stringVar.length() == 0) {
            categories.addAll(filterCategoriesList);
        } else {
            for (CategoryWise wp : filterCategoriesList) {
                if (wp.getAlbumName().toLowerCase(Locale.getDefault()).contains(stringVar)) {
                    categories.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
