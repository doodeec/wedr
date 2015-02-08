package com.doodeec.weather.android.activity;

import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.activity.drawer.DrawerMenuItem;
import com.doodeec.weather.android.adapter.DrawerAdapter;
import com.doodeec.weather.android.dialog.AboutDialog;
import com.doodeec.weather.android.util.RecyclerViewItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Base drawer activity provides layout with drawer
 * and R.id.container FrameLayout for fragment to insert
 *
 * @author Dusan Bartos
 */
public abstract class BaseDrawerActivity extends ActionBarActivity implements RecyclerViewItemClickListener.OnItemClickListener {

    @InjectView(R.id.drawer_area)
    RelativeLayout mDrawerArea;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.drawer_menu)
    RecyclerView mDrawerMenu;

    protected DrawerAdapter mDrawerAdapter;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_enter_from_right, R.anim.anim_leave_to_left20);
        setContentView(R.layout.activity_base);

        ButterKnife.inject(this);

        if (mDrawerArea == null || mDrawerLayout == null || mDrawerMenu == null) {
            throw new AssertionError("Base drawer activity has invalid layout");
        }

        mDrawerAdapter = new DrawerAdapter(this, new DrawerMenuItem[]{
                new DrawerMenuItem(R.string.drawer_today, R.drawable.ic_drawer_today_dark),
                new DrawerMenuItem(R.string.drawer_forecast, R.drawable.ic_drawer_forecast_dark)
        });

        mDrawerMenu.setLayoutManager(new LinearLayoutManager(this));
        mDrawerMenu.setAdapter(mDrawerAdapter);
        mDrawerMenu.setHasFixedSize(true);
        mDrawerMenu.addOnItemTouchListener(new RecyclerViewItemClickListener(this, this));

        // setup actionbar home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // setup drawer toggle
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
    protected void onPause() {
        overridePendingTransition(R.anim.anim_enter_from_left20, R.anim.anim_leave_to_right);
        super.onPause();
    }

    @Override
    public void onItemClick(View view, int position) {
        hideDrawer();

        // open new activity
        switch (mDrawerAdapter.getItem(position).getTitleResource()) {
            case R.string.drawer_today:
                Intent todayIntent = new Intent(this, TodayActivity.class);
                startActivity(todayIntent);
                break;

            case R.string.drawer_forecast:
                Intent forecastIntent = new Intent(this, ForecastActivity.class);
                startActivity(forecastIntent);
                break;
        }
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
                if (mDrawerLayout.isDrawerOpen(mDrawerArea)) {
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
        if (mDrawerLayout.isDrawerOpen(mDrawerArea)) {
            mDrawerLayout.closeDrawers();
        }
    }

    private void showSettings() {
        SettingsActivity.showSettings(this);
    }
}
