package com.worldplanet.users.worldplanet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.worldplanet.users.worldplanet.R;

public class LandingScreenActivity extends AppCompatActivity {
    private String CURRENT_APP_VERSION;
    private TextView loginTv, signUpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    //todo change this tym
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (getIntent().getStringExtra("BACK_PRESSED") != null) {
                        finish();
                    } else {
                        LandingScreenActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                             //   CheckForNewVersionUpdate();
                                Intent intent = new Intent(LandingScreenActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
            }
        };
        timerThread.start();

    }
}