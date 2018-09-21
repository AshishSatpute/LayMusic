package com.worldplanet.user.worldplanet.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldplanet.user.worldplanet.R;

public class CustomListAdapter2 extends RecyclerView.Adapter<CustomListAdapter2.ViewHolder> {
    public static final String TAG = CustomListAdapter2.class.getCanonicalName();
    private String[] fruitname;
    private String[] desc;
    private Integer[] imgid;
    private Activity context;


    public CustomListAdapter2(Activity context, String[] fruitname, String[] desc, Integer[] imgid) {
        Log.i(TAG, "CustomListAdapter2: ");
        // commentted
        // super(context, R.layout.listview_layout, fruitname);
        this.context = context;
        this.fruitname = fruitname;
        this.desc = desc;
        this.imgid = imgid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListAdapter2.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.ivw.setImageResource(imgid[position]);
        holder.tvwl1.setText(fruitname[position]);
        holder.tvwl2.setText(desc[position]);

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvwl1;
        TextView tvwl2;
        ImageView ivw;

        public ViewHolder(View itemView) {
            super(itemView);
            tvwl1 = itemView.findViewById(R.id.Song_Name);
            tvwl2 = itemView.findViewById(R.id.Song_details);
            ivw = itemView.findViewById(R.id.Song_image);
        }
    }
}
