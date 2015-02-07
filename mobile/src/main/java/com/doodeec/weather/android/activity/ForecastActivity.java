package com.doodeec.weather.android.activity;

import android.os.Bundle;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.fragment.ForecastFragment;

public class ForecastActivity extends BaseDrawerActivity implements ForecastFragment.OnForecastInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ForecastFragment.newInstance())
                    .commit();
        }
    }
}
