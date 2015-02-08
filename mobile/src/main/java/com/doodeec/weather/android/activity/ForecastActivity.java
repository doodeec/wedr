package com.doodeec.weather.android.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.client.data.model.DailyForecast;
import com.doodeec.weather.android.fragment.ForecastFragment;

public class ForecastActivity extends BaseDrawerActivity implements ForecastFragment.OnForecastInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ForecastFragment.newInstance(), ForecastFragment.FORECAST_FRG_TAG)
                    .commit();
        }
    }

    @Override
    public void onDailyForecastClicked(DailyForecast dailyForecast) {
        Toast.makeText(this, "Daily forecast clicked", Toast.LENGTH_SHORT).show();
    }
}
