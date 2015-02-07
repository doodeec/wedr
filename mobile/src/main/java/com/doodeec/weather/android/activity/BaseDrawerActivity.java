package com.doodeec.weather.android.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.activity.drawer.DrawerAdapter;
import com.doodeec.weather.android.activity.drawer.DrawerMenuItem;
import com.doodeec.weather.android.dialog.AboutDialog;
import com.doodeec.weather.android.util.RecyclerViewItemClickListener;

/**
 * Base drawer activity provides layout with drawer
 * and R.id.container FrameLayout for fragment to insert
 *
 * @author Dusan Bartos
 */
public abstract class BaseDrawerActivity extends ActionBarActivity implements RecyclerViewItemClickListener.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerMenu;
    protected DrawerAdapter mDrawerAdapter;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mDrawerAdapter = new DrawerAdapter(this, new DrawerMenuItem[]{
                new DrawerMenuItem(R.string.drawer_today, R.drawable.ic_drawer_today_dark),
                new DrawerMenuItem(R.string.drawer_forecast, R.drawable.ic_drawer_forecast_dark)
        });

        mDrawerMenu = (RecyclerView) findViewById(R.id.drawer_menu);
        mDrawerMenu.setLayoutManager(new LinearLayoutManager(this));
        mDrawerMenu.setAdapter(mDrawerAdapter);
        mDrawerMenu.setHasFixedSize(true);
        mDrawerMenu.addOnItemTouchListener(new RecyclerViewItemClickListener(this, this));

        // setup actionbar home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // setup drawer toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Item clicked " + position, Toast.LENGTH_SHORT).show();
        //TODO open activity
        hideDrawer();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // do nothing, drawer menu does not support long tap
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(mDrawerMenu)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
                return true;

            case R.id.action_settings:
                showSettings();
                return true;

            case R.id.action_about:
                AboutDialog.showDialog(getSupportFragmentManager());
                return true;

            default:
                hideDrawer();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideDrawer() {
        if (mDrawerLayout.isDrawerOpen(mDrawerMenu)) {
            mDrawerLayout.closeDrawers();
        }
    }

    private void showSettings() {
        SettingsActivity.showSettings(this);
    }
}
