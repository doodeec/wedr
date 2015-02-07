package com.doodeec.weather.android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.doodeec.weather.android.R;

public class SettingsActivity extends ActionBarActivity {
    
    public static void showSettings(Context context) {
        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        context.startActivity(settingsIntent);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();

        // setup action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        ListPreference lengthPreference;
        ListPreference tempPreference;

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_general);

            lengthPreference = (ListPreference) findPreference("length_unit");
            tempPreference = (ListPreference) findPreference("temperature_unit");
        }

        @Override
        public void onResume() {
            super.onResume();
            onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), null);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            lengthPreference.setSummary(lengthPreference.getEntry());
            tempPreference.setSummary(tempPreference.getEntry());
        }
    }
}