package com.worldplanet.users.worldplanet.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldplanet.users.worldplanet.R;

public class CustomListAdapter extends ArrayAdapter<String> {
    private String[] fruitname;
    private String[] desc;
    private Integer[] imgid;
    private Activity context;


    public CustomListAdapter(Activity context, String[] fruitname, String[] desc, Integer[] imgid) {
        super(context, R.layout.listview_layout, fruitname);
// TODO Auto-generated constructor stub
        this.context = context;
        this.fruitname = fruitname;
        this.desc = desc;
        this.imgid = imgid;

    }

        public View getView(int position, View convertView, ViewGroup parent) {
            View r=convertView;
            ViewHolder viewHolder=null;
            if(r==null)
            {
                LayoutInflater layoutInflater=context.getLayoutInflater();
                r=layoutInflater.inflate(R.layout.listview_layout,null,true);
                viewHolder= new ViewHolder(r);
                r.setTag(viewHolder);
            }
            else
            {
                viewHolder= (ViewHolder) r.getTag();
            }
            viewHolder.ivw.setImageResource(imgid[position]);
            viewHolder.tvwl1.setText(fruitname[position]);
            viewHolder.tvwl2.setText(desc[position]);

            return r;
        }

    static class ViewHolder {

        TextView tvwl1;
        TextView tvwl2;
        ImageView ivw;

        ViewHolder(View v) {
            tvwl1 = (TextView) v.findViewById(R.id.Song_Name);
            tvwl2 = (TextView) v.findViewById(R.id.Song_details);
            ivw = (ImageView) v.findViewById(R.id.Song_image);
        }


    }
}
