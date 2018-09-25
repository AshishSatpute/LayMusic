package com.worldplanet.users.worldplanet.categories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.worldplanet.users.worldplanet.Adapter.CustomListAdapter;
import com.worldplanet.users.worldplanet.R;

public class Category extends AppCompatActivity{

    ListView lst;

    String[] fruit= {"Hold On"};
    String[] desc={"This is Origami"};
    Integer[] imgid={R.drawable.ic_album_black_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        lst=(ListView) findViewById(R.id.listview1);
        CustomListAdapter customListView=new CustomListAdapter(this,fruit, desc, imgid);
        lst.setAdapter(customListView);

    }


}

