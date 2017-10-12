package com.homenas.netdrive;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public MenuItem viewMode;
    public Boolean viewGrid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        // Rotate back to 0 degree
                        fab.animate().rotation(0F).setInterpolator(new DecelerateInterpolator());
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        // Rotate 45 degree
                        fab.animate().rotation(45F).setInterpolator(new DecelerateInterpolator());
                    }
                });
                snackbar.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the first MenuItem title for Actionbar title
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(navigationView.getMenu().getItem(0).getTitle().toString());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        viewMode = menu.findItem(R.id.action_view);
        MenuIcon(this,viewMode,R.drawable.ic_view_list_black_24dp,android.R.color.white);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_view) {
            if(viewGrid) {
                MenuIcon(this, viewMode, R.drawable.ic_view_list_black_24dp, android.R.color.white);
            }else{
                MenuIcon(this, viewMode, R.drawable.ic_view_module_black_24dp, android.R.color.white);
            }
            viewGrid = !viewGrid;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // Get navigation item name and set it to actionbar title
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(item.getTitle().toString());
        }

        if (id == R.id.nav_local) {
            // Handle the camera action
        } else if (id == R.id.nav_sdcard) {

        } else if (id == R.id.nav_network) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void MenuIcon(Context context, MenuItem item, int icon, @ColorRes int color) {
        item.setIcon(icon);
        Drawable menuDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(menuDrawable);
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context,color));
    }
}
