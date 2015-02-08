package com.doodeec.weather.android.activity;

import android.os.Bundle;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.fragment.ForecastFragment;

public class ForecastActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ForecastFragment.newInstance(), ForecastFragment.FORECAST_FRG_TAG)
                    .commit();
        }
    }
}
