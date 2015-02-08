package com.doodeec.weather.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.fragment.SettingsFragment;

public class SettingsActivity extends ActionBarActivity {

    public static void showSettings(Context context) {
        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        context.startActivity(settingsIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_enter_from_top, R.anim.anim_leave_to_bottom20);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance(), SettingsFragment.SETTINGS_FRG_TAG)
                .commit();

        // setup action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.anim_enter_from_bottom20, R.anim.anim_leave_to_top);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}