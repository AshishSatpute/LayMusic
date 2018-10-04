package com.worldplanet.users.wpes.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.worldplanet.users.wpes.R;
import com.worldplanet.users.wpes.bottomnavigation.HomeFragment;
import com.worldplanet.users.wpes.bottomnavigation.MyMusicFragment;
import com.worldplanet.users.wpes.bottomnavigation.SearchFragment;
import com.worldplanet.users.wpes.navigation.Aboutus;
import com.worldplanet.users.wpes.navigation.Contactus;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    BottomNavigationView navigation;
    public static final String TAG = MainActivity.class.getCanonicalName();
    private static final int REQ_GROUP_PERMISSION = 425;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");

       /* int Permission_All = 1;
        String[] Permissions = {
                Manifest.permission.MEDIA_CONTENT_CONTROL,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };


        if (!hasPermission(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }*/

        ArrayList<String> permissionsNeeded = new ArrayList<>();
        ArrayList<String> permissionsAvailable = new ArrayList<>();
        permissionsAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionsAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsAvailable.add(Manifest.permission.MEDIA_CONTENT_CONTROL);

        for (String permission : permissionsAvailable) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionsNeeded.add(permission);
        }

        requestGroupPermission(permissionsNeeded);




        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Linear, new HomeFragment());
        transaction.commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigations);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void requestGroupPermission(ArrayList<String> permissions) {
        String[] permissionList = new String[permissions.size()];
        permissions.toArray(permissionList);
        ActivityCompat.requestPermissions(MainActivity.this, permissionList, REQ_GROUP_PERMISSION);

    }


    ///

   /* public static boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

*/

    private HomeFragment crimeFragment0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.i(TAG, "onNavigationItemSelected: chick");
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    Log.i(TAG, "onNavigationItemSelected: navigation_home");
                    HomeFragment crimeFragment0 = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Linear, crimeFragment0).commit();
                    return true;
                }
                case R.id.navigation_search: {
                    Log.i(TAG, "onNavigationItemSelected: navigation_search");
                   // navigation.setVisibility(View.GONE);
                    SearchFragment searchFragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Linear, searchFragment).commit();
                    return true;
                }
                case R.id.navigation_mymusic: {
                    Log.i(TAG, "onNavigationItemSelected: navigation_mymusic");
                    MyMusicFragment crimeFragment1 = new MyMusicFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Linear, crimeFragment1).commit();
                    return true;
                }
                default: {
                    Log.i(TAG, "onNavigationItemSelected: default home");
                    crimeFragment0 = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Linear, crimeFragment0).commit();
                }
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
            return super.onOptionsItemSelected(item);

        } /*else if (id == R.id.nav_settings) {
            Intent home = new Intent(this, Nav_Settings.class);
            startActivity(home);
            return super.onOptionsItemSelected(item);


        }*/ else if (id == R.id.nav_share) {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "https://play.google.com/store/apps/details?id=com.wpes.user.laymusic";
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Choose sharing method"));
            return super.onOptionsItemSelected(item);

        } else if (id == R.id.nav_aboutus) {
            Intent home = new Intent(this, Aboutus.class);
            startActivity(home);
            return super.onOptionsItemSelected(item);

        } else if (id == R.id.nav_contactus) {
            Intent home = new Intent(this, Contactus.class);
            startActivity(home);
            return super.onOptionsItemSelected(item);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            startActivity(new Intent(this,MainActivity.class));
            Toast.makeText(getBaseContext(), R.string.exitmsg,
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }
}
