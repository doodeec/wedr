package com.doodeec.weather.android.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.util.WedrPreferences;

/**
 * @author Dusan Bartos 
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String SETTINGS_FRG_TAG = "settingsFragment";

    private ListPreference lengthPreference;
    private ListPreference tempPreference;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.pref_general);

        lengthPreference = (ListPreference) findPreference(WedrPreferences.PREFERENCE_LENGTH_UNIT);
        tempPreference = (ListPreference) findPreference(WedrPreferences.PREFERENCE_TEMP_UNIT);
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
