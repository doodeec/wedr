package com.doodeec.weather.android.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.doodeec.scom.CancellableServerRequest;
import com.doodeec.scom.RequestError;
import com.doodeec.scom.listener.BaseRequestListener;
import com.doodeec.weather.android.R;
import com.doodeec.weather.android.client.APIService;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.fragment.TodayFragment;

public class TodayActivity extends BaseDrawerActivity implements TodayFragment.OnTodayInteractionListener {

    CancellableServerRequest mLoadWeatherRequest;
    TodayFragment mTodayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TodayFragment.newInstance(), TodayFragment.TODAY_FRG_TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        mTodayFragment = (TodayFragment) getSupportFragmentManager().findFragmentByTag(TodayFragment.TODAY_FRG_TAG);

        mLoadWeatherRequest = APIService.loadWeatherForLocation(48.9533934, 18.2092358, new BaseRequestListener<WeatherData>() {
            @Override
            public void onError(RequestError requestError) {
                Toast.makeText(TodayActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                mLoadWeatherRequest = null;
            }

            @Override
            public void onSuccess(WeatherData weatherData) {
                mTodayFragment.updateData(weatherData);
                mLoadWeatherRequest = null;
            }

            @Override
            public void onCancelled() {
                Toast.makeText(TodayActivity.this, "Data loading cancelled", Toast.LENGTH_SHORT).show();
                mLoadWeatherRequest = null;
            }

            @Override
            public void onProgress(Integer integer) {
            }
        });
    }

    @Override
    protected void onPause() {
        // stop loading request if activity is paused
        if (mLoadWeatherRequest != null) {
            mLoadWeatherRequest.cancel(true);
        }
        super.onPause();
    }
}
