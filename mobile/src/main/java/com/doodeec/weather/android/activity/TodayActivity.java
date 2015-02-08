package com.doodeec.weather.android.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.doodeec.scom.CancellableServerRequest;
import com.doodeec.scom.RequestError;
import com.doodeec.scom.listener.BaseRequestListener;
import com.doodeec.weather.android.R;
import com.doodeec.weather.android.client.APIService;
import com.doodeec.weather.android.client.data.SessionData;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.fragment.TodayFragment;
import com.doodeec.weather.android.geoloc.LocationService;

public class TodayActivity extends BaseDrawerActivity implements LocationService.OnLocationRetrievedListener {

    private CancellableServerRequest mLoadWeatherRequest;
    private CancellableServerRequest mLoadIconRequest;
    private TodayFragment mTodayFragment;

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

        mTodayFragment.showProgress(true);
        //TODO load last data
        LocationService.requestLocation(this);
    }

    @Override
    public void onLocation(double latitude, double longitude) {
        mLoadWeatherRequest = APIService.loadWeatherForLocation(latitude, longitude,
                new BaseRequestListener<WeatherData>() {
                    @Override
                    public void onError(RequestError requestError) {
                        Toast.makeText(TodayActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                        mLoadWeatherRequest = null;
                    }

                    @Override
                    public void onSuccess(WeatherData weatherData) {
                        SessionData.getInstance().setWeatherData(weatherData);
                        if (mTodayFragment.isAdded()) {
                            mTodayFragment.updateData(weatherData);
                            mTodayFragment.showProgress(false);
                        }

                        mLoadWeatherRequest = null;
                        loadWeatherIcon(weatherData);
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
    public void onLocationError() {
        Toast.makeText(this, "Location unavailable", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        // stop loading request if activity is paused
        if (mLoadWeatherRequest != null) {
            mLoadWeatherRequest.cancel(true);
            mLoadWeatherRequest = null;
        }
        if (mLoadIconRequest != null) {
            mLoadIconRequest.cancel(true);
            mLoadIconRequest = null;
        }
        super.onPause();
    }

    /**
     * Loads weather icon asynchronously from server
     *
     * @param weatherData data
     */
    private void loadWeatherIcon(WeatherData weatherData) {
        if (weatherData != null && weatherData.getCondition() != null &&
                weatherData.getCondition().getIconURL() != null) {
            mLoadIconRequest = APIService.loadWeatherIcon(weatherData.getCondition().getIconURL(),
                    new BaseRequestListener<Bitmap>() {
                        @Override
                        public void onError(RequestError error) {
                            //TODO show this?
                            Toast.makeText(TodayActivity.this, "Error loading weather icon", Toast.LENGTH_SHORT).show();
                            mLoadIconRequest = null;
                        }

                        @Override
                        public void onSuccess(Bitmap icon) {
                            if (mTodayFragment.isAdded()) {
                                mTodayFragment.updateIcon(icon);
                            }
                            mLoadIconRequest = null;
                        }

                        @Override
                        public void onCancelled() {
                            mLoadIconRequest = null;
                        }

                        @Override
                        public void onProgress(Integer progress) {
                        }
                    });
        }
    }
}
