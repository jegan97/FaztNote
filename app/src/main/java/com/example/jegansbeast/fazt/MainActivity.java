package com.example.jegansbeast.fazt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.settings.SettingsActivity;
import com.example.jegansbeast.fazt.subject.SubjectFragment;
import com.example.jegansbeast.fazt.timetable.TimeTableFragment;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity implements SubjectFragment.OnFragmentInteractionListener,TimeTableFragment.OnFragmentInteractionListener{
    Toolbar toolbar;
    Drawer drawer;
    Context context = this;
    SubjectItemMonitor monitor ;
    AccountHeader header;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CheckBox enable_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        if(monitor==null)
            monitor= new SubjectItemMonitor(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
            enable_notification = (CheckBox) collapsingToolbarLayout.findViewById(R.id.notify_subject);

        }


        toolbar.setTitleTextColor(Color.WHITE);


         header = new AccountHeaderBuilder().withActivity(this).withHeaderBackground(R.drawable.nav_image).build();


        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withFullscreen(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(drawerItem!=null) {
                    drawer.closeDrawer();
                    if (drawerItem.getIdentifier() == 1) {
                        setSubjectFragmentDetails();
                        SubjectFragment subjectFragment = new SubjectFragment();
                        transaction.replace(R.id.content_frame,subjectFragment);
                    } else if (drawerItem.getIdentifier() == 2) {
                        setTimeTableFragmentDetails();
                        TimeTableFragment timeTableFragment = new TimeTableFragment();
                        transaction.replace(R.id.content_frame,timeTableFragment);
                    }
                    else if(drawerItem.getIdentifier()==3){
                        Intent intent = new Intent(context, SettingsActivity.class);
                        startActivity(intent);
                    }
                }
                transaction.commit();
                return true;
            }
        })
                .withAccountHeader(header)
                .build();


        drawer.addItem(new PrimaryDrawerItem().withName("Subjects").withIcon(GoogleMaterial.Icon.gmd_library_books).withIconColor(Color.parseColor("#607D8B")).withIdentifier(1));
        drawer.addItem(new PrimaryDrawerItem().withName("TimeTable").withIcon(FontAwesome.Icon.faw_table).withIconColor(Color.parseColor("#607D8B")).withIdentifier(2));
        drawer.addItem(new PrimaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cogs).withIconColor(Color.parseColor("#607D8B")).withIdentifier(3));
        //
        setSubjectFragmentDetails();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new SubjectFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setSubjectFragmentDetails(){
        collapsingToolbarLayout.setTitle("Subjects");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }
        enable_notification.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(Color.parseColor("#009688"));
        collapsingToolbarLayout.setBackgroundColor(Color.parseColor("#009688"));
        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#009688"));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_bars).color(Color.WHITE).sizeDp(15));
        Object tag = header.getHeaderBackgroundView().getTag();

        if(tag!=null)
        {
            Integer id = (Integer) tag;
            if(id!=R.drawable.subject) {
                header.setBackgroundRes(R.drawable.subject);
                header.getHeaderBackgroundView().setTag(R.drawable.subject);
            }
        }
        else{
            header.setBackgroundRes(R.drawable.subject);
            header.getHeaderBackgroundView().setTag(R.drawable.subject);
        }
        drawer.getRecyclerView().setBackgroundColor(Color.WHITE);

    }

    public void setTimeTableFragmentDetails(){
        collapsingToolbarLayout.setTitle("TimeTable");
        toolbar.setTitle("TimeTable");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#7B1FA2"));

        }
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setBackgroundColor(Color.parseColor("#9C27B0"));
        collapsingToolbarLayout.setBackgroundColor(Color.parseColor("#9C27B0"));
        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#9C27B0"));

        enable_notification.setVisibility(View.INVISIBLE);
        toolbar.setNavigationIcon(new IconicsDrawable(context).icon(FontAwesome.Icon.faw_bars).color(Color.parseColor("#FFFFFF")).sizeDp(15));
        Object tag = header.getHeaderBackgroundView().getTag();

        if(tag!=null)
        {
            Integer id = (Integer) tag;
            if(id!=R.drawable.table) {
                header.setBackgroundRes(R.drawable.table);
                header.getHeaderBackgroundView().setTag(R.drawable.table);
            }
        }
        else{
            header.setBackgroundRes(R.drawable.table);
            header.getHeaderBackgroundView().setTag(R.drawable.table);
        }

    }

    @Override
    protected void onDestroy() {
        if(monitor!=null){
            monitor.closeDB();
        }
        super.onDestroy();
    }
}
