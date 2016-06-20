package com.example.jegansbeast.fazt.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jegansbeast.fazt.R;
import com.mikepenz.materialdrawer.Drawer;

public class SettingsActivity extends AppCompatActivity {

    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }


    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
